package net.javenture.framework.json;


import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.netty.Media;
import net.javenture.framework.netty.NettyResponse;
import net.javenture.framework.netty.NettyStatus;
import net.javenture.framework.script.IScriptEntry;
import net.javenture.framework.script.ScriptObject;
import net.javenture.framework.utf8.Utf8OutputStream;
import net.javenture.framework.util.Validator;

import java.io.IOException;


/*
	2019/12/17
 */
final public class JsonContainer
	implements IJsonStreamableEntry
{
	//
	final private boolean COMPOSE;
	final private ScriptObject OBJECT;


	//
	public JsonContainer()
	{
		this(false);
	}


	public JsonContainer(boolean compose)
	{
		COMPOSE = compose;
		OBJECT = new ScriptObject();
	}


	//
	@Override
	public void toJsonStream(Utf8OutputStream destination)
		throws IOException
	{
		ScriptObject object = COMPOSE ? OBJECT.compose() : OBJECT;
		object.toJsonStream(destination);
	}


	@Override
	public String toString()
	{
		return JsonUtil.toString(this);
	}


	public JsonContainer add(@NullDisallow IScriptEntry entry)
	{
		Validator.notNull(entry.name(), "[entry.name()]");
		OBJECT.add(entry);

		return this;
	}


	public JsonContainer add(IScriptEntry... entries)
	{
		for (IScriptEntry entry : entries)
		{
			Validator.notNull(entry.name(), "[entry.name()]");
			OBJECT.add(entry);
		}

		return this;
	}


	public NettyResponse toNettyResponse()
		throws IOException
	{
		try (JsonOutputStream stream = new JsonOutputStream())
		{
			stream.write(this);

			return new NettyResponse(NettyStatus.OK_200, Media.JSON, stream.toInputStream());
		}
	}

}
