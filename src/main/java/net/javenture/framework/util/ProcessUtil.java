package net.javenture.framework.util;


import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.utf8.Utf8OutputStream;

import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;


/*
	2020/10/13
 */
final public class ProcessUtil
{
	//
	final private static int RUNTIME_TIMEOUT = 100; // 0.1 s
	final private static int RUNTIME_LIMIT = 30_000; // 30 s
	final private static byte[] BUFFER = new byte[64 * 1024];                                                          // ???: size


	//
	private ProcessUtil()
	{
	}


	//
	public static Status start(List<String> command)
		throws Exception
	{
		Process process = new ProcessBuilder(command)
			.start();

		long runtime = 0;

		while (true)
		{
			if (process.waitFor(RUNTIME_TIMEOUT, TimeUnit.MILLISECONDS))
			{
				break;
			}
			else
			{
				runtime += RUNTIME_TIMEOUT;

				if (runtime > RUNTIME_LIMIT)
				{
					process.destroyForcibly();

					throw new Exception("runtime limit exceeded");
				}
			}
		}

		//
		int exit = process.exitValue();
		InputStream streamErrorInput = process.getErrorStream();
		Utf8OutputStream streamErrorOutput = new Utf8OutputStream();
		StreamUtil.transfer(streamErrorInput, streamErrorOutput, BUFFER);
		String error = streamErrorOutput.toString();

		return new Status(exit, error);
	}


	public static Status start(List<String> command, @NullDisallow ByteOutputStream output)
		throws Exception
	{
		Process process = new ProcessBuilder(command)
			.start();

		InputStream input = process.getInputStream();
		long runtime = 0;

		while (true)
		{
			while (true)
			{
				int read = input.read(BUFFER);

				if (read > 0)
				{
					// DEL
					//System.out.println("read: " + read);


					output.write(BUFFER, 0, read);                                                         // XXX: optimize
				}
				else
				{
					// DEL
					//System.out.println("read: break");

					break;
				}
			}

			if (process.waitFor(RUNTIME_TIMEOUT, TimeUnit.MILLISECONDS))
			{
				break;
			}
			else
			{
				runtime += RUNTIME_TIMEOUT;

				if (runtime > RUNTIME_LIMIT)
				{
					process.destroyForcibly();

					throw new Exception("runtime limit exceeded");
				}
			}
		}

		//
		int exit = process.exitValue();
		InputStream streamErrorInput = process.getErrorStream();
		Utf8OutputStream streamErrorOutput = new Utf8OutputStream();
		StreamUtil.transfer(streamErrorInput, streamErrorOutput, BUFFER);
		String error = streamErrorOutput.toString();

		return new Status(exit, error);
	}


	//
	final public static class Status
	{
		//
		final public int EXIT;
		final public String ERROR;

		//
		private Status(int exit, String error)
		{
			EXIT = exit;
			ERROR = error;
		}
	}

}
