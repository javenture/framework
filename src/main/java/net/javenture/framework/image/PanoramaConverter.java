package net.javenture.framework.image;


import boofcv.abst.fiducial.calib.CalibrationDetectorChessboardX;
import boofcv.abst.fiducial.calib.ConfigGridDimen;
import boofcv.alg.geo.calibration.CalibrationObservation;
import boofcv.factory.fiducial.FactoryFiducialCalibration;
import boofcv.io.calibration.CalibrationIO;
import boofcv.io.image.ConvertBufferedImage;
import boofcv.struct.calib.CameraUniversalOmni;
import boofcv.struct.geo.PointIndex2D_F64;
import boofcv.struct.image.GrayF32;

import net.javenture.framework.camera.GoProFusionCamera;
import net.javenture.framework.log.Log;
import net.javenture.framework.math.*;
import net.javenture.framework.math.Rectangle;
import net.javenture.framework.netty.Media;
import net.javenture.framework.util.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;


final public class PanoramaConverter
{



	//
	final private static Object LOCK = new Object();






	//
	public static void main(String[] args)
		throws Exception
	{
		Platform platform = Platform.WINDOWS;
		Log.root("D:\\Web\\framework\\log");

		// ???: LibrawUtil temp
		//ATempFileGenerator.parent("D:\\Web\\framework\\temp");


		//


























		// chessboard match
		if (false)
		{
			System.out.println("chessboard match");
			System.out.println();

			// 1.5m
			String dir = "D:/Work/GoPro Fusion/29/";

			String[][] arrayName =
			{
				{ "GPFR2628.png", "GPBK2628.png" },
				{ "GPFR2629.png", "GPBK2629.png" },
				{ "GPFR2630.png", "GPBK2630.png" },
				{ "GPFR2631.png", "GPBK2631.png" },
				{ "GPFR2632.png", "GPBK2632.png" },
				{ "GPFR2633.png", "GPBK2633.png" },
				{ "GPFR2634.png", "GPBK2634.png" }
			};

			//
			CameraUniversalOmni modelFront = CalibrationIO.load(new File(dir, "front.3.yaml"));
			CameraUniversalOmni modelBack = CalibrationIO.load(new File(dir, "back.3.yaml"));

			//
			int width = GoProFusionCamera.WIDTH;
			int height = GoProFusionCamera.HEIGHT;

			double radiusFront = 1552;
			double radiusBack = 1552;

			//
			FisheyeProjection.Config configFront = new FisheyeProjection.Config();
			configFront.WIDTH = width;
			configFront.HEIGHT = height;
			configFront.RECTANGLE = new Rectangle(0, 0, width - 1, height - 1);
			configFront.CIRCLE = new ImmutableCircle(modelFront.cx, modelFront.cy, radiusFront);
			FisheyeProjection projectionFisheyeFront0 = new FisheyeProjection(configFront, modelFront);

			//
			FisheyeProjection.Config configBack = new FisheyeProjection.Config();
			configBack.WIDTH = width;
			configBack.HEIGHT = height;
			configBack.RECTANGLE = new Rectangle(0, 0, width - 1, height - 1);
			configBack.CIRCLE = new ImmutableCircle(modelBack.cx, modelBack.cy, radiusBack);                                         // ??? center
			FisheyeProjection projectionFisheyeBack0 = new FisheyeProjection(configBack, modelBack);




			//
			Cartesian3dVector[] arrayPointFront = new Cartesian3dVector[arrayName.length * 4];
			Cartesian3dVector[] arrayPointBack = new Cartesian3dVector[arrayName.length * 4];
			Cartesian3dVector[] arrayVectorFront = new Cartesian3dVector[arrayName.length * 2];
			Cartesian3dVector[] arrayVectorBack = new Cartesian3dVector[arrayName.length * 2];


			//
			for (int i = 0; i < arrayName.length; i++)
			{
				String[] names = arrayName[i];

				// front
				{
					String source1 = names[0];
					File fileInput1 = new File(dir, source1);
					FileContent<BufferedImage> wrapper = BufferedImageUtil.read(fileInput1);
					BufferedImage input = wrapper.VALUE;

					//
					GrayF32 image = ConvertBufferedImage.convertFrom(input, (GrayF32) null);
					CalibrationDetectorChessboardX detector = FactoryFiducialCalibration.chessboardX(null, new ConfigGridDimen(9, 6, 30));

					if (detector.process(image))
					{
						CalibrationObservation observation = detector.getDetectedPoints();
						List<PointIndex2D_F64> points = observation.getPoints();

						// #0
						{
							PointIndex2D_F64 pointIndex2D = points.get(0);
							Cartesian2dPoint pointCartesian2d = new Cartesian2dPoint(pointIndex2D.p.x, pointIndex2D.p.y);
							Cartesian3dVector vectorCartesian3d = new Cartesian3dVector();
							projectionFisheyeFront0.convert0(pointCartesian2d, vectorCartesian3d);
							//vectorCartesian3d.normalize();
							arrayPointFront[i * 4] = vectorCartesian3d;

							// DEL
							System.out.println("F." + pointIndex2D.index + ": " + pointIndex2D.p.x + "; " + pointIndex2D.p.y);
						}

						// #4
						{
							PointIndex2D_F64 pointIndex2D = points.get(4);
							Cartesian2dPoint pointCartesian2d = new Cartesian2dPoint(pointIndex2D.p.x, pointIndex2D.p.y);
							Cartesian3dVector vectorCartesian3d = new Cartesian3dVector();
							projectionFisheyeFront0.convert0(pointCartesian2d, vectorCartesian3d);
							//vectorCartesian3d.normalize();
							arrayPointFront[i * 4 + 1] = vectorCartesian3d;

							// DEL
							System.out.println("F." + pointIndex2D.index + ": " + pointIndex2D.p.x + "; " + pointIndex2D.p.y);
						}

						// #35
						{
							PointIndex2D_F64 pointIndex2D = points.get(35);
							Cartesian2dPoint pointCartesian2d = new Cartesian2dPoint(pointIndex2D.p.x, pointIndex2D.p.y);
							Cartesian3dVector vectorCartesian3d = new Cartesian3dVector();
							projectionFisheyeFront0.convert0(pointCartesian2d, vectorCartesian3d);
							//vectorCartesian3d.normalize();
							arrayPointFront[i * 4 + 2] = vectorCartesian3d;

							// DEL
							System.out.println("F." + pointIndex2D.index + ": " + pointIndex2D.p.x + "; " + pointIndex2D.p.y);
						}

						// #39
						{
							PointIndex2D_F64 pointIndex2D = points.get(39);
							Cartesian2dPoint pointCartesian2d = new Cartesian2dPoint(pointIndex2D.p.x, pointIndex2D.p.y);
							Cartesian3dVector vectorCartesian3d = new Cartesian3dVector();
							projectionFisheyeFront0.convert0(pointCartesian2d, vectorCartesian3d);
							//vectorCartesian3d.normalize();
							arrayPointFront[i * 4 + 3] = vectorCartesian3d;

							// DEL
							System.out.println("F." + pointIndex2D.index + ": " + pointIndex2D.p.x + "; " + pointIndex2D.p.y);
						}


						// DEL
						System.out.println();
					}
					else
					{
						System.err.println("Failed to detect target: " + source1);
					}
				}

				// back
				{
					String source1 = names[1];
					File fileInput1 = new File(dir, source1);
					FileContent<BufferedImage> wrapper = BufferedImageUtil.read(fileInput1);
					BufferedImage input = wrapper.VALUE;

					//
					GrayF32 image = ConvertBufferedImage.convertFrom(input, (GrayF32) null);
					CalibrationDetectorChessboardX detector = FactoryFiducialCalibration.chessboardX(null, new ConfigGridDimen(9, 6, 30));

					if (detector.process(image))
					{
						CalibrationObservation observation = detector.getDetectedPoints();
						List<PointIndex2D_F64> points = observation.getPoints();

						// #0
						{
							PointIndex2D_F64 pointIndex2D = points.get(0);
							Cartesian2dPoint pointCartesian2d = new Cartesian2dPoint(pointIndex2D.p.x, pointIndex2D.p.y);
							Cartesian3dVector vectorCartesian3d = new Cartesian3dVector();
							projectionFisheyeBack0.convert0(pointCartesian2d, vectorCartesian3d);
							IDualFisheyeOrientation.BACK.global(vectorCartesian3d);
							//vectorCartesian3d.normalize();
							arrayPointBack[i * 4] = vectorCartesian3d;

							// DEL
							System.out.println("B." + pointIndex2D.index + ": " + pointIndex2D.p.x + "; " + pointIndex2D.p.y);
						}

						// #4
						{
							PointIndex2D_F64 pointIndex2D = points.get(4);
							Cartesian2dPoint pointCartesian2d = new Cartesian2dPoint(pointIndex2D.p.x, pointIndex2D.p.y);
							Cartesian3dVector vectorCartesian3d = new Cartesian3dVector();
							projectionFisheyeBack0.convert0(pointCartesian2d, vectorCartesian3d);
							IDualFisheyeOrientation.BACK.global(vectorCartesian3d);
							//vectorCartesian3d.normalize();
							arrayPointBack[i * 4 + 1] = vectorCartesian3d;

							// DEL
							System.out.println("B." + pointIndex2D.index + ": " + pointIndex2D.p.x + "; " + pointIndex2D.p.y);
						}

						// #35
						{
							PointIndex2D_F64 pointIndex2D = points.get(35);
							Cartesian2dPoint pointCartesian2d = new Cartesian2dPoint(pointIndex2D.p.x, pointIndex2D.p.y);
							Cartesian3dVector vectorCartesian3d = new Cartesian3dVector();
							projectionFisheyeBack0.convert0(pointCartesian2d, vectorCartesian3d);
							IDualFisheyeOrientation.BACK.global(vectorCartesian3d);
							//vectorCartesian3d.normalize();
							arrayPointBack[i * 4 + 2] = vectorCartesian3d;

							// DEL
							System.out.println("B." + pointIndex2D.index + ": " + pointIndex2D.p.x + "; " + pointIndex2D.p.y);
						}

						// #39
						{
							PointIndex2D_F64 pointIndex2D = points.get(39);
							Cartesian2dPoint pointCartesian2d = new Cartesian2dPoint(pointIndex2D.p.x, pointIndex2D.p.y);
							Cartesian3dVector vectorCartesian3d = new Cartesian3dVector();
							projectionFisheyeBack0.convert0(pointCartesian2d, vectorCartesian3d);
							IDualFisheyeOrientation.BACK.global(vectorCartesian3d);
							//vectorCartesian3d.normalize();
							arrayPointBack[i * 4 + 3] = vectorCartesian3d;

							// DEL
							System.out.println("B." + pointIndex2D.index + ": " + pointIndex2D.p.x + "; " + pointIndex2D.p.y);
						}


						// DEL
						System.out.println();
					}
					else
					{
						System.err.println("Failed to detect target: " + source1);
					}

				}
			}

			//
			int length = arrayPointFront.length;
			double distance0 = 0;
			double[] arrayDistance0 = new double[length];

			for (int i = 0; i < length; i++)
			{
				Cartesian3dVector vectorCartesian3dFront = arrayPointFront[i];
				Cartesian3dVector vectorCartesian3dBack = arrayPointBack[i];
				double distance = TupleUtil.distance2(vectorCartesian3dFront, vectorCartesian3dBack);
				arrayDistance0[i] = distance;
				distance0 += distance;


				//CubemapPoint pointCubemapFront = new CubemapPoint();
				//CubemapPoint pointCubemapBack = new CubemapPoint();
				//projectionCubemap.convert(vectorCartesian3dFront, pointCubemapFront);
				//projectionCubemap.convert(vectorCartesian3dBack, pointCubemapBack);

				//System.out.println(pointCubemapFront + " - " + pointCubemapBack);
				//System.out.println(CubemapUtil.distance(pointCubemapFront, pointCubemapBack));
				System.out.println("Distance [" + i + "]: " + distance);
			}

			// DEL
			System.out.println("Distance: " + distance0);
			System.out.println();





			//
			{
				DualFisheyeNegotiation negotiation = new DualFisheyeNegotiation();
				Cartesian3dVector vectorCartesian3dFront = new Cartesian3dVector();
				Cartesian3dVector vectorCartesian3dBack = new Cartesian3dVector();

				// #1
/*
				int angleRange = 33; // ! 33 & 15
				int shiftRange = 13; // ! 13 & 7
				int angleIncrement = 2; // ! 2 & 1
				int shiftIncrement = 2; // ! 2 & 1
				double angleStep = ANGLE_STEP / 1; // ! / 1 & / 10
				double shiftStep = SHIFT_STEP / 1; // ! / 1 & / 5
				double angleInitX = 0;
				double angleInitY = 0;
				double angleInitZ = 0;
				double shiftInitX = 0;
				double shiftInitY = 0;
				double shiftInitZ = 0;
*/

/*
				// #2 (2.5m)
				int angleRange = 15; // ! 33 & 15
				int shiftRange = 7; // ! 13 & 7
				int angleIncrement = 1; // ! 2 & 1
				int shiftIncrement = 1; // ! 2 & 1
				double angleStep = ANGLE_STEP / 10; // ! / 1 & / 10
				double shiftStep = SHIFT_STEP / 5; // ! / 1 & / 5
				double angleInitX = 0.476;
				double angleInitY = -0.532;
				double angleInitZ = 0.084;
				double shiftInitX = 0.003455;
				double shiftInitY = -0.004837;
				double shiftInitZ = -0.002073;
*/

				// #2 (1.5m)
				int angleRange = 15; // ! 33 & 15
				int shiftRange = 7; // ! 13 & 7
				int angleIncrement = 1; // ! 2 & 1
				int shiftIncrement = 1; // ! 2 & 1
				double angleStep = ANGLE_STEP / 10; // ! / 1 & / 10
				double shiftStep = SHIFT_STEP / 5; // ! / 1 & / 5
				double angleInitX = 0.476;
				double angleInitY = -0.588;
				double angleInitZ = 0.14;
				double shiftInitX = -0.004837;
				double shiftInitY = -0.008983;
				double shiftInitZ = -0.002073;


/*
2.5m
====
Iteration #1
runtime: 86799
iteration: 107850176
sum0: 6.777668749339179E-6
angleX: 0.476
angleY: -0.532
angleZ: 0.084
shiftX: 0.003455
shiftY: -0.004837
shiftZ: -0.002073

Iteration #2
runtime: 80145
iteration: 100544625
sum0: 1.0556684273612922E-6
angleX: 0.4648
angleY: -0.5628
angleZ: 0.1008
shiftX: 0.0035932
shiftY: -0.0056662
shiftZ: -0.0026258


1.5m
====
runtime: 172320
iteration: 107850176
sum0: 3.4244285833066843E-5
angleX: 0.476
angleY: -0.588
angleZ: 0.14
shiftX: -0.004837
shiftY: -0.008983
shiftZ: -0.002073

runtime: 157609
iteration: 100544625
sum0: 1.6679773931456306E-5
angleX: 0.4648
angleY: -0.6103999999999999
angleZ: 0.14
shiftX: -0.0049752
shiftY: -0.0099504
shiftZ: -0.0026258
 */

				double sum0 = distance0;
				double angleX0 = 0;
				double angleY0 = 0;
				double angleZ0 = 0;
				double shiftX0 = 0;
				double shiftY0 = 0;
				double shiftZ0 = 0;

				{
					long iteration = 0;

					long begin = UtcTimeUtil.ns();


					for (int aX = -angleRange; aX <= angleRange; aX += angleIncrement)
					{
						double angleX = angleInitX + angleStep * aX;

						for (int aY = -angleRange; aY <= angleRange; aY += angleIncrement)
						{
							double angleY = angleInitY + angleStep * aY;

							for (int aZ = -angleRange; aZ <= angleRange; aZ += angleIncrement)
							{
								double angleZ = angleInitZ + angleStep * aZ;

								for (int sX = -shiftRange; sX <= shiftRange; sX += shiftIncrement)
								{
									double shiftX = shiftInitX + shiftStep * sX;

									for (int sY = -shiftRange; sY <= shiftRange; sY += shiftIncrement)
									{
										double shiftY = shiftInitY + shiftStep * sY;

										for (int sZ = -shiftRange; sZ <= shiftRange; sZ += shiftIncrement)
										{
											double shiftZ = shiftInitZ + shiftStep * sZ;

											//
											iteration++;
											double sum = 0;


											{
												negotiation.update(Math.toRadians(angleX), Math.toRadians(angleY), Math.toRadians(angleZ), shiftX, shiftY, shiftZ);

												for (int i = 0; i < length; i++)
												{
													vectorCartesian3dFront.set(arrayPointFront[i]);
													vectorCartesian3dBack.set(arrayPointBack[i]);

													negotiation.front(vectorCartesian3dFront);
													negotiation.back(vectorCartesian3dBack);

													sum += TupleUtil.distance2(vectorCartesian3dFront, vectorCartesian3dBack);
												}
											}

											if (sum < sum0)
											{
												sum0 = sum;
												angleX0 = angleX;
												angleY0 = angleY;
												angleZ0 = angleZ;
												shiftX0 = shiftX;
												shiftY0 = shiftY;
												shiftZ0 = shiftZ;

												System.out.println("sum0: " + sum0);
												System.out.println("angleX: " + angleX0);
												System.out.println("angleY: " + angleY0);
												System.out.println("angleZ: " + angleZ0);
												System.out.println("shiftX: " + shiftX0);
												System.out.println("shiftY: " + shiftY0);
												System.out.println("shiftZ: " + shiftZ0);
												System.out.println();
											}
										}
									}
								}
							}
						}
					}

					//
					long end = UtcTimeUtil.ns();
					System.out.println("runtime: " + (end - begin) / 1000000);
					System.out.println("iteration: " + iteration);
				}
			}
		}































		// CubeProjection
		if (false)
		{
			System.out.println("CubeProjection");


			CubeProjection projectionCubemap = CubeProjection.SIZE_512;
			CubePoint pointCube = new CubePoint();
			CubePoint pointCube2 = new CubePoint();
			Cartesian3dVector vectorCartesian3d = new Cartesian3dVector();


			int count = 0;

			//CubemapFace face = CubemapFace.TOP;

			for (CubeFace face : CubeFace.VALUES)
			{
				for (double i = 0; i <= projectionCubemap.SIZE - 1; i += 0.1)
				{
					for (double j = 0; j <= projectionCubemap.SIZE - 1; j += 0.1)
					{
						pointCube.set(face, i, j);
						projectionCubemap.convert(pointCube, vectorCartesian3d);
						vectorCartesian3d.normalize();
						projectionCubemap.convert(vectorCartesian3d, pointCube2);

						boolean match =
							pointCube.face == pointCube2.face
							&&
							DoubleUtil.equal(pointCube.x, pointCube2.x, 0.00000001)
							&&
							DoubleUtil.equal(pointCube.y, pointCube2.y, 0.00000001);

						if (!match)
						{
							count++;
							System.out.println(pointCube);
							System.out.println("->");
							System.out.println(vectorCartesian3d);
							System.out.println("->");
							System.out.println(pointCube2);
							System.out.println();
						}
					}
				}
			}

			//
			System.out.println("count: " + count);
		}











		// RGB <-> LAB
/*
		if (false)
		{
			System.out.println("RGB <-> LAB");
			System.out.println();

			int count = 0;
			Rgba8Color colorRgb = new Rgba8Color();
			Lab32Color colorLab = new Lab32Color();

			double minL = Double.MAX_VALUE;
			double maxL = Double.MIN_VALUE;
			double minA = Double.MAX_VALUE;
			double maxA = Double.MIN_VALUE;
			double minB = Double.MAX_VALUE;
			double maxB = Double.MIN_VALUE;

			//int r = 99;
			//int g = 100;
			//int b = 101;

			long begin = UtcTimeUtil.ms();

			for (int r = 0; r < Rgba8Color.VALUE_MAX_INT; r++)
			{
				for (int g = 0; g < Rgba8Color.VALUE_MAX_INT; g++)
				{
					for (int b = 0; b < Rgba8Color.VALUE_MAX_INT; b++)
					{
						colorRgb.rgb(r, g, b);
						ColorUtil.convert(colorRgb, colorLab);
						ColorUtil.convert(colorLab, colorRgb);

						if (colorRgb.r() != r || colorRgb.g() != g || colorRgb.b() != b)
						{
							count++;
							//System.out.println(color8Rgb);
						}

						{
							double l = colorLab.l();

							if (l < minL) minL = l;

							if (l > maxL) maxL = l;
						}

						{
							double a = colorLab.a();

							if (a < minA) minA = a;

							if (a > maxA) maxA = a;
						}

						{
							double B = colorLab.b();

							if (B < minB) minB = B;

							if (B > maxB) maxB = B;
						}
					}

				}
			}

			long end = UtcTimeUtil.ms();
			System.out.println("runtime: " + (end - begin));

			System.out.println("count: " + count + " / " + 256 * 256 * 256);

			System.out.println("L: " + minL + " ... " + maxL);
			System.out.println("A: " + minA + " ... " + maxA);
			System.out.println("B: " + minB + " ... " + maxB);

			// count: 14835346 / 16777216



			//double k = 1.0 / 255;

			//System.out.println(2.0 / 255 + " " + 2.0 * k);
		}
*/











		// XYZ matrix
		if (false)
		{
			System.out.println("XYZ matrix");
			System.out.println();

			RgbColorSpace.matrix(RgbColorSpace.REC_709_RGB);

		}







		// RGB <-> XYZ
		if (false)
		{
			System.out.println("RGB <-> XYZ");
			System.out.println();

			long count = 0;
			ColorConverter converter = new ColorConverter();
			RgbI8Color colorRgb = new RgbI8Color();
			XyzF32Color colorXyz = new XyzF32Color();

			//int r = 255;
			//int g = 255;
			//int b = 255;

			for (int r = 0; r < 255; r++)
			{
				for (int g = 0; g < 255; g++)
				{
					for (int b = 0; b < 255; b++)
					{
						colorRgb.rgb(r, g, b);
						converter.convert(colorRgb, colorXyz, RgbColorSpace.S_RGB);
						converter.convert(colorXyz, colorRgb, RgbColorSpace.S_RGB);

						if (colorRgb.r() != r || colorRgb.g() != g || colorRgb.b() != b)
						{
							count++;
							//System.out.println(colorRgba8);
						}
					}

				}
			}

			System.out.println("count: " + count + " / " + 256 * 256 * 256);
		}







		// RGB <-> HSL
		if (false)
		{
			System.out.println("RGB <-> HSL");
			System.out.println();

			long count = 0;
			ColorConverter converter = new ColorConverter();
			RgbI8Color colorRgb = new RgbI8Color();
			HslF32Color colorHsl = new HslF32Color();

			for (int r = 0; r < 255; r++)
			{
				for (int g = 0; g < 255; g++)
				{
					for (int b = 0; b < 255; b++)
					{
						colorRgb.rgb0(r, g, b);
						converter.convert(colorRgb, colorHsl);
						converter.convert(colorHsl, colorRgb);

						if (colorRgb.r() != r || colorRgb.g() != g || colorRgb.b() != b)
						{
							count++;
							//System.out.println(colorRgba8);
						}
					}

				}
			}

			System.out.println("count: " + count);
		}











		// renderer
		if (false)
		{
			System.out.println("renderer");
			System.out.println();


			String dir = "D:\\Work\\GoPro Fusion\\33";

			//
			LibrawUtil.Preset preset = new LibrawUtil.Preset(/*"-6", "-W", "-w", "-a", "-g", "2.222", "4.5", "-o", "0", "-c", "0", "-q", "12"*/);                    // XXX
			RgbColorSpace space = RgbColorSpace.S_RGB;
			GprReadImageOption optionGpr = new GprReadImageOption(preset, space);
			DngReadImageOption optionDng = new DngReadImageOption(preset, space);

			// front
			{
				double cx = 1549.8917500465589;
				double cy = 1498.8584734944334;
				double radius = 1502;

				//
				String file = "GPFR0019.DNG";
				File f = new File(dir, file);




				FileContent<LabF32Image> content = LabF32ImageUtil.read(f, optionGpr, optionDng);
				LabF32Image imageLabF32;

				if (content.STATUS == FileContent.Status.OK)
				{
					imageLabF32 = content.VALUE;
				}
				else
				{
					throw new RuntimeException(content.STATUS + ";" + content.EXCEPTION);
				}

				//
				BufferedImage imageBuffered = new BufferedImage(GoProFusionCamera.WIDTH, GoProFusionCamera.HEIGHT, BufferedImage.TYPE_INT_RGB);
				ImageUtil.convert(imageLabF32, imageBuffered, RgbColorSpace.S_RGB);

				//
				ImageRenderer renderer = new ImageRenderer();
				ImageRenderer.Circle circle = new ImageRenderer.Circle(cx, cy, radius, 2, 255, 0, 0);
				renderer.add(circle);
				renderer.render(imageBuffered);

				File f2 = new File(dir, "radius.0a.png");
				PngImageUtil.write(imageBuffered, f2, 0);
			}

			// back
			{
				double cx = 1543.9930537621372;
				double cy = 1506.2893979047979;
				double radius = 1502;

				//
				String file = "GPBK0019.DNG";
				File f = new File(dir, file);

				FileContent<LabF32Image> content = LabF32ImageUtil.read(f, optionGpr, optionDng);
				LabF32Image imageLabF32;

				if (content.STATUS == FileContent.Status.OK)
				{
					imageLabF32 = content.VALUE;
				}
				else
				{
					throw new RuntimeException(content.STATUS + ";" + content.EXCEPTION);
				}

				//
				BufferedImage imageBuffered = new BufferedImage(GoProFusionCamera.WIDTH, GoProFusionCamera.HEIGHT, BufferedImage.TYPE_INT_RGB);
				ImageUtil.convert(imageLabF32, imageBuffered, RgbColorSpace.S_RGB);

				//
				ImageRenderer renderer = new ImageRenderer();
				ImageRenderer.Circle circle = new ImageRenderer.Circle(cx, cy, radius, 2, 255, 0, 0);
				renderer.add(circle);
				renderer.render(imageBuffered);

				File f2 = new File(dir, "radius.1a.png");
				PngImageUtil.write(imageBuffered, f2, 0);
			}
		}


















		// stitch
		if (false)
		{
			System.out.println("stitch");
			System.out.println();

			//
			String number = "0";
			Media media = Media.PNG; // Media.GPR;

			String dirSource = "D:\\Work\\HDR\\0\\";
			String dirDestination = dirSource + number + "\\";

			{
				File file = new File(dirDestination);

				if (!file.exists())
				{
					boolean b = file.mkdirs();

					if (!b) System.out.println("file.mkdirs(): " + b + " (" + file + ")");
				}
			}

			//
			LibrawUtil.Preset preset = new LibrawUtil.Preset
			(
				LibrawUtil.OPTION_FORMAT_16_BIT,
				LibrawUtil.OPTION_WHITE_BALANCE_CAMERA,
				LibrawUtil.OPTION_BRIGHTNESS_AUTOMATICALLY_DISABLED,
				LibrawUtil.THRESHOLD_ZERO,
				LibrawUtil.OPTION_GAMMA_SRGB,
				//LibrawUtil.OPTION_GAMMA_LINEAR,
				LibrawUtil.OPTION_COLORSPACE_RAW,
				LibrawUtil.OPTION_INTERPOLATION_AAHD
			);

			RgbColorSpace space = RgbColorSpace.S_RGB;
			GprReadImageOption optionReadGpr = new GprReadImageOption(preset, space);                            // ??? preset
			DngReadImageOption optionReadDng = new DngReadImageOption(preset, space);
			PngWriteImageOption optionWritePng = new PngWriteImageOption(0);
			JpegWriteImageOption optionWriteJpeg = new JpegWriteImageOption(0.8);                                          // XXX: visual difference
			IImageOption[] options = { optionReadGpr, optionReadDng, optionWritePng, optionWriteJpeg };

			//
			CameraUniversalOmni modelFront = CalibrationIO.load(new File(dirSource, "front.3.yaml"));
			CameraUniversalOmni modelBack = CalibrationIO.load(new File(dirSource, "back.3.yaml"));

			//
			String sourceFront = "GPFR" + number + media.EXTENSION;
			File fileFront = new File(dirSource, sourceFront);
			FileContent<LabF32Image> contentFront = LabF32ImageUtil.read(fileFront, options);
			LabF32Image imageLabF32Front;

			if (contentFront.STATUS == FileContent.Status.OK)
			{
				imageLabF32Front = contentFront.VALUE;
			}
			else
			{
				throw new RuntimeException(contentFront.STATUS + "; " + contentFront.EXCEPTION);
			}

			//
			String sourceBack = "GPBK" + number + media.EXTENSION;
			File fileBack = new File(dirSource, sourceBack);
			FileContent<LabF32Image> contentBack = LabF32ImageUtil.read(fileBack, options);
			LabF32Image imageLabF32Back;

			if (contentBack.STATUS == FileContent.Status.OK)
			{
				imageLabF32Back = contentBack.VALUE;
			}
			else
			{
				throw new RuntimeException(contentBack.STATUS + "; " + contentBack.EXCEPTION);
			}

			//
			//int width = GoProFusionCamera.WIDTH;
			//int height = GoProFusionCamera.HEIGHT;

			//double radiusFront = 1552;
			//double radiusBack = 1552;

			//
			//FisheyeProjection.Config configFront = new FisheyeProjection.Config();
			//configFront.WIDTH = width;
			//configFront.HEIGHT = height;
			//configFront.RECTANGLE = new Rectangle(4, 4, width - 4, height - 4); // !
			//configFront.CIRCLE = new ImmutableCircle(modelFront.cx, modelFront.cy, radiusFront);
			//FisheyeProjection projectionFisheyeFront = new FisheyeProjection(configFront, modelFront);

			//
			//FisheyeProjection.Config configBack = new FisheyeProjection.Config();
			//configBack.WIDTH = width;
			//configBack.HEIGHT = height;
			//configBack.RECTANGLE = new Rectangle(4, 4, width - 4, height - 4); // !
			//configBack.CIRCLE = new ImmutableCircle(modelBack.cx, modelBack.cy, radiusBack);                                         // ??? center
			//FisheyeProjection projectionFisheyeBack = new FisheyeProjection(configBack, modelBack);

			//
			long begin = UtcTimeUtil.ms();


			//DualFisheyeDeviation deviationFront = DualFisheyeDeviation.DEFAULT;
			DualFisheyeDeviation deviationFront = new DualFisheyeDeviation(DualFisheyeType.FRONT, ANGLE_X, ANGLE_Y, ANGLE_Z, SHIFT_X, SHIFT_Y, SHIFT_Z);

			//DualFisheyeDeviation deviationBack = DualFisheyeDeviation.DEFAULT;
			DualFisheyeDeviation deviationBack = new DualFisheyeDeviation(DualFisheyeType.BACK, ANGLE_X, ANGLE_Y, ANGLE_Z, SHIFT_X, SHIFT_Y, SHIFT_Z);


			//DualFisheyeImageStitch.Entry entryFront = new DualFisheyeImageStitch.Entry(DualFisheyeType.FRONT, imageLabF32Front, projectionFisheyeFront, deviationFront);
			//DualFisheyeImageStitch.Entry entryBack = new DualFisheyeImageStitch.Entry(DualFisheyeType.BACK, imageLabF32Back, projectionFisheyeBack, deviationBack);
			//DualFisheyeImageStitch stitch = new DualFisheyeImageStitch(entryFront, entryBack, F32ImageInterpolation.SPLINE_16, DualFisheyeImageStitch.Joint.BLEND);


			// -> cube
			if (true)
			{
				CubeProjection projection = CubeProjection.SIZE_2048;
				int cube = projection.SIZE;
				//DualFisheyeImageStitch.Mode mode = DualFisheyeImageStitch.Mode.STITCH;
				ImageFormat format = ImageFormat.JPEG;
				//ACartesian3dTuple.ITransformer transformer = tuple -> tuple.apply(Quaternion.fromAxisAngle(0, 1, 0, -MathUtil.PI / 5, true));
				LabF32Image imageLabF32Temp = new LabF32Image(cube, cube);
				LabF32Image imageLabF32Temp2 = new LabF32Image(cube, cube);

				DualFisheyeToCubemapConverter.IProcessor processor = (face) ->
				{
					File fileDestination = new File(dirDestination, face.ALIAS + format.EXTENSION);

					// sharpen none
					LabF32ImageUtil.write(imageLabF32Temp, fileDestination, options);

					// sharpen 4
					//LabF32Color.Channel[] channels = LabF32Color.Channel.VALUES; // XXX: { LabF32Color.Channel.L }; // LabF32Color.Channel.VALUES;
					//F32ImageUtil.sharpen4(imageLabF32Temp, imageLabF32Temp2, LabF32Color.INSTANCE, channels);
					//LabF32ImageUtil.write(imageLabF32Temp2, fileDestination);

					// sharpen 8
					//F32ImageUtil.sharpen8(imageLabF32Temp, imageLabF32Temp2, false, LabF32Color.INSTANCE, LabF32Color.Channel.VALUES);
					//LabF32ImageUtil.write(imageLabF32Temp2, fileDestination);
				};

				DualFisheyeToCubemapConverter converter = new DualFisheyeToCubemapConverter()
					.images(imageLabF32Front, imageLabF32Back)
					.models(modelFront, modelBack)
					.deviations(deviationFront, deviationBack)
					.mode(DualFisheyeImageStitch.Mode.STITCH)
					.interpolation(F32ImageInterpolation.SPLINE_16)
					.joint(DualFisheyeImageStitch.Joint.BLEND)
					.transformer(ACartesian3dTuple.ITransformer.DEFAULT)
					.projection(projection);

				converter.convert(processor, imageLabF32Temp);
			}

			// -> eq
			if (true)
			{
				EquirectangularProjection projection = EquirectangularProjection.SIZE_4096; // new EquirectangularProjection(4096);
				ImageFormat format = ImageFormat.JPEG; // ImageFormat.PNG;
				//Quaternion quaternion = Quaternion.fromAxisAngle(0, 1, 0, -MathUtil.PI / 5, true);
				//ACartesian3dTuple.ITransformer transformer = ACartesian3dTuple.ITransformer.DEFAULT; // tuple -> tuple.apply(quaternion);
				LabF32Image imageLabF32Temp = new LabF32Image(projection.WIDTH, projection.HEIGHT);

				DualFisheyeToEquirectangularConverter.IProcessor processor = () ->
				{
					File fileDestination = new File(dirDestination, "eq" + format.EXTENSION);
					LabF32ImageUtil.write(imageLabF32Temp, fileDestination, options);
				};

				DualFisheyeToEquirectangularConverter converter = new DualFisheyeToEquirectangularConverter()
					.images(imageLabF32Front, imageLabF32Back)
					.models(modelFront, modelBack)
					.deviations(deviationFront, deviationBack)
					.mode(DualFisheyeImageStitch.Mode.STITCH)
					.interpolation(F32ImageInterpolation.SPLINE_16)
					.joint(DualFisheyeImageStitch.Joint.BLEND)
					.transformer(ACartesian3dTuple.ITransformer.DEFAULT)
					.projection(projection);

				converter.convert(processor, imageLabF32Temp);
			}






			long end = UtcTimeUtil.ms();
			System.out.println("runtime: " + (end - begin));


			//
			//DualFisheyeImageStitch.Trace trace = stitch.trace();
			//trace.write(dir, name + suffix);
		}
























		// eq -> cube
		if (false)
		{
			System.out.println("eq -> cube");
			System.out.println();

			CubeProjection projectionCubemap = CubeProjection.SIZE_1024;
			int cube = projectionCubemap.SIZE;
			F32ImageInterpolation interpolation = F32ImageInterpolation.SPLINE_16;

			String dir = "D:\\Work\\MyriadTour360\\";
			String source = "equirectangular.4096.png";           // ! corrupt image
			File fileSource = new File(dir, source);
			FileContent<LabF32Image> content = LabF32ImageUtil.read(fileSource);

			if (content.STATUS == FileContent.Status.OK)
			{
				ImageFormat format = ImageFormat.PNG;
				LabF32Image imageLabF32Temp = new LabF32Image(cube, cube);
				Quaternion quaternion = Quaternion.fromAxisAngle(0, 1, 0, -MathUtil.PI / 5, true);
				ACartesian3dTuple.ITransformer transformer = ACartesian3dTuple.ITransformer.DEFAULT; // tuple -> tuple.apply(quaternion);

				EquirectangularToCubeConverter.IProcessor processor = (face) ->
				{
					File fileDestination = new File(dir, "1." + face.ALIAS + format.EXTENSION);
					LabF32ImageUtil.write(imageLabF32Temp, fileDestination);
				};

				EquirectangularToCubeConverter.convert(content.VALUE, projectionCubemap, interpolation, transformer, processor, imageLabF32Temp);
			}
			else
			{
				System.out.println("error");
			}
		}




		// cubemap -> eq
		if (false)
		{
			System.out.println("cubemap -> eq");
			System.out.println();

			//
			String dir = "E:\\Work\\GoPro Fusion.2\\39";

			//
			Media media = Media.PNG;
			String suffix = ".2048.2";

			CubeProjection projectionCube = CubeProjection.SIZE_2048;
			int cube = projectionCube.size();

			EquirectangularProjection projectionEquirectangular = EquirectangularProjection.SIZE_4096;

			LabF32Image image = new LabF32Image(projectionEquirectangular.WIDTH, projectionEquirectangular.HEIGHT);

			Cartesian2dPoint pointCartesian2d = new Cartesian2dPoint();
			Cartesian3dVector vectorCartesian3d = new Cartesian3dVector();
			CubePoint pointCube = new CubePoint();
			LabF32Color colorLab = new LabF32Color();
			F32ImageInterpolation interpolation = F32ImageInterpolation.SPLINE_36;

			//CubemapFace face = CubemapFace.FRONT;

			int error = 0;

			for (CubeFace face : CubeFace.VALUES)
			{
				String source = face.name().toLowerCase() + suffix + media.EXTENSION;
				File file = new File(dir, source);
				FileContent<BufferedImage> content = BufferedImageUtil.read(file);
				BufferedImage inputBufferedSource = content.VALUE;

				LabF32Image imageLabF32Source = new LabF32Image(cube, cube);
				ImageUtil.convert(inputBufferedSource, imageLabF32Source, RgbColorSpace.S_RGB);

				for (int j = 0; j < projectionEquirectangular.HEIGHT; j++)
				{
					for (int i = 0; i < projectionEquirectangular.WIDTH; i++)
					{
						//if (i >= 1530 && i <= 1533 && j >= 620 && j <= 621)
						{
							pointCartesian2d.set(i, j);
							projectionEquirectangular.convert(pointCartesian2d, vectorCartesian3d);
							projectionCube.convert(vectorCartesian3d, pointCube);

/*
							if (pointCube.face == face)
							{
								// DEL
								System.out.println("x: " + i + ", y: " + j);
								System.out.println(pointCube);


								if (interpolation.interpolate(imageLabF32Source, pointCube.x, pointCube.y, colorLab, LabF32Color.Channel.VALUES))
								{
									image.set0(i, j, colorLab);


									// DEL
									System.out.println(colorLab);
								}
								else
								{
									error++;
								}


								// DEL
								System.out.println();
							}
*/

							if
							(
								pointCube.face == face
								&&
								interpolation.interpolate(imageLabF32Source, pointCube.x, pointCube.y, colorLab, LabF32Color.Channel.VALUES)
							)
							{
								image.set0(i, j, colorLab);
							}
							else
							{
								error++;
							}
						}

					}
				}
			}

			//
			File file = new File(dir, "eq5.png");
			LabF32ImageUtil.write(image, file);

			System.out.println("error: " + error);
		}








		// GprUtil
		if (false)
		{
			System.out.println("GprUtil");
			System.out.println();

			//
			LibrawUtil.Preset preset = new LibrawUtil.Preset
			(
				LibrawUtil.OPTION_FORMAT_16_BIT,
				LibrawUtil.OPTION_GAMMA_SRGB, // OPTION_GAMMA_LINEAR,
				LibrawUtil.OPTION_COLORSPACE_SRGB, // OPTION_COLORSPACE_RAW,
				LibrawUtil.OPTION_INTERPOLATION_AAHD
			);

			RgbColorSpace space = RgbColorSpace.S_RGB;
			GprReadImageOption optionReadGpr = new GprReadImageOption(preset, space);
			DngReadImageOption optionReadDng = new DngReadImageOption(preset, space);
			PngWriteImageOption optionWritePng = new PngWriteImageOption(0);
			JpegWriteImageOption optionWriteJpeg = new JpegWriteImageOption(1);
			IImageOption[] options = { optionReadGpr, optionReadDng, optionWritePng, optionWriteJpeg };

			//
			ImageFormat format = ImageFormat.JPEG;
			String dir = "D:\\Work\\HDR2";
			String source = "GPFR2751.GPR";
			String destination = source + "" + format.EXTENSION;
			File fileSource = new File(dir, source);
			File fileDestination = new File(dir, destination);
			FileContent<LabF32Image> content = LabF32ImageUtil.read(fileSource, options);

			if (content.STATUS == FileContent.Status.OK)
			{
				FileContent<LabF32Image> content2 = LabF32ImageUtil.write(content.VALUE, fileDestination, options);

				if (content2.STATUS != FileContent.Status.OK)
				{
					// DEL
					System.out.println("error: " + content2.STATUS + "; " + content2.EXCEPTION);
				}

			}
			else
			{
				System.out.println("error: " + content.STATUS + "; " + content.EXCEPTION);
			}
		}











		// gamma correction LAB
		if (false)
		{
			System.out.println("gamma correction LAB");
			System.out.println();

			//
			LibrawUtil.Preset preset = new LibrawUtil.Preset
			(
				LibrawUtil.OPTION_FORMAT_16_BIT,
				LibrawUtil.OPTION_WHITE_BALANCE_CAMERA,
				LibrawUtil.OPTION_GAMMA_LINEAR, // OPTION_GAMMA_LINEAR,
				LibrawUtil.OPTION_COLORSPACE_SRGB, // OPTION_COLORSPACE_SRGB,
				LibrawUtil.OPTION_INTERPOLATION_AAHD
			);

			RgbColorSpace space = RgbColorSpace.S_RGB;
			GprReadImageOption optionReadGpr = new GprReadImageOption(preset, space);
			DngReadImageOption optionReadDng = new DngReadImageOption(preset, space);
			PngWriteImageOption optionWritePng = new PngWriteImageOption(0);
			JpegWriteImageOption optionWriteJpeg = new JpegWriteImageOption(1);
			IImageOption[] options = { optionReadGpr, optionReadDng, optionWritePng, optionWriteJpeg };

			//
			ImageFormat format = ImageFormat.PNG;
			String dir = "D:\\Work\\HDR2";
			String source = "GPFR2751.dng";
			String destination = source + "" + format.EXTENSION;
			File fileSource = new File(dir, source);
			File fileDestination = new File(dir, destination);
			FileContent<LabF32Image> content = LabF32ImageUtil.read(fileSource, options);

			if (content.STATUS == FileContent.Status.OK)
			{
				LabF32Image image = content.VALUE;
				int width = image.width();
				int height = image.height();

				LabF32Color color = new LabF32Color();
				double gamma = 1.0 / 2.2;


				for (int j = 0; j < height; j++)
				{
					for (int i = 0; i < width; i++)
					{
						image.get(i, j, color);
						double l = color.l();
						double a = color.a();
						double b = color.b();
						l = l / 4; //Math.pow(l, gamma);
						color.l(l);
						color.a(a);
						color.b(b);
						image.set(i, j, color);
					}
				}


				FileContent<LabF32Image> content2 = LabF32ImageUtil.write(image, fileDestination, options);

				if (content2.STATUS != FileContent.Status.OK)
				{
					// DEL
					System.out.println("error2: " + content2.STATUS + "; " + content2.EXCEPTION);
				}

			}
			else
			{
				System.out.println("error: " + content.STATUS + "; " + content.EXCEPTION);
			}
		}



		// gamma correction RGB
		if (true)
		{
			System.out.println("gamma correction RGB");
			System.out.println();

			//
			LibrawUtil.Preset preset = new LibrawUtil.Preset
			(
				LibrawUtil.OPTION_FORMAT_16_BIT,
				LibrawUtil.OPTION_WHITE_BALANCE_CAMERA,
				LibrawUtil.OPTION_GAMMA_LINEAR, // OPTION_GAMMA_LINEAR,
				LibrawUtil.OPTION_COLORSPACE_SRGB, // OPTION_COLORSPACE_SRGB,
				LibrawUtil.OPTION_INTERPOLATION_AAHD,
				LibrawUtil.OPTION_HIGHLIGHT_2_BLEND,
				LibrawUtil.OPTION_BRIGHTNESS_AUTOMATICALLY_DISABLED
				//LibrawUtil.OPTION_EXPOSURE_LIGHTEN_3
			);

			RgbColorSpace space = RgbColorSpace.S_RGB;
			GprReadImageOption optionReadGpr = new GprReadImageOption(preset, space);
			DngReadImageOption optionReadDng = new DngReadImageOption(preset, space);
			PngWriteImageOption optionWritePng = new PngWriteImageOption(0);
			JpegWriteImageOption optionWriteJpeg = new JpegWriteImageOption(1);
			IImageOption[] options = { optionReadGpr, optionReadDng, optionWritePng, optionWriteJpeg };

			//
			ImageFormat format = ImageFormat.PNG;
			String dir = "D:\\Work\\HDR2";
			String source = "GPFR2751.dng";
			String destination = source + "" + format.EXTENSION;
			File fileSource = new File(dir, source);
			File fileDestination = new File(dir, destination);
			FileContent<RgbF32Image> content = RgbF32ImageUtil.read(fileSource, options);

			if (content.STATUS == FileContent.Status.OK)
			{
				RgbF32Image image = content.VALUE;
				int width = image.width();
				int height = image.height();

				RgbF32Color colorRgbF32 = new RgbF32Color();
				double gamma = 1.0 / 2.4; // 1.0 / 3;                    // k>1 would darken the image, k<1 would lighten the image

				double exposure = Math.exp(1.0); //  ... 0.5, 1, 1.1, 1.2
				double contrast = 0;


				HslF32Color colorHslF32 = new HslF32Color();
				ColorConverter converter = new ColorConverter();


				for (int j = 0; j < height; j++)
				{
					for (int i = 0; i < width; i++)
					{
						image.get(i, j, colorRgbF32);

						//double r = color.r() * exposure; // Math.pow(color.r(), gamma);
						//double g = color.g() * exposure; // Math.pow(color.g(), gamma);
						//double b = color.b() * exposure; // Math.pow(color.b(), gamma);

						double r = colorRgbF32.r();
						r *= exposure;
						r = Math.pow(r, gamma);
						r += contrast;

						double g = colorRgbF32.g();
						g *= exposure;
						g = Math.pow(g, gamma);
						g += contrast;

						double b = colorRgbF32.b();
						b *= exposure;
						b = Math.pow(b, gamma);
						b += contrast;

						colorRgbF32.r(r);
						colorRgbF32.g(g);
						colorRgbF32.b(b);


/*
						converter.convert(colorRgbF32, colorHslF32);
						colorHslF32.l(colorHslF32.l() - 10);
						converter.convert(colorHslF32, colorRgbF32);
*/


						image.set(i, j, colorRgbF32);
					}
				}








				FileContent<RgbF32Image> content2 = RgbF32ImageUtil.write(image, fileDestination, options);

				if (content2.STATUS != FileContent.Status.OK)
				{
					// DEL
					System.out.println("error2: " + content2.STATUS + "; " + content2.EXCEPTION);
				}

			}
			else
			{
				System.out.println("error: " + content.STATUS + "; " + content.EXCEPTION);
			}
		}












		// platform
		if (false)
		{
			String os = System.getProperty("os.name");
			System.out.println("os: " + os);


			System.getProperties().list(System.out);
		}








		// eq -> video
		if (false)
		{
			System.out.println("eq -> video");
			System.out.println();

			int cube = 1024;
			CubeProjection projectionCube = new CubeProjection(cube);
			EquirectangularProjection projectionEquirectangular = EquirectangularProjection.SIZE_4096;
			Cartesian3dVector vectorCartesian3d = new Cartesian3dVector();
			Cartesian2dPoint pointCartesian2d = new Cartesian2dPoint();
			F32ImageInterpolation interpolation = F32ImageInterpolation.CUBIC_16;

			String dir = "D:\\Work\\MyriadMuseum\\2964";
			String source = "eq.jpeg";
			File file = new File(dir, source);
			FileContent<BufferedImage> content = BufferedImageUtil.read(file);
			BufferedImage inputBufferedSource = content.VALUE;
			LabF32Image imageLabF32Source = new LabF32Image(inputBufferedSource.getWidth(), inputBufferedSource.getHeight());
			ImageUtil.convert(inputBufferedSource, imageLabF32Source, RgbColorSpace.S_RGB);




			//int width = 1920;
			//int height = 1080;

			double fov = Math.toRadians(75);
			double tan = MathUtil.tan(fov / 2);

			LabF32Image imageDestination = new LabF32Image(cube, cube);
			LabF32Color colorInit = new LabF32Color();
			LabF32Color color = new LabF32Color();

			int frames = 360;
			double angle = Math.toRadians((double) 360 / frames);

			for (int f = 0; f < frames; f++)
			{
				F32ImageUtil.fill(imageDestination, colorInit);
				Quaternion quaternion = Quaternion.fromAxisAngle(0, 0, 1, -angle * f, true); // -angle -> right; +angle -> left

				for (int j = 0; j < cube; j++)
				{
					for (int i = 0; i < cube; i++)
					{
						projectionCube.convert(CubeFace.FRONT, i, j, vectorCartesian3d);
						vectorCartesian3d.apply(quaternion);

						// XXX: rotation

						projectionEquirectangular.convert(vectorCartesian3d, pointCartesian2d);
						interpolation.interpolate(imageLabF32Source, pointCartesian2d.x, pointCartesian2d.y, color, LabF32Color.Channel.VALUES);
						imageDestination.set0(i, j, color);
					}
				}

				//
				StringUtil.IPrefix<Integer> prefix = value ->
				{
					String s = "" + value;
					int length = s.length();

					if (length == 1) return "000" + s;
					else if (length == 2) return "00" + s;
					else if (length == 3) return "0" + s;
					else return s;
				};

				ImageFormat format = ImageFormat.PNG;
				File fileDestination = new File(dir, "video." + StringUtil.prefix(f, prefix) + format.EXTENSION);
				LabF32ImageUtil.write(imageDestination, fileDestination);
			}


			// D:\FFmpeg\ffmpeg -r 24 -f image2 -s 1024x1024 -start_number 0 -i video.%04d.png -vframes 360 -vcodec libx264 -crf 25 -pix_fmt yuv420p test.mp4
		}











	}


	//
	final private static double ANGLE_STEP = 0.028;
	final private static double SHIFT_STEP = 0.000691;

	// 1.5m
	final private static double ANGLE_X = Math.toRadians(0.4648);
	final private static double ANGLE_Y = Math.toRadians(-0.61039);
	final private static double ANGLE_Z = Math.toRadians(0.14);
	final private static double SHIFT_X = -0.0049752;
	final private static double SHIFT_Y = -0.0099504;
	final private static double SHIFT_Z = -0.0026258;


	private static void calculate2()
	{
/*
		// cube
		Coordinate A = new Coordinate(3.514624533756838, -1.1193672675579882, 5);
		Coordinate B = new Coordinate(0.1036599756226894, -0.8040852406142132, 5);
		Coordinate C = new Coordinate(0.09388489713934249, -2.9603716050424107, 5);
		Coordinate D = new Coordinate(3.485578165511212, -4.099986225893948, 5);
*/

/*
		// tv
		Coordinate A = new Coordinate(2.8295454859432487, -0.8979439285046313, 4.016264545552975);
		Coordinate B = new Coordinate(0.10552461784587612, -0.7962592052703142, 4.929834992134568);
		Coordinate C = new Coordinate(0.0868381914841984, -2.5400411719867244, 4.301639318996679);
		Coordinate D = new Coordinate(2.3686533110205987, -2.790939079927227, 3.4015228914139);
*/

		// door
		Cartesian3dPoint A = new Cartesian3dPoint(-1.8198680588620817, 1.2420745147664032, 4.485937532291517);
		Cartesian3dPoint B = new Cartesian3dPoint(-2.503787513887176, 1.4639814487012797, 4.066077125187558);
		Cartesian3dPoint C = new Cartesian3dPoint(-2.409958176398924, -2.080260185055336, 3.8485365027765273);
		Cartesian3dPoint D = new Cartesian3dPoint(-1.759192593935768, -1.855282797199924, 4.294623433289411);


		PlaneCartesianEquation equation = new PlaneCartesianEquation(A.x, A.y, A.z, B.x, B.y, B.z, C.x, C.y, C.z);
		Cartesian3dPoint D1 = intersection(equation, D.x, D.y, D.z);

		if (D1 != null)
		{
			PlaneCartesianEquation equation2 = new PlaneCartesianEquation(A.x, A.y, A.z, D1.x, D1.y, D1.z, C.x, C.y, C.z);
			Cartesian3dPoint B1 = intersection(equation2, B.x, B.y, B.z);

			if (B1 != null)
			{
				System.out.println("B1: [" + B1.x + ", " + B1.y + ", " + B1.z + "]");
				System.out.println("D1: [" + D1.x + ", " + D1.y + ", " + D1.z + "]");
			}
			else
			{
				System.out.println("B1 = null");
			}
		}
		else
		{
			System.out.println("D1 = null");
		}

	}




/*
	private static void calculate()
	{
		int count = 0;
		double limit = 0.01;
		double x1 = 3, y1 = 5;
		double x2 = 5, y2 = 4;
		double x3 = 5, y3 = 2;

		double xMin = 0, xMax = x1;

		while (true)
		{
			double ab = Math.hypot(x1 - x2, y1 - y2);
			double bc = Math.hypot(x2 - x3, y2 - y3);
			double ac = Math.hypot(x1 - x3, y1 - y3);
			double diffefence = Math.hypot(ab, bc) - ac;
			//double abs = Math.abs(diffefence);
			System.out.println("ab: " + ab + "; bc: " + bc + "; ac: " + ac + "; diffefence: " + diffefence);

			if (diffefence < -limit)
			{
				double x = xMax - (xMax - xMin) / 2;
				double y = x * y1 / x1;

				xMin = x;
				x1 = x;
				y1 = y;
			}
			else if (diffefence > limit)
			{
				double x = xMin + (xMax - xMin) / 2;
				double y = x * y1 / x1;

				xMax = x;
				x1 = x;
				y1 = y;
			}
			else
			{
				break;
			}

			if (count++ > 100) break;
		}

		System.out.println("x1: " + x1 + "; y1: " + y1);
	}
*/


	private static Cartesian3dPoint intersection(PlaneCartesianEquation equation, double x, double y, double z)
	{
		double denominator = equation.A * x + equation.B * y + equation.C * z;

		if (denominator == 0)
		{
			return null;
		}
		else
		{
			double t = -equation.D / denominator;

			return new Cartesian3dPoint(t * x, t * y, t * z);
		}
	}

}
