package net.javenture.framework.firebird;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.log.Log;
import net.javenture.framework.util.UtcTimeUtil;
import net.javenture.framework.util.Validator;

import org.firebirdsql.ds.FBSimpleDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;


/*
	2019/08/22
 */
final public class DatabaseConnector
{
	// XXX: read/write stat ?


	//
	final private static Log LOG = Log.instance(DatabaseConnector.class);
	final private static long LIFETIME = 30 * 60 * 1000; // 30 min                                       // XXX: 30 +- 10%
	final private static AtomicInteger GENERATOR = new AtomicInteger(0);


	private enum State
	{
		UNKNOWN,
		IDLE,
		LOCK
	}


	//
	final private DatabaseInstance INSTANCE;
	final private AtomicReference<Connection> CONNECTION;
	final private int ID;
	final private AtomicBoolean ACTIVITY;
	final private AtomicReference<State> STATE;
	final private AtomicBoolean ERROR;
	final private AtomicLong DATE;


	//
	private DatabaseConnector(@NullDisallow DatabaseInstance instance)                             // XXX: long lifetime
	{

		INSTANCE = instance;
		CONNECTION = new AtomicReference<>(null);
		ID = GENERATOR.getAndIncrement();
		ACTIVITY = new AtomicBoolean(true);
		STATE = new AtomicReference<>(State.UNKNOWN);
		ERROR = new AtomicBoolean(false);
		DATE = new AtomicLong(UtcTimeUtil.ms());
	}


	//
	@NullDisallow DatabaseInstance instance()
	{
		return INSTANCE;
	}


	@NullAllow Connection connection()
	{
		return CONNECTION.get();
	}


	private void connection(@NullDisallow Connection connection)
	{
		Validator.notNull(connection, "[connection]");
		Validator.state(CONNECTION.compareAndSet(null, connection), "connection already assigned");
	}


	int id()
	{
		return ID;
	}


	boolean activity()
	{
		return ACTIVITY.get();
	}


	void enable()
	{
		ACTIVITY.set(true);
	}


	void disable()
	{
		ACTIVITY.set(false);
	}


	void idle()
	{
		if (!STATE.compareAndSet(State.UNKNOWN, State.IDLE)) throw new IllegalStateException();

		DATE.set(UtcTimeUtil.ms());
	}


	boolean lock()
	{
		return STATE.compareAndSet(State.IDLE, State.LOCK);

		// XXX: date ?
	}


	void unlock()                                                                                                 // XXX: release ?
	{
		if (!STATE.compareAndSet(State.LOCK, State.UNKNOWN)) throw new IllegalStateException();

		// XXX: date ?
	}


	boolean error()
	{
		return ERROR.get();
	}


	void error(boolean condition)
	{
		if (condition) ERROR.set(true);
	}


	boolean expired()
	{
		return
			STATE.get() == State.IDLE
			&&
			UtcTimeUtil.ms() - DATE.get() > LIFETIME;
	}


	private Connection clear()
	{
		return CONNECTION.getAndSet(null);
	}


	//
	public static DatabaseConnector open(DatabaseInstance instance)                                                                  // XXX: pool
	{
		/*
			https://github.com/FirebirdSQL/jaybird/wiki/Connection-properties
			https://stackoverflow.com/questions/42945173/jaybird-3-and-firebird-transaction-information
		 */

		try
		{
			// DEL
			long begin = UtcTimeUtil.ms();


			DatabaseProperties properties = instance.properties();

			FBSimpleDataSource source = new FBSimpleDataSource();                                              // XXX: final static ?
			source.setDatabase(properties.URL);
			source.setUserName(properties.USER);
			source.setPassword(properties.PASSWORD);
			source.setCharSet(properties.CHARSET);
			//source.setDefaultTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);                 // XXX: ?
			// source.setConnectTimeout();                   // XXX: ?

			DatabaseConnector connector = new DatabaseConnector(instance);
			connector.connection(source.getConnection());


			// DEL
			long end = UtcTimeUtil.ms();
			System.out.println("connection: " + (end - begin) + "ms");


			return connector;



			/*
			Properties properties = new Properties();
			properties.put("user", user);
			properties.put("password", password);
			properties.put("lc_ctype", charset);

			return DriverManager.getConnection(PROTOCOL + url, properties);
			*/
		}
		catch (SQLException e)
		{
			LOG.error(e);

			return null;
		}
	}


	public static void close(DatabaseConnector connector)
	{
		try
		{
			connector.clear()
				.close();
		}
		catch (SQLException e)
		{
			LOG.error(e);
		}
	}


}
