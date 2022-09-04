package net.javenture.framework.image;


import net.javenture.framework.annotation.SingleThread;
import net.javenture.framework.math.ACartesian3dTuple;
import net.javenture.framework.math.Cartesian2dPoint;
import net.javenture.framework.math.Cartesian3dPoint;


/*
	2020/10/21
 */
@SingleThread
final public class EquirectangularToCubeConverter
{
	//
	final public static F32ImageInterpolation INTERPOLATION_DEFAULT = F32ImageInterpolation.CUBIC_16;
	final private static LabF32Color COLOR_DEFAULT = new LabF32Color();


	//
	private EquirectangularToCubeConverter()
	{
	}


	//
	@SuppressWarnings("UnnecessaryLocalVariable")
	public static void convert
	(
		LabF32Image source,
		CubeProjection projection,
		F32ImageInterpolation interpolation,
		ACartesian3dTuple.ITransformer transformer,
		IProcessor processor,
		LabF32Image temp
	)
	{
		int width = source.WIDTH;
		int height = source.HEIGHT;

		if (width != 0 && height != 0 && width == height * 2)
		{
			CubeProjection projectionCube = projection;
			EquirectangularProjection projectionEquirectangular = new EquirectangularProjection(width);
			Cartesian2dPoint pointCartesian2d = new Cartesian2dPoint();
			Cartesian3dPoint pointCartesian3d = new Cartesian3dPoint();
			LabF32Color colorLabF32 = new LabF32Color();

			for (CubeFace face : CubeFace.values())
			{
				temp.init(COLOR_DEFAULT);

				for (int j = 0; j < projectionCube.SIZE; j++)
				{
					for (int i = 0; i < projectionCube.SIZE; i++)
					{
						projectionCube.convert(face, i, j, pointCartesian3d);
						transformer.transform(pointCartesian3d);
						projectionEquirectangular.convert(pointCartesian3d, pointCartesian2d);

						if (interpolation.interpolate(source, pointCartesian2d, colorLabF32, LabF32Color.Channel.VALUES)) temp.set0(i, j, colorLabF32);
					}
				}

				processor.process(face);
			}
		}
	}


	//
	@FunctionalInterface
	public interface IProcessor
	{
		void process(CubeFace face);
	}

}
