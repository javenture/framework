package net.javenture.framework.image;


import net.javenture.framework.boofcv.BoofcvGrayF32Util;

import boofcv.abst.feature.describe.ConfigBrief;
import boofcv.abst.feature.detect.interest.InterestPointDetector;
import boofcv.alg.descriptor.DescriptorDistance;
import boofcv.factory.feature.detect.interest.FactoryInterestPoint;
import boofcv.struct.feature.TupleDesc_B;
import boofcv.struct.image.GrayF32;
import georegression.struct.point.Point2D_F64;

import java.util.ArrayList;


/*
	2021/01/23
 */
final public class DualF32ImageFeaturesAssociateUtil
{
/*
	//
	public static ArrayList<Entry> associate(LabF32Image imageLab0, LabF32Image imageLab1, float scale)
	{
		GrayF32 imageGray0 = BoofcvGrayF32Util.convert(imageLab0, LabF32Color.Channel.L, scale);
		GrayF32 imageGray1 = BoofcvGrayF32Util.convert(imageLab1, LabF32Color.Channel.L, scale);

		return associate(imageGray0, imageGray1);
	}


	public static ArrayList<Entry> associate(float[][] array0, float[][] array1, float scale)
	{
		GrayF32 imageGray0 = BoofcvGrayF32Util.convert(array0, scale);
		GrayF32 imageGray1 = BoofcvGrayF32Util.convert(array1, scale);

		return associate(imageGray0, imageGray1);
	}


	public static ArrayList<Entry> associate(GrayF32 imageGray0, GrayF32 imageGray1)
	{
		//
		InterestPointDetector<GrayF32> detector0 = FactoryInterestPoint.fastHessian(null, GrayF32.class);
		detector0.detect(imageGray0);
		int number0 = detector0.getNumberOfFeatures();

		DescribeRegionPoint<GrayF32, TupleDesc_B> descriptor0 = FactoryDescribeRegionPoint.brief(new ConfigBrief(false), GrayF32.class);
		descriptor0.setImage(imageGray0);

		Point2D_F64[] arrayPoint0 = new Point2D_F64[number0];
		TupleDesc_B[] arrayDescription0 = new TupleDesc_B[number0];

		for (int i = 0; i < detector0.getNumberOfFeatures(); i++)
		{
			Point2D_F64 point = detector0.getLocation(i);
			TupleDesc_B description = descriptor0.createDescription();

			if (descriptor0.process(point.x, point.y, 0, 1, description))
			{
				arrayPoint0[i] = point.copy();
				arrayDescription0[i] = description;
			}
		}

		//
		InterestPointDetector<GrayF32> detector1 = FactoryInterestPoint.fastHessian(null, GrayF32.class);
		detector1.detect(imageGray1);
		int number1 = detector1.getNumberOfFeatures();

		DescribeRegionPoint<GrayF32, TupleDesc_B> descriptor1 = FactoryDescribeRegionPoint.brief(new ConfigBrief(false), GrayF32.class);
		descriptor1.setImage(imageGray1);

		Point2D_F64[] arrayPoint1 = new Point2D_F64[number1];
		TupleDesc_B[] arrayDescription1 = new TupleDesc_B[number1];

		for (int i = 0; i < detector1.getNumberOfFeatures(); i++)
		{
			Point2D_F64 point = detector1.getLocation(i);
			TupleDesc_B description = descriptor1.createDescription();

			if (descriptor1.process(point.x, point.y, 0, 1, description))
			{
				arrayPoint1[i] = point.copy();
				arrayDescription1[i] = description;
			}
		}

		//
		ArrayList<Pair> list = new ArrayList<>(Math.min(number0, number1));
		TupleDesc_B description0;
		TupleDesc_B description1;

		// forward
		for (int i = 0; i < number0; i++)
		{
			description0 = arrayDescription0[i];
			int distance0 = Integer.MAX_VALUE;

			for (int j = 0; j < number1; j++)
			{
				description1 = arrayDescription1[j];
				int distance = DescriptorDistance.hamming(description0, description1);

				if (distance < distance0) distance0 = distance;
			}

			for (int j = 0; j < number1; j++)
			{
				description1 = arrayDescription1[j];
				int distance = DescriptorDistance.hamming(description0, description1);

				if (distance == distance0) list.add(new Pair(i, j, distance));
			}
		}

		// backward
		for (Pair pair : list)
		{
			int i0 = pair.I;
			int j0 = pair.J;
			int distance0 = pair.DISTANCE;
			description1 = arrayDescription1[j0];

			for (int i = 0; i < number0; i++)
			{
				if (i != i0)
				{
					description0 = arrayDescription0[i];
					int distance = DescriptorDistance.hamming(description0, description1);

					if (distance < distance0)
					{
						pair.activity = false;

						break;
					}
				}
			}
		}

		//
		ArrayList<Entry> result = new ArrayList<>(list.size());

		for (Pair pair : list)
		{
			if (pair.activity) result.add(new Entry(arrayPoint0[pair.I], arrayPoint1[pair.J], pair.DISTANCE));
		}

		return result;
	}


	//
	final public static class Entry
	{
		//
		final public double X0;
		final public double Y0;
		final public double X1;
		final public double Y1;
		final public double DISTANCE;

		//
		private Entry(Point2D_F64 point0, Point2D_F64 point1, double distance)
		{
			X0 = point0.x;
			Y0 = point0.y;
			X1 = point1.x;
			Y1 = point1.y;
			DISTANCE = distance;
		}
	}


	final private static class Pair
	{
		//
		final private int I;
		final private int J;
		final private int DISTANCE;
		private boolean activity;

		//
		private Pair(int i, int j, int distance)
		{
			I = i;
			J = j;
			DISTANCE = distance;
			activity = true;
		}
	}
*/

}
