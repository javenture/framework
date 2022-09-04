package net.javenture.framework.image;


import net.javenture.framework.log.Log;
import net.javenture.framework.test.TempFileUtil;
import net.javenture.framework.util.FileContent;
import net.javenture.framework.util.IPlatformObject;
import net.javenture.framework.util.Platform;
import net.javenture.framework.util.ProcessUtil;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;


/*
	2020/10/19
 */
final public class GprUtil
{
	//
	final private static Class<?> CLASS = GprUtil.class;
	final private static Log LOG = Log.instance(CLASS);
	final private static Object LOCK = new Object();

	final private static Platform PLATFORM;
	final private static AtomicReference<IPlatformObject<String>> COMMAND;


	static
	{
		PLATFORM = Platform.detect();

		//
		IPlatformObject<String> command = platform ->
		{
			switch (platform)
			{
				case WINDOWS: return "D:\\Web\\framework\\bin\\gpr\\windows\\gpr_tools_windows.exe";
				case LINUX: return "/home/web/framework/bin/gpr/linux/gpr_tools_linux";
				//case MACOS: throw new UnsupportedOperationException();                                                              // XXX
			}

			throw new UnsupportedOperationException("platform: " + platform);
		};

		COMMAND = new AtomicReference<>(command);
	}


	//
	private GprUtil()
	{
	}


	//
	public static FileContent<IBinaryPpmImage> convert(File file, LibrawUtil.Preset preset)
	{
		FileContent<IBinaryPpmImage> result;

		synchronized (LOCK) // !
		{
			File fileTemp = TempFileUtil.create();                                                                                // XXX: custom
			File fileTempGpr = null;
			File fileTempDng = null;

			try
			{
				if (fileTemp != null)
				{
					fileTempGpr = new File(fileTemp.getParent(), fileTemp.getName() + ".gpr"); // ! ".gpr"
					fileTempDng = new File(fileTemp.getParent(), fileTemp.getName() + ".dng"); // ! ".dng"
					Files.copy(file.toPath(), fileTempGpr.toPath(), StandardCopyOption.REPLACE_EXISTING);                                                // ???

					List<String> list = new ArrayList<>(5);
					list.add(COMMAND.get().value(PLATFORM));
					list.add("-i");
					list.add(fileTempGpr.getPath());
					list.add("-o");
					list.add(fileTempDng.getPath());

					ProcessUtil.Status status = ProcessUtil.start(list);
					int exit = status.EXIT;
					String error = status.ERROR;

					if (!error.isEmpty()) LOG.error(error);                                    // ???

					result = exit == 0
						? LibrawUtil.convert(fileTempDng, preset)
						: new FileContent<>(FileContent.Status.ERROR, new GprException("process exit with code: " + exit));
				}
				else
				{
					result = new FileContent<>(FileContent.Status.ERROR, new GprException("unable to create temp file"));
				}
			}
			catch (Exception e)
			{
				result = new FileContent<>(FileContent.Status.ERROR, e);
			}

			//
			try
			{
				if (fileTemp != null && fileTemp.exists() && !fileTemp.delete()) throw new Exception();
			}
			catch (Exception ignore)
			{
				LOG.error("unable to delete temp file: '" + fileTemp.getPath() + "'");
			}

			try
			{
				if (fileTempGpr != null && fileTempGpr.exists() && !fileTempGpr.delete()) throw new Exception();
			}
			catch (Exception ignore)
			{
				LOG.error("unable to delete GPR temp file: '" + fileTempGpr.getPath() + "'");
			}

			try
			{
				if (fileTempDng != null && fileTempDng.exists() && !fileTempDng.delete()) throw new Exception();
			}
			catch (Exception ignore)
			{
				LOG.error("unable to delete DNG temp file: '" + fileTempDng.getPath() + "'");
			}
		}

		return result;
	}


	public static IPlatformObject<String> command()
	{
		return COMMAND.get();
	}


	public static void command(IPlatformObject<String> object)
	{
		COMMAND.set(object);
	}


	//
	final private static class GprException
		extends Exception
	{
		private GprException(String message)
		{
			super(message);
		}
	}

}
