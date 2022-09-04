package net.javenture.framework.kernel;


import net.javenture.framework.firebird.*;
import net.javenture.framework.identifier.LongIdentifier;
import net.javenture.framework.monument.entity.user.UserItem;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;


public class Kernel
{
	/*
		TODO
		- Item lock/unlock (user, session)
	 */







	final private static Class<?> CLASS = Kernel.class;


	final public static String PROJECT_NAME = "Framework";                                           // XXX
	final public static String PROJECT_ALIAS = "net/javenture/framework";
	final public static String DATABASE_ALIAS = "noname";

	final public static String ENCODING_UTF8 = "UTF8";

	final public static Charset CHARSET_ISO = StandardCharsets.ISO_8859_1;
	final public static Charset CHARSET_UTF8 = StandardCharsets.UTF_8; // Charset.forName("UTF-8");


	final public static String ROOT = "/";
	final public static String ROOT_LOG = "/log/";


	final public static String PROJECT_ROOT = "D:/Web/"; // "/home/web/" | "D:/Web/"


	//
	public Kernel()
	{
		// клиент при авторизации на главном сервере получает адрес сервера с котором будет в дальнейнем работать



		// for (clients)
		// new Instanse(client)
		// new DatabaseManager(instance)





		// HttpRequest -> Session ID -> Client ID -> ThreadLoacal(client) -> Instance -> DatabaseManager



		// Client list: add -> reload instances



		// database.onConnect() { ... }


	}


	public static void execute()
	{
		// run app from server (parameter: port)
		// register workers + ports in server

		// load config (app + instance)
		// search actual database

		// enable + check database
		// start processes
		// enable http

		// ...

		// disable http
		// stop processes
		// disable database





		/*
			MyClassLoader mycl = new MyClassLoader(urlarray);
			Class<?> myclass = mycl.loadClass("USAGE"); // get the class
			Method m = myclass.getMethod("main", String[].class); // get the method you want to call
			String[] args = new String[0]; // the arguments. Change this if you want to pass different args
			m.invoke(null, args);  // invoke the method


			ClassLoader Application - parent
			ClassLoader Instance(Application)


		 */


		{
			DatabaseProperties properties = new DatabaseProperties("//127.0.0.1/" + "d:/web/framework/db/noname.fdb", "OLEG", "ZZZ", "UTF8");
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
			LongIdentifier id = new LongIdentifier(1);

			try (DatabaseTransaction transaction = DatabaseTransaction.write())
			{
				UserItem item = new UserItem(id);
				item.setName("A");
				item.setHashcode("111");
				item.setAccess(UserItem.ACCESS_ADMIN);
				item.create();

				//id = item.identifier();

				//
				item.setName("B");
				item.save();

				//
				transaction.commit();
			}
			catch (Exception e)
			{
				System.out.println("" + e);
			}

			try (DatabaseTransaction transaction = DatabaseTransaction.write())
			{
				UserItem item = new UserItem(id);
				item.load();

				item.setName("C");
				item.save();



				//
				transaction.commit();
			}
			catch (Exception e)
			{
				System.out.println("" + e);
			}

			//
			DatabaseInstance.Local.set(instance, null);
			instance.disable();
		}


		System.gc();
		System.gc();

	}



/*
	private static String getLocationWww()
	{
		return PROJECT_ROOT + PROJECT_ALIAS + "/www";
	}
*/


/*
	public static String getRealPath(String url)                         // XXX ?
	{
		return getLocationWww() + url;
	}
*/


}
