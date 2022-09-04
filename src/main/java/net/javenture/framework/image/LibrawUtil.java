package net.javenture.framework.image;


import net.javenture.framework.log.Log;
import net.javenture.framework.util.ByteOutputStream;
import net.javenture.framework.util.FileContent;
import net.javenture.framework.util.IPlatformObject;
import net.javenture.framework.util.Platform;
import net.javenture.framework.util.ProcessUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;


/*
	2020/11/27
 */
final public class LibrawUtil
{
	//
	final private static Class<?> CLASS = LibrawUtil.class;
	final private static Log LOG = Log.instance(CLASS);
	final private static Object LOCK = new Object();

	final public static Option OPTION_FORMAT_16_BIT = new Option("-6");

	final public static Option OPTION_WHITE_BALANCE_CAMERA = new Option("-w");
	final public static Option OPTION_WHITE_BALANCE_IMAGE = new Option("-a");

	final public static Option OPTION_BRIGHTNESS_AUTOMATICALLY_DISABLED = new Option("-W");

	final public static Option OPTION_COLOR_MATRIX_USE = new Option("+M");
	final public static Option OPTION_COLOR_MATRIX_DONT_USE = new Option("-M");

	final public static Option THRESHOLD_ZERO = new Option("-c", 0);

	final public static Option OPTION_INTERPOLATION_LINEAR = new Option("-q", 0);
	final public static Option OPTION_INTERPOLATION_VNG = new Option("-q", 1);
	final public static Option OPTION_INTERPOLATION_PPG = new Option("-q", 2);
	final public static Option OPTION_INTERPOLATION_AHD = new Option("-q", 3);
	final public static Option OPTION_INTERPOLATION_DCB = new Option("-q", 4);
	final public static Option OPTION_INTERPOLATION_DHT = new Option("-q", 11);
	final public static Option OPTION_INTERPOLATION_AAHD = new Option("-q", 12);

	final public static Option OPTION_COLORSPACE_RAW = new Option("-o", 0);
	final public static Option OPTION_COLORSPACE_SRGB = new Option("-o", 1);
	final public static Option OPTION_COLORSPACE_ADOBE = new Option("-o", 2);
	final public static Option OPTION_COLORSPACE_WIDE = new Option("-o", 3);
	final public static Option OPTION_COLORSPACE_PROPHOTO = new Option("-o", 4);
	final public static Option OPTION_COLORSPACE_XYZ = new Option("-o", 5);
	final public static Option OPTION_COLORSPACE_ACES = new Option("-o", 6);

	final public static Option OPTION_HIGHLIGHT_0_CLIP = new Option("-H", 0);
	final public static Option OPTION_HIGHLIGHT_1_UNCLIP = new Option("-H", 1);
	final public static Option OPTION_HIGHLIGHT_2_BLEND = new Option("-H", 2); // !
	final public static Option OPTION_HIGHLIGHT_3_REBUILD = new Option("-H", 3);
	final public static Option OPTION_HIGHLIGHT_4_REBUILD = new Option("-H", 4);
	final public static Option OPTION_HIGHLIGHT_5_REBUILD = new Option("-H", 5);
	final public static Option OPTION_HIGHLIGHT_6_REBUILD = new Option("-H", 6);
	final public static Option OPTION_HIGHLIGHT_7_REBUILD = new Option("-H", 7);
	final public static Option OPTION_HIGHLIGHT_8_REBUILD = new Option("-H", 8);
	final public static Option OPTION_HIGHLIGHT_9_REBUILD = new Option("-H", 9);

	final public static Option OPTION_GAMMA_LINEAR = new GammaOption(1, 1);
	final public static Option OPTION_GAMMA_SRGB = new GammaOption(2.4, 12.9);
	final public static Option OPTION_GAMMA_ADOBE_RGB = new GammaOption(2.2, 0);
	final public static Option OPTION_GAMMA_PROPHOTO_RGB = new GammaOption(1.8, 0);
	final public static Option OPTION_GAMMA_BT_709 = new GammaOption(2.222, 4.5);

	final public static Option OPTION_DENOISING_100 = new DenoisingOption(100);
	final public static Option OPTION_DENOISING_200 = new DenoisingOption(200);
	final public static Option OPTION_DENOISING_300 = new DenoisingOption(300);
	final public static Option OPTION_DENOISING_400 = new DenoisingOption(400);
	final public static Option OPTION_DENOISING_500 = new DenoisingOption(500);
	final public static Option OPTION_DENOISING_600 = new DenoisingOption(600);
	final public static Option OPTION_DENOISING_700 = new DenoisingOption(700);
	final public static Option OPTION_DENOISING_800 = new DenoisingOption(800);
	final public static Option OPTION_DENOISING_900 = new DenoisingOption(900);
	final public static Option OPTION_DENOISING_1000 = new DenoisingOption(1000);

	/*
		-aexpo e p
		Turns on exposure correction. e is exposure shift in linear scale from 0.25 (darken 2 stops) to 8.0 (lighten 3 stops).
		p is highlights preservation amount from 0.0 (no preservation, full clipping) to 1.0 (full preservation, S-like curve in highlights)
	 */
	final public static Option OPTION_EXPOSURE_DARKEN_2 = new Option("-aexpo", 0.25, 1.0);
	final public static Option OPTION_EXPOSURE_DARKEN_1 = new Option("-aexpo", 0.125, 1.0);
	final public static Option OPTION_EXPOSURE_NONE = new Option("-aexpo", 1.0, 1.0);
	final public static Option OPTION_EXPOSURE_LIGHTEN_1 = new Option("-aexpo", 2.0, 1.0);
	final public static Option OPTION_EXPOSURE_LIGHTEN_2 = new Option("-aexpo", 4.0, 1.0);
	final public static Option OPTION_EXPOSURE_LIGHTEN_3 = new Option("-aexpo", 8.0, 1.0);

	final private static Platform PLATFORM;
	final private static AtomicReference<IPlatformObject<String>> COMMAND_IDENTITY;
	final private static AtomicReference<IPlatformObject<String>> COMMAND_CONVERT;


	static
	{
		PLATFORM = Platform.detect();

		// identity
		IPlatformObject<String> commandIdentity = platform ->
		{
			switch (platform)
			{
				case WINDOWS: return "D:\\Web\\framework\\bin\\libraw\\windows\\raw-identify.exe";
				//case LINUX: throw new UnsupportedOperationException();                                                              // XXX
				//case MACOS: throw new UnsupportedOperationException();                                                              // XXX
			}

			throw new UnsupportedOperationException("platform: " + platform);
		};

		COMMAND_IDENTITY = new AtomicReference<>(commandIdentity);

		// convert
		IPlatformObject<String> commandConvert = platform ->
		{
			switch (platform)
			{
				case WINDOWS: return "D:\\Web\\framework\\bin\\libraw\\windows\\dcraw_emu.exe";
				//case LINUX: throw new UnsupportedOperationException();                                                              // XXX
				//case MACOS: throw new UnsupportedOperationException();                                                              // XXX
			}

			throw new UnsupportedOperationException("platform: " + platform);
		};

		COMMAND_CONVERT = new AtomicReference<>(commandConvert);
	}


	//
	private LibrawUtil()
	{
	}


	//
	public static void identity(File file)
	{
		// XXX


		throw new UnsupportedOperationException();
	}


	public static FileContent<IBinaryPpmImage> convert(File file, Preset preset)
	{
		FileContent<IBinaryPpmImage> result;

		synchronized (LOCK) // !
		{
			try
			{
				List<String> options = preset.OPTIONS;

				List<String> list = new ArrayList<>(options.size() + 4);
				list.add(COMMAND_CONVERT.get().value(PLATFORM));
				list.addAll(options);
				list.add("-Z"); // !
				list.add("-"); // !
				list.add(file.getPath());

				ByteOutputStream stream = new ByteOutputStream();
				ProcessUtil.Status status = ProcessUtil.start(list, stream);
				int exit = status.EXIT;
				String error = status.ERROR;

				if (!error.isEmpty()) LOG.error(error);

				//
				result = exit == 0
					? BinaryPpmImageUtil.read(stream.toBytes())
					: new FileContent<>(FileContent.Status.ERROR, new LibrawException("process exit with code: " + exit));
			}
			catch (Exception e)
			{
				result = new FileContent<>(FileContent.Status.ERROR, e);
			}
		}

		return result;
	}


	public static IPlatformObject<String> commandIdentity()
	{
		return COMMAND_IDENTITY.get();
	}


	public static void commandIdentity(IPlatformObject<String> object)
	{
		COMMAND_IDENTITY.set(object);
	}


	//
	final public static class Preset
	{
		//
		final private List<String> OPTIONS;

		//
		public Preset(Option... options)
		{
			String[] array;

			{
				int length = 0;

				for (Option option : options) length += option.ARRAY.length;

				array = new String[length];
			}

			int i = 0;

			for (Option option : options)
			{
				System.arraycopy(option.ARRAY, 0, array, i, option.ARRAY.length);
				i += option.ARRAY.length;
			}

			//
			OPTIONS = List.of(array);


			// DEL
			//System.out.println(OPTIONS);
		}
	}


	public static class Option
	{
		//
		final private String[] ARRAY;

		//
		public Option(Object... objects)
		{
			int length = objects.length;
			String[] array = new String[length];

			for (int i = 0; i < length; i++) array[i] = "" + objects[i];

			ARRAY = array;
		}
	}


	final public static class GammaOption
		extends Option
	{
		//
		public GammaOption(double pow, double slope)
		{
			super("-g", pow, slope);
		}
	}


	final public static class DenoisingOption
		extends Option
	{
		//
		public DenoisingOption(int value)
		{
			super("-n", value);
		}
	}


	final private static class LibrawException
		extends Exception
	{
		private LibrawException(String message)
		{
			super(message);
		}
	}

}
