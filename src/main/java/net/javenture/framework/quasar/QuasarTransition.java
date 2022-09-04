package net.javenture.framework.quasar;


import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.utf8.IUtf8StreamableEntry;
import net.javenture.framework.utf8.Utf8OutputStream;
import net.javenture.framework.util.Validator;

import java.io.IOException;


/*
	2019/11/09
 */
final public class QuasarTransition
	implements IUtf8StreamableEntry
{
	//
	final private String NAME;
	final private IUtf8StreamableEntry ENTRY;


	//
	private QuasarTransition(@NullDisallow String name)
	{
		Validator.notNull(name, "[name]");

		NAME = name;
		ENTRY = IUtf8StreamableEntry.entry(name, true);
	}


	//
	@Override
	public void toUtf8Stream(Utf8OutputStream destination)
		throws IOException
	{
		destination.write(ENTRY);
	}


	@Override
	public String toString()
	{
		return NAME;
	}


	public String name()
	{
		return NAME;
	}


	//
	final public static QuasarTransition SLIDE_RIGHT = new QuasarTransition("slide-right");
	final public static QuasarTransition SLIDE_LEFT = new QuasarTransition("slide-left");
	final public static QuasarTransition SLIDE_UP = new QuasarTransition("slide-up");
	final public static QuasarTransition SLIDE_DOWN = new QuasarTransition("slide-down");
	final public static QuasarTransition FADE = new QuasarTransition("fade");
	final public static QuasarTransition SCALE = new QuasarTransition("scale");
	final public static QuasarTransition ROTATE = new QuasarTransition("rotate");
	final public static QuasarTransition FLIP_RIGHT = new QuasarTransition("flip-right");
	final public static QuasarTransition FLIP_LEFT = new QuasarTransition("flip-left");
	final public static QuasarTransition FLIP_UP = new QuasarTransition("flip-up");
	final public static QuasarTransition FLIP_DOWN = new QuasarTransition("flip-down");
	final public static QuasarTransition JUMP_RIGHT = new QuasarTransition("jump-right");
	final public static QuasarTransition JUMP_LEFT = new QuasarTransition("jump-left");
	final public static QuasarTransition JUMP_UP = new QuasarTransition("jump-up");
	final public static QuasarTransition JUMP_DOWN = new QuasarTransition("jump-down");

}
