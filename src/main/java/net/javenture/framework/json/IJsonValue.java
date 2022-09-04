package net.javenture.framework.json;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.util.IParser;


/*
	2019/11/21
 */
public interface IJsonValue
	extends IJsonEntry, CharSequence
{
	//
	boolean isNull();

	@NullAllow Boolean getBoolean() throws Exception;
	boolean getBoolean(boolean init);
	@NullAllow Boolean getBoolean(@NullAllow Boolean init);

	@NullAllow Short getShort() throws Exception;
	short getShort(short init);
	@NullAllow Short getShort(@NullAllow Short init);

	@NullAllow Integer getInt() throws Exception;
	int getInt(int init);
	@NullAllow Integer getInt(@NullAllow Integer init);

	@NullAllow Long getLong() throws Exception;
	long getLong(long init);
	@NullAllow Long getLong(@NullAllow Long init);

	@NullAllow Float getFloat() throws Exception;
	float getFloat(float init);
	@NullAllow Float getFloat(@NullAllow Float init);

	@NullAllow Double getDouble() throws Exception;
	double getDouble(double init);
	@NullAllow Double getDouble(@NullAllow Double init);

	@NullAllow byte[] getBytes() throws Exception;
	@NullAllow byte[] getBytes(@NullAllow byte[] init);

	@NullDisallow <T> IParser.Report<T> parse(IParser<T> parser);

	String getString();

}
