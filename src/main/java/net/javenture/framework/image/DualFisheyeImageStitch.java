package net.javenture.framework.image;


import net.javenture.framework.annotation.SingleThread;
import net.javenture.framework.boofcv.BoofcvGrayF32Util;
import net.javenture.framework.boofcv.ImageDeformPointMLS_F64;
import net.javenture.framework.log.Log;
import net.javenture.framework.math.ACartesian2dTuple;
import net.javenture.framework.math.ACartesian3dTuple;
import net.javenture.framework.math.Cartesian2dPoint;
import net.javenture.framework.math.Cartesian3dPoint;
import net.javenture.framework.math.Cartesian3dRotation;
import net.javenture.framework.math.Cartesian3dVector;
import net.javenture.framework.math.DoubleSumTable;
import net.javenture.framework.math.MathUtil;
import net.javenture.framework.math.Matrix2CramerSolver;
import net.javenture.framework.math.TupleUtil;
import net.javenture.framework.util.Chain;
import net.javenture.framework.util.Range;
import net.javenture.framework.util.Validator;

import boofcv.alg.distort.mls.TypeDeformMLS;
import boofcv.alg.feature.detect.edge.CannyEdge;
import boofcv.alg.feature.detect.edge.EdgeContour;
import boofcv.alg.feature.detect.edge.EdgeSegment;
import boofcv.factory.feature.detect.edge.FactoryEdgeDetectors;
import boofcv.struct.image.GrayF32;
import georegression.struct.point.Point2D_F64;
import georegression.struct.point.Point2D_I32;
import org.apache.commons.math3.analysis.interpolation.SplineInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;


/*
	2020/10/20
 */
@SingleThread
final public class DualFisheyeImageStitch
{
	//
	final private static Log LOG = Log.instance(DualFisheyeImageStitch.class);
	final private static EquirectangularProjection PROJECTION_EQUIRECTANGULAR_4096 = new EquirectangularProjection(4096); // ! 4096
	final private static EquirectangularProjection PROJECTION_EQUIRECTANGULAR_3600 = new EquirectangularProjection(3600); // ! 3600

	public enum Joint
	{
		CENTER,
		STRICT,
		BLEND
	}

	public enum Mode
	{
		FRONT,
		BACK,
		STITCH
	}


	//
	final private Entry ENTRY_FRONT;
	final private Entry ENTRY_BACK;
	final private Interpolation INTERPOLATION;
	final private Color COLOR;
	final private ASeam SEAM;
	final private Cartesian3dVector VECTOR_CARTESIAN_3D;
	final private Cartesian2dPoint POINT_CARTESIAN_2D;


	//
	public DualFisheyeImageStitch(Entry entryFront, Entry entryBack, F32ImageInterpolation interpolation, Joint joint)                 // boolean preview - without color & warp
	{
		ENTRY_FRONT = entryFront;
		ENTRY_BACK = entryBack;
		INTERPOLATION = new Interpolation(interpolation);
		COLOR = new Color(entryFront, entryBack);
		SEAM = ASeam.instance(entryFront, entryBack, COLOR, INTERPOLATION, joint);
		VECTOR_CARTESIAN_3D = new Cartesian3dVector();
		POINT_CARTESIAN_2D = new Cartesian2dPoint();

		//
		Correlation correlation = new Correlation(ENTRY_FRONT, ENTRY_BACK);

		for (Vector vector : correlation.CHAIN_FRONT) ENTRY_FRONT.DEFORMATION.add(vector);

		for (Vector vector : correlation.CHAIN_BACK) ENTRY_BACK.DEFORMATION.add(vector);

		try
		{
			ENTRY_FRONT.DEFORMATION.fixate();
			ENTRY_BACK.DEFORMATION.fixate();
		}
		catch (RuntimeException e)
		{
			System.out.println(e);                                                      // XXX
		}

		COLOR.prepare();
		SEAM.prepare();
	}


	//
	public boolean process(Mode mode, ACartesian3dTuple tuple, LabF32Color destination)
	{
		switch (mode)
		{
			case FRONT: return process(ENTRY_FRONT, tuple, destination);
			case BACK: return process(ENTRY_BACK, tuple, destination);
			case STITCH: return SEAM.process(this, tuple, destination);
			default: throw new RuntimeException();
		}
	}


	private boolean process(Entry entry, ACartesian3dTuple tuple, LabF32Color destination)
	{
		VECTOR_CARTESIAN_3D.set(tuple);
		entry.DEVIATION.apply(VECTOR_CARTESIAN_3D);

		if (entry.DEFORMATION.compute(VECTOR_CARTESIAN_3D))
		{
			entry.ORIENTATION.local(VECTOR_CARTESIAN_3D);

			if (entry.PROJECTION.convert(VECTOR_CARTESIAN_3D, POINT_CARTESIAN_2D))
			{
				INTERPOLATION.ACTION.execute(entry.IMAGE, POINT_CARTESIAN_2D, destination, LabF32Color.Channel.VALUES);
				COLOR.correction(entry, tuple, destination);

				return true;
			}
		}

		return false;
	}


	public Trace trace()
	{
		return new Trace(this);
	}


	//
	@SuppressWarnings("SameParameterValue")
	private static float interpolate(LabF32Image imageLabF32, ACartesian2dTuple tupleCartesian2d, LabF32Color.Channel channel)
	{
		return F32ImageInterpolation.Spline16.interpolate0(imageLabF32, tupleCartesian2d, channel); // ! Spline16
	}


	@SuppressWarnings("SameParameterValue")
	private static void interpolate(LabF32Image imageLabF32, ACartesian2dTuple tupleCartesian2d, LabF32Color colorLabF32, LabF32Color.Channel[] channels)
	{
		F32ImageInterpolation.Spline16.interpolate0(imageLabF32, tupleCartesian2d, colorLabF32, channels); // ! Spline16
	}


	//
	final public static class Entry
	{
		//
		final private static int INDEX_FRONT = 0;
		final private static int INDEX_BACK = 1;

		//
		final private DualFisheyeType TYPE;
		final private int INDEX;
		final private LabF32Image IMAGE;
		final private FisheyeProjection PROJECTION;
		final private DualFisheyeDeviation DEVIATION;
		final private IDualFisheyeOrientation ORIENTATION;
		final private Deformation DEFORMATION;
		final private Source SOURCE;

		//
		public Entry(DualFisheyeType type, LabF32Image image, FisheyeProjection projection, DualFisheyeDeviation deviation)
		{
			int index;
			IDualFisheyeOrientation orientation;

			switch (type)
			{
				case FRONT:
				{
					index = INDEX_FRONT;
					orientation = IDualFisheyeOrientation.FRONT;

					break;
				}

				case BACK:
				{
					index = INDEX_BACK;
					orientation = IDualFisheyeOrientation.BACK;

					break;
				}

				default: throw new UnsupportedOperationException();
			}

			//
			TYPE = type;
			INDEX = index;
			IMAGE = image;
			PROJECTION = projection;
			DEVIATION = deviation;
			ORIENTATION = orientation;
			DEFORMATION = new Deformation(this);
			SOURCE = new Source(this);
		}
	}


	final private static class Interpolation
	{
		//
		final private IAction ACTION;

		//
		private Interpolation(F32ImageInterpolation interpolation)
		{
			ACTION = IAction.instance(interpolation);
		}

		//
		@FunctionalInterface
		private interface IAction
		{
			//
			void execute(LabF32Image imageLabF32, Cartesian2dPoint pointCartesian2d, LabF32Color colorLabF32, LabF32Color.Channel[] channels);

			//
			static IAction instance(F32ImageInterpolation interpolation)
			{
				switch (interpolation)
				{
					case NEAREST: return F32ImageInterpolation.Nearest::interpolate0;
					case LINEAR_4: return F32ImageInterpolation.Linear4::interpolate0;
					case CUBIC_16: return F32ImageInterpolation.Cubic16::interpolate0;
					case SPLINE_16: return F32ImageInterpolation.Spline16::interpolate0;
					case SPLINE_36: return F32ImageInterpolation.Spline36::interpolate0;
					case SINC_64: return F32ImageInterpolation.Sinc64::interpolate0;
					default: throw new UnsupportedOperationException();
				}
			}
		}
	}


	final private static class Source
	{
		//
		final private static int WIDTH = PROJECTION_EQUIRECTANGULAR_4096.WIDTH;
		final private static int HEIGHT = PROJECTION_EQUIRECTANGULAR_4096.HEIGHT;

		//
		final private Entry ENTRY;
		final private int TOP;
		final private int BOTTOM;

		//
		private Source(Entry entry)
		{
			int top;
			int bottom;

			switch (entry.TYPE)
			{
				case FRONT:
				{
					top = 808;
					bottom = HEIGHT - 1;

					break;
				}

				case BACK:
				{
					top = 0;
					bottom = 1239;

					break;
				}

				default: throw new UnsupportedOperationException();
			}

			//
			ENTRY = entry;
			TOP = top;
			BOTTOM = bottom;
		}

		//
		private LabF32Image image(Interpolation interpolation)
		{
			LabF32Image result = new LabF32Image(WIDTH, HEIGHT);
			Cartesian2dPoint pointCartesian2d = new Cartesian2dPoint();
			Cartesian3dVector vectorCartesian3d = new Cartesian3dVector();
			LabF32Color colorLab32 = new LabF32Color();

			for (int j = TOP; j <= BOTTOM; j++)
			{
				for (int i = 0; i < WIDTH; i++)
				{
					PROJECTION_EQUIRECTANGULAR_4096.convert(i, j, vectorCartesian3d);
					TupleUtil.rotate(Cartesian3dRotation.Y_270, vectorCartesian3d); // ! Y_270

					ENTRY.DEVIATION.apply(vectorCartesian3d);
					ENTRY.ORIENTATION.local(vectorCartesian3d);

					if (ENTRY.PROJECTION.convert(vectorCartesian3d, pointCartesian2d))
					{
						interpolation.ACTION.execute(ENTRY.IMAGE, pointCartesian2d, colorLab32, LabF32Color.Channel.VALUES);
						result.set0(i, j, colorLab32);
					}
				}
			}

			return result;
		}

		private boolean contains(Cartesian2dPoint pointCartesian2d)
		{
			//int x0 = (int) pointCartesian2d.x;
			int y0 = (int) pointCartesian2d.y;

			return y0 >= TOP && y0 <= BOTTOM;
		}
	}


	final private static class Correlation
	{
		//
		final private static int SHIFT = 8;
		final private static int WIDTH = Fragment.WIDTH / SHIFT;
		final private static int HEIGHT = Fragment.HEIGHT / SHIFT;
		final private static int U0 = Fragment.WIDTH - Pattern.WIDTH;
		final private static int V0 = Fragment.HEIGHT - Pattern.HEIGHT;
		final private static int RANGE_U = 16;
		final private static int RANGE_V = 32;
		final private static double CORRELATION_THRESHOLD = 0.95;
		final private static double CORRELATION_THRESHOLD2 = CORRELATION_THRESHOLD * CORRELATION_THRESHOLD;

		//
		final private Chain<Vector> CHAIN_FRONT;
		final private Chain<Vector> CHAIN_BACK;

		//
		private Correlation(Entry entryFront, Entry entryBack)
		{
			Fragment fragmentFront = new Fragment(entryFront);
			Fragment fragmentBack = new Fragment(entryBack);

			Match[][] matchFront = new Match[HEIGHT][WIDTH];
			Match[][] matchBack = new Match[HEIGHT][WIDTH];


			// DEL
			System.out.println("--------------------------------------- fragmentBack - fragmentFront");


			match(fragmentBack, fragmentFront, matchBack, matchFront);


			// DEL
			System.out.println("--------------------------------------- fragmentFront - fragmentBack");


			match(fragmentFront, fragmentBack, matchFront, matchBack);


			// DEL
			System.out.println("--------------------------------------- chain matchFront");


			CHAIN_FRONT = chain(matchFront);


			// DEL
			System.out.println("--------------------------------------- chain matchBack");


			CHAIN_BACK = chain(matchBack);
		}

		//
		private static void match(Fragment fragmentData, Fragment fragmentPattern, Match[][] matchesData, Match[][] matchesPattern)
		{
			Report[] reports = new Report[RANGE_U * 2 * RANGE_V * 2];

			for (int v = 0; v < V0 + 1; v += SHIFT)
			{
				boolean[] usage2 = fragmentPattern.USAGE[v];

				for (int u = 0; u < U0 + 1; u += SHIFT)
				{
					if (usage2[u])
					{
						int uMin = u - RANGE_U;
						int uMax = u + RANGE_U;
						int vMin = v - RANGE_V;
						int vMax = v + RANGE_V;

						if (uMin < 0) uMin = 0;

						if (uMax > U0) uMax = U0;

						if (vMin < 0) vMin = 0;

						if (vMax > V0) vMax = V0;

						//
						int count = search(fragmentData, fragmentPattern, u, v, uMin, uMax, vMin, vMax, reports);

						if (count != 0)
						{
							Report report = reports[0];


							// DEL
							System.out.println("report: " + report.U + " - " + u + " = " + (report.U - u) + "; " + report.V + " - " + v + " = " + (report.V - v));



							Direction direction = new Direction(report.U - u, report.V - v);

							// out
							{
								int iMatch = u / SHIFT; // !
								int jMatch = v / SHIFT; // !

								// 00
								{
									Match match = matchesPattern[jMatch][iMatch];

									if (match == null) matchesPattern[jMatch][iMatch] = match = new Match();

									match.out(direction);


									// DEL
									System.out.println("out: " + iMatch + ";" + jMatch);
								}

								// 10
								{
									Match match = matchesPattern[jMatch][iMatch + 1];

									if (match == null) matchesPattern[jMatch][iMatch + 1] = match = new Match();

									match.out(direction);


									// DEL
									System.out.println("out: " + (iMatch + 1) + ";" + jMatch);
								}

								// 01
								{
									Match match = matchesPattern[jMatch + 1][iMatch];

									if (match == null) matchesPattern[jMatch + 1][iMatch] = match = new Match();

									match.out(direction);


									// DEL
									System.out.println("out: " + iMatch + ";" + (jMatch + 1));
								}

								// 11
								{
									Match match = matchesPattern[jMatch + 1][iMatch + 1];

									if (match == null) matchesPattern[jMatch + 1][iMatch + 1] = match = new Match();

									match.out(direction);


									// DEL
									System.out.println("out: " + (iMatch + 1) + ";" + (jMatch + 1));
								}
							}

							// in
							{
								int iMatch = report.U / SHIFT; // !
								int jMatch = report.V / SHIFT; // !
								int deltaU = report.U - iMatch * SHIFT;
								int deltaV = report.V - jMatch * SHIFT;

								// 00
								if (deltaU == 0 && deltaV == 0)
								{
									Match match = matchesData[jMatch][iMatch];

									if (match == null) matchesData[jMatch][iMatch] = match = new Match();

									match.in(direction);


									// DEL
									System.out.println("in: " + iMatch + ";" + jMatch);
								}

								// 10
								if (deltaV == 0)
								{
									Match match = matchesData[jMatch][iMatch + 1];

									if (match == null) matchesData[jMatch][iMatch + 1] = match = new Match();

									match.in(direction);


									// DEL
									System.out.println("in: " + (iMatch + 1) + ";" + jMatch);
								}

								// 01
								if (deltaU == 0)
								{
									Match match = matchesData[jMatch + 1][iMatch];

									if (match == null) matchesData[jMatch + 1][iMatch] = match = new Match();

									match.in(direction);


									// DEL
									System.out.println("in: " + iMatch + ";" + (jMatch + 1));
								}

								// 11
								{
									Match match = matchesData[jMatch + 1][iMatch + 1];

									if (match == null) matchesData[jMatch + 1][iMatch + 1] = match = new Match();

									match.in(direction);


									// DEL
									System.out.println("in: " + (iMatch + 1) + ";" + (jMatch + 1));
								}
							}



							// DEL
							System.out.println();





							//
	/*
							TupleUtil.divide(pattern.U, pattern.V, report.U, report.V, 0.5, tuple);

							render1.addLine((pattern.U + 7) * 3 + 1, (pattern.V + 7) * 3 + 1, (int) (tuple.x + 7) * 3 + 1, (int) (tuple.y + 7) * 3 + 1, color);
							render1.addPoint((pattern.U + 7) * 3 + 1, (pattern.V + 7) * 3 + 1, 1, color);

							render0.addLine((report.U + 7) * 3 + 1, (report.V + 7) * 3 + 1, (int) (tuple.x + 7) * 3 + 1, (int) (tuple.y + 7) * 3 + 1, color);
							render0.addPoint((report.U + 7) * 3 + 1, (report.V + 7) * 3 + 1, 1, color);
	*/
						}
					}
				}
			}
		}

		private static int search(Fragment fragmentData, Fragment fragmentPattern, int u, int v, int uMin, int uMax, int vMin, int vMax, Report[] destination)
		{
			int result = 0;
			Pattern pattern = new Pattern(fragmentPattern, u, v);

			for (int j = vMin; j <= vMax; j++)
			{
				boolean[] usage2 = fragmentData.USAGE[j];
				double[] energy2 = fragmentData.ENERGY[j];

				for (int i = uMin; i <= uMax; i++)
				{
					if (usage2[i])
					{
						double numerator = 0;

						for (int j2 = 0; j2 < Pattern.HEIGHT; j2++)
						{
							float[] array2 = fragmentData.ARRAY[j + j2];
							double[] difference2 = pattern.DIFFERENCE[j2];

							for (int i2 = 0; i2 < Pattern.WIDTH; i2++) numerator += array2[i + i2] * difference2[i2];
						}

						//
						double correlation2 = numerator * numerator / (energy2[i] * pattern.ENERGY);

						if (correlation2 > CORRELATION_THRESHOLD2) destination[result++] = new Report(pattern, i, j, correlation2);
					}
				}
			}

			if (result > 1) Arrays.sort(destination, 0, result, Report.COMPARATOR);

			return result;
		}

		private static Chain<Vector> chain(Match[][] matches)
		{
			Chain<Vector> result = new Chain<>();
			Cartesian2dPoint pointCartesian2d = new Cartesian2dPoint();


			// DEL
			int count = 0;


			for (int j = 0; j < HEIGHT; j++)
			{
				Match[] matches2 = matches[j];

				for (int i = 0; i < WIDTH; i++)
				{
					Match match = matches2[i];



					// DEL
/*
					if (match != null)
					{
						System.out.println("match: " + i + "; " + j);
						System.out.println("activity: " + match.activity + "; matchX: " + match.x + "; matchY: " + match.y);

						System.out.println("DIRECTION_OUT");

						for (Direction direction : match.DIRECTION_OUT)
						{
							System.out.println(direction.X + "; " + direction.Y);
						}

						System.out.println("DIRECTION_IN");

						for (Direction direction : match.DIRECTION_IN)
						{
							System.out.println(direction.X + "; " + direction.Y);
						}

						System.out.println();
					}
*/



					if (match != null && match.direction(pointCartesian2d))
					{

						double xSource = (double) i * SHIFT + 3.5;
						double ySource = (double) j * SHIFT + 3.5 + Fragment.TOP;
						double xDestination = xSource + pointCartesian2d.x * 0.75;                        //  * 0.75
						double yDestination = ySource + pointCartesian2d.y * 0.75;
						result.add(new Vector(xSource, ySource, xDestination, yDestination));


						// DEL
						count++;

						System.out.println();
						System.out.println("----------------------------------------");
						System.out.println("S: " + xSource + "; " + ySource);
						System.out.println("D: " + xDestination + "; " + yDestination);
						System.out.println();
					}
				}
			}


			// DEL
			System.out.println("count: " + count);


			return result;
		}

		//
		final private static class Report
		{
			//
			final private static Comparator<Report> COMPARATOR = (report0, report1) ->
			{
				float correlation0 = report0.CORRELATION2;
				float correlation1 = report1.CORRELATION2;

				if (correlation0 > correlation1) return -1;
				else if (correlation0 < correlation1) return 1;
				else return Float.compare(report0.DISTANCE2, report1.DISTANCE2);
			};

			//
			final private int U;
			final private int V;
			final private float CORRELATION2;
			final private float DISTANCE2;

			//
			private Report(Pattern pattern, int u, int v, double correlation2)
			{
				U = u;
				V = v;
				CORRELATION2 = (float) correlation2;
				DISTANCE2 = TupleUtil.distance2(U, V, pattern.U, pattern.V);
			}

			//
			@Override
			public String toString()
			{
				return "[U: " + U + "; V: " + V + "; CORRELATION2: " + CORRELATION2 + "; DISTANCE2: " + DISTANCE2 + "]";
			}
		}
	}


	final private static class Fragment
	{
		//
		final private static int WIDTH = PROJECTION_EQUIRECTANGULAR_4096.WIDTH;
		final private static int HEIGHT = 144;
		final private static int LIMIT_X = WIDTH - Pattern.WIDTH + 1;
		final private static int LIMIT_Y = HEIGHT - Pattern.HEIGHT + 1;
		final private static int TOP = WIDTH / 4 - HEIGHT / 2;
		final private static double ENERGY_THRESHOLD = 1;
		final private static int CANNY_BLUR = 2;
		final private static float CANNY_MAX = 0.05F; // ? 0.1 | 0.05
		final private static float CANNY_MIN = CANNY_MAX * 0.1F;
		final private static Class<GrayF32> CANNY_IMAGE_TYPE = GrayF32.class;
		final private static Class<GrayF32> CANNY_DERIV_TYPE = GrayF32.class;
		final private static int CANNY_X_FIRST = 0;
		final private static int CANNY_X_LAST = WIDTH - 1;
		final private static int CANNY_Y_FIRST = 0;
		final private static int CANNY_Y_LAST = HEIGHT - 1;

		//
		final private float[][] ARRAY;
		final private boolean[][] USAGE;
		final private double[][] AVG;
		final private double[][] ENERGY;

		//
		private Fragment(Entry entry)
		{
			float[][] array = array(entry);
			boolean[][] usage = usage(array);

			// avg, energy
			double[][] avg = new double[LIMIT_Y][LIMIT_X];
			double[][] energy = new double[LIMIT_Y][LIMIT_X];
			DoubleSumTable table = new DoubleSumTable(array, false);
			DoubleSumTable table2 = new DoubleSumTable(array, true);

			for (int j = 0; j < LIMIT_Y; j++)
			{
				double[] avg2 = avg[j];
				double[] energy2 = energy[j];
				boolean[] usage2 = usage[j];

				for (int i = 0; i < LIMIT_X; i++)
				{
					if (usage2[i])
					{
						double sum = table.value(i, j, Pattern.WIDTH, Pattern.HEIGHT);
						double sum2 = table2.value(i, j, Pattern.WIDTH, Pattern.HEIGHT);
						avg2[i] = sum * Pattern.COUNT_DENOMINATOR;
						energy2[i] = sum2 - sum * sum * Pattern.COUNT_DENOMINATOR;
					}
				}
			}

			//
			ARRAY = array;
			USAGE = usage;
			AVG = avg;
			ENERGY = energy;
		}

		//
		private static float[][] array(Entry entry)
		{
			float[][] result = new float[HEIGHT][WIDTH];
			Cartesian2dPoint pointCartesian2d = new Cartesian2dPoint();
			Cartesian3dVector vectorCartesian3d = new Cartesian3dVector();

			for (int j = 0; j < HEIGHT; j++)
			{
				float[] result2 = result[j];

				for (int i = 0; i < WIDTH; i++)
				{
					PROJECTION_EQUIRECTANGULAR_4096.convert(i, TOP + j, vectorCartesian3d);
					TupleUtil.rotate(Cartesian3dRotation.Y_270, vectorCartesian3d);

					entry.DEVIATION.apply(vectorCartesian3d);
					entry.ORIENTATION.local(vectorCartesian3d);

					if (entry.PROJECTION.convert(vectorCartesian3d, pointCartesian2d)) result2[i] = interpolate(entry.IMAGE, pointCartesian2d, LabF32Color.Channel.L);
				}
			}

			return result;
		}

		private static boolean[][] usage(float[][] array)
		{
			boolean[][] result = new boolean[LIMIT_Y][LIMIT_X];
			float[][] edge = edge(array);
			DoubleSumTable table = new DoubleSumTable(edge, false);
			DoubleSumTable table2 = new DoubleSumTable(edge, true);

			for (int j = 0; j < LIMIT_Y; j++)
			{
				boolean[] result2 = result[j];

				for (int i = 0; i < LIMIT_X; i++)
				{
					double sum = table.value(i, j, Pattern.WIDTH, Pattern.HEIGHT);
					double sum2 = table2.value(i, j, Pattern.WIDTH, Pattern.HEIGHT);
					double energy = sum2 - sum * sum * Pattern.COUNT_DENOMINATOR;

					if (energy > ENERGY_THRESHOLD) result2[i] = true;
				}
			}

			return result;
		}

		private static LabF32Image image(Entry entry)
		{
			float[][] array = array(entry);

			return image(array);
		}

		private static LabF32Image image(float[][] array)
		{
			LabF32Image result = new LabF32Image(WIDTH, HEIGHT);
			result.set0(0, 0, WIDTH, HEIGHT, LabF32Color.Channel.L, array);

			return result;
		}

		private static LabF32Image imageEdge(Entry entry)
		{
			LabF32Image result = new LabF32Image(WIDTH, HEIGHT);
			float[][] array = array(entry);
			float[][] edge = edge(array);
			result.set0(0, 0, WIDTH, HEIGHT, LabF32Color.Channel.L, edge);

			return result;
		}

		private static LabF32Image imageUsage(Entry entry)
		{
			LabF32Image result = new LabF32Image(WIDTH, HEIGHT);
			float[][] array = array(entry);
			LabF32Image image = image(array);
			boolean[][] usage = usage(array);
			LabF32Color colorLab32 = new LabF32Color();

			for (int j = 0; j < LIMIT_Y; j++)
			{
				boolean[] usage2 = usage[j];

				for (int i = 0; i < LIMIT_X; i++)
				{
					if (usage2[i])
					{
						for (int j2 = 0; j2 < Pattern.HEIGHT; j2++)
						{
							for (int i2 = 0; i2 < Pattern.WIDTH; i2++)
							{
								image.get0(i + i2, j + j2, colorLab32);
								result.set0(i + i2, j + j2, colorLab32);
							}
						}
					}
				}
			}

			return result;
		}

		private static float[][] edge(float[][] array)
		{
			boolean[][] activity = new boolean[HEIGHT][WIDTH];
			GrayF32 imageGrayF32 = new GrayF32(WIDTH, HEIGHT);
			BoofcvGrayF32Util.convert(array, 1, imageGrayF32);
			CannyEdge<GrayF32, GrayF32> canny = FactoryEdgeDetectors.canny(CANNY_BLUR, true, true, CANNY_IMAGE_TYPE, CANNY_DERIV_TYPE);
			canny.process(imageGrayF32, CANNY_MIN, CANNY_MAX, null);
			List<EdgeContour> contours = canny.getContours();

			for (EdgeContour contour : contours)
			{
				for (EdgeSegment segment : contour.segments)
				{
					for (Point2D_I32 point : segment.points)
					{
						int y0 = point.y;
						int x0 = point.x;
						int xp = x0 != CANNY_X_LAST ? x0 + 1 : x0;
						int xm = x0 != CANNY_X_FIRST ? x0 - 1 : x0;
						int yp = y0 != CANNY_Y_LAST ? y0 + 1 : y0;
						int ym = y0 != CANNY_Y_FIRST ? y0 - 1 : y0;

						activity[ym][x0] = true;
						activity[y0][xm] = true;
						activity[y0][x0] = true;
						activity[y0][xp] = true;
						activity[yp][x0] = true;
					}
				}
			}

			//
			float[][] result = new float[HEIGHT][WIDTH];

			for (int j = 0; j < HEIGHT; j++)
			{
				float[] result2 = result[j];
				boolean[] activity2 = activity[j];
				float[] array2 = array[j];

				for (int i = 0; i < WIDTH; i++)
				{
					if (activity2[i]) result2[i] = array2[i];
				}
			}

			return result;
		}
	}


	final private static class Pattern
	{
		//
		final private static int WIDTH = 16;
		final private static int HEIGHT = 16;
		final private static double COUNT_DENOMINATOR = (double) 1 / (WIDTH * HEIGHT);

		//
		final private int U;
		final private int V;
		final private double ENERGY;
		final private double[][] DIFFERENCE;

		//
		private Pattern(Fragment fragment, int u, int v)
		{
			double[][] difference = new double[HEIGHT][WIDTH];

			{
				float[][] array = fragment.ARRAY;
				double avg = fragment.AVG[v][u];

				for (int j = 0; j < HEIGHT; j++)
				{
					double[] difference2 = difference[j];
					float[] array2 = array[v + j];

					for (int i = 0; i < WIDTH; i++) difference2[i] = array2[u + i] - avg;
				}
			}

			//
			U = u;
			V = v;
			ENERGY = fragment.ENERGY[v][u];
			DIFFERENCE = difference;
		}
	}


	final private static class Deformation
	{
		//
		final private static int WIDTH = PROJECTION_EQUIRECTANGULAR_4096.WIDTH;
		final private static int HEIGHT = 576;
		final private static int SCALE = 4;
		final private static int COLUMN = WIDTH / SCALE + 1;
		final private static int ROW = HEIGHT / SCALE + 1;

		//
		final private Entry ENTRY;
		final private ImageDeformPointMLS_F64 DEFORMATION;
		final private double MIN;
		final private double MAX;
		final private int SHIFT;
		final private int BORDER;
		final private Cartesian2dPoint POINT_CARTESIAN_2D;
		final private Cartesian3dPoint POINT_CARTESIAN_3D;
		final private Point2D_F64 POINT_BOOFCV_2D;

		//
		private Deformation(Entry entry)
		{
			ImageDeformPointMLS_F64 deformation = new ImageDeformPointMLS_F64(TypeDeformMLS.RIGID);
			deformation.configure(WIDTH, HEIGHT, ROW, COLUMN);

			int min;
			int max;
			int shift;
			int border;

			switch (entry.TYPE)
			{
				case FRONT:
				{
					min = 808;
					max = 1383;
					shift = 0;
					border = HEIGHT;

					break;
				}

				case BACK:
				{
					min = 664;
					max = 1239;
					shift = 288;
					border = 0;

					break;
				}

				default: throw new UnsupportedOperationException();
			}

			//
			ENTRY = entry;
			DEFORMATION = deformation;
			MIN = min;
			MAX = max;
			SHIFT = shift;
			BORDER = border;
			POINT_CARTESIAN_2D = new Cartesian2dPoint();
			POINT_CARTESIAN_3D = new Cartesian3dPoint();
			POINT_BOOFCV_2D = new Point2D_F64();
		}

		//
		private void add(Vector vector)
		{
			double xSource = vector.SOURCE_X;
			double ySource = vector.SOURCE_Y;
			double xDestination = vector.DESTINATION_X;
			double yDestination = vector.DESTINATION_Y;
			Validator.argument(ySource >= MIN && ySource <= MAX, "");                                                 // XXX
			Validator.argument(yDestination >= MIN && yDestination <= MAX, "");                                       // XXX

			DEFORMATION.add(xSource, ySource - MIN + SHIFT, xDestination, yDestination - MIN + SHIFT);
		}

		private void fixate()
			throws RuntimeException                                                                     // ???
		{
			for (int x = 0; x < WIDTH; x += SCALE /* * 4*/) DEFORMATION.add(x, BORDER, x, BORDER);                                        // ???

			DEFORMATION.fixate();
		}

		private boolean compute(Cartesian3dVector vector)
		{
			POINT_CARTESIAN_3D.set(vector);
			TupleUtil.rotate(Cartesian3dRotation.Y_90, POINT_CARTESIAN_3D); // ! Y_90
			PROJECTION_EQUIRECTANGULAR_4096.convert(POINT_CARTESIAN_3D, POINT_CARTESIAN_2D);

			if (ENTRY.SOURCE.contains(POINT_CARTESIAN_2D))
			{
				if (compute0(POINT_CARTESIAN_2D))
				{
					PROJECTION_EQUIRECTANGULAR_4096.convert(POINT_CARTESIAN_2D, POINT_CARTESIAN_3D);
					TupleUtil.rotate(Cartesian3dRotation.Y_270, POINT_CARTESIAN_3D, vector); // ! Y_270
				}

				return true;
			}
			else
			{
				return false;
			}
		}

		private boolean compute0(Cartesian2dPoint point)
		{
			double x = point.x;
			double y = point.y;

			if (y >= MIN && y <= MAX)
			{
				DEFORMATION.compute(x, y - MIN, POINT_BOOFCV_2D);
				point.x = POINT_BOOFCV_2D.x;
				point.y = POINT_BOOFCV_2D.y + MIN;

				return true;
			}
			else
			{
				return false;
			}
		}
	}


	final private static class Match
	{
		//
		private boolean activity;
		//private int count;
		final private Chain<Direction> DIRECTION_OUT;
		final private Chain<Direction> DIRECTION_IN;
		private double x;
		private double y;

		//
		private Match()
		{
			activity = false;
			//count = 0;
			DIRECTION_OUT = new Chain<>();
			DIRECTION_IN = new Chain<>();
			x = Double.NaN;
			y = Double.NaN;
		}

		//
		private void out(Direction direction)
		{
			if (DIRECTION_OUT.size() > 1) // ! > 1
			{
				int count = 0;
				double sumX = 0;
				double sumY = 0;

				for (Direction direction0 : DIRECTION_OUT)
				{
					if (Math.abs(direction0.X - direction.X) + Math.abs(direction0.Y - direction.Y) <= 2) // ! <= 2
					{
						count++;
						sumX += direction0.X;
						sumY += direction0.Y;
					}
				}

				if (count >= 2)
				{
					count++;
					sumX += direction.X;
					sumY += direction.Y;

					activity = true;
					x = sumX / count;
					y = sumY / count;
				}
			}

			DIRECTION_OUT.add(direction);


/*
			if (activity)
			{
				boolean error = false;

				for (Direction direction0 : DIRECTION_OUT)
				{
					if (Math.abs(direction0.X - direction.X) + Math.abs(direction0.Y - direction.Y) > 2) // ! > 2
					{
						error = true;

						break;
					}
				}

				if (error) activity = false;
				else DIRECTION_OUT.add(direction);
			}
*/
		}

		private void in(Direction direction)
		{
			DIRECTION_IN.add(direction);
		}

		private boolean direction(Cartesian2dPoint destination)
		{
			if (activity)
			{
				int count = 0;

				for (Direction direction : DIRECTION_IN)
				{
					if (Math.abs(x + direction.X) + Math.abs(y + direction.Y) < 2) count++;
				}

				//if (true)
				if (count > 0) // ! > 0
				{
					destination.set(x, y);

					return true;
				}
			}

			return false;
		}
	}


	final private static class Direction
	{
		//
		final private int X;
		final private int Y;

		//
		private Direction(int x, int y)
		{
			X = x;
			Y = y;
		}
	}


	final private static class Vector
	{
		//
		final private double SOURCE_X;
		final private double SOURCE_Y;
		final private double DESTINATION_X;
		final private double DESTINATION_Y;

		//
		private Vector(double xSource, double ySource, double xDestination, double yDestination)
		{
			SOURCE_X = xSource;
			SOURCE_Y = ySource;
			DESTINATION_X = xDestination;
			DESTINATION_Y = yDestination;
		}
	}


	final private static class Color
	{
		//
		final private static int WIDTH = PROJECTION_EQUIRECTANGULAR_3600.WIDTH;
		final private static int HEIGHT = 64;
		final private static int STEP = WIDTH / 360;
		final private static double STEP_DENOMINATOR = 1.0 / STEP;
		final private static int REGION = 128;                                                             // ???
		final private static int COUNT = REGION * HEIGHT;
		final private static int TOP = WIDTH / 4 - HEIGHT / 2;
		final private static int ANGLE_FROM = 30;
		final private static int ANGLE_TO = 360 - ANGLE_FROM;
		final private static Range ANGLE_RANGE = new Range(-2, 361);
		final private static IConverter[] Y = { value -> value, value -> PROJECTION_EQUIRECTANGULAR_3600.HEIGHT - value };
		final private static double GRADIENT_FROM = WIDTH / 4.0 + HEIGHT / 2.0;
		final private static double GRADIENT_TO = WIDTH / 4.0 + WIDTH / 8.0;
		final private static double GRADIENT_DENOMINATOR = 1 / (GRADIENT_TO - GRADIENT_FROM);
		final private static double SIGMA_N = 0.01 / 255.0; // ? 2.0 / 255
		final private static double SIGMA_G = 10.0 / 255.0; // ? 0.5 / 255
		final private static double SIGMA_N2 = SIGMA_N * SIGMA_N;
		final private static double SIGMA_G2 = SIGMA_G * SIGMA_G;

		//
		final private Entry ENTRY_FRONT;
		final private Entry ENTRY_BACK;
		final private double[] AVG_L;
		final private double[] AVG_A;
		final private double[] AVG_B;
		final private Interpolation[] INTERPOLATION_L;
		final private Interpolation[] INTERPOLATION_A;
		final private Interpolation[] INTERPOLATION_B;
		final private Cartesian2dPoint POINT_CARTESIAN_2D;
		final private Cartesian3dPoint POINT_CARTESIAN_3D;

		//
		private Color(Entry entryFront, Entry entryBack)
		{
			ENTRY_FRONT = entryFront;
			ENTRY_BACK = entryBack;
			AVG_L = new double[] { 1, 1 };
			AVG_A = new double[] { 1, 1 };
			AVG_B = new double[] { 1, 1 };
			INTERPOLATION_L = new Interpolation[] { new Interpolation(), new Interpolation() };
			INTERPOLATION_A = new Interpolation[] { new Interpolation(), new Interpolation() };
			INTERPOLATION_B = new Interpolation[] { new Interpolation(), new Interpolation() };
			POINT_CARTESIAN_2D = new Cartesian2dPoint();
			POINT_CARTESIAN_3D = new Cartesian3dPoint();
		}

		//
		private void prepare()
		{
			DoubleSumTable tableFrontL;
			DoubleSumTable tableFrontA;
			DoubleSumTable tableFrontB;
			DoubleSumTable tableBackL;
			DoubleSumTable tableBackA;
			DoubleSumTable tableBackB;

			{
				LabF32Image imageFront = image(ENTRY_FRONT);
				float[][] arrayFrontL = new float[HEIGHT][WIDTH];
				float[][] arrayFrontA = new float[HEIGHT][WIDTH];
				float[][] arrayFrontB = new float[HEIGHT][WIDTH];
				data(imageFront, arrayFrontL, arrayFrontA, arrayFrontB);
				tableFrontL = new DoubleSumTable(arrayFrontL, false);
				tableFrontA = new DoubleSumTable(arrayFrontA, false);
				tableFrontB = new DoubleSumTable(arrayFrontB, false);

				LabF32Image imageBack = image(ENTRY_BACK);
				float[][] arrayBackL = new float[HEIGHT][WIDTH];
				float[][] arrayBackA = new float[HEIGHT][WIDTH];
				float[][] arrayBackB = new float[HEIGHT][WIDTH];
				data(imageBack, arrayBackL, arrayBackA, arrayBackB);
				tableBackL = new DoubleSumTable(arrayBackL, false);
				tableBackA = new DoubleSumTable(arrayBackA, false);
				tableBackB = new DoubleSumTable(arrayBackB, false);
			}

			//
			double[] arrayFrontL = new double[ANGLE_RANGE.LENGTH];
			double[] arrayFrontA = new double[ANGLE_RANGE.LENGTH];
			double[] arrayFrontB = new double[ANGLE_RANGE.LENGTH];
			double[] arrayBackL = new double[ANGLE_RANGE.LENGTH];
			double[] arrayBackA = new double[ANGLE_RANGE.LENGTH];
			double[] arrayBackB = new double[ANGLE_RANGE.LENGTH];
			double sumFrontL = 0;
			double sumFrontA = 0;
			double sumFrontB = 0;
			double sumBackL = 0;
			double sumBackA = 0;
			double sumBackB = 0;
			double[] coefficients = new double[2];

			for (int angle = ANGLE_FROM; angle < ANGLE_TO; angle++)
			{
				int index = ANGLE_RANGE.index(angle);
				int i = angle * STEP - REGION / 2;
				int j = 0; // !

				// L
				{
					double sumFront = tableFrontL.value(i, j, REGION, HEIGHT);
					double sumBack = tableBackL.value(i, j, REGION, HEIGHT);
					double avgFront = MathUtil.log(sumFront / COUNT); // ! log
					double avgBack = MathUtil.log(sumBack / COUNT); // ! log
					Matrix2CramerSolver.Report report = calculate(avgFront, avgBack, coefficients);


					// DEL
					if (report != Matrix2CramerSolver.Report.ONE) System.out.println("Matrix2CramerSolver!");


					double coefficientFront = coefficients[ENTRY_FRONT.INDEX];
					double coefficientBack = coefficients[ENTRY_BACK.INDEX];
					arrayFrontL[index] = coefficientFront;
					arrayBackL[index] = coefficientBack;
					sumFrontL += coefficientFront;
					sumBackL += coefficientBack;
				}

				// a
				{
					double sumFront = tableFrontA.value(i, j, REGION, HEIGHT);
					double sumBack = tableBackA.value(i, j, REGION, HEIGHT);
					double avgFront = sumFront / COUNT;
					double avgBack = sumBack / COUNT;
					Matrix2CramerSolver.Report report = calculate(avgFront, avgBack, coefficients);


					// DEL
					if (report != Matrix2CramerSolver.Report.ONE) System.out.println("Matrix2CramerSolver!");


					double coefficientFront = coefficients[ENTRY_FRONT.INDEX];
					double coefficientBack = coefficients[ENTRY_BACK.INDEX];
					arrayFrontA[index] = coefficientFront;
					arrayBackA[index] = coefficientBack;
					sumFrontA += coefficientFront;
					sumBackA += coefficientBack;
				}

				// b
				{
					double sumFront = tableFrontB.value(i, j, REGION, HEIGHT);
					double sumBack = tableBackB.value(i, j, REGION, HEIGHT);
					double avgFront = sumFront / COUNT;
					double avgBack = sumBack / COUNT;
					Matrix2CramerSolver.Report report = calculate(avgFront, avgBack, coefficients);


					// DEL
					if (report != Matrix2CramerSolver.Report.ONE) System.out.println("Matrix2CramerSolver!");


					double coefficientFront = coefficients[ENTRY_FRONT.INDEX];
					double coefficientBack = coefficients[ENTRY_BACK.INDEX];
					arrayFrontB[index] = coefficientFront;
					arrayBackB[index] = coefficientBack;
					sumFrontB += coefficientFront;
					sumBackB += coefficientBack;
				}
			}

			//
			int count = ANGLE_TO - ANGLE_FROM + 1;
			double avgFrontL = sumFrontL / count;
			double avgBackL = sumBackL / count;
			double avgFrontA = sumFrontA / count;
			double avgBackA = sumBackA / count;
			double avgFrontB = sumFrontB / count;
			double avgBackB = sumBackB / count;

			//
			fill(arrayFrontL);
			fill(arrayBackL);
			fill(arrayFrontA);
			fill(arrayBackA);
			fill(arrayFrontB);
			fill(arrayBackB);

			//
			AVG_L[ENTRY_FRONT.INDEX] = avgFrontL;
			AVG_L[ENTRY_BACK.INDEX] = avgBackL;
			AVG_A[ENTRY_FRONT.INDEX] = avgFrontA;
			AVG_A[ENTRY_BACK.INDEX] = avgBackA;
			AVG_B[ENTRY_FRONT.INDEX] = avgFrontB;
			AVG_B[ENTRY_BACK.INDEX] = avgBackB;

			INTERPOLATION_L[ENTRY_FRONT.INDEX].update(arrayFrontL);
			INTERPOLATION_L[ENTRY_BACK.INDEX].update(arrayBackL);
			INTERPOLATION_A[ENTRY_FRONT.INDEX].update(arrayFrontA);
			INTERPOLATION_A[ENTRY_BACK.INDEX].update(arrayBackA);
			INTERPOLATION_B[ENTRY_FRONT.INDEX].update(arrayFrontB);
			INTERPOLATION_B[ENTRY_BACK.INDEX].update(arrayBackB);
		}

		private static LabF32Image image(Entry entry)
		{
			LabF32Image result = new LabF32Image(WIDTH, HEIGHT);
			Cartesian2dPoint pointCartesian2d = new Cartesian2dPoint();
			Cartesian3dVector vectorCartesian3d = new Cartesian3dVector();
			LabF32Color colorLab = new LabF32Color();

			for (int j = 0; j < HEIGHT; j++)
			{
				for (int i = 0; i < WIDTH; i++)
				{
					PROJECTION_EQUIRECTANGULAR_3600.convert(i, TOP + j, vectorCartesian3d);
					TupleUtil.rotate(Cartesian3dRotation.Y_270, vectorCartesian3d); // ! Y_270

					entry.DEVIATION.apply(vectorCartesian3d);
					entry.DEFORMATION.compute(vectorCartesian3d);
					entry.ORIENTATION.local(vectorCartesian3d);

					if (entry.PROJECTION.convert(vectorCartesian3d, pointCartesian2d))
					{
						interpolate(entry.IMAGE, pointCartesian2d, colorLab, LabF32Color.Channel.VALUES);
						result.set0(i, j, colorLab);
					}
				}
			}

			return result;
		}

		private static void data(LabF32Image image, float[][] arrayL, float[][] arrayA, float[][] arrayB)
		{
			LabF32Color colorLab = new LabF32Color();

			for (int j = 0; j < HEIGHT; j++)
			{
				float[] arrayL2 = arrayL[j];
				float[] arrayA2 = arrayA[j];
				float[] arrayB2 = arrayB[j];

				for (int i = 0; i < WIDTH; i++)
				{
					image.get0(i, j, colorLab);
					float l = colorLab.l();
					float a = colorLab.a();
					float b = colorLab.b();

					arrayL2[i] = l;
					arrayA2[i] = (a - LabF32Color.A_MIN_FLOAT) * LabF32Color.A_RANGE_DENOMINATOR_FLOAT;
					arrayB2[i] = (b - LabF32Color.B_MIN_FLOAT) * LabF32Color.B_RANGE_DENOMINATOR_FLOAT;
				}
			}
		}

		private static Matrix2CramerSolver.Report calculate(double avgFront, double avgBack, double[] destination)                                 // XXX: NaN
		{
			double a0 = avgFront * avgFront / SIGMA_N2 + 1 / SIGMA_G2;                                         // XXX: denominator
			double b0 = -avgBack * avgFront / SIGMA_N2;
			double c0 = 1 / SIGMA_G2;

			double a1 = -avgFront * avgBack / SIGMA_N2;
			double b1 = avgBack * avgBack / SIGMA_N2 + 1 / SIGMA_G2;
			double c1 = 1 / SIGMA_G2;

			return Matrix2CramerSolver.solve(a0, b0, c0, a1, b1, c1, destination);
		}

		private static void fill(double[] array)
		{
			double valueLeft = array[ANGLE_RANGE.index(ANGLE_FROM)];
			double valueRight = array[ANGLE_RANGE.index(ANGLE_TO - 1)];
			double difference = valueLeft - valueRight;
			double step = difference / (ANGLE_FROM * 2 + 1);

			for (int i = 0; i < ANGLE_FROM; i++) array[ANGLE_RANGE.index(i)] = valueLeft - (ANGLE_FROM - i) * step;

			for (int i = ANGLE_TO; i < 360; i++) array[ANGLE_RANGE.index(i)] = valueRight + (i - ANGLE_TO + 1) * step;

			for (int i = ANGLE_RANGE.MIN; i < 0; i++) array[ANGLE_RANGE.index(i)] = array[ANGLE_RANGE.index(360 + i)];

			for (int i = 360; i <= ANGLE_RANGE.MAX; i++) array[ANGLE_RANGE.index(i)] = array[ANGLE_RANGE.index(i - 360)];
		}

		private void correction(Entry entry, ACartesian3dTuple tupleCartesian3d, LabF32Color colorLab32)
		{
			POINT_CARTESIAN_3D.set(tupleCartesian3d);
			TupleUtil.rotate(Cartesian3dRotation.Y_90, POINT_CARTESIAN_3D); // ! Y_90
			PROJECTION_EQUIRECTANGULAR_3600.convert(POINT_CARTESIAN_3D, POINT_CARTESIAN_2D);
			correction(entry, POINT_CARTESIAN_2D, colorLab32);
		}

		private void correction(Entry entry, int x, int y, LabF32Color colorLab32)
		{
			POINT_CARTESIAN_2D.set(x, y);
			correction(entry, POINT_CARTESIAN_2D, colorLab32);
		}


		private void correction(Entry entry, ACartesian2dTuple tupleCartesian2d, LabF32Color colorLab32)
		{
			double ratioL;
			double ratioA;
			double ratioB;

			double x = tupleCartesian2d.x * STEP_DENOMINATOR;
			double y = Y[entry.INDEX].get(tupleCartesian2d.y);

			if (y < GRADIENT_FROM)
			{
				ratioL = INTERPOLATION_L[entry.INDEX].value(x);
				ratioA = INTERPOLATION_A[entry.INDEX].value(x);
				ratioB = INTERPOLATION_B[entry.INDEX].value(x);
			}
			else if (y > GRADIENT_TO)
			{
				ratioL = AVG_L[entry.INDEX];
				ratioA = AVG_A[entry.INDEX];
				ratioB = AVG_B[entry.INDEX];
			}
			else
			{
				double w = (y - GRADIENT_FROM) * GRADIENT_DENOMINATOR;
				double w0 = 1 - w;

				ratioL = AVG_L[entry.INDEX] * w + INTERPOLATION_L[entry.INDEX].value(x) * w0;
				ratioA = AVG_A[entry.INDEX] * w + INTERPOLATION_A[entry.INDEX].value(x) * w0;
				ratioB = AVG_B[entry.INDEX] * w + INTERPOLATION_B[entry.INDEX].value(x) * w0;
			}

			//
			double l0 = colorLab32.l();
			double a0 = colorLab32.a();
			double b0 = colorLab32.b();

			double l = l0 > 0 ? MathUtil.pow(l0, ratioL) : l0;
			double a = (a0 - LabF32Color.A_MIN_FLOAT) * ratioA + LabF32Color.A_MIN_FLOAT;
			double b = (b0 - LabF32Color.B_MIN_FLOAT) * ratioB + LabF32Color.B_MIN_FLOAT;
			colorLab32.lab0(l, a, b);
		}

		//
		final private static class Interpolation
		{
			//
			final private static double[] X = new double[ANGLE_RANGE.LENGTH];
			final private static double[] Y = new double[ANGLE_RANGE.LENGTH];

			static
			{
				for (int i = ANGLE_RANGE.MIN; i <= ANGLE_RANGE.MAX; i++)
				{
					int index = ANGLE_RANGE.index(i);
					X[index] = i;
					Y[index] = 1;
				}
			}

			//
			private PolynomialSplineFunction function;

			//
			private Interpolation()
			{
				function = new SplineInterpolator()
					.interpolate(X, Y);
			}

			//
			private void update(double[] y)
			{
				function = new SplineInterpolator()
					.interpolate(X, y);
			}

			private double value(double x)
			{
				return function.value(x);
			}
		}

		@FunctionalInterface
		private interface IConverter
		{
			double get(double value);
		}
	}


	abstract private static class ASeam
	{
		//
		final private static int WIDTH = PROJECTION_EQUIRECTANGULAR_3600.WIDTH;
		final private static int HEIGHT = 100;                                                                         // ???
		final private static int X_LAST = WIDTH - 1;
		final private static int Y_LAST = HEIGHT - 1;
		final private static int TOP = WIDTH / 4 - HEIGHT / 2;
		final static int BLEND_CENTER = PROJECTION_EQUIRECTANGULAR_3600.HEIGHT / 2;
		final static int BLEND_RANGE = 16;
		final static BlendCoefficient[] COEFFICIENT = new BlendCoefficient[BLEND_RANGE * 2 + 1];

		static
		{
			double step = (double) 1 / (BLEND_RANGE * 2 + 2);

			for (int i = 0; i < BLEND_RANGE * 2 + 1; i++) COEFFICIENT[i] = BlendType.cubic((i + 1) * step);
		}

		//
		final protected Entry ENTRY_FRONT;
		final protected Entry ENTRY_BACK;
		final protected Color COLOR;
		final protected Interpolation INTERPOLATION;
		final protected Cartesian2dPoint POINT_CARTESIAN_2D;
		final protected Cartesian3dPoint POINT_CARTESIAN_3D;
		final protected LabF32Color COLOR_FRONT;
		final protected LabF32Color COLOR_BACK;
		final protected AtomicReference<Config> CONFIG;                                        // ???

		//
		abstract protected boolean process(DualFisheyeImageStitch stitch, ACartesian3dTuple tuple, LabF32Color destination);

		//
		private ASeam(Entry entryFront, Entry entryBack, Color color, Interpolation interpolation)
		{
			ENTRY_FRONT = entryFront;
			ENTRY_BACK = entryBack;
			COLOR = color;
			INTERPOLATION = interpolation;
			POINT_CARTESIAN_2D = new Cartesian2dPoint();
			POINT_CARTESIAN_3D = new Cartesian3dPoint();
			COLOR_FRONT = new LabF32Color();
			COLOR_BACK = new LabF32Color();
			CONFIG = new AtomicReference<>();
		}

		//
		private void prepare()
		{
			LabF32Image imageFront = image(ENTRY_FRONT, COLOR);
			LabF32Image imageBack = image(ENTRY_BACK, COLOR);
			double[][] difference = difference(imageFront, imageBack);

			Config config = path(difference);
			CONFIG.set(config);

			//int[] path = config.PATH;






		}

		//
		private static ASeam instance(Entry entryFront, Entry entryBack, Color color, Interpolation interpolation, Joint joint)
		{
			switch (joint)
			{
				case CENTER: return new CenterSeam(entryFront, entryBack, color, interpolation);
				case STRICT: return new StrictSeam(entryFront, entryBack, color, interpolation);
				case BLEND: return new BlendSeam(entryFront, entryBack, color, interpolation);
				default: throw new UnsupportedOperationException();
			}
		}

		private static Node node(Node nodeMinus, Node nodeZero, Node nodePlus)
		{
			double valueMinus = nodeMinus.value;
			double valueZero = nodeZero.value;
			double valuePlus = nodePlus.value;

			if (valueMinus == valueZero && valuePlus == valueZero) return nodeZero;
			else if (valueMinus == valueZero) return valuePlus < valueZero ? nodePlus : nodeZero;
			else if (valuePlus == valueZero) return valueMinus < valueZero ? nodeMinus : nodeZero;
			else if (valueMinus < valueZero) return valuePlus < valueMinus ? nodePlus : nodeMinus;
			else if (valuePlus < valueZero) return valueMinus < valuePlus ? nodeMinus : nodePlus;
			else return nodeZero;
		}

		private static double[][] difference(LabF32Image imageFront, LabF32Image imageBack)
		{
			double[][] result = new double[HEIGHT][WIDTH];
			LabF32Color colorLab32Front = new LabF32Color();
			LabF32Color colorLab32Back = new LabF32Color();

			for (int j = 1; j < HEIGHT; j++)
			{
				double[] result0 = result[j];

				for (int i = 0; i < WIDTH; i++)
				{
					imageFront.get0(i, j, colorLab32Front);
					imageBack.get0(i, j - 1, colorLab32Back); // ! j - 1
					result0[i] = LabF32ColorUtil.difference2(colorLab32Front, colorLab32Back); // ! .difference2
				}
			}

			return result;
		}

		private static Config path(double[][] difference)
		{
			Node nodeMinus = new Node();
			Node nodeZero = new Node();
			Node nodePlus = new Node();
			Node node;

			// left - right
			double costLeftRight = 0;
			int[] pathLeftRight = new int[WIDTH];
			int[][] pathLeftRight0 = new int[HEIGHT][WIDTH];

			{
				double[][] accumulation = new double[HEIGHT][WIDTH];
				accumulation[0][0] = Double.MAX_VALUE;
				accumulation[Y_LAST][0] = Double.MAX_VALUE;

				for (int i = 1; i < WIDTH; i++)
				{
					accumulation[0][i] = Double.MAX_VALUE;
					accumulation[Y_LAST][i] = Double.MAX_VALUE;

					for (int j = 1; j < Y_LAST; j++)
					{
						int i0 = i - 1;
						nodeMinus.set(j - 1, accumulation[j - 1][i0]);
						nodeZero.set(j, accumulation[j][i0]);
						nodePlus.set(j + 1, accumulation[j + 1][i0]);
						node = node(nodeMinus, nodeZero, nodePlus);
						accumulation[j][i] = difference[j][i] + node.value;
						pathLeftRight0[j][i] = node.index;
					}
				}

				//
				int j0 = -1;
				double min = Double.MAX_VALUE;

				for (int j = 1; j < Y_LAST; j++)
				{
					double value = accumulation[j][X_LAST];

					if (value < min)
					{
						min = value;
						j0 = j;
					}
				}

				//
				int j = j0;

				for (int i = X_LAST; i > 0; i--)
				{
					costLeftRight += difference[j][i];
					pathLeftRight[i] = j + TOP;
					j = pathLeftRight0[j][i];
				}

				pathLeftRight[0] = j + TOP;
			}

			// right - left
			int[] pathRightLeft = new int[WIDTH];
			double costRightLeft = 0;
			int[][] pathRightLeft0 = new int[HEIGHT][WIDTH];

			{
				double[][] accumulation = new double[HEIGHT][WIDTH];
				accumulation[0][X_LAST] = Double.MAX_VALUE;
				accumulation[Y_LAST][X_LAST] = Double.MAX_VALUE;

				for (int i = X_LAST - 1; i >= 0; i--)
				{
					accumulation[0][i] = Double.MAX_VALUE;
					accumulation[Y_LAST][i] = Double.MAX_VALUE;

					for (int j = 1; j < Y_LAST; j++)
					{
						int i0 = i + 1;
						nodeMinus.set(j - 1, accumulation[j - 1][i0]);
						nodeZero.set(j, accumulation[j][i0]);
						nodePlus.set(j + 1, accumulation[j + 1][i0]);
						node = node(nodeMinus, nodeZero, nodePlus);
						accumulation[j][i] = difference[j][i] + node.value;
						pathRightLeft0[j][i] = node.index;
					}
				}

				//
				int j0 = -1;
				double min = Double.MAX_VALUE;

				for (int j = 1; j < Y_LAST; j++)
				{
					double value = accumulation[j][0];

					if (value < min)
					{
						min = value;
						j0 = j;
					}
				}

				//
				int j = j0;

				for (int i = 0; i < X_LAST; i++)
				{
					costRightLeft += difference[j][i];
					pathRightLeft[i] = j + TOP;
					j = pathRightLeft0[j][i];
				}

				pathRightLeft[X_LAST] = j + TOP;
			}

			return costLeftRight < costRightLeft
				? new Config(pathLeftRight)
				: new Config(pathRightLeft);
		}

		private static LabF32Image image(Entry entry, Color color)
		{
			LabF32Image result = new LabF32Image(WIDTH, HEIGHT);
			Cartesian2dPoint pointCartesian2d = new Cartesian2dPoint();
			Cartesian3dVector vectorCartesian3d = new Cartesian3dVector();
			LabF32Color colorLab32 = new LabF32Color();

			for (int j = 0; j < HEIGHT; j++)
			{
				for (int i = 0; i < WIDTH; i++)
				{
					PROJECTION_EQUIRECTANGULAR_3600.convert(i, TOP + j, vectorCartesian3d);
					TupleUtil.rotate(Cartesian3dRotation.Y_270, vectorCartesian3d); // ! Y_270

					entry.DEVIATION.apply(vectorCartesian3d);
					entry.DEFORMATION.compute(vectorCartesian3d);
					entry.ORIENTATION.local(vectorCartesian3d);

					if (entry.PROJECTION.convert(vectorCartesian3d, pointCartesian2d))
					{
						interpolate(entry.IMAGE, pointCartesian2d, colorLab32, LabF32Color.Channel.VALUES);
						color.correction(entry, i, TOP + j, colorLab32);
						result.set0(i, j, colorLab32);
					}
				}
			}

			return result;
		}

		private static LabF32Image imageDifference(Entry entryFront, Entry entryBack, Color color)
		{
			LabF32Image result = new LabF32Image(WIDTH, HEIGHT);
			LabF32Image imageFront = image(entryFront, color);
			LabF32Image imageBack = image(entryBack, color);
			double[][] difference = difference(imageFront, imageBack);
			double max = 0;

			for (int j = 0; j < HEIGHT; j++)
			{
				double[] difference0 = difference[j];

				for (int i = 0; i < WIDTH; i++)
				{
					double value = difference0[i];

					if (value > max) max = value;
				}
			}

			if (max != 0)
			{
				for (int j = 0; j < HEIGHT; j++)
				{
					double[] difference0 = difference[j];

					for (int i = 0; i < WIDTH; i++) result.set0(i, j, LabF32Color.Channel.L, difference0[i] / max);
				}
			}

			return result;
		}

		private static LabF32Image imagePath(Entry entryFront, Entry entryBack, Color color)
		{
			LabF32Image result = new LabF32Image(WIDTH, HEIGHT);
			LabF32Image imageFront = image(entryFront, color);
			LabF32Image imageBack = image(entryBack, color);
			double[][] difference = difference(imageFront, imageBack);
			Config config = path(difference);
			int[] path = config.PATH;
			LabF32Color colorLab32 = new LabF32Color();

			for (int i = 0; i < WIDTH; i++)
			{
				int j0 = path[i] - TOP;

				for (int j = 0; j < HEIGHT; j++)
				{
					if (j < j0 - 1) imageBack.get0(i, j, colorLab32);
					else if (j > j0) imageFront.get0(i, j, colorLab32);
					else colorLab32.lab0(1, 0, 0);

					result.set0(i, j, colorLab32);
				}
			}

			return result;
		}

		//
		final private static class Node
		{
			//
			private int index;
			private double value;

			//
			private void set(int index, double value)
			{
				this.index = index;
				this.value = value;
			}
		}

		final protected static class Config
		{
			//
			final private int[] PATH;

			//
			private Config(int[] path)
			{
				PATH = path;
			}
		}
	}


	final private static class CenterSeam
		extends ASeam
	{
		//
		private CenterSeam(Entry entryFront, Entry entryBack, Color color, Interpolation interpolation)
		{
			super(entryFront, entryBack, color, interpolation);
		}

		//
		@Override
		protected boolean process(DualFisheyeImageStitch stitch, ACartesian3dTuple tuple, LabF32Color destination)
		{
			POINT_CARTESIAN_3D.set(tuple);
			TupleUtil.rotate(Cartesian3dRotation.Y_90, POINT_CARTESIAN_3D); // ! Y_90
			PROJECTION_EQUIRECTANGULAR_3600.convert(POINT_CARTESIAN_3D, POINT_CARTESIAN_2D);
			int y = (int) POINT_CARTESIAN_2D.y;

			if (y < BLEND_CENTER) return stitch.process(ENTRY_BACK, tuple, destination);
			else return stitch.process(ENTRY_FRONT, tuple, destination);
		}
	}


	final private static class StrictSeam
		extends ASeam
	{
		//
		private StrictSeam(Entry entryFront, Entry entryBack, Color color, Interpolation interpolation)
		{
			super(entryFront, entryBack, color, interpolation);
		}

		//
		@Override
		protected boolean process(DualFisheyeImageStitch stitch, ACartesian3dTuple tuple, LabF32Color destination)
		{
			POINT_CARTESIAN_3D.set(tuple);
			TupleUtil.rotate(Cartesian3dRotation.Y_90, POINT_CARTESIAN_3D); // ! Y_90
			PROJECTION_EQUIRECTANGULAR_3600.convert(POINT_CARTESIAN_3D, POINT_CARTESIAN_2D);
			int x = (int) POINT_CARTESIAN_2D.x;
			int y = (int) POINT_CARTESIAN_2D.y;

			Config config = CONFIG.get();
			int path = config.PATH[x];

			return y < path
				? stitch.process(ENTRY_BACK, tuple, destination)
				: stitch.process(ENTRY_FRONT, tuple, destination);
		}
	}


	final private static class BlendSeam
		extends ASeam
	{
		//
		private BlendSeam(Entry entryFront, Entry entryBack, Color color, Interpolation interpolation)
		{
			super(entryFront, entryBack, color, interpolation);
		}

		//
		@Override
		protected boolean process(DualFisheyeImageStitch stitch, ACartesian3dTuple tuple, LabF32Color destination)
		{
			POINT_CARTESIAN_3D.set(tuple);
			TupleUtil.rotate(Cartesian3dRotation.Y_90, POINT_CARTESIAN_3D); // ! Y_90
			PROJECTION_EQUIRECTANGULAR_3600.convert(POINT_CARTESIAN_3D, POINT_CARTESIAN_2D);
			int x = (int) POINT_CARTESIAN_2D.x;
			int y = (int) POINT_CARTESIAN_2D.y;

			Config config = CONFIG.get();
			int path = config.PATH[x];

			if (y < path - BLEND_RANGE)
			{
				return stitch.process(ENTRY_BACK, tuple, destination);
			}
			else if (y > path + BLEND_RANGE)
			{
				return stitch.process(ENTRY_FRONT, tuple, destination);
			}
			else if
			(
				stitch.process(ENTRY_BACK, tuple, COLOR_BACK)
				&&
				stitch.process(ENTRY_FRONT, tuple, COLOR_FRONT)
			)
			{
				BlendCoefficient coefficient = COEFFICIENT[y - path + BLEND_RANGE];
				LabF32ColorUtil.blend(COLOR_FRONT, COLOR_BACK, coefficient, destination);

				return true;
			}
			else
			{
				return false;
			}
		}
	}


	final public static class Trace
	{
		//
		final private static ImageFormat FORMAT = ImageFormat.PNG;
		final private static RgbColorSpace SPACE = RgbColorSpace.S_RGB;

		//
		final private DualFisheyeImageStitch STITCH;

		//
		private Trace(DualFisheyeImageStitch stitch)
		{
			STITCH = stitch;
		}

		//
		public void write(String dir, String prefix)
		{
			// source
			{
				Source source = STITCH.ENTRY_FRONT.SOURCE;
				LabF32Image imageLabF32 = source.image(STITCH.INTERPOLATION);
				BufferedImage imageBuffered = new BufferedImage(imageLabF32.WIDTH, imageLabF32.HEIGHT, BufferedImage.TYPE_INT_RGB);
				ImageUtil.convert(imageLabF32, imageBuffered, SPACE);
				File file = new File(dir, prefix + ".source.0" + FORMAT.EXTENSION);
				PngImageUtil.write0(imageBuffered, file, PngImageUtil.COMPRESSION_MAX);
			}

			{
				Source source = STITCH.ENTRY_BACK.SOURCE;
				LabF32Image imageLabF32 = source.image(STITCH.INTERPOLATION);
				BufferedImage imageBuffered = new BufferedImage(imageLabF32.WIDTH, imageLabF32.HEIGHT, BufferedImage.TYPE_INT_RGB);
				ImageUtil.convert(imageLabF32, imageBuffered, SPACE);
				File file = new File(dir, prefix + ".source.1" + FORMAT.EXTENSION);
				PngImageUtil.write0(imageBuffered, file, PngImageUtil.COMPRESSION_MAX);
			}

			// fragment
			{
				LabF32Image imageLabF32 = Fragment.image(STITCH.ENTRY_FRONT);
				BufferedImage imageBuffered = new BufferedImage(imageLabF32.WIDTH, imageLabF32.HEIGHT, BufferedImage.TYPE_INT_RGB);
				ImageUtil.convert(imageLabF32, imageBuffered, SPACE);
				File file = new File(dir, prefix + ".fragment.0" + FORMAT.EXTENSION);
				PngImageUtil.write0(imageBuffered, file, PngImageUtil.COMPRESSION_MAX);
			}

			{
				LabF32Image imageLabF32 = Fragment.image(STITCH.ENTRY_BACK);
				BufferedImage imageBuffered = new BufferedImage(imageLabF32.WIDTH, imageLabF32.HEIGHT, BufferedImage.TYPE_INT_RGB);
				ImageUtil.convert(imageLabF32, imageBuffered, SPACE);
				File file = new File(dir, prefix + ".fragment.1" + FORMAT.EXTENSION);
				PngImageUtil.write0(imageBuffered, file, PngImageUtil.COMPRESSION_MAX);
			}

			{
				LabF32Image imageLabF32 = Fragment.imageEdge(STITCH.ENTRY_FRONT);
				BufferedImage imageBuffered = new BufferedImage(imageLabF32.WIDTH, imageLabF32.HEIGHT, BufferedImage.TYPE_INT_RGB);
				ImageUtil.convert(imageLabF32, imageBuffered, SPACE);
				File file = new File(dir, prefix + ".fragment-edge.0" + FORMAT.EXTENSION);
				PngImageUtil.write0(imageBuffered, file, PngImageUtil.COMPRESSION_MAX);
			}

			{
				LabF32Image imageLabF32 = Fragment.imageEdge(STITCH.ENTRY_BACK);
				BufferedImage imageBuffered = new BufferedImage(imageLabF32.WIDTH, imageLabF32.HEIGHT, BufferedImage.TYPE_INT_RGB);
				ImageUtil.convert(imageLabF32, imageBuffered, SPACE);
				File file = new File(dir, prefix + ".fragment-edge.1" + FORMAT.EXTENSION);
				PngImageUtil.write0(imageBuffered, file, PngImageUtil.COMPRESSION_MAX);
			}

			{
				LabF32Image imageLabF32 = Fragment.imageUsage(STITCH.ENTRY_FRONT);
				BufferedImage imageBuffered = new BufferedImage(imageLabF32.WIDTH, imageLabF32.HEIGHT, BufferedImage.TYPE_INT_RGB);
				ImageUtil.convert(imageLabF32, imageBuffered, SPACE);
				File file = new File(dir, prefix + ".fragment-usage.0" + FORMAT.EXTENSION);
				PngImageUtil.write0(imageBuffered, file, PngImageUtil.COMPRESSION_MAX);
			}

			{
				LabF32Image imageLabF32 = Fragment.imageUsage(STITCH.ENTRY_BACK);
				BufferedImage imageBuffered = new BufferedImage(imageLabF32.WIDTH, imageLabF32.HEIGHT, BufferedImage.TYPE_INT_RGB);
				ImageUtil.convert(imageLabF32, imageBuffered, SPACE);
				File file = new File(dir, prefix + ".fragment-usage.1" + FORMAT.EXTENSION);
				PngImageUtil.write0(imageBuffered, file, PngImageUtil.COMPRESSION_MAX);
			}

			// color
			{
				LabF32Image imageLabF32 = Color.image(STITCH.ENTRY_FRONT);
				BufferedImage imageBuffered = new BufferedImage(imageLabF32.WIDTH, imageLabF32.HEIGHT, BufferedImage.TYPE_INT_RGB);
				ImageUtil.convert(imageLabF32, imageBuffered, SPACE);
				File file = new File(dir, prefix + ".color.0" + FORMAT.EXTENSION);
				PngImageUtil.write0(imageBuffered, file, PngImageUtil.COMPRESSION_MAX);
			}

			{
				LabF32Image imageLabF32 = Color.image(STITCH.ENTRY_BACK);
				BufferedImage imageBuffered = new BufferedImage(imageLabF32.WIDTH, imageLabF32.HEIGHT, BufferedImage.TYPE_INT_RGB);
				ImageUtil.convert(imageLabF32, imageBuffered, SPACE);
				File file = new File(dir, prefix + ".color.1" + FORMAT.EXTENSION);
				PngImageUtil.write0(imageBuffered, file, PngImageUtil.COMPRESSION_MAX);
			}

			// seam
			{
				LabF32Image imageLabF32 = ASeam.image(STITCH.ENTRY_FRONT, STITCH.COLOR);
				BufferedImage imageBuffered = new BufferedImage(imageLabF32.WIDTH, imageLabF32.HEIGHT, BufferedImage.TYPE_INT_RGB);
				ImageUtil.convert(imageLabF32, imageBuffered, SPACE);
				File file = new File(dir, prefix + ".seam.0" + FORMAT.EXTENSION);
				PngImageUtil.write0(imageBuffered, file, PngImageUtil.COMPRESSION_MAX);
			}

			{
				LabF32Image imageLabF32 = ASeam.image(STITCH.ENTRY_BACK, STITCH.COLOR);
				BufferedImage imageBuffered = new BufferedImage(imageLabF32.WIDTH, imageLabF32.HEIGHT, BufferedImage.TYPE_INT_RGB);
				ImageUtil.convert(imageLabF32, imageBuffered, SPACE);
				File file = new File(dir, prefix + ".seam.1" + FORMAT.EXTENSION);
				PngImageUtil.write0(imageBuffered, file, PngImageUtil.COMPRESSION_MAX);
			}

			{
				LabF32Image imageLabF32 = ASeam.imageDifference(STITCH.ENTRY_FRONT, STITCH.ENTRY_BACK, STITCH.COLOR);
				BufferedImage imageBuffered = new BufferedImage(imageLabF32.WIDTH, imageLabF32.HEIGHT, BufferedImage.TYPE_INT_RGB);
				ImageUtil.convert(imageLabF32, imageBuffered, SPACE);
				File file = new File(dir, prefix + ".seam-difference" + FORMAT.EXTENSION);
				PngImageUtil.write0(imageBuffered, file, PngImageUtil.COMPRESSION_MAX);
			}

			{
				LabF32Image imageLabF32 = ASeam.imagePath(STITCH.ENTRY_FRONT, STITCH.ENTRY_BACK, STITCH.COLOR);
				BufferedImage imageBuffered = new BufferedImage(imageLabF32.WIDTH, imageLabF32.HEIGHT, BufferedImage.TYPE_INT_RGB);
				ImageUtil.convert(imageLabF32, imageBuffered, SPACE);
				File file = new File(dir, prefix + ".seam-path" + FORMAT.EXTENSION);
				PngImageUtil.write0(imageBuffered, file, PngImageUtil.COMPRESSION_MAX);
			}
		}
	}

}
