package net.javenture.framework.firebird;


/*
	2019/08/21
 */
final public class DatabaseProperties
{
	//
	final String URL;
	final String USER;
	final String PASSWORD;
	final String CHARSET;


	//
	public DatabaseProperties(String url, String user, String password, String charset)
	{
		URL = url;
		USER = user;
		PASSWORD = password;
		CHARSET = charset;
	}

}
