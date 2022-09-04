package net.javenture.framework.script;


import net.javenture.framework.utf8.Utf8Byte;
import net.javenture.framework.utf8.Utf8OutputStream;

import java.io.IOException;


/*
	2019/08/02
 */
final public class UndefinedScriptValue
	extends AScriptValue
{
	//
	private UndefinedScriptValue()
	{
	}


	//
	@Override
	public void toUtf8Stream(Utf8OutputStream destination)
		throws IOException
	{
		destination.write(Utf8Byte.U_SMALL, Utf8Byte.N_SMALL, Utf8Byte.D_SMALL, Utf8Byte.E_SMALL, Utf8Byte.F_SMALL, Utf8Byte.I_SMALL, Utf8Byte.N_SMALL, Utf8Byte.E_SMALL, Utf8Byte.D_SMALL);
	}


	@Override
	public void toScriptStream(Utf8OutputStream destination)
		throws IOException
	{
		destination.write(Utf8Byte.U_SMALL, Utf8Byte.N_SMALL, Utf8Byte.D_SMALL, Utf8Byte.E_SMALL, Utf8Byte.F_SMALL, Utf8Byte.I_SMALL, Utf8Byte.N_SMALL, Utf8Byte.E_SMALL, Utf8Byte.D_SMALL);
	}


	@Override
	public void toJsonStream(Utf8OutputStream destination)
		throws IOException
	{
		destination.write(Utf8Byte.U_SMALL, Utf8Byte.N_SMALL, Utf8Byte.D_SMALL, Utf8Byte.E_SMALL, Utf8Byte.F_SMALL, Utf8Byte.I_SMALL, Utf8Byte.N_SMALL, Utf8Byte.E_SMALL, Utf8Byte.D_SMALL);
	}


	//
	final public static UndefinedScriptValue INSTANCE = new UndefinedScriptValue();

}
