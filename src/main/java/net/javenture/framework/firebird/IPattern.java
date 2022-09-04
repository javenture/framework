package net.javenture.framework.firebird;


/*
	2019/12/01
 */
interface IPattern
{
	//
	enum Pattern
	{
		UNKNOWN,
		ITEM_SINGLE_GENERATED,
		ITEM_SINGLE,
		ITEM_DUAL,
		RELATION_SINGLE,
		GROUP_SINGLE
	}


	//
	Pattern pattern();

}
