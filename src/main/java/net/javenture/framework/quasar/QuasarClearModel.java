package net.javenture.framework.quasar;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.json.JsonObject;
import net.javenture.framework.netty.NettyParameters;
import net.javenture.framework.util.IParser;
import net.javenture.framework.model.AModel;
import net.javenture.framework.script.IScriptEntry;


/*
	2019/10/04
 */
final public class QuasarClearModel
	extends AModel
{
	//
	final private static String SUFFIX = "$CLEAR";


	//
	final private AModel MODEL;


	//
	public QuasarClearModel(AModel<?> model)                                                             // XXX: String suffix
	{
		super(model.name() + SUFFIX, model.objects());

		MODEL = model;
	}


	@Override
	public boolean nullable()
	{
		return MODEL.nullable();
	}


	@Override
	public boolean quotation()
	{
		return MODEL.quotation();
	}


	@Override
	public boolean trim()
	{
		return MODEL.trim();
	}


	@Override
	public @NullAllow Object init()
	{
		return MODEL.init();
	}


	@Override
	public @NullDisallow IScriptEntry toScriptEntry()
	{
		return MODEL.toScriptEntry();
	}


	@Override
	public @NullDisallow IScriptEntry toScriptEntry(@NullAllow Object value)
	{
		return MODEL.toScriptEntry(value);
	}


	@Override
	public void toNettyParameter(NettyParameters destination)
	{
		throw new UnsupportedOperationException();
	}


	@Override
	public void toNettyParameter(Object value, NettyParameters destination)
	{
		throw new UnsupportedOperationException();
	}


	@Override
	public IParser.Report parse(CharSequence source)
	{
		throw new UnsupportedOperationException();
	}


	@Override
	public IParser.Report parse(@NullAllow JsonObject source)
	{
		throw new UnsupportedOperationException();
	}


	@Override
	public boolean defined(Object value)
	{
		throw new UnsupportedOperationException();
	}

}
