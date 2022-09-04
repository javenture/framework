package net.javenture.framework.firebird;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.identifier.IntIdentifier;
import net.javenture.framework.log.Log;
import net.javenture.framework.util.LongSequence;
import net.javenture.framework.util.Millisecond;
import net.javenture.framework.util.Switch;
import net.javenture.framework.util.Uuid;
import net.javenture.framework.util.Validator;

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicInteger;


/*
	2021/06/02
 */
final public class JournalManager                                        // XXX: package
{
	//
	final private static Log LOG = Log.instance(JournalManager.class);
	final private static Switch SWITCH = new Switch(true, false);
	final private static ConcurrentSkipListMap<IntIdentifier, IDatabaseEntity.IConfig> CONFIG = new ConcurrentSkipListMap<>();


	//
	public static void register(DatabaseTransaction transaction, IDatabaseEntity.IConfig[] configs)
		throws Exception
	{
		for (IDatabaseEntity.IConfig config : configs)
		{
			config.init(transaction);
			IntIdentifier idSignature = config.signature();
			Validator.notNull(idSignature, "[idSignature]");
			CONFIG.put(idSignature, config);
		}
	}


	public static void validate()
	{
		try (DatabaseTransaction transaction = DatabaseTransaction.service())
		{
			DatabaseInstance.TransactionManager manager = transaction.instance().MANAGER;
			LongSequence sequence = JournalQueueItem.processed(transaction);

			for (long idJournalQueue : sequence)
			{
				JournalQueueItem itemJournalQueue = new JournalQueueItem(idJournalQueue);
				itemJournalQueue.load0(transaction);
				Uuid uuid = itemJournalQueue.getTransactionUuid();

				if (!manager.exists(uuid))
				{
					itemJournalQueue.setState(JournalQueueItem.State.COMPLETE);
					itemJournalQueue.save0(transaction);
				}
			}

			//
			transaction.commit();
		}
		catch (Exception e)
		{
			LOG.error(e);
		}
	}


	static void write
	(
		@NullDisallow DatabaseTransaction transaction,
		@NullDisallow JournalOperation operation,
		@NullDisallow IntIdentifier idSignature,
		@NullDisallow byte[] key,
		@NullAllow byte[] data
	)
		throws Exception
	{
		Validator.notNull(transaction, "[transaction]");
		Validator.notNull(operation, "[operation]");
		Validator.notNull(idSignature, "[idSignature]"); // !
		Validator.notNull(key, "[key]"); // !

		//
		Event event = transaction.journal()
			.event();

		switch (operation)
		{
			case TRANSACTION_COMMIT:
			{
				JournalDataItem itemJournalData = new JournalDataItem(event.ID, event.NUMBER);
				itemJournalData.setOperation(operation);
				//itemJournalData.setUserKey(new byte[] { 0 });                                                 // XXX
				itemJournalData.setDate(Millisecond.now());
				itemJournalData.create0(transaction);

				break;
			}

			case ENTITY_CREATE:
			case ENTITY_SAVE:
			case ENTITY_RECYCLE:
			case ENTITY_RECYCLE_IF_EXISTS:
			case ENTITY_DELETE:
			case ENTITY_DELETE_IF_EXISTS:
			case ENTITY_UNDO:
			case ENTITY_INIT:
			{
				JournalDataItem itemJournalData = new JournalDataItem(event.ID, event.NUMBER);
				itemJournalData.setOperation(operation);
				itemJournalData.setSignatureId(idSignature);
				itemJournalData.setItemKey(key);
				itemJournalData.setItemData(data);
				//itemJournalData.setUserKey(new byte[] { 0 });                                                 // XXX
				itemJournalData.setDate(Millisecond.now());

				//
				JournalReferenceItem itemJournalReference = new JournalReferenceItem(idSignature, key);

				if (itemJournalReference.loadIfExists0(transaction))
				{
					long id0 = itemJournalReference.getId();
					int number0 = itemJournalReference.getNumber();

					itemJournalData.setPreviousIterationId(id0);
					itemJournalData.setPreviousIterationNumber(number0);
					itemJournalData.setPreviousVersionId(id0);
					itemJournalData.setPreviousVersionNumber(number0);
				}

				//
				itemJournalData.create0(transaction);

				itemJournalReference.setId(event.ID);
				itemJournalReference.setNumber(event.NUMBER);
				itemJournalReference.save0(transaction);

				break;
			}

			default: throw new UnsupportedOperationException("[operation] (" + operation + ") not supported");
		}
	}



/*
	public static <T extends IFactoryObject<T>> void user(IFactoryObject<T> current, IFactoryObject<T> next)                                            // XXX: -> UserManager
	{


		// XXX: ThreadLocal USER
		// if (USER == current) ...
		// else throws new Exception();

	}
*/


	public static void init()
	{
		try (DatabaseTransaction transaction = DatabaseTransaction.service())
		{
			// journal data
			{
				transaction.execute(SqlTemplates.JOURNAL_DATA_DELETE);

				//
				long id = 0;
				int number = 0;
				JournalOperation operation = JournalOperation.TRANSACTION_COMMIT;
				Millisecond date = Millisecond.ZERO;
				SqlParameters parameters = new SqlParameters(id, number, operation, date);
				transaction.execute(SqlTemplates.JOURNAL_DATA_INSERT, parameters);
			}

			// journal reference
			{
				transaction.execute(SqlTemplates.JOURNAL_REFERENCE_DELETE);
			}

			// journal queue
			{
				transaction.execute(SqlTemplates.JOURNAL_QUEUE_DELETE);

				//
				long id = 0;
				Uuid uuid = Uuid.UNKNOWN;
				JournalQueueItem.State state = JournalQueueItem.State.COMPLETE;
				SqlParameters parameters = new SqlParameters(id, uuid, state);
				transaction.execute(SqlTemplates.JOURNAL_QUEUE_INSERT, parameters);
			}

			// journal sequence
			{
				JournalSequence.init(transaction);
			}

			//
			transaction.commit();
		}
		catch (Exception e)
		{
			LOG.error(e);
		}
	}


	//
	final private static class SqlTemplates
	{
		final private static ASqlTemplate.AUpdate JOURNAL_DATA_DELETE = new ASqlTemplate.AUpdate()
		{
			@Override
			protected SqlMarkup _markup()
			{
				return new SqlMarkup
				(
					"delete",
						"from", JournalDataItem.Config.NAME_TABLE
				);
			}
		};

		final private static ASqlTemplate.AUpdate JOURNAL_DATA_INSERT = new ASqlTemplate.AUpdate()
		{
			@Override
			protected SqlMarkup _markup()
			{
				return new SqlMarkup
				(
					"insert",
					"into",
						JournalDataItem.Config.NAME_TABLE,
					"(",
						JournalDataItem.Config.FIELD_ID.sender(0), ",",
						JournalDataItem.Config.FIELD_NUMBER.sender(1), ",",
						JournalDataItem.Config.FIELD_OPERATION.sender(2), ",",
						JournalDataItem.Config.FIELD_DATE.sender(3),
					")",
					"values",
					"(",
						"?", ",",
						"?", ",",
						"?", ",",
						"?",
					")"
				);
			}
		};

		final private static ASqlTemplate.AUpdate JOURNAL_REFERENCE_DELETE = new ASqlTemplate.AUpdate()
		{
			@Override
			protected SqlMarkup _markup()
			{
				return new SqlMarkup
				(
					"delete",
						"from", JournalReferenceItem.Config.NAME_TABLE
				);
			}
		};

		final private static ASqlTemplate.AUpdate JOURNAL_QUEUE_DELETE = new ASqlTemplate.AUpdate()
		{
			@Override
			protected SqlMarkup _markup()
			{
				return new SqlMarkup
				(
					"delete",
						"from", JournalQueueItem.Config.NAME_TABLE
				);
			}
		};

		final private static ASqlTemplate.AUpdate JOURNAL_QUEUE_INSERT = new ASqlTemplate.AUpdate()
		{
			@Override
			protected SqlMarkup _markup()
			{
				return new SqlMarkup
				(
					"insert",
					"into",
						JournalQueueItem.Config.NAME_TABLE,
					"(",
						JournalQueueItem.Config.FIELD_ID.sender(0), ",",
						JournalQueueItem.Config.FIELD_TRANSACTION_UUID.sender(1), ",",
						JournalQueueItem.Config.FIELD_STATE.sender(2),
					")",
					"values",
					"(",
						"?", ",",
						"?", ",",
						"?",
					")"
				);
			}
		};
	}


	final static class Iteration
	{
		//
		final private static Object LOCK = new Object();

		//
		final long ID;
		final private AtomicInteger NUMBER;

		//
		Iteration(DatabaseTransaction transaction)
			throws Exception
		{
			synchronized (LOCK)
			{
				ID = JournalSequence.get(transaction);
				NUMBER = new AtomicInteger(0);

				JournalQueueItem itemJournalQueue = new JournalQueueItem(ID);
				itemJournalQueue.setTransactionUuid(transaction.uuid());
				itemJournalQueue.setState(JournalQueueItem.State.PROCESS);
				itemJournalQueue.create0(transaction); // ! create0

				transaction.commit0(); // !
			}
		}

		//
		private Event event()
		{
			return new Event(ID, NUMBER.getAndIncrement());
		}

		void complete(DatabaseTransaction transaction)
			throws Exception
		{
			JournalQueueItem itemJournalQueue = new JournalQueueItem(ID);
			itemJournalQueue.setTransactionUuid(transaction.uuid());
			itemJournalQueue.setState(JournalQueueItem.State.COMPLETE);
			itemJournalQueue.save0(transaction); // ! save0
		}
	}


	final static class Event
	{
		//
		final long ID;
		final int NUMBER;

		//
		Event(long id, int number)
		{
			ID = id;
			NUMBER = number;
		}
	}

}
