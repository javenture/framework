package net.javenture.framework.firebird;


import net.javenture.framework.annotation.MultiThread;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.log.Log;
import net.javenture.framework.util.IContainer;
import net.javenture.framework.util.StringFragmenter;
import net.javenture.framework.util.Validator;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;


/*
	2019/12/23
 */
@MultiThread
abstract public class ASqlTemplate
	implements ISqlTemplate
{
	// XXX: timers: total, wait, active, count

	//
	final private static AtomicInteger GENERATOR_ID = new AtomicInteger(0);


	//
	final private int ID;
	final private @NullDisallow SqlType TYPE;
	final private @NullDisallow String TEXT;
	final private @NullDisallow SqlSenders SENDERS;
	final private @NullDisallow SqlReceivers RECEIVERS;


	//
	protected ASqlTemplate(SqlType type)
	{
		StringFragmenter fragmenter = new StringFragmenter(SqlMarkup.DELIMITER);
		ArrayList<SqlSenders.Entry> senders = new ArrayList<>(); // ! ArrayList
		ArrayList<SqlReceivers.Entry> receivers = new ArrayList<>(); // ! ArrayList

		//
		SqlMarkup markup = _markup();

		for (Object object : markup)
		{
			if (object instanceof DatabaseTable)
			{
				DatabaseTable table = (DatabaseTable) object;
				fragmenter.add(table.NAME);
			}
			else if (object instanceof ADatabaseField)
			{
				ADatabaseField field = (ADatabaseField) object;
				fragmenter.add(field.ALIAS);
			}
			else if (object instanceof SqlSenders.Entry)
			{
				SqlSenders.Entry sender = (SqlSenders.Entry) object;
				int size = senders.size();
				Validator.argument(sender.NUMBER == size, "[sender.NUMBER] (", sender.NUMBER, ") != ", size);
				senders.add(sender);
				fragmenter.add(sender.CONTENT);
			}
			else if (object instanceof SqlReceivers.Entry)
			{
				SqlReceivers.Entry receiver = (SqlReceivers.Entry) object;
				int size = receivers.size();
				Validator.argument(receiver.NUMBER == size, "[receiver.NUMBER] (", receiver.NUMBER, ") != ", size);
				receivers.add(receiver);
				fragmenter.add(receiver.CONTENT);
			}
			else
			{
				fragmenter.add(object);
			}
		}

		//
		ID = GENERATOR_ID.getAndIncrement();
		TYPE = type;
		TEXT = fragmenter.toString();
		SENDERS = !senders.isEmpty() ? new SqlSenders(senders) : SqlSenders.BLANK;;
		RECEIVERS = !receivers.isEmpty() ? new SqlReceivers(receivers) : SqlReceivers.BLANK;
	}


	//
	abstract protected SqlMarkup _markup(); // ! _markup


	//
	@Override
	final public int id()
	{
		return ID;
	}


	@Override
	final public SqlType type()
	{
		return TYPE;
	}


	@Override
	final public String text()
	{
		return TEXT;
	}


	@Override
	final public SqlSenders senders()
	{
		return SENDERS;
	}


	@Override
	final public SqlReceivers receivers()
	{
		return RECEIVERS;
	}


	@Override
	final public String toString()
	{
		return TEXT;
	}


	//
	abstract public static class AQuery
		extends ASqlTemplate
	{
		//
		protected AQuery()
		{
			super(SqlType.QUERY);
		}
	}


	abstract public static class AUpdate
		extends ASqlTemplate
	{
		//
		protected AUpdate()
		{
			super(SqlType.UPDATE);
		}
	}


	abstract public static class ABatch
		extends ASqlTemplate
	{
		//
		protected ABatch()
		{
			super(SqlType.BATCH);
		}
	}


	abstract public static class AObjectQuery<T>
		extends AQuery
	{
		//
		final protected static Log LOG = Log.instance(AObjectQuery.class);

		//
		abstract protected T init();

		//
		@SuppressWarnings("unchecked")
		public T execute(Object... values) // ! not final
		{
			T result;

			try (DatabaseTransaction transaction = DatabaseTransaction.read())
			{
				SqlParameters parameters = SqlParameters.prepare(values);
				ISqlResponse response = transaction.execute(this, parameters);
				IDataset dataset = response.dataset();
				result = dataset.first() ? (T) dataset.get(0) : init();

				//
				transaction.commit();
			}
			catch (Exception e)
			{
				LOG.error(e);
				result = init();
			}

			return result;
		}

		@SuppressWarnings("unchecked")
		public T execute(DatabaseTransaction transaction, Object... values) // ! not final
			throws Exception
		{
			SqlParameters parameters = SqlParameters.prepare(values);
			ISqlResponse response = transaction.execute(this, parameters);
			IDataset dataset = response.dataset();

			return dataset.first()
				? (T) dataset.get(0)
				: init();
		}
	}


	abstract public static class AContainerQuery<C extends IContainer>
		extends AQuery
	{
		//
		final protected static Log LOG = Log.instance(AContainerQuery.class);

		//
		abstract protected C container();

		//
		@SuppressWarnings("unchecked")
		public C execute(Object... values) // ! not final
		{
			C result;

			try (DatabaseTransaction transaction = DatabaseTransaction.read())
			{
				result = container();
				transaction.execute(this, SqlParameters.prepare(values), IColumnSqlProcessor.fill(result));

				//
				transaction.commit();
			}
			catch (Exception e)
			{
				LOG.error(e);
				result = container();
			}

			return result;
		}

		@SuppressWarnings("unchecked")
		public C execute(DatabaseTransaction transaction, Object... values) // ! not final
			throws Exception
		{
			C result = container();
			transaction.execute(this, SqlParameters.prepare(values), IColumnSqlProcessor.fill(result));

			return result;
		}
	}


	abstract public static class ARawContainerQuery<R extends IContainer, C extends IContainer>
		extends AQuery
	{
		//
		final protected static Log LOG = Log.instance(ARawContainerQuery.class);

		//
		abstract protected R raw();
		abstract protected C convert(R raw);
		abstract protected C init();

		//
		@SuppressWarnings("unchecked")
		public C execute(Object... values) // ! not final
		{
			C result;

			try (DatabaseTransaction transaction = DatabaseTransaction.read())
			{
				R raw = raw();
				transaction.execute(this, SqlParameters.prepare(values), IColumnSqlProcessor.fill(raw));
				result = convert(raw);

				//
				transaction.commit();
			}
			catch (Exception e)
			{
				LOG.error(e);
				result = init();
			}

			return result;
		}

		@SuppressWarnings("unchecked")
		public C execute(DatabaseTransaction transaction, Object... values) // ! not final
			throws Exception
		{
			R raw = raw();
			transaction.execute(this, SqlParameters.prepare(values), IColumnSqlProcessor.fill(raw));

			return convert(raw);
		}
	}

}
