package net.javenture.framework.javenture;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.json.IJsonStreamableEntry;
import net.javenture.framework.json.JsonOutputStream;
import net.javenture.framework.netty.Media;
import net.javenture.framework.netty.NettyResponse;
import net.javenture.framework.netty.NettyStatus;
import net.javenture.framework.utf8.IUtf8StreamableEntry;
import net.javenture.framework.utf8.Utf8OutputStream;
import net.javenture.framework.util.Validator;
import net.javenture.framework.model.AModel;
import net.javenture.framework.vue.AVueUrl;
import net.javenture.framework.script.ScriptObject;
import net.javenture.framework.script.ScriptProperty;

import java.io.IOException;


/*
	2019/12/17
 */
final public class JaventureExchange                                                   // XXX: JaventureExchange -> HttpInteraction | NettyInteraction ?
	extends ScriptObject                             // XXX: ?
{

	// XXX: общий класс для обработчиков FRONTEND & BACKEND





	//
	final private static String LABEL_STATUS = "status";
	final private static String LABEL_DATA = "data";                               // XXX: content
	final private static String LABEL_NOTIFICATION = "notification";
	final private static String LABEL_REDIRECT = "redirect";


	//
	public JaventureExchange()
	{
	}


	//
	public JaventureExchange status(@NullDisallow Status value)
	{
		Validator.notNull(value, "[status]");
		super.add(new ScriptProperty(LABEL_STATUS, value));

		return this;
	}


	public JaventureExchange data(@NullAllow Data value)                                          // XXX: @NullAllow ?
	{
		if (value != null) super.add(value);

		return this;
	}


	public JaventureExchange data(@NullAllow ScriptObject value)                      // XXX: @NullAllow ?                               // XXX
	{
		if (value != null) super.add(value);

		return this;
	}


	// XXX: report - handler(report)



	public JaventureExchange notification(@NullAllow String value)                                        // XXX: @NullAllow ?
	{
		if (value != null) super.add(new ScriptProperty(LABEL_NOTIFICATION, value, true));

		return this;
	}


	public JaventureExchange redirect(@NullAllow AVueUrl value)                                                 // XXX: @NullAllow ?
	{
		if (value != null) super.add(value.toScriptObject(LABEL_REDIRECT));

		return this;
	}


	public JaventureExchange redirect(@NullAllow String value)                                               // XXX: @NullAllow ?
	{
		if (value != null) super.add(new ScriptProperty(LABEL_REDIRECT, value, true));

		return this;
	}





	public NettyResponse response()
		throws IOException
	{
		try (JsonOutputStream stream = new JsonOutputStream()) // ! JsonOutputStream
		{
			stream.write((IJsonStreamableEntry) this);

			return new NettyResponse(NettyStatus.OK_200, Media.JSON, stream.toInputStream());
		}
	}


	//
	public enum Status
		implements IUtf8StreamableEntry
	{
		//
		OK("OK"),
		ERROR("ERROR");

		//
		final private IUtf8StreamableEntry ENTRY;

		//
		Status(String value)
		{
			ENTRY = IUtf8StreamableEntry.entry(value, true);
		}

		//
		@Override
		public void toUtf8Stream(Utf8OutputStream destination)
			throws IOException
		{
			ENTRY.toUtf8Stream(destination);
		}
	}


	final public static class Data
		extends ScriptObject
	{
		//
		public Data()
		{
			super(LABEL_DATA);
		}

		//
		public <V> Data add(AModel<V> model)
		{
			super.add(model.toScriptStructure());

			return this;
		}

		public <V> Data add(AModel<V> model, V value)
		{
			super.add(model.toScriptStructure(value));

			return this;
		}
	}

}
