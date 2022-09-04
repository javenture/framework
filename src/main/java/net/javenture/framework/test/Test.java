package net.javenture.framework.test;


import net.javenture.framework.firebird.*;
import net.javenture.framework.hash.Md5Hash;
import net.javenture.framework.hash.Murmur3Hash;
import net.javenture.framework.identifier.IntIdentifier;
import net.javenture.framework.identifier.LongIdentifier;
import net.javenture.framework.identifier.LongIdentifierContainer;
import net.javenture.framework.json.JsonArray;
import net.javenture.framework.json.JsonContainer;
import net.javenture.framework.json.JsonDocument;
import net.javenture.framework.json.NumberJsonValue;
import net.javenture.framework.log.Log;
import net.javenture.framework.math.Cartesian3dPoint;
import net.javenture.framework.math.MathUtil;
import net.javenture.framework.math.Polar3dPoint;
import net.javenture.framework.math.TupleUtil;
import net.javenture.framework.netty.NettyParameters;
import net.javenture.framework.netty.NettyUrl;
import net.javenture.framework.script.IScriptEntry;
import net.javenture.framework.script.ScriptArray;
import net.javenture.framework.script.ScriptObject;
import net.javenture.framework.script.ScriptProperty;
import net.javenture.framework.utf8.Utf8Number;
import net.javenture.framework.utf8.Utf8OutputStream;
import net.javenture.framework.utf8.Utf8UrlEncoder;
import net.javenture.framework.util.*;
import net.javenture.framework.model.BooleanModel;

import java.io.File;
import java.util.*;


public class Test
{
	//
	final private static Log LOG = Log.instance(Test.class);


	//
	public static void main(String[] args)
		throws Exception
	{
		Log.root("d:/web/framework/log");


		//
		// Kernel.execute();



/*
		System.out.println("Test");

		Application application = new Application();
		application.start();

		Thread.sleep(10000);

		application.init(1);

		Thread.sleep(10000);

		application.stop();
*/

/*
		try
		{
			TestLoader loader = new TestLoader();


			//URL[] urls = { new URL("D:/Web/noname/out/production/noname/noname/test/Application.class") }; // file://
			//URLClassLoader child = new URLClassLoader(urls);

			Class classToLoad = Class.forName("com.MyClass", true, loader);
			Method method = classToLoad.getDeclaredMethod("myMethod");
			Object instance = classToLoad.newInstance();
			Object result = method.invoke(instance);
		}
		catch (Exception e)
		{
			System.out.println("exception: " + e);
		}
*/






		//
/*
		if (false)
		{
			HtmlPreset preset = new HtmlPreset                       // XXX: final static
			(
				new H1()                           // XXX -> Html.h1()
					.content("Title2"),

				new Input()                        // XXX -> Html.input()
					.type(Input.Type.TEXT)
					.name("name2")
					.value("BBB")
					.child
					(
					),

				new Input()
					.type(Input.Type.PASSWORD)
					.name("password2")
					.value("222")
			);

			//
			HtmlDocument document = new HtmlDocument(Language.RUSSIAN);
			Head head = document.head();
			Body body = document.body();

			{
				Div div1 = new Div()
					.id("DIV1");

				{
					Form form = new Form();

					{
						H1 h1 = new H1()
							.content("Title1");

						Input name = new Input()
							.type(Input.Type.TEXT)
							.name("name")
							.value("AAA");

						Input password = new Input()
							.type(Input.Type.PASSWORD)
							.name("password")
							.value("111");

						form.child(h1, name, password);
					}

					div1.child(form);
				}

				Div div2 = new Div()
					.id("DIV2")
					.child(preset);


				int i = 0;

				// language="JavaScript"
				String s =
					"var i = " + i + ";";


				body.child(div1, div2);


				IntIdentifier id = new IntIdentifier(1);
				HtmlText text = new HtmlText(id);
			}

			//
			System.out.println(document.toString());
		}
*/


/*
		if (false)
		{
			//
			int count = 1000000;
			long runtime = 0;
			int l = 0;

			Timer timer = new Timer();
			timer.start();

			for (int i = 0; i < count; i++)
			{
				HtmlPreset preset = new HtmlPreset                       // XXX: final static
				(
					new H1()
						.content("Title2"),

					new Input()
						.type(Input.Type.TEXT)
						.name("name2")
						// .attribute("1", "2")
						.value("BBB"),

					new QuasarPasswordInput()
						//.name("password2")                             // XXX
						.value("222")
				);

				//
				HtmlDocument document = new HtmlDocument(Language.RUSSIAN);
				Head head = document.head();
				Body body = document.body();

				{
					Div div2 = new Div()
						.id("DIV2")
						.child(preset);

					QuasarButton button2 = new QuasarButton();
						//.raised(true);

					body.child(div2, button2);
				}

				Utf8OutputStream output = new Utf8OutputStream();

				Timer timer2 = new Timer();
				timer2.start();

				document.toStream(output);

				timer2.stop();
				runtime += timer2.runtime();

				Utf8InputStream input = output.toInputStream();
				String s = input.toString();
				l += s.length();
				//System.out.println(s);
			}

			timer.stop();

			System.out.println("runtime: " + timer.runtime() / 1000_000_000 + "; avg: " + runtime / count + "; length: " + l); // runtime: 5; avg: 1300; length: 425000000 => ~ 200K / s
			Thread.sleep(20000);
		}
*/













/*
		String s = "";

		for (char c = Character.MIN_VALUE; c < Character.MAX_VALUE; c++)
		{
			if (!Character.isSurrogate(c)) s += c;
		}

		{
			if (!Character.isSurrogate(Character.MAX_VALUE)) s += Character.MAX_VALUE;
		}

		//
		Utf8OutputStream streamOutput = new Utf8OutputStream();
		streamOutput.write(s);
		String s2 = streamOutput.toString();

		System.out.println("s.length: " + s.length() + "; s2.length: " + s2.length() + "; " + s.equals(s2));
*/























/*
		if (false)
		{
			DatabaseProperties properties = new DatabaseProperties("//127.0.0.1/" + "d:/web/framework/db/test.fdb", "OLEG", "ZZZ", "UTF8");
			DatabaseInstance instance = new DatabaseInstance(properties, 3);

			try
			{

				instance.enable();
				//instance.init();
			}
			catch (Exception e)
			{
				System.out.println("" + e);
				e.printStackTrace();
			}

			//
			DatabaseInstance.Local.set(null, instance);

			try (DatabaseTransaction transaction = DatabaseTransaction.write())
			{
				UserItem item1 = new UserItem();
				item1.setActivity(true);
				item1.setName("AAA");
				item1.setHashcode("111");
				item1.setAccess(UserItem.ACCESS_ADMIN);
				item1.create();

				//
				transaction.commit();
			}
			catch (Exception e)
			{
				System.out.println("" + e);
				System.out.println(Arrays.toString(e.getStackTrace()));
			}


			//
			DatabaseInstance.Local.set(instance, null);

			// Thread.sleep(15000);

			instance.disable();


			System.out.println("OK");
			System.out.println();
			System.out.println();
		}
*/









/*
		if (false)
		{
			DatabaseProperties properties = new DatabaseProperties("//127.0.0.1/" + "d:/web/framework/db/test.fdb", "OLEG", "ZZZ", "UTF8");
			DatabaseInstance instance = new DatabaseInstance(properties, 3);

			try
			{

				instance.enable();
				//instance.init();
			}
			catch (Exception e)
			{
				System.out.println("" + e);
				e.printStackTrace();
			}

			//
			DatabaseInstance.Local.set(null, instance);

			try (DatabaseTransaction transaction = DatabaseTransaction.update())
			{
				LongIdentifierArray array = UserItem.list();

				for (LongIdentifier id : array)
				{
					UserItem item = new UserItem(id);
					item.load();

					System.out.println("ID: " + id + "; Name: " + item.getName() + "; Hashcode: " + item.getHashcode());
				}

				//
				transaction.commit();
			}
			catch (Exception e)
			{
				System.out.println("A: " + e);
				System.out.println(Arrays.toString(e.getStackTrace()));
			}

			//
			DatabaseInstance.Local.set(instance, null);

			// Thread.sleep(15000);

			instance.disable();
		}
*/





/*
		if (false)
		{
			DatabaseProperties properties = new DatabaseProperties("//127.0.0.1/" + "d:/web/framework/db/test.fdb", "OLEG", "ZZZ", "UTF8");
			DatabaseInstance instance = new DatabaseInstance(properties, 3);

			try
			{

				instance.enable();
				//instance.init();
			}
			catch (Exception e)
			{
				System.out.println("" + e);
				e.printStackTrace();
			}

			//
			DatabaseInstance.Local.set(null, instance);

			//
			IDatabaseOperation.IUpdateSequence<UserItem> sequence = item ->
			{
				item.load();
				item.setHashcode("333");
				item.save();
			};

			LongIdentifierArray array = UserItem.list();

			for (LongIdentifier id : array)
			{
				UserItem item = new UserItem(id);
				item.execute(sequence);
			}

			for (LongIdentifier id : array)
			{
				UserItem item = new UserItem(id);
				item.load();

				System.out.println("ID: " + id + "; Name: " + item.getName() + "; Hashcode: " + item.getHashcode());
			}


			//
			DatabaseInstance.Local.set(instance, null);

			// Thread.sleep(15000);

			instance.disable();
		}
*/





/*
		if (false)
		{
			DatabaseProperties properties = new DatabaseProperties("//127.0.0.1/" + "d:/web/framework/db/test.fdb", "OLEG", "ZZZ", "UTF8");
			DatabaseInstance instance = new DatabaseInstance(properties, 3);

			try
			{

				instance.enable();
				//instance.init();
			}
			catch (Exception e)
			{
				System.out.println("" + e);
				e.printStackTrace();
			}

			//
			DatabaseInstance.Local.set(null, instance);

			//
			LongIdentifier key = new LongIdentifier(1);

			try (DatabaseTransaction transaction = DatabaseTransaction.update())
			{
				System.out.println("1");


				UserItem item = new UserItem();
				item.setName("A");
				item.setHashcode("111");
				item.setAccess(UserItem.ACCESS_ADMIN);
				item.create();

				//id = item.identifier();

				//
				item.setName("B");
				item.save();


				key = item.key();


				System.out.println("2");

				//
				transaction.commit();
			}
			catch (Exception e)
			{
				System.out.println("A: " + e);
				System.out.println(Arrays.toString(e.getStackTrace()));
			}

			System.out.println("ID: " + key);

			try (DatabaseTransaction transaction = DatabaseTransaction.update())
			{
				System.out.println("3");


				UserItem item = new UserItem(key);
				item.load();

				item.setActivity(true);
				item.setName("C");
				item.save();


				System.out.println("4");






				//
				transaction.commit();
			}
			catch (Exception e)
			{
				System.out.println("B: " + e);
				System.out.println(Arrays.toString(e.getStackTrace()));
			}

			//
			DatabaseInstance.Local.set(instance, null);


			// Thread.sleep(15000);

			instance.disable();







			//


		}
*/












/*
		if (false)
		{
			long[] array =
			{
				Long.MIN_VALUE, Long.MIN_VALUE + 1, Long.MIN_VALUE + 2, Long.MIN_VALUE + 3, Long.MIN_VALUE + 4, Long.MIN_VALUE + 5, Long.MIN_VALUE + 6, Long.MIN_VALUE + 7, Long.MIN_VALUE + 8,
				Long.MIN_VALUE + 9, Long.MIN_VALUE + 10, Long.MIN_VALUE + 11,
				-2, -1,
				0,
				1, 2,
				Long.MAX_VALUE, Long.MAX_VALUE - 1, Long.MAX_VALUE - 2, Long.MAX_VALUE - 3, Long.MAX_VALUE - 4, Long.MAX_VALUE - 5, Long.MAX_VALUE - 6, Long.MAX_VALUE - 7, Long.MAX_VALUE - 8,
				Long.MAX_VALUE - 9, Long.MAX_VALUE - 10, Long.MAX_VALUE - 11
			};

			for (long l : array)
			{
				Long l2 = LongUtil.parse("" + l, null);
				System.out.println("l: " + l + "; l2:" + l2 + "; " + (LongUtil.equal(l, l2) ? "OK" : "ERROR"));
			}

			System.out.println();
			System.out.println();
			System.out.println();

			for (long l : array)
			{
				String s = "" + l;
				Long l2 = LongUtil.parse("**" + s + "***", 2, s.length() + 2, null);
				System.out.println("l: " + l + "; l2:" + l2 + "; " + (LongUtil.equal(l, l2) ? "OK" : "ERROR"));
			}

			System.out.println();
			System.out.println();
			System.out.println();
		}


		if (true)
		{

			String[] array2 = { "-9223372036854775809", "9223372036854775808", "--1", "++2", "-0", "+0", "+1", "+", "-" };

			for (String s : array2)
			{
				Long l2 = LongUtil.parse(s, null);
				System.out.println("s: " + s + "; l2:" + l2);
			}
		}
*/




/*
		if (true)
		{
			int[] array =
			{
				Integer.MIN_VALUE, Integer.MIN_VALUE + 1, Integer.MIN_VALUE + 2, Integer.MIN_VALUE + 3, Integer.MIN_VALUE + 4, Integer.MIN_VALUE + 5, Integer.MIN_VALUE + 6, Integer.MIN_VALUE + 7, Integer.MIN_VALUE + 8,
				Integer.MIN_VALUE + 9, Integer.MIN_VALUE + 10, Integer.MIN_VALUE + 11,
				-2, -1,
				0,
				1, 2,
				Integer.MAX_VALUE, Integer.MAX_VALUE - 1, Integer.MAX_VALUE - 2, Integer.MAX_VALUE - 3, Integer.MAX_VALUE - 4, Integer.MAX_VALUE - 5, Integer.MAX_VALUE - 6, Integer.MAX_VALUE - 7, Integer.MAX_VALUE - 8,
				Integer.MAX_VALUE - 9, Integer.MAX_VALUE - 10, Integer.MAX_VALUE - 11
			};

			for (int i : array)
			{
				Integer i2 = IntUtil.parse("" + i, null);
				System.out.println("l: " + i + "; l2:" + i2 + "; " + (IntUtil.equal(i, i2) ? "OK" : "ERROR"));
			}

			System.out.println();
			System.out.println();
			System.out.println();

			for (int i : array)
			{
				String s = "" + i;
				Integer i2 = IntUtil.parse("**" + s + "***", 2, s.length() + 2, null);
				System.out.println("l: " + i + "; l2:" + i2 + "; " + (IntUtil.equal(i, i2) ? "OK" : "ERROR"));
			}

			System.out.println();
			System.out.println();
			System.out.println();
		}


		if (true)
		{

			String[] array2 = { "-2147483649", "2147483648", "--1", "++2", "-0", "+0", "+1", "+", "-" };

			for (String s : array2)
			{
				Integer i2 = IntUtil.parse(s, null);
				System.out.println("s: " + s + "; l2:" + i2);
			}
		}
*/


		if (false)
		{
			ScriptObject object = new ScriptObject();

			object.add
			(
				new ScriptObject("FILTER")
					.add
					(
						new ScriptObject("PRODUCT")
							.add(new ScriptProperty("SELECTION", ""))
					),

				new ScriptObject("FILTER")
					.add
					(
						new ScriptObject("PRODUCT")
							.add(new ScriptArray("SELECTED"))
					),

				new ScriptObject("FILTER")
					.add
					(
						new ScriptObject("PRODUCT")
							.add(new ScriptProperty("COUNT", 0))
					)
			);

			System.out.println(object.toString());
			System.out.println();
			System.out.println(object.compose().toString());
		}








		if (false)
		{
			System.out.println("JsonDocument.1");

			String s = "{ \"A\": 1, \"B\": [ true, null, 1.01 \n], \"C\": { \"D\": \"100\" } }";
			JsonDocument document = new JsonDocument(s);

			System.out.println();

			{
				NumberJsonValue number = document.getJsonNumber("A");
				System.out.println("number: " + number.getInt());
			}

			{
				JsonArray array = document.getJsonArray("B");
				System.out.println("array.size(): " + array.size());

				System.out.println("#0 == null: " + array.isJsonNull(0));
				System.out.println("#1 == null: " + array.isJsonNull(1));
				System.out.println("#2: " + array.getJsonNumber(2));
			}

			{
				IntIdentifier.Model model = new IntIdentifier.Model("D", false, true, false, IntIdentifier.UNKNOWN, "C");
				IParser.Report<IntIdentifier> report = model.parse(document);

				System.out.println(report.EXISTS + "; " + report.DEFINED + "; " + report.VALUE);
			}
		}

		if (false)
		{
			System.out.println("JsonDocument.2");

			String s =
				"{" +
					" \"A\":" +
					" {" +
						" \"B\":" +
						" {" +
							" \"C\": [\"1\", \"2\", \"100\"], " +
							" \"D\": null" +
						" }" +

					" }" +
				"}";

			JsonDocument document = new JsonDocument(s);

			System.out.println();

			//System.out.println(document.getJsonArray(OBJECTS));

			{
				IntContainer.Model model = new IntContainer.Model("C", false, true, false, new IntContainer(0), "A", "B");
				IParser.Report<IntContainer> report = model.parse(document);

				System.out.println("C: " + report.EXISTS + "; " + report.DEFINED + "; " + report.VALUE);
			}

			{
				BooleanModel model = new BooleanModel("D", true, true, false, false, "A", "B");
				IParser.Report<Boolean> report = model.parse(document);

				System.out.println("D: " + report.EXISTS + "; " + report.DEFINED + "; " + report.VALUE + "; == null: " + (report.VALUE == null));
			}

		}

		if (false)
		{
			IntContainer.Model model = new IntContainer.Model("B", true, false, false, null, "A");

			//IntContainer container = null;

			IntContainer container = new IntContainer();
			container.add(1);
			container.add(2);
			container.add(100);

			NettyParameters parameters = new NettyParameters();
			parameters.add(model, container);


			System.out.println("'" + parameters.getString(model.alias()) + "'");


			IParser.Report<IntContainer> report = model.parse(parameters);
			System.out.println(report.EXISTS + "; " + report.DEFINED + "; " + report.VALUE);
		}

		if (false)
		{
			IntContainer.Model model = new IntContainer.Model("B", true, false, false, null, "A");

			//IntContainer container = null;

			IntContainer container = new IntContainer();
			container.add(1);
			container.add(2);
			container.add(10000);

			//
			JsonContainer container2 = new JsonContainer();

			{
				IScriptEntry entry = model.toScriptEntry(container);
				container2.add(new ScriptProperty("TEST", entry));
			}

			{
				container.add(90000);
				container2.add(model.toScriptStructure(container));
			}


			System.out.println("" + container2);

			//
			JsonDocument document = new JsonDocument(container2.toString());
			IParser.Report<IntContainer> report = model.parse(document);
			System.out.println(report.EXISTS + "; " + report.DEFINED + "; " + report.VALUE);

		}

/*
		if (false)
		{
			JsonContainer container = new JsonContainer();

			{
				UserItem item = new UserItem();
				item.setActivity(true);
				item.setName("A");
				item.setHashcode("B");
				item.setAccess(0);
				item.toJson(container);
			}

			System.out.println("container: " + container);

			{
				JsonDocument document = new JsonDocument(container.toString());

				UserItem item = new UserItem();
				item.fromJson(document);

				System.out.println();
				System.out.println(item.getActivity());
				System.out.println(item.getName());
				System.out.println(item.getHashcode());
				System.out.println(item.getAccess());
				System.out.println();
			}
		}
*/

















		// NettyUrl
		if (false)
		{
			String[] array =
			{
				"http://www.site.com/1/2.3?4=5#6",
				"http://site.com/1/2.3?4=5#6",
				"http://site.com/1//2.3?4=5#6",                                                 // ???
				"site.com/1/2.3?4=5#6",
				"site.com/1/2/3/4.5?6=7#8",
				"/index.html",

				"//",
				//"",
			};

			for (String s : array)
			{
				NettyUrl url = new NettyUrl(s);

				System.out.println("url:  " + "'" + s + "'");
				System.out.println("scheme: " + "'" + url.scheme() + "'");
				System.out.println("host: " + "'" + url.host() + "'");
				System.out.println("port: " + "'" + url.port() + "'");
				System.out.println("path: " + "'" + url.path() + "'");
				System.out.println("file: " + "'" + url.file() + "'");
				System.out.println("query: " + "'" + url.query() + "'");
				System.out.println("fragment: " + "'" + url.fragment() + "'");
				System.out.println("url2: " + "'" + url + "'");
				System.out.println();
			}
		}



		if (false)
		{
			double[][] array =
			{
				{ 1, 0, 0 },
				{ -1, 0, 0 },
				{ 0, 1, 0 },
				{ 0, -1, 0 },
				{ 0, 0, 1 },
				{ 0, 0, -1 },

				{ 1, 1, 1 },
				{ -1, -1, -1 },
			};

			for (double[] values : array)
			{
				Cartesian3dPoint pointCartesian3d = new Cartesian3dPoint();
				pointCartesian3d.set(values[0], values[1], values[2]);
				Polar3dPoint pointPolar3d = new Polar3dPoint();
				TupleUtil.convert(pointCartesian3d, pointPolar3d);
				Cartesian3dPoint pointCartesian3d2 = new Cartesian3dPoint();
				TupleUtil.convert(pointPolar3d, pointCartesian3d2);


				System.out.println("pointCartesian3d:  " + pointCartesian3d);
				System.out.println("pointPolar3d:      " + pointPolar3d);
				System.out.println("pointCartesian3d2: " + pointCartesian3d2);
				System.out.println(pointCartesian3d2.equals(pointCartesian3d, true));
				System.out.println();
			}
		}




		if (false)
		{
			Polar3dPoint pointPolar3d = new Polar3dPoint(1, Math.PI, Math.PI);
			Cartesian3dPoint pointCartesian3d = new Cartesian3dPoint();
			TupleUtil.convert(pointPolar3d, pointCartesian3d);

			System.out.println("pointPolar3d:      " + pointPolar3d);
			System.out.println("pointCartesian3d:  " + pointCartesian3d);
		}









		if (false)
		{
			String s = "<svg xmlns=\"http://www.w3.org/2000/svg\" viewBox=\"0 0 448 512\"><path d=\"M4.686 427.314L104 328l-32.922-31.029C55.958 281.851 66.666 256 88.048 256h112C213.303 256 224 266.745 224 280v112c0 21.382-25.803 32.09-40.922 16.971L152 376l-99.314 99.314c-6.248 6.248-16.379 6.248-22.627 0L4.686 449.941c-6.248-6.248-6.248-16.379 0-22.627zM443.314 84.686L344 184l32.922 31.029c15.12 15.12 4.412 40.971-16.97 40.971h-112C234.697 256 224 245.255 224 232V120c0-21.382 25.803-32.09 40.922-16.971L296 136l99.314-99.314c6.248-6.248 16.379-6.248 22.627 0l25.373 25.373c6.248 6.248 6.248 16.379 0 22.627z\"></path></svg>";
			String s2 = Utf8UrlEncoder.encode(s);
			System.out.println("s.length():   " + s.length());
			System.out.println("s2.length():  " + s2.length());
			System.out.println("data:image/svg+xml;charset=US-ASCII," + s2);
		}





		if (false)
		{
			CharSequence s = "";

		}




		System.out.println();
		System.out.println("OK");

		System.exit(0);
	}











	// XXX: http://www.unicode.org/cldr/charts/latest/supplemental/language_plural_rules.html


}
