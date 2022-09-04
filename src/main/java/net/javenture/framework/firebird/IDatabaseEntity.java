package net.javenture.framework.firebird;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.identifier.IntIdentifier;
import net.javenture.framework.log.ILog;
import net.javenture.framework.log.Log;


/*
	2019/12/13
 */
public interface IDatabaseEntity<E extends IDatabaseEntity>
	extends IDatabaseOperation, ILog
{
	//
	IConfig config();
	boolean defined();
	void clear();
	ISnapshot snapshot();


	//
	interface IConfig
		extends IPattern
	{
		Log log();
		DatabaseTable table();
		boolean journal();                                                     // XXX: options
		boolean cache();                                                       // XXX: options
		void init(DatabaseTransaction transaction) throws Exception;
		@NullAllow IntIdentifier signature();
	}

}
