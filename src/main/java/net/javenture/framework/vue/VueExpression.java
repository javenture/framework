package net.javenture.framework.vue;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.model.AModel;
import net.javenture.framework.utf8.IUtf8StreamableEntry;
import net.javenture.framework.utf8.Utf8OutputStream;

import java.io.IOException;


/*
	2019/10/04
 */
@Deprecated
final public class VueExpression                     // XXX: VueExpression -> VueInlineExpression?                                                   // XXX: -> ScriptExpression
	implements IUtf8StreamableEntry
{
	//
	final private static String NULL = "null";


	//
	final private @NullAllow String VALUE;


	//
	public VueExpression(@NullAllow String value)
	{
		VALUE = value;
	}


	//
	@Override
	public void toUtf8Stream(Utf8OutputStream destination)
		throws IOException
	{
		destination.write(VALUE);
	}



	// XXX: statement increment
	// XXX: statement decrement


	public static <T> VueExpression isEqual(AModel<T> model, @NullAllow T value)                                     // XXX: ScriptComparison() | .comparisonIsEqual()
	{
		if (value != null)
		{
			boolean quotation = model.quotation();

			return new VueExpression(model.alias() + " === " + (quotation ? "'" + value + "'" : "" + value));
		}
		else
		{
			return new VueExpression(model.alias() + " === " + NULL);
		}
	}


	public static <T> VueExpression isEqual(AModel<T> model0, AModel<T> model1)                             // XXX: boolean strict
	{
		return new VueExpression(model0.alias() + " === " + model1.alias());
	}


	public static <T> VueExpression notEqual(AModel<T> model, T value)                                         // XXX: boolean strict
	{
		if (value != null)
		{
			boolean quotation = model.quotation();

			return new VueExpression(model.alias() + " !== " + (quotation ? "'" + value + "'" : "" + value));
		}
		else
		{
			return new VueExpression(model.alias() + " !== " + NULL);
		}
	}


	public static <T> VueExpression notEqual(AModel<T> model0, AModel<T> model1)
	{
		return new VueExpression(model0.alias() + " !== " + model1.alias());
	}


}
