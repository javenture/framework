package net.javenture.framework.json;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.hex.HexUtil;
import net.javenture.framework.util.BooleanUtil;
import net.javenture.framework.util.DoubleUtil;
import net.javenture.framework.util.FloatUtil;
import net.javenture.framework.util.IParser;
import net.javenture.framework.util.IntUtil;
import net.javenture.framework.util.LongUtil;
import net.javenture.framework.util.ShortUtil;


/*
	2019/11/21
 */
final public class StringJsonValue
	implements IJsonValue
{
	//
	final private @NullDisallow CharSequence SEQUENCE;


	//
	StringJsonValue(@NullDisallow CharSequence sequence)
	{
		SEQUENCE = sequence;
	}


	//
	@Override
	public Type type()
	{
		return Type.STRING;
	}


	@Override
	public String toString()
	{
		return SEQUENCE.toString();
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
		throws Exception
	{
		return BooleanUtil.parse(SEQUENCE);
	}


	@Override
	public boolean getBoolean(boolean init)
	{
		return BooleanUtil.parse(SEQUENCE, init);
	}


	@Override
	public @NullAllow Boolean getBoolean(@NullAllow Boolean init)
	{
		return BooleanUtil.parse(SEQUENCE, init);
	}


	@Override
	public @NullDisallow Short getShort()
		throws Exception
	{
		return ShortUtil.parse(SEQUENCE);
	}


	@Override
	public short getShort(short init)
	{
		return ShortUtil.parse(SEQUENCE, init);
	}


	@Override
	public @NullAllow Short getShort(@NullAllow Short init)
	{
		return ShortUtil.parse(SEQUENCE, init);
	}


	@Override
	public @NullDisallow Integer getInt()
		throws Exception
	{
		return IntUtil.parse(SEQUENCE);
	}


	@Override
	public int getInt(int init)
	{
		return IntUtil.parse(SEQUENCE, init);
	}


	@Override
	public @NullAllow Integer getInt(@NullAllow Integer init)
	{
		return IntUtil.parse(SEQUENCE, init);
	}


	@Override
	public @NullDisallow Long getLong()
		throws Exception
	{
		return LongUtil.parse(SEQUENCE);
	}


	@Override
	public long getLong(long init)
	{
		return LongUtil.parse(SEQUENCE, init);
	}


	@Override
	public @NullAllow Long getLong(@NullAllow Long init)
	{
		return LongUtil.parse(SEQUENCE, init);
	}


	@Override
	public @NullDisallow Float getFloat()
		throws Exception
	{
		return FloatUtil.parse(SEQUENCE);
	}


	@Override
	public float getFloat(float init)
	{
		return FloatUtil.parse(SEQUENCE, init);
	}


	@Override
	public @NullAllow Float getFloat(@NullAllow Float init)
	{
		return FloatUtil.parse(SEQUENCE, init);
	}


	@Override
	public @NullDisallow Double getDouble()
		throws Exception
	{
		return DoubleUtil.parse(SEQUENCE);
	}


	@Override
	public double getDouble(double init)
	{
		return DoubleUtil.parse(SEQUENCE, init);
	}


	@Override
	public @NullAllow Double getDouble(@NullAllow Double init)
	{
		return DoubleUtil.parse(SEQUENCE, init);
	}


	@Override
	public @NullDisallow byte[] getBytes()
		throws Exception
	{
		return HexUtil.bytes(SEQUENCE);
	}


	@Override
	public @NullAllow byte[] getBytes(@NullAllow byte[] init)
	{
		return HexUtil.bytes(SEQUENCE, init);
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

}
