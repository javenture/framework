package net.javenture.framework.image;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.util.FileContent;
import net.javenture.framework.util.FileUtil;

import java.io.File;


/*
	2020/10/19
 */
final public class BinaryPpmImageUtil
{
	//
	private BinaryPpmImageUtil()
	{
	}


	//
	public static FileContent<IBinaryPpmImage> read(File file)
	{
		FileContent<IBinaryPpmImage> result;
		FileContent<byte[]> content = FileUtil.read(file);

		result = content.STATUS == FileContent.Status.OK
			? read(content.VALUE)
			: new FileContent<>(content);

		return result;
	}


	public static FileContent<IBinaryPpmImage> read(byte[] array)
	{
		IBinaryPpmImage.Header header = IBinaryPpmImage.Header.parse(array);

		return read0(header, array);
	}


	static FileContent<IBinaryPpmImage> read0(@NullAllow IBinaryPpmImage.Header header, @NullDisallow byte[] array)
	{
		if (header != null)
		{
			switch (header.type())
			{
				case I8:
				{
					FileContent<BinaryPpmI8Image> content = BinaryPpmI8ImageUtil.read0(header, array);

					return new FileContent<>(content.STATUS, content.VALUE, content.EXCEPTION);
				}

				case I16:
				{
					FileContent<BinaryPpmI16Image> content = BinaryPpmI16ImageUtil.read0(header, array);

					return new FileContent<>(content.STATUS, content.VALUE, content.EXCEPTION);
				}

				default: throw new RuntimeException(); // !
			}
		}

		return new FileContent<>(FileContent.Status.ERROR_CONTENT);
	}

}
