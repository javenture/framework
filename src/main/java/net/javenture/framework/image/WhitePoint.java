package net.javenture.framework.image;


/*
	2020/04/27
 */
public enum WhitePoint
{
	/*
		https://en.wikipedia.org/wiki/Standard_illuminant#White_point
	 */
	D50("D50", 0.3457, 0.3585),
	D65("D65", 0.3127, 0.3290);


	//
	final public String ALIAS;
	final public double XW;
	final public double YW;
	final public double ZW;
	final double XWN;
	final double YWN;
	final double ZWN;
	final double XWN_DENOMINATOR;
	final double YWN_DENOMINATOR;
	final double ZWN_DENOMINATOR;


	//
	WhitePoint(String alias, double xw, double yw)
	{
		ALIAS = alias;
		XW = xw;
		YW = yw;
		ZW = 1 - xw - yw;
		XWN = XW / YW;
		YWN = 1; // YW / YW
		ZWN = ZW / YW;
		XWN_DENOMINATOR = YW / XW;
		YWN_DENOMINATOR = 1; // 1 / YWN
		ZWN_DENOMINATOR = YW / ZW;
	}

}
