package net.javenture.framework.json;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.util.BooleanUtil;
import net.javenture.framework.util.IParser;


/*
	2019/11/21
 */
final public class BooleanJsonValue
	implements IJsonValue
{
	//
	final private boolean VALUE;
	final private @NullDisallow CharSequence SEQUENCE;


	//
	private BooleanJsonValue(boolean value)
	{
		VALUE = value;
		SEQUENCE = value ? BooleanUtil.True.STRING : BooleanUtil.False.STRING;
	}


	//
	@Override
	public Type type()
	{
		return Type.BOOLEAN;
	}


	@Override
	public String toString()
	{
		return VALUE ? BooleanUtil.True.STRING : BooleanUtil.False.STRING;
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
		return false;
	}


	@Override
	public @NullDisallow Boolean getBoolean()
	{
		return VALUE;
	}


	@Override
	public boolean getBoolean(boolean init)
	{
		return VALUE;
	}


	@Override
	public @NullAllow Boolean getBoolean(@NullAllow Boolean init)
	{
		return VALUE;
	}


	@Override
	public @NullDisallow Short getShort()
		throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException();
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
	public @NullDisallow Integer getInt()
		throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException();
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
	public @NullDisallow Long getLong()
		throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException();
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
	public @NullDisallow Float getFloat()
		throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException();
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
	public @NullDisallow Double getDouble()
		throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException();
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
	public @NullDisallow byte[] getBytes()
		throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException();
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
	final static BooleanJsonValue TRUE = new BooleanJsonValue(true);
	final static BooleanJsonValue FALSE = new BooleanJsonValue(false);

}
