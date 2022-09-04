package net.javenture.framework.firebird;


import net.javenture.framework.log.LogAttachment;


/*
	2019/12/23
 */
final public class QuerySqlRequest
	implements ISqlRequest
{
	//
	final private String TEXT;
	final private boolean METADATA;


	//
	public QuerySqlRequest(String text)
	{
		this(text, false);
	}


	public QuerySqlRequest(ASqlTemplate.AQuery template)
	{
		this(template, false);
	}


	public QuerySqlRequest(SqlMarkup markup)
	{
		this(markup, false);
	}


	public QuerySqlRequest(String text, boolean metadata)
	{
		TEXT = text;
		METADATA = metadata;
	}


	public QuerySqlRequest(ASqlTemplate.AQuery template, boolean metadata)
	{
		TEXT = template.text();
		METADATA = metadata;
	}


	public QuerySqlRequest(SqlMarkup markup, boolean metadata)
	{
		ISqlTemplate template = SqlTemplate.query(markup);

		TEXT = template.text();
		METADATA = metadata;
	}


	//
	@Override
	public SqlType type()
	{
		return SqlType.QUERY;
	}


	@Override
	public String text()
	{
		return TEXT;
	}


	@Override
	public boolean metadata()
	{
		return METADATA;
	}


	@Override
	public LogAttachment log()
	{
		LogAttachment result = new LogAttachment();
		result.add(SqlType.QUERY, "TYPE");
		result.add(TEXT, "TEXT");
		result.add(METADATA, "METADATA");

		return result;
	}


	@Override
	public String toString()
	{
		return TEXT;
	}

}
