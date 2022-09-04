package net.javenture.framework.json;


/*
	2019/06/29
 */
@Deprecated
final class NotFoundJsonException                                                  // XXX: ?
	extends Exception
{
	//
	NotFoundJsonException(CharSequence name)
	{
		super(name.toString());
	}

}
