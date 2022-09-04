package net.javenture.framework.netty;


import net.javenture.framework.util.IParser;


/*
	2020/09/30
 */
abstract public class AUuidNettySessions
	extends ANettySessions<UuidNettySessionId>
{
	//
	final private int PARAMETERS;


	//
	protected AUuidNettySessions(int parameters)
	{
		PARAMETERS = parameters;
	}


	//
	@Override
	final protected NettySession<UuidNettySessionId> create()
	{
		return new NettySession<>(UuidNettySessionId.GENERATOR.get(), PARAMETERS);
	}


	@Override
	final protected IParser<UuidNettySessionId> parser()
	{
		return UuidNettySessionId.Parser.UNKNOWN;
	}

}
