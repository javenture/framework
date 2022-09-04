package net.javenture.framework.javenture;


import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.script.*;
import net.javenture.framework.model.AModel;
import net.javenture.framework.vue.AVueUrl;
import net.javenture.framework.vue.VueApp;


/*
	2019/08/03
 */
final public class JaventureUtil
{
	// XXX: NettyResource ?


	//
	private JaventureUtil()
	{
	}


	//
	final public static GetScriptFunction FUNCTION_GET = new GetScriptFunction();                                   // XXX: ScriptMethod ?
	final public static SetScriptFunction FUNCTION_SET = new SetScriptFunction();
	final public static EqualScriptFunction FUNCTION_EQUAL = new EqualScriptFunction();
	final public static LocationScriptFunction FUNCTION_LOCATION = new LocationScriptFunction();
	final public static ExchangeScriptFunction FUNCTION_EXCHANGE = new ExchangeScriptFunction();

	final public static QuasarGetScrollScriptFunction FUNCTION_QUASAR_GET_SCROLL = new QuasarGetScrollScriptFunction();
	final public static QuasarSetScrollScriptFunction FUNCTION_QUASAR_SET_SCROLL = new QuasarSetScrollScriptFunction();


	//
	final public static class GetScriptFunction
		extends AScriptFunction
	{
		@Override
		protected String _name()
		{
			return "Javenture.util.get";
		}

		@Override
		protected String _content()
		{
			return null;
		}
	}


	final public static class SetScriptFunction
		extends AScriptFunction
	{
		@Override
		protected String _name()
		{
			return "Javenture.util.set";
		}

		@Override
		protected String _content()
		{
			return null;
		}
	}


	final public static class EqualScriptFunction
		extends AScriptFunction
	{
		//
		@Override
		protected String _name()
		{
			return "Javenture.util.equal";
		}

		@Override
		protected String _content()
		{
			return null;
		}

		public Invoke invoke(Object object1, Object object2)
		{
			// XXX: ?


			throw new UnsupportedOperationException();
		}
	}


	final public static class LocationScriptFunction
		extends AScriptFunction
	{
		//
		final private static String NAME_DESTINATION = "destination";
		final private static String NAME_REDIRECT = "redirect";

		//
		@Override
		protected String _name()
		{
			return "Javenture.vue.location";
		}

		@Override
		protected String _content()
		{
			return null;
		}

		public Invoke invoke(@NullDisallow AVueUrl destination)                                     // XXX: Config
		{
			ScriptObject object = new ScriptObject()
				.add(destination.toScriptObject(NAME_DESTINATION));

			return super.invoke()
				.argument(object);
		}

		public Invoke invoke(@NullDisallow AVueUrl destination, boolean context)                                     // XXX: Config
		{
			ScriptObject object = new ScriptObject()
				.add(destination.toScriptObject(NAME_DESTINATION, context));

			return super.invoke()
				.argument(object);
		}

		public Invoke invoke(@NullDisallow AVueUrl destination, @NullDisallow AVueUrl redirect)
		{
			ScriptObject object = new ScriptObject()
				.add(destination.toScriptObject(NAME_DESTINATION))
				.add(redirect.toScriptObject(NAME_REDIRECT));

			return super.invoke()
				.argument(object);
		}

		public Invoke invoke(@NullDisallow AVueUrl destination, @NullDisallow AVueUrl redirect, boolean context)
		{
			ScriptObject object = new ScriptObject()
				.add(destination.toScriptObject(NAME_DESTINATION, context))
				.add(redirect.toScriptObject(NAME_REDIRECT, context));

			return super.invoke()
				.argument(object);
		}

		public Invoke invoke(String url)
		{
			return super.invoke()
				.argument(url);
		}
	}


	final public static class ExchangeScriptFunction
		extends AScriptFunction
	{
		//
		final private static String PROPERTY_BACKEND = "backend";
		final private static String PROPERTY_REDIRECT = "redirect";
		final private static String PROPERTY_CONFIRMATION = "confirmation";

		//
		@Override
		protected String _name()
		{
			return "Javenture.vue.exchange";
		}

		@Override
		protected String _content()
		{
			return null;
		}

		public Invoke invoke(VueApp application, Config value)
		{
			return super.invoke()
				.argument(application.name(), false)                                                        // XXX: "this." +
				.argument(value);
		}

		//
		final public static class Config
			extends ScriptObject
		{
			//
			public Config()
			{
			}

			//
			public Config backend(AVueUrl value)
			{
				return backend(value, false);
			}

			public Config backend(AVueUrl value, boolean context)                                                      // XXX: context ?
			{
				super.add(value.toScriptObject(PROPERTY_BACKEND, context));

				return this;
			}

			public Config backend(String value)
			{
				super.add(new ScriptProperty(PROPERTY_BACKEND, value, true));

				return this;
			}

			public Config redirect(AVueUrl value)
			{
				return redirect(value, false);
			}

			public Config redirect(AVueUrl value, boolean context)                                                  // XXX: context ?
			{
				super.add(value.toScriptObject(PROPERTY_REDIRECT, context));

				return this;
			}

			public Config redirect(String value)
			{
				super.add(new ScriptProperty(PROPERTY_REDIRECT, value, true));

				return this;
			}

			public Config request(Request request)
			{
				super.add(request);

				return this;
			}

			public Config response(Response response)
			{
				super.add(response);

				return this;
			}

			public Config confirmation(String value)
			{
				super.add(new ScriptProperty(PROPERTY_CONFIRMATION, value, true));

				return this;
			}
		}

		final public static class Request                                                                                // XXX: simplify ?
			extends ScriptArray                                   // XXX: del
		{
			//
			final private static String ARRAY = "request";
			final private static String PROPERTY_NAME = "name";
			final private static String PROPERTY_VALUE = "value";

			//
			final private boolean CONTEXT;                                                                            // XXX: make always true ?

			//
			public Request()
			{
				this(false);
			}

			public Request(boolean context)
			{
				super(ARRAY);

				CONTEXT = context;
			}

			//
			public Request add(AModel model)
			{
				String alias = model.alias();

				super.add
				(
					new ScriptObject()
						.add(new ScriptProperty(PROPERTY_NAME, alias, true))
						.add(new ScriptProperty(PROPERTY_VALUE, CONTEXT ? "this." + alias : alias, false))                            // XXX
				);

				return this;
			}

			public Request add(Iterable<? extends AModel> models)
			{
				for (AModel model : models) add(model);

				return this;
			}

/*
			public <V> Request add(AVueModel<V> model, AVueModel<V> value)                                     // XXX
			{


			}
*/


			public <V> Request add(AModel<V> model, V value)
			{
				String alias = model.alias();
				IScriptEntry entry = model.toScriptEntry(value);

				super.add
				(
					new ScriptObject
					(
						new ScriptProperty(PROPERTY_NAME, alias, true),
						new ScriptProperty(PROPERTY_VALUE, entry)
					)
				);

				return this;
			}

			public Request redirect(AVueUrl url)                                                                  // XXX: !!!!!!!!!!!!!!!
			{
				super.add
				(
					new ScriptObject()
						.add(new ScriptProperty(PROPERTY_NAME, "REDIRECT", true))
						.add(url.toScriptObject(PROPERTY_VALUE))
				);

				return this;
			}

			public Request redirect(String url)                                                                  // XXX: !!!!!!!!!!!!!!!
			{
				super.add
				(
					new ScriptObject()
						.add(new ScriptProperty(PROPERTY_NAME, "REDIRECT", true))
						.add(new ScriptProperty(PROPERTY_VALUE, url, true))
				);

				return this;
			}
		}

		final public static class Response                                                                                  // XXX: simplify ?
			extends ScriptArray                                   // XXX: del
		{
			//
			final private static String ARRAY = "response";
			final private static String PROPERTY_NAME = "name";
			final private static String PROPERTY_INIT = "init";

			//
			final private boolean CONTEXT;                                                                            // XXX: make always true ?

			//
			public Response()
			{
				this(false);
			}

			public Response(boolean context)
			{
				super(ARRAY);

				CONTEXT = context;
			}

			//
			public Response add(AModel model)
			{
				String alias = model.alias();

				super.add
				(
					new ScriptObject()
						.add(new ScriptProperty(PROPERTY_NAME, alias, true))
						.add(new ScriptProperty(PROPERTY_INIT, CONTEXT ? "this." + alias : alias, false))
				);

				return this;
			}

			public Response add(Iterable<? extends AModel> models)
			{
				for (AModel model : models) add(model);

				return this;
			}

			public <V> Response add(AModel<V> model, V init)
			{
				String alias = model.alias();
				IScriptEntry entry = model.toScriptEntry(init);

				super.add
				(
					Script.object
					(
						Script.property(PROPERTY_NAME, alias, true),
						Script.property(PROPERTY_INIT, entry)
					)
				);

				return this;
			}
		}
	}


	/*
		Quasar
	 */
	final public static class QuasarGetScrollScriptFunction                                                    // XXX: ???
		extends AScriptFunction
	{
		@Override
		protected String _name()
		{
			return "Javenture.quasar.getScroll";
		}

		@Override
		protected String _content()
		{
			return null;
		}
	}


	final public static class QuasarSetScrollScriptFunction
		extends AScriptFunction
	{
		@Override
		protected String _name()
		{
			return "Javenture.quasar.setScroll";
		}

		@Override
		protected String _content()
		{
			return null;
		}
	}

}
