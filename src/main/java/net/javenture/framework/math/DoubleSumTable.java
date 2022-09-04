package net.javenture.framework.math;


import net.javenture.framework.util.Validator;


/*
	2020/03/28
 */
final public class DoubleSumTable
{
	//
	final private double[][] ARRAY;


	//
	public DoubleSumTable(double[][] array, boolean square)
	{
		int height = array.length;
		int width = height != 0 ? array[0].length : 0;
		Validator.argument(height != 0, "array is empty");
		Validator.argument(width != 0, "array is empty");

		//
		ARRAY = new double[height][width];

		if (square)
		{
			{
				double value = array[0][0];
				ARRAY[0][0] = value * value;
			}

			for (int i = 1; i < width; i++)
			{
				double value = array[0][i];
				ARRAY[0][i] = value * value + ARRAY[0][i - 1];
			}

			for (int j = 1; j < height; j++)
			{
				double value = array[j][0];
				ARRAY[j][0] = value * value + ARRAY[j - 1][0];
			}

			for (int j = 1; j < height; j++)
			{
				for (int i = 1; i < width; i++)
				{
					double value = array[j][i];
					ARRAY[j][i] = value * value + ARRAY[j][i - 1] + ARRAY[j - 1][i] - ARRAY[j - 1][i - 1];
				}
			}
		}
		else
		{
			ARRAY[0][0] = array[0][0];

			for (int i = 1; i < width; i++) ARRAY[0][i] = array[0][i] + ARRAY[0][i - 1];

			for (int j = 1; j < height; j++) ARRAY[j][0] = array[j][0] + ARRAY[j - 1][0];

			for (int j = 1; j < height; j++)
			{
				for (int i = 1; i < width; i++) ARRAY[j][i] = array[j][i] + ARRAY[j][i - 1] + ARRAY[j - 1][i] - ARRAY[j - 1][i - 1];
			}
		}
	}


	public DoubleSumTable(float[][] array, boolean square)
	{
		int height = array.length;
		int width = height != 0 ? array[0].length : 0;
		Validator.argument(height != 0, "array is empty");
		Validator.argument(width != 0, "array is empty");

		//
		ARRAY = new double[height][width];

		if (square)
		{
			{
				double value = array[0][0];
				ARRAY[0][0] = value * value;
			}

			for (int i = 1; i < width; i++)
			{
				double value = array[0][i];
				ARRAY[0][i] = value * value + ARRAY[0][i - 1];
			}

			for (int j = 1; j < height; j++)
			{
				double value = array[j][0];
				ARRAY[j][0] = value * value + ARRAY[j - 1][0];
			}

			for (int j = 1; j < height; j++)
			{
				for (int i = 1; i < width; i++)
				{
					double value = array[j][i];
					ARRAY[j][i] = value * value + ARRAY[j][i - 1] + ARRAY[j - 1][i] - ARRAY[j - 1][i - 1];
				}
			}
		}
		else
		{
			ARRAY[0][0] = array[0][0];

			for (int i = 1; i < width; i++) ARRAY[0][i] = array[0][i] + ARRAY[0][i - 1];

			for (int j = 1; j < height; j++) ARRAY[j][0] = array[j][0] + ARRAY[j - 1][0];

			for (int j = 1; j < height; j++)
			{
				for (int i = 1; i < width; i++) ARRAY[j][i] = array[j][i] + ARRAY[j][i - 1] + ARRAY[j - 1][i] - ARRAY[j - 1][i - 1];
			}
		}
	}


	//
	public double value(int i, int j, int width, int height)
	{
		if (i == 0 && j == 0) return ARRAY[height - 1][width - 1];
		else if (i == 0) return ARRAY[j + height - 1][width - 1] - ARRAY[j - 1][width - 1];
		else if (j == 0) return ARRAY[height - 1][i + width - 1] - ARRAY[height - 1][i - 1];
		else return ARRAY[j + height - 1][i + width - 1] - ARRAY[j + height - 1][i - 1] - ARRAY[j - 1][i + width - 1] + ARRAY[j - 1][i - 1];
	}

}
