package net.javenture.framework.firebird;


import net.javenture.framework.log.LogAttachment;


/*
	2019/12/23
 */
final public class UpdateSqlRequest
	implements ISqlRequest
{
	//
	final private String TEXT;


	//
	public UpdateSqlRequest(String text)
	{
		TEXT = text;
	}


	public UpdateSqlRequest(ASqlTemplate.AUpdate template)
	{
		TEXT = template.text();
	}


	public UpdateSqlRequest(SqlMarkup markup)
	{
		ISqlTemplate template = SqlTemplate.update(markup);
		TEXT = template.text();
	}


	//
	@Override
	public SqlType type()
	{
		return SqlType.UPDATE;
	}


	@Override
	public String text()
	{
		return TEXT;
	}


	@Override
	public boolean metadata()
	{
		return false;
	}


	@Override
	public LogAttachment log()
	{
		LogAttachment result = new LogAttachment();
		result.add(SqlType.UPDATE, "TYPE");
		result.add(TEXT, "TEXT");

		return result;
	}


	@Override
	public String toString()
	{
		return TEXT;
	}

}
