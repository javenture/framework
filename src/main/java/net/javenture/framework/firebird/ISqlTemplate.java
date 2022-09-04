package net.javenture.framework.firebird;


import net.javenture.framework.annotation.NullDisallow;


/*
	2019/08/24
 */
public interface ISqlTemplate
{
	//
	int id();
	@NullDisallow SqlType type();
	@NullDisallow String text();
	@NullDisallow SqlSenders senders();
	@NullDisallow SqlReceivers receivers();

}
