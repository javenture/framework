package net.javenture.framework.vue;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.model.AModel;
import net.javenture.framework.netty.NettyParameters;
import net.javenture.framework.netty.NettyRequest;
import net.javenture.framework.netty.NettyUrl;
import net.javenture.framework.script.ScriptArray;
import net.javenture.framework.script.ScriptObject;
import net.javenture.framework.script.ScriptProperty;
import net.javenture.framework.script.AScriptFunction;
import net.javenture.framework.utf8.IUtf8StreamableEntry;
import net.javenture.framework.utf8.Utf8OutputStream;
import net.javenture.framework.util.Validator;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;


/*
	2019/10/04
 */
abstract public class AVueUrl<U extends AVueUrl>                           // XXX: VueRoute
	implements IUtf8StreamableEntry
{
	//
	private enum Type
	{
		UNKNOWN,
		VALUE,
		MODEL
	}


	final private static String LABEL_URL = "url";
	final private static String LABEL_PARAMETERS = "parameters";
	final private static String LABEL_NAME = "name";
	final private static String LABEL_VALUE = "value";
	final private static String LABEL_INIT = "init";


	//
	final Parameter[] PARAMETERS;

	@SuppressWarnings("unchecked")
	final private U THIS = (U) this;


	//
	protected AVueUrl()
	{
		Config config = config();
		Entry[] entries = config.ENTRIES.get();
		int length = entries.length;
		PARAMETERS = new Parameter[length];

		for (int i = 0; i < length; i++) PARAMETERS[i] = new Parameter(Type.UNKNOWN, null);
	}


	protected AVueUrl(@NullDisallow U value)
	{
		this.PARAMETERS = value.PARAMETERS.clone();
	}


	//
	abstract protected @NullDisallow Config config();                                                           // XXX: config() -> _config() ?


	//
	@Override
	final public void toUtf8Stream(Utf8OutputStream destination)
		throws IOException                                                                            // XXX: ?
	{
		toNettyUrl().toUtf8Stream(destination);
	}


	@Override
	final public String toString()                                                               // XXX: ?
	{
		return toNettyUrl().toString();
	}


	final public U parse(@NullDisallow NettyRequest request)
	{
		// XXX: NettyUri uri - match with config.PATH ?


		NettyParameters parameters = request.parameters();

		//
		Config config = config();
		Entry[] entries = config.ENTRIES.get();
		int length = entries.length;

		for (int i = 0; i < length; i++)
		{
			AModel model = entries[i].MODEL;
			Object object = model.parse(parameters).VALUE;

			if (model.defined(object)) PARAMETERS[i] = new Parameter(Type.VALUE, object);
		}

		return THIS;
	}


	final public boolean undefined()                                        // XXX: defined ?
	{
		Config config = config();
		Entry[] entries = config.ENTRIES.get();

		for (int i = 0; i < entries.length; i++)
		{
			Object object = PARAMETERS[i].OBJECT;

			if (object != null && entries[i].MODEL.defined(object)) return false;
		}

		return true;
	}


	final protected boolean undefined(@NullDisallow Entry entry)            // XXX: defined ?
	{
		Object object = PARAMETERS[entry.INDEX].OBJECT;

		return object == null || !entry.MODEL.defined(object);
	}


	@SuppressWarnings("unchecked")
	final protected @NullDisallow <V> V get(@NullDisallow Entry<V> entry)
	{
		Object object = PARAMETERS[entry.INDEX].OBJECT;

		return object != null ? (V) object : entry.MODEL.init();
	}


	final protected <V> void set(@NullDisallow Entry<V> entry, @NullDisallow V value)
	{
		Validator.notNull(value, "[value]");
		PARAMETERS[entry.INDEX] = new Parameter(Type.VALUE, value);
	}


	final protected void reactive(@NullDisallow Entry entry)
	{
		PARAMETERS[entry.INDEX] = new Parameter(Type.MODEL, null);
	}


	final protected void clear(@NullDisallow Entry entry)
	{
		PARAMETERS[entry.INDEX] = new Parameter(Type.UNKNOWN, null);
	}


	final public ScriptObject toScriptObject()
	{
		return toScriptObject(null, false);
	}


	final public ScriptObject toScriptObject(boolean context)
	{
		return toScriptObject(null, context);
	}


	final public ScriptObject toScriptObject(@NullAllow String name)
	{
		return toScriptObject(name, false);
	}


	final public ScriptObject toScriptObject(@NullAllow String name, boolean context)
	{
		ScriptObject result = new ScriptObject(name);
		Config config = config();
		Entry[] entries = config.ENTRIES.get();
		int length = entries.length;

		// url
		result.add(config.OBJECT_URL);

		// parameters
		if (length != 0)
		{
			ScriptArray array = new ScriptArray(LABEL_PARAMETERS);

			for (int i = 0; i < length; i++)
			{
				switch (PARAMETERS[i].TYPE)
				{
					case VALUE:
					{
						Entry entry = entries[i];
						AModel model = entry.MODEL;
						Object object = PARAMETERS[i].OBJECT;

						if (object != null && model.defined(object))
						{
							ScriptProperty property = new ScriptProperty(LABEL_VALUE, model.toScriptEntry(object));

							ScriptObject parameter = new ScriptObject()
								.add(entry.PROPERTY_NAME, property);

							array.add(parameter);
						}

						break;
					}

					case MODEL:
					{
						Entry entry = entries[i];

						ScriptObject parameter = new ScriptObject()
							.add(entry.PROPERTY_NAME)
							.add(context ? entry.PROPERTY_VALUE_THIS : entry.PROPERTY_VALUE)
							.add(entry.PROPERTY_INIT);

						array.add(parameter);

						break;
					}
				}
			}

			if (array.exist()) result.add(array);
		}

		return result;
	}


	final public NettyUrl toNettyUrl()
	{
		Config config = config();
		NettyUrl url = config.URL;
		Entry[] entries = config.ENTRIES.get();

		NettyParameters parameters = new NettyParameters();
		boolean change = false;

		//
		NettyUrl.Query query = url.query();

		if (query != null)
		{
			for (NettyParameters.Entry entry : query) parameters.add(entry);
		}

		//
		for (int i = 0; i < entries.length; i++)
		{
			AModel model = entries[i].MODEL;
			Object object = PARAMETERS[i].OBJECT;

			if (object != null && model.defined(object))
			{
				change = true;
				model.toNettyParameter(object, parameters);
			}
		}

		if (change)
		{
			return new NettyUrl.Builder(url)
				.query(new NettyUrl.Query(parameters))
				.url();
		}
		else
		{
			return url;
		}
	}


	//
	final public static class Config
	{
		//
		final private static Entry[] BLANK = new Entry[0];

		//
		final private NettyUrl URL;
		final private AtomicReference<Entry[]> ENTRIES;
		final private ScriptObject OBJECT_URL;
		final private Object LOCK;
		final private AtomicInteger INDEX;

		//
		public Config(@NullDisallow NettyUrl url)
		{
			Validator.notNull(url, "[url]");

			URL = url;
			ENTRIES = new AtomicReference<>(BLANK);
			OBJECT_URL = url.toScriptObject(LABEL_URL);
			LOCK = new Object();
			INDEX = new AtomicInteger(0);
		}

		public Config(@NullDisallow String url)
		{
			Validator.notNull(url, "[url]");

			URL = new NettyUrl(url);
			ENTRIES = new AtomicReference<>(BLANK);
			OBJECT_URL = URL.toScriptObject(LABEL_URL);
			LOCK = new Object();
			INDEX = new AtomicInteger(0);
		}

		//
		private int register(Entry entry)
		{
			int index = INDEX.getAndIncrement();

			synchronized (LOCK)
			{
				Entry[] entries = ENTRIES.get();
				int length = entries.length;
				Entry[] entries2 = new Entry[length + 1];
				System.arraycopy(entries, 0, entries2, 0, length);
				entries2[index] = entry;
				ENTRIES.set(entries2);
			}

			return index;
		}
	}


	final public static class Entry<V>
	{
		//
		final private int INDEX;
		final private AModel<V> MODEL;
		final private ScriptProperty PROPERTY_NAME;
		final private ScriptProperty PROPERTY_INIT;
		final private ScriptProperty PROPERTY_VALUE; // reactive
		final private ScriptProperty PROPERTY_VALUE_THIS; // reactive + 'this.'

		//
		public Entry(Config config, AModel<V> model)
		{
			INDEX = config.register(this);
			MODEL = model;
			PROPERTY_NAME = new ScriptProperty(LABEL_NAME, model.alias(), true);
			PROPERTY_INIT = new ScriptProperty(LABEL_INIT, model.toScriptEntry());
			PROPERTY_VALUE = new ScriptProperty(LABEL_VALUE, model.alias(), false);
			PROPERTY_VALUE_THIS = new ScriptProperty(LABEL_VALUE, "this." + model.alias(), false);
		}

		public Entry(Config config, AModel<V> model, AScriptFunction function)                                                        // XXX: ???
		{
			AScriptFunction.Invoke invoke = function.invoke();
			invoke.semicolon(false);

			INDEX = config.register(this);
			MODEL = model;
			PROPERTY_NAME = new ScriptProperty(LABEL_NAME, model.alias(), true);
			PROPERTY_INIT = new ScriptProperty(LABEL_INIT, model.toScriptEntry());
			PROPERTY_VALUE = new ScriptProperty(LABEL_VALUE, invoke, false);
			PROPERTY_VALUE_THIS = new ScriptProperty(LABEL_VALUE, invoke, false);                       // XXX: ?
		}
	}


	private static class Parameter
	{
		//
		final private Type TYPE;
		final private @NullAllow Object OBJECT;

		//
		private Parameter(Type type, Object object)
		{
			TYPE = type;
			OBJECT = object;
		}
	}

}
