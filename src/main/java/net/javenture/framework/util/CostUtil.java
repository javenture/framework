package net.javenture.framework.util;


final public class CostUtil
{
	final private static Class<?> CLASS = CostUtil.class;


	//
	private CostUtil()
	{
	}


	//
	public static boolean equal(double value1, double value2, double threshold)
	{
		double difference = Math.abs(value1 - value2);

		return !(difference > threshold);
	}


	public static boolean greater(double value1, double value2, double threshold)
	{
		double difference = value1 - value2;

		return difference > 0 && Math.abs(difference) > threshold;
	}


	public static boolean lesser(double value1, double value2, double threshold)
	{
		double difference = value1 - value2;

		return difference < 0 && Math.abs(difference) > threshold;
	}


	public static double calculate(double cost, double markup)
	{
		return cost * (100 + markup) / 100;
	}


	public static double markup(double cost, double percent)
	{
		return calculate(cost, percent) - cost;
	}


	public static double percent(double smaller, double bigger)
	{
		Validator.argument(smaller != 0, "division by zero");

		return (bigger - smaller) / smaller * 100;
	}

}
