package net.javenture.framework.firebird;


import net.javenture.framework.log.Log;


/*
	2019/12/13
 */
public interface IRelation<R extends IRelation>
	extends IDatabaseEntity<R>
{
	//
	@Override IConfig config();
	@Override boolean defined();
	@Override void clear();
	@Override ISnapshot snapshot();


	//
	interface IConfig
		extends IDatabaseEntity.IConfig
	{
		@Override Log log();
		@Override DatabaseTable table();
		@Override boolean journal();
		@Override boolean cache();
	}

}
