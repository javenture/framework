package net.javenture.framework.image;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.util.FileContent;
import net.javenture.framework.util.FileUtil;

import java.io.File;


/*
	2020/10/19
 */
final public class BinaryPpmI8ImageUtil
{
	//
	private BinaryPpmI8ImageUtil()
	{
	}


	//
	public static FileContent<BinaryPpmI8Image> read(File file)
	{
		FileContent<byte[]> content = FileUtil.read(file);

		return content.STATUS == FileContent.Status.OK
			? read(content.VALUE)
			: new FileContent<>(content);
	}


	public static FileContent<BinaryPpmI8Image> read(@NullDisallow byte[] array)
	{
		IBinaryPpmImage.Header header = IBinaryPpmImage.Header.parse(array);

		return read0(header, array);
	}


	static FileContent<BinaryPpmI8Image> read0(@NullAllow IBinaryPpmImage.Header header, @NullDisallow byte[] array)
	{
		return BinaryPpmI8Image.validate(header, array)
			? new FileContent<>(FileContent.Status.OK, new BinaryPpmI8Image(header, array))
			: new FileContent<>(FileContent.Status.ERROR_CONTENT);
	}

}
