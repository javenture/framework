package net.javenture.framework.firebird;


import net.javenture.framework.annotation.ForeingThread;
import net.javenture.framework.annotation.MultiThread;
import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.annotation.OwnerThread;
import net.javenture.framework.exception.DatabaseException;
import net.javenture.framework.identifier.IntIdentifier;
import net.javenture.framework.log.ILog;
import net.javenture.framework.log.LogAttachment;
import net.javenture.framework.util.ByteArrayUtil;
import net.javenture.framework.util.Primitives;
import net.javenture.framework.util.TrafficCounter;
import net.javenture.framework.util.UtcTimeUtil;
import net.javenture.framework.util.Uuid;
import net.javenture.framework.util.Validator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;


/*
	2021/05/28
 */
@MultiThread
final public class DatabaseTransaction
	implements ILog, AutoCloseable
{
	//
	final private static AtomicLong GENERATOR_ID = new AtomicLong(0);

	// IDLE -> (READY <-> ACTIVE) -> DONE
	enum Stage
	{
		IDLE,
		READY,
		ACTIVE,
		DONE
	}

	public enum Type
	{
		//
		READ,
		WRITE,
		SERVICE;

		//
		boolean allow(SqlType type)
		{
			return
				this == READ && type == SqlType.QUERY
				||
				this == WRITE
				||
				this == SERVICE;
		}
	}


	//
	final private Type TYPE;
	final private DatabaseInstance INSTANCE;
	final private boolean POOL;
	final private long ID;
	final private Uuid UUID;
	final private long THREAD;
	final private AtomicReference<DatabaseConnector> CONNECTOR;
	final private AtomicReference<Stage> STAGE;
	final private AtomicBoolean FINALIZED;
	final private Semaphore SEMAPHORE;
	final private TrafficCounter WRITE;
	final private TrafficCounter READ;
	final private Metric METRIC;
	final private AtomicReference<JournalManager.Iteration> JOURNAL;

	private int level = 0;


	// список всех транзакций FIREBIRD (с учетом изменения номера при commit(true))
	// "select current_transaction from rdb$database";


	//
	@OwnerThread
	private DatabaseTransaction(Type type, @NullDisallow DatabaseInstance instance, boolean pool)
	{
		Validator.notNull(instance, "[instance]");

		TYPE = type;
		INSTANCE = instance;
		POOL = pool;
		ID = GENERATOR_ID.getAndIncrement();
		UUID = Uuid.generate();
		THREAD = Thread.currentThread().getId();
		CONNECTOR = new AtomicReference<>(null);
		STAGE = new AtomicReference<>(Stage.IDLE);
		FINALIZED = new AtomicBoolean(true);
		SEMAPHORE = new Semaphore(0, true);
		WRITE = new TrafficCounter();
		READ = new TrafficCounter();
		METRIC = new Metric();
		JOURNAL = new AtomicReference<>(null);

		//
		METRIC.begin = UtcTimeUtil.ns();
	}


	//
	@OwnerThread
	private void init()
		throws Exception
	{
		Connection connection = connector().connection();

		switch (TYPE)
		{
			case READ:
			{
				connection.setAutoCommit(false);
				connection.setReadOnly(true);

				break;
			}

			case WRITE:
			{
				connection.setAutoCommit(false);
				connection.setReadOnly(false);
				JOURNAL.set(new JournalManager.Iteration(this));

				break;
			}

			case SERVICE:
			{
				connection.setAutoCommit(false);
				connection.setReadOnly(false);

				break;
			}
		}

		//
		METRIC.init = UtcTimeUtil.ns();
	}


	public Type type()
	{
		return TYPE;
	}


	public DatabaseInstance instance()
	{
		return INSTANCE;
	}


	public boolean pool()
	{
		return POOL;
	}


	public long id()
	{
		return ID;
	}


	public Uuid uuid()
	{
		return UUID;
	}


	@OwnerThread
	private @NullDisallow DatabaseConnector connector()
	{
		return CONNECTOR.get();
	}


	@OwnerThread
	@ForeingThread
	Stage stage()
	{
		return STAGE.get();
	}


	@OwnerThread
	JournalManager.Iteration journal()
	{
		return JOURNAL.get();
	}


	@OwnerThread
	@ForeingThread
	void assign(@NullDisallow DatabaseConnector connector)
	{
		Validator.notNull(connector, "[connector]");
		Validator.state(CONNECTOR.compareAndSet(null, connector), "connector already assigned");
	}


	@ForeingThread
	DatabaseConnector recycle()
	{
		DatabaseConnector connector = CONNECTOR.getAndSet(null);
		Validator.state(connector != null, "[connector] is null");

		return connector;
	}


	@OwnerThread
	void ready()
	{
		Validator.notNull(CONNECTOR.get(), "[connector]");
		Validator.state(STAGE.compareAndSet(Stage.IDLE, Stage.READY), "[STAGE] (", STAGE.get(), ") is invalid");
	}


	@ForeingThread
	void done()
	{
		Validator.state(STAGE.compareAndSet(Stage.IDLE, Stage.DONE), "[STAGE] (", STAGE.get(), ") is invalid");
	}


	@OwnerThread
	private void acquire()
		throws InterruptedException
	{
		SEMAPHORE.acquire();
	}


	@ForeingThread
	void release()
	{
		SEMAPHORE.release();
	}


	@OwnerThread
	@ForeingThread
	public boolean error()
	{
		return CONNECTOR.get()
			.error();
	}


	@OwnerThread
	public void error(boolean condition)
	{
		CONNECTOR.get()
			.error(condition);
	}


	private boolean root()
	{
		return level == 0;
	}


	private void next()
	{
		level++;
	}


	private void previous()
	{
		level--;
	}


	@OwnerThread
	public ISqlResponse execute(ISqlTemplate template, IRowSqlProcessor preprocessor)
		throws Exception
	{
		return execute(template.type(), template.text(), false, null, template.senders(), template.receivers(), preprocessor);
	}


	@OwnerThread
	public ISqlResponse execute(ISqlTemplate template, SqlParameters parameters, IRowSqlProcessor preprocessor)
		throws Exception
	{
		return execute(template.type(), template.text(), false, parameters, template.senders(), template.receivers(), preprocessor);
	}


	@OwnerThread
	public ISqlResponse execute(ISqlRequest request, IRowSqlProcessor preprocessor)
		throws Exception
	{
		return execute(request.type(), request.text(), request.metadata(), null, null, null, preprocessor);
	}


	@OwnerThread
	public ISqlResponse execute(ISqlRequest request, SqlParameters parameters, IRowSqlProcessor preprocessor)
		throws Exception
	{
		return execute(request.type(), request.text(), request.metadata(), parameters, null, null, preprocessor);
	}


	@OwnerThread
	public ISqlResponse execute(ISqlTemplate template, IColumnSqlProcessor... preprocessors)
		throws Exception
	{
		return execute(template.type(), template.text(), false, null, template.senders(), template.receivers(), preprocessors);
	}


	@OwnerThread
	public ISqlResponse execute(ISqlTemplate template, SqlParameters parameters, IColumnSqlProcessor... preprocessors)
		throws Exception
	{
		return execute(template.type(), template.text(), false, parameters, template.senders(), template.receivers(), preprocessors);
	}


	@OwnerThread
	public ISqlResponse execute(ISqlRequest request, IColumnSqlProcessor... preprocessors)
		throws Exception
	{
		return execute(request.type(), request.text(), request.metadata(), null, null, null, preprocessors);
	}


	@OwnerThread
	public ISqlResponse execute(ISqlRequest request, SqlParameters parameters, IColumnSqlProcessor... preprocessors)
		throws Exception
	{
		return execute(request.type(), request.text(), request.metadata(), parameters, null, null, preprocessors);
	}


	@OwnerThread
	private ISqlResponse execute                                                       // XXX: timer
	(
		SqlType type,
		String text,
		boolean metadata,
		@NullAllow SqlParameters parameters,
		@NullAllow SqlSenders senders,
		@NullAllow SqlReceivers receivers,
		ISqlProcessor... preprocessors
	)
		throws Exception
	{
		if (!TYPE.allow(type)) throw new DatabaseException("[transaction.TYPE] (" + TYPE + ") disallow [type] (" + type + ")");

		if (error()) throw new DatabaseException("request ignored: transaction already has error");

		switch (stage())
		{
			case IDLE: throw new DatabaseException("[STAGE] (IDLE) is invalid");

			case READY:
			{
				try
				{
					STAGE.set(Stage.ACTIVE);
					// timer.start();                                                                                          // XXX: transaction.EXECUTION_TIMER

					return execute0(type, text, metadata, parameters, senders, receivers, preprocessors);
				}
				finally
				{
					// timer.stop();                                                                                           // XXX: transaction.EXECUTION_TIMER
					STAGE.set(Stage.READY);
				}
			}

			case ACTIVE: throw new DatabaseException("[STAGE] (ACTIVE) is invalid");
			case DONE: throw new DatabaseException("[STAGE] (DONE): transaction cancelled");
			default: throw new DatabaseException();
		}
	}


	private ISqlResponse execute0
	(
		SqlType type,
		String text,
		boolean metadata,
		@NullAllow SqlParameters parameters,
		@NullAllow SqlSenders senders,
		@NullAllow SqlReceivers receivers,
		ISqlProcessor... preprocessors
	)
		throws Exception
	{
		ISqlResponse result;
		FINALIZED.set(false);
		DatabaseConnector connector = connector();
		Connection connection = connector.connection();

		try (PreparedStatement statement = connection.prepareStatement(text))                        // XXX: cache PreparedStatement for connection with ASqlTemplate ID
		{
			// statement.clearParameters();                                                                                 // XXX: ?


			WRITE.update(Primitives.CHAR.DIMENSION * text.length());

			// parameters
			if (parameters != null && parameters.exists())
			{
				Object[] values0 = parameters.first();
				int length = values0.length;

				if (length != 0)
				{
					// senders
					if (senders != null) Validator.condition(length == senders.count(), "[parameter.length] (", length, ") != [senders.count()] (", senders.count(), ")");
					else senders = new SqlSenders(values0);

					switch (type)
					{
						case QUERY:
						case UPDATE:
						{
							senders.send(statement, values0, WRITE);

							break;
						}

						case BATCH:
						{
							// XXX:
							// http://tracker.firebirdsql.org/browse/JDBC-377
							// http://tracker.firebirdsql.org/browse/CORE-820
							// http://tracker.firebirdsql.org/browse/CORE-3821

							for (Object[] values : parameters)
							{
								senders.send(statement, values, WRITE);
								statement.addBatch();
							}

							break;
						}
					}
				}
			}

			// execution
			switch (type)
			{
				case QUERY:
				{
					try (ResultSet set = statement.executeQuery())
					{
						ResultSetMetaData data = set.getMetaData();
						int columns = data.getColumnCount();

						// metadata
						DatabaseType[] types = null;
						String[] labels = null;

						if (metadata)
						{
							// types
							types = new DatabaseType[columns];

							for (int i = 0; i < columns; i++) types[i] = DatabaseType.relation(data.getColumnType(i + 1));

							// labels
							labels = new String[columns];

							for (int i = 0; i < columns; i++) labels[i] = data.getColumnLabel(i + 1);
						}

						// receivers
						if (receivers != null) Validator.condition(columns == receivers.count(), "[ResultSetMetaData.getColumnCount()] (", columns, ") != [receivers.count()] (", receivers.count(), ")");
						else receivers = new SqlReceivers(data);

						// processors
						if (preprocessors.length != 0)
						{
							if (preprocessors.length == 1 && preprocessors[0] instanceof IRowSqlProcessor)
							{
								IDataset dataset = receivers.receive(set, types, labels, (IRowSqlProcessor) preprocessors[0], READ);
								result = new QuerySqlResponse(dataset);
							}
							else
							{
								Validator.condition(columns == preprocessors.length, "[ResultSetMetaData.getColumnCount()] (", columns, ") != [preprocessors.length] (", preprocessors.length, ")");
								IDataset dataset = receivers.receive(set, types, labels, (IColumnSqlProcessor[]) preprocessors, READ);
								result = new QuerySqlResponse(dataset);
							}
						}
						else
						{
							result = new QuerySqlResponse(receivers.receive(set, types, labels, READ));
						}
					}

					break;
				}

				case UPDATE:
				{
					int count = statement.executeUpdate();
					result = new UpdateSqlResponse(count);

					break;
				}

				case BATCH:
				{
					int[] counts = statement.executeBatch();
					result = new BatchSqlResponse(counts);

					break;
				}

				default: throw new IllegalArgumentException("[type] (" + type + ") not supported");
			}
		}
		catch (Exception e)
		{
			error(true);

			throw e;
		}

		return result;
	}


	@OwnerThread
	public void commit()
		throws Exception
	{
		if (root())
		{
			if (TYPE == Type.WRITE) JOURNAL.get().complete(this);

			commit0();
		}
	}


	@OwnerThread
	public void commit(boolean forced)
		throws Exception
	{
		if (forced)
		{
			if (TYPE == Type.WRITE) JournalManager.write(this, JournalOperation.TRANSACTION_COMMIT, IntIdentifier.UNKNOWN, ByteArrayUtil.BLANK, null);

			commit0();
		}
	}


	void commit0()
		throws SQLException
	{
		connector().connection()
			.commit();

		FINALIZED.set(true);
	}


	@OwnerThread
	public void rollback()
		throws Exception
	{
		error(true);

		if (root()) rollback0();
	}


	private void rollback0()
		throws SQLException
	{
		connector().connection()
			.rollback();

		FINALIZED.set(true);
	}


	@Override
	@OwnerThread
	public void close()
		throws Exception                                                                   // TEST
	{
		// XXX: check thread

		if (root())
		{
			// XXX: if (count == 0)


			try
			{
				if (!FINALIZED.get())
				{
					rollback0();

					throw new DatabaseException("transaction NOT finalized");
				}


				// DEL
				//System.out.println("transaction end: " + ID + "." + NUMBER + "; " + TYPE);
			}
			finally
			{
				DatabaseTransaction transaction = DatabaseTransactionThreadLocal.get();

				if (transaction == this) DatabaseTransactionThreadLocal.set(null);

				//
				STAGE.set(Stage.DONE);
				METRIC.end = UtcTimeUtil.ns();
				INSTANCE.MANAGER.next();
			}
		}
		else
		{
			previous();
		}
	}


	@Override
	public LogAttachment log()
	{
		LogAttachment result = new LogAttachment();
		result.add(ID, "ID");
		result.add(THREAD, "THREAD");
		result.add(STAGE, "STAGE");
		result.add(CONNECTOR, "CONNECTOR");
		result.add(FINALIZED, "FINALIZED");
		result.add(level, "level");

		return result;
	}


	//
	public static DatabaseTransaction read()
		throws Exception
	{
		return read(false);
	}


	private static DatabaseTransaction read(boolean forced)                                 // XXX: public                        // XXX: boolean local - use ThreadLocal ?
		throws Exception
	{
		{
			DatabaseTransaction transaction = DatabaseTransactionThreadLocal.get();

			if (transaction != null)
			{
				transaction.next();

				return transaction;
			}
		}

		{
			DatabaseConnector connector = DatabaseConnectorThreadLocal.get();

			if (connector != null)
			{
				DatabaseInstance instance = connector.instance();

				DatabaseTransaction transaction = new DatabaseTransaction(Type.READ, instance, false); // ! 1
				transaction.assign(connector);
				transaction.ready();
				transaction.init();

				instance.queue(transaction); // ! 2

				DatabaseTransactionThreadLocal.set(transaction);                                                             // XXX: forced ???

				return transaction;
			}
		}

		{
			DatabaseInstance instance = Validator.ifNotNull(DatabaseInstance.Default.get(), DatabaseInstance.Local.get());
			Validator.notNull(instance, "[instance]");
			Validator.state(instance.available(), "[instance] (", instance, ") not available");

			DatabaseTransaction transaction = new DatabaseTransaction(Type.READ, instance, true); // ! 1

			instance.queue(transaction); // ! 2

			transaction.acquire(); // ! 3
			transaction.init();


			// DEL
			//System.out.println("transaction begin: " + transaction.ID + "." + transaction.NUMBER + "; " + transaction.TYPE);



			DatabaseTransactionThreadLocal.set(transaction);                                                                  // XXX: forced ???

			return transaction;
		}

		//throw new IllegalStateException("transaction not available");
	}


	public static DatabaseTransaction write()
		throws Exception
	{
		return write(false);
	}


	private static DatabaseTransaction write(boolean forced)                               // XXX: public                     // XXX: ?
		throws Exception
	{
		{
			DatabaseTransaction transaction = DatabaseTransactionThreadLocal.get();

			if (transaction != null)
			{
				if (transaction.type() == Type.WRITE || transaction.type() == Type.SERVICE)
				{
					transaction.next();

					return transaction;
				}
				else
				{
					transaction.error(true);

					throw new Exception("illegal nesting: WRITE inside READ transaction");
				}
			}
		}

		{
			DatabaseConnector connector = DatabaseConnectorThreadLocal.get();

			if (connector != null)
			{
				DatabaseInstance instance = connector.instance();

				DatabaseTransaction transaction = new DatabaseTransaction(Type.WRITE, instance, false); // ! 1
				transaction.assign(connector);
				transaction.ready();
				transaction.init();

				instance.queue(transaction); // ! 2

				DatabaseTransactionThreadLocal.set(transaction);                                                             // XXX: forced ???

				return transaction;
			}
		}

		{
			DatabaseInstance instance = Validator.ifNotNull(DatabaseInstance.Default.get(), DatabaseInstance.Local.get());
			Validator.notNull(instance, "[instance]");
			Validator.state(instance.available(), "[instance] (", instance, ") not available");

			DatabaseTransaction transaction = new DatabaseTransaction(Type.WRITE, instance, true); // ! 1

			instance.queue(transaction); // ! 2

			transaction.acquire(); // ! 3
			transaction.init();


			// DEL
			//System.out.println("transaction begin: " + transaction.ID + "." + transaction.NUMBER + "; " + transaction.TYPE);



			DatabaseTransactionThreadLocal.set(transaction);                                                                  // XXX: forced ???

			return transaction;
		}

		//throw new IllegalStateException("transaction not available");
	}


	public static DatabaseTransaction service()
		throws Exception
	{
		return service(false);
	}


	private static DatabaseTransaction service(boolean forced)                               // XXX: public                     // XXX: ?
		throws Exception
	{
		{
			DatabaseTransaction transaction = DatabaseTransactionThreadLocal.get();

			if (transaction != null)
			{
				if (transaction.type() == Type.SERVICE)
				{
					transaction.next();

					return transaction;
				}
				else
				{
					transaction.error(true);

					throw new Exception("illegal nesting: SERVICE inside READ or WRITE transaction");
				}
			}
		}

		{
			DatabaseConnector connector = DatabaseConnectorThreadLocal.get();

			if (connector != null)
			{
				DatabaseInstance instance = connector.instance();

				DatabaseTransaction transaction = new DatabaseTransaction(Type.SERVICE, instance, false); // ! 1
				transaction.assign(connector);
				transaction.ready();
				transaction.init();

				instance.queue(transaction); // ! 2

				DatabaseTransactionThreadLocal.set(transaction);                                                             // XXX: forced ???

				return transaction;
			}
		}

		{
			DatabaseInstance instance = Validator.ifNotNull(DatabaseInstance.Default.get(), DatabaseInstance.Local.get());
			Validator.notNull(instance, "[instance]");
			Validator.state(instance.available(), "[instance] (", instance, ") not available");

			DatabaseTransaction transaction = new DatabaseTransaction(Type.SERVICE, instance, true); // ! 1

			instance.queue(transaction); // ! 2

			transaction.acquire(); // ! 3
			transaction.init();


			// DEL
			//System.out.println("transaction begin: " + transaction.ID + "." + transaction.NUMBER + "; " + transaction.TYPE);



			DatabaseTransactionThreadLocal.set(transaction);                                                                  // XXX: forced ???

			return transaction;
		}

		//throw new IllegalStateException("transaction not available");
	}


	//
	final static class Metric
	{
		//
		private long begin;
		private long init;
		private long end;

		//
		private Metric()
		{
			begin = -1;
			init = -1;
			end = -1;


			// XXX: total SQL runtime ?
		}
	}

}
