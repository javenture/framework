package net.javenture.framework.quasar;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.font.IFont;

import java.util.concurrent.atomic.AtomicReference;


/*
	2019/04/30
 */
final public class QuasarConfig
{
	//
	private QuasarConfig()
	{
	}


	//
	final public static class Font
	{
		final private static AtomicReference<IFont[]> REFERENCE = new AtomicReference<>(null);

		//
		public static @NullAllow IFont[] get()
		{
			IFont[] result = REFERENCE.get();

			return result != null ? result.clone() : null;
		}

		public static void set(@NullAllow IFont[] fonts)
		{
			REFERENCE.set(fonts != null ? fonts.clone() : null);
		}

		static @NullAllow IFont[] get0()
		{
			return REFERENCE.get();
		}
	}


	//

}
