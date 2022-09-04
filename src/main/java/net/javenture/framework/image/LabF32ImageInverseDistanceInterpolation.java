package net.javenture.framework.image;


import net.javenture.framework.math.MathUtil;

import java.util.Arrays;


/*
	2020/02/26
 */
final public class LabF32ImageInverseDistanceInterpolation
{
	/*
		Modified Shepard's method
		https://en.wikipedia.org/wiki/Inverse_distance_weighting
	 */
	final private static double THRESHOLD = 0.00001;


	//
	final private int WIDTH;
	final private int HEIGHT;
	final private double RADIUS;
	final private double RADIUS2;
	final private int SHIFT;
	final private Object[][] ARRAY;


	//
	public LabF32ImageInverseDistanceInterpolation(int width, int height, int radius)
	{
		WIDTH = width;
		HEIGHT = height;
		RADIUS = radius + 0.5;
		RADIUS2 = (radius + 0.5) * (radius + 0.5);
		SHIFT = radius;
		ARRAY = new Object[height][width];
	}


	//
	public void init()
	{
		for (int y = 0; y < HEIGHT; y++)
		{
			for (int x = 0; x < WIDTH; x++) ARRAY[y][x] = null;
		}
	}


	public void add(double x, double y, LabF32Color color)
	{
		int x0 = (int) (x + 0.5);
		int y0 = (int) (y + 0.5);
		Entry entry = new Entry(x + 0.5 - x0, y + 0.5 - y0, color);
		Object object0 = ARRAY[y0][x0];

		if (object0 == null)
		{
			ARRAY[y0][x0] = entry;
		}
		else if (object0 instanceof Entry)
		{
			ARRAY[y0][x0] = new Entry[]
			{
				(Entry) object0,
				entry
			};
		}
		else if (object0 instanceof Entry[])
		{
			Entry[] array0 = (Entry[]) object0;
			int length = array0.length;
			Entry[] array = Arrays.copyOf(array0, length + 1);
			array[length] = entry;
			ARRAY[y0][x0] = array;
		}
	}


	public void calculate(int x, int y, LabF32Color destination)
	{
		int count = 0;
		double numeratorL = 0;
		double numeratorA = 0;
		double numeratorB = 0;
		double denominator = 0;

		double x0 = x + 0.5;
		double y0 = y + 0.5;

		int iMin = x - SHIFT;
		int iMax = x + SHIFT;
		int jMin = y - SHIFT;
		int jMax = y + SHIFT;

		if (iMin < 0) iMin = 0;

		if (iMax > WIDTH - 1) iMax = WIDTH - 1;

		if (jMin < 0) jMin = 0;

		if (jMax > HEIGHT - 1) jMax = HEIGHT - 1;

		//
		for (int j = jMin; j <= jMax; j++)
		{
			for (int i = iMin; i <= iMax; i++)
			{
				Object object = ARRAY[j][i];

				if (object instanceof Entry)
				{
					Entry entry = (Entry) object;
					count++;
					double dx = (double) i + entry.X - x0;
					double dy = (double) j + entry.Y - y0;
					double d2 = dx * dx + dy * dy;

					if (d2 < THRESHOLD)
					{
						destination.lab0(entry.L, entry.A, entry.B);

						return;
					}
					else if (d2 < RADIUS2)
					{
						double d = MathUtil.sqrt(d2);
						double w = (RADIUS - d) / (RADIUS * d);
						double w2 = w * w;

						numeratorL += entry.L * w2;
						numeratorA += entry.A * w2;
						numeratorB += entry.B * w2;
						denominator += w2;
					}
				}
				else if (object instanceof Entry[])
				{
					Entry[] entries = (Entry[]) object;

					for (Entry entry : entries)
					{
						count++;
						double dx = (double) i + entry.X - x0;
						double dy = (double) j + entry.Y - y0;
						double d2 = dx * dx + dy * dy;

						if (d2 < THRESHOLD)
						{
							destination.lab0(entry.L, entry.A, entry.B);

							return;
						}
						else if (d2 < RADIUS2)
						{
							double d = MathUtil.sqrt(d2);
							double w = (RADIUS - d) / (RADIUS * d);
							double w2 = w * w;

							numeratorL += entry.L * w2;
							numeratorA += entry.A * w2;
							numeratorB += entry.B * w2;
							denominator += w2;
						}
					}
				}
			}
		}

		//
		if (count != 0)
		{
			double k = 1 / denominator;
			double l = numeratorL * k;
			double a = numeratorA * k;
			double b = numeratorB * k;
			destination.lab0(l, a, b);
		}
		else
		{
			destination.lab0(0, 0, 0);
		}
	}


	public void metric()
	{
		int countNull = 0;
		int countObject = 0;
		int countArray = 0;
		int countEntry = 0;

		for (int y = 0; y < HEIGHT; y++)
		{
			for (int x = 0; x < WIDTH; x++)
			{
				Object object = ARRAY[y][x];

				if (object == null)
				{
					countNull++;
				}
				else if (object instanceof Entry)
				{
					countObject++;
					countEntry++;
				}
				else if (object instanceof Entry[])
				{
					countArray++;
					countEntry += ((Entry[]) object).length;
				}
			}
		}


		System.out.println("countNull: " + countNull);
		System.out.println("countObject: " + countObject);
		System.out.println("countArray: " + countArray);
		System.out.println("countEntry: " + countEntry);
	}


	//
	final private static class Entry
	{
		//
		final private float X;
		final private float Y;
		final private float L;
		final private float A;
		final private float B;

		//
		private Entry(double x, double y, LabF32Color color)
		{
			X = (float) x;
			Y = (float) y;
			L = color.l();
			A = color.a();
			B = color.b();
		}
	}

}
