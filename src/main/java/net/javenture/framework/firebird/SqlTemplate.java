package net.javenture.framework.firebird;


/*
	2019/08/17
 */
final public class SqlTemplate
{
	//
	private SqlTemplate()
	{
	}


	//
	public static ISqlTemplate query(SqlMarkup markup)
	{
		return new ASqlTemplate.AQuery()
		{
			@Override
			final protected SqlMarkup _markup()
			{
				return markup;
			}
		};
	}


	public static ISqlTemplate update(SqlMarkup markup)
	{
		return new ASqlTemplate.AUpdate()
		{
			@Override
			final protected SqlMarkup _markup()
			{
				return markup;
			}
		};
	}


	public static ISqlTemplate batch(SqlMarkup markup)
	{
		return new ASqlTemplate.ABatch()
		{
			@Override
			final protected SqlMarkup _markup()
			{
				return markup;
			}
		};
	}

}
