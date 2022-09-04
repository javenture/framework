package net.javenture.framework.json;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.util.IParser;
import net.javenture.framework.util.NullUtil;


/*
	2019/11/21
 */
final public class NullJsonValue
	implements IJsonValue
{
	//
	final private static @NullDisallow CharSequence SEQUENCE = NullUtil.STRING;


	//
	private NullJsonValue()
	{
	}


	//
	@Override
	public Type type()
	{
		return Type.NULL;
	}


	@Override
	public String toString()
	{
		return NullUtil.STRING;
	}


	@Override
	public int length()
	{
		return SEQUENCE.length();
	}


	@Override
	public char charAt(int index)
	{
		return SEQUENCE.charAt(index);
	}


	@Override
	public CharSequence subSequence(int start, int end)
	{
		return SEQUENCE.subSequence(start, end);
	}


	@Override
	public boolean isNull()
	{
		return true;
	}


	@Override
	public @NullAllow Boolean getBoolean()
	{
		return null;
	}


	@Override
	public boolean getBoolean(boolean init)
	{
		return init;
	}


	@Override
	public @NullAllow Boolean getBoolean(@NullAllow Boolean init)
	{
		return init;
	}


	@Override
	public @NullAllow Short getShort()
	{
		return null;
	}


	@Override
	public short getShort(short init)
	{
		return init;
	}


	@Override
	public @NullAllow Short getShort(@NullAllow Short init)
	{
		return init;
	}


	@Override
	public @NullAllow Integer getInt()
	{
		return null;
	}


	@Override
	public int getInt(int init)
	{
		return init;
	}


	@Override
	public @NullAllow Integer getInt(@NullAllow Integer init)
	{
		return init;
	}


	@Override
	public @NullAllow Long getLong()
	{
		return null;
	}


	@Override
	public long getLong(long init)
	{
		return init;
	}


	@Override
	public @NullAllow Long getLong(@NullAllow Long init)
	{
		return init;
	}


	@Override
	public @NullAllow Float getFloat()
	{
		return null;
	}


	@Override
	public float getFloat(float init)
	{
		return init;
	}


	@Override
	public @NullAllow Float getFloat(@NullAllow Float init)
	{
		return init;
	}


	@Override
	public @NullAllow Double getDouble()
	{
		return null;
	}


	@Override
	public double getDouble(double init)
	{
		return init;
	}


	@Override
	public @NullAllow Double getDouble(@NullAllow Double init)
	{
		return init;
	}


	@Override
	public @NullAllow byte[] getBytes()
	{
		return null;
	}


	@Override
	public @NullAllow byte[] getBytes(@NullAllow byte[] init)
	{
		return init;
	}


	@Override
	public @NullDisallow <T> IParser.Report<T> parse(IParser<T> parser)
	{
		return parser.parse(SEQUENCE);
	}


	@Override
	public String getString()
	{
		return toString();
	}


	//
	final static NullJsonValue INSTANCE = new NullJsonValue();

}
