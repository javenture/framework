package net.javenture.framework.utf8;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.hex.HexUtil;
import net.javenture.framework.util.Validator;


/*
	2019/06/03
 */
final public class Utf8UrlDecoder
{
	//
	private Utf8UrlDecoder()
	{
	}


	//
	public static @NullAllow String decode(@NullDisallow String value)
	{
		return decode(value, 0, value.length());
	}


	public static @NullAllow String decode(@NullDisallow String value, int from, int to)
	{
		Validator.argument(from >= 0, "[from] (", from, ") < 0");
		Validator.argument(to <= value.length(), "[to] (", to, ") > [value.length()] (", value.length(), ")");

		//
        int length = to - from;

        if (length > 0) // > 0 !
		{
			int first = -1;

			for (int i = from; i < to; i++)
			{
				char c = value.charAt(i);

				if (c == '%' || c == '+')
				{
					first = i;

					break;
				}
			}

			if (first != -1)
			{
				char[] result = new char[length];
				int count = 0;
				boolean error = false;

				Utf8Coder coder = new Utf8Coder(length / 3);
				int i = from;

				while (i < to)
				{
					char c = value.charAt(i);

					if (c == '%')
					{
						coder.init();

						do
						{
							if (i + 2 < to)
							{
								Byte b = HexUtil.convert(value.charAt(i + 1), value.charAt(i + 2));
								i += 3;

								if (b != null)
								{
									coder.write(b);
								}
								else
								{
									error = true;

									break;
								}
							}
							else
							{
								error = true;
								i += 3;

								break;
							}
						} while (i < to && value.charAt(i) == '%');

						while (true)
						{
							int c2 = coder.read();

							if (c2 != -1) result[count++] = (char) c2;
							else break;
						}
					}
					else if (c == '+')
					{
						result[count++] = ' ';
						i++;
					}
					else
					{
						result[count++] = c;
						i++;
					}
				}

				if (!error) return count != 0 ? new String(result, 0, count) : "";
				else return null;
			}
			else
			{
				return value.substring(from, to);
			}
		}
		else
		{
			return "";
		}
	}

}
