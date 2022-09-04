package net.javenture.framework.vue;


import net.javenture.framework.model.StringModel;
import net.javenture.framework.script.AScriptFunction;
import net.javenture.framework.util.IConst;


/*
	2018/03/28
 */
@Deprecated
final public class FormVueApp
{
	//
	final public static StringModel MODEL_URL = new StringModel("URL", false, true, false, "");
	final public static StringModel MODEL_REDIRECT = new StringModel("REDIRECT", false, true, false, "");

	final public static AScriptFunction FUNCTION_LOAD = new AScriptFunction()
	{
		@Override
		protected String _name()
		{
			return "load";
		}

		@Override
		protected String _content()
		{
			return
				"function (action)" +
				"{" +
				"var app_data = this;" +
				"var app_url = this.URL;" +
				"var items = this.ITEMS;" +
				"" +
				"var form = new FormData();" +
				"form.append('ACTION', action);" +
				"" +
				"for (var i = 0; i < items.length; i++)" +
				"{" +
				"var item = items[i];" +
				"var item_name = item.NAME;" +
				"var item_keys = item.KEYS;" +
				"" +
				"for (var j = 0; j < item_keys.length; j++)" +
				"{" +
				"var item_key = item_keys[j];" +
				"form.append(item_name + '.' + item_key.NAME, item_key.VALUE);" +
				"}" +
				"}" +
				"" +
				"fetch" +
				"(" +
				"app_url," +
				"{" +
				"method: 'POST'," +
				"body: form" +
				"}" +
				")" +
				".then" +
				"(" +
				"function(response)" +
				"{" +
				"return response.json();" +
				"}" +
				")" +
				".then" +
				"(" +
				"function(json)" +
				"{" +
				"for (var i = 0; i < items.length; i++)" +
				"{" +
				"var item = items[i];" +
				"var data = json[item.NAME];" +
				"" +
				"if (data)" +
				"{" +
				"for (var model in data)" +
				"{" +
				"if (data.hasOwnProperty(model)) app_data[item.NAME][model] = data[model];" +
				"}" +
				"}" +
				"}" +
				"}" +
				")" +
				".catch" +
				"(" +
				");" +
				"}";
		}
	};


	final public static SaveVueFunction FUNCTION_SAVE = new SaveVueFunction();


	final public static class SaveVueFunction
		extends AScriptFunction
	{
		@Override
		protected String _name()
		{
			return "save";
		}

		@Override
		protected String _content()
		{
			return
				"function (action)" +
				"{" +
				"var app_data = this;" +
				"var app_url = this.URL;" +
				"var app_redirect = this.REDIRECT;" +
				"var items = this.ITEMS;" +
				"" +
				"var form = new FormData();" +
				"form.append('ACTION', action);" +
				"" +
				"for (var i = 0; i < items.length; i++)" +
				"{" +
				"var item = items[i];" +
				"var item_name = item.NAME;" +
				"var item_keys = item.KEYS;" +
				"" +
				"for (var j = 0; j < item_keys.length; j++)" +
				"{" +
				"var item_key = item_keys[j];" +
				"form.append(item_name + '.' + item_key.NAME, item_key.VALUE);" +
				"}" +
				"" +
				"var item_models = app_data[item_name];" +
				"" +
				"for (var model in item_models)" +
				"{" +
				"if (item_models.hasOwnProperty(model)) form.append(item_name + '.' + model, item_models[model]);" +
				"}" +
				"}" +
				"" +
				"fetch" +
				"(" +
				"app_url," +
				"{" +
				"method: 'POST'," +
				"body: form" +
				"}" +
				")" +
				".then" +
				"(" +
				"function()" +
				"{" +
				"window.location = app_redirect;" +
				"}" +
				")" +
				".catch" +
				"(" +
				");" +
				"}";
		}

		public Invoke invoke(IConst action)                                     // XXX: !!!
		{
			return super.invoke()
				.argument(action);
		}
	};

	final public static AScriptFunction FUNCTION_CANCEL = new AScriptFunction()
	{
		@Override
		protected String _name()
		{
			return "cancel";
		}

		@Override
		protected String _content()
		{
			return
				"function ()" +
				"{" +
				"window.location = this.REDIRECT;" +
				"}";
		}
	};

}
