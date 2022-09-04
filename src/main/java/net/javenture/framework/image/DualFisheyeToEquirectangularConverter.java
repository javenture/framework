package net.javenture.framework.image;


import boofcv.struct.calib.CameraUniversalOmni;
import net.javenture.framework.camera.GoProFusionCamera;
import net.javenture.framework.math.ACartesian3dTuple;
import net.javenture.framework.math.Cartesian3dPoint;
import net.javenture.framework.math.ImmutableCircle;
import net.javenture.framework.math.Rectangle;


/*
	2022/01/26
 */
final public class DualFisheyeToEquirectangularConverter
{
	//
	final private static DualFisheyeDeviation DEVIATION_DEFAULT = DualFisheyeDeviation.DEFAULT;
	final private static DualFisheyeImageStitch.Mode MODE_DEFAULT = DualFisheyeImageStitch.Mode.STITCH;
	final private static F32ImageInterpolation INTERPOLATION_DEFAULT = F32ImageInterpolation.NEAREST;
	final private static DualFisheyeImageStitch.Joint JOINT_DEFAULT = DualFisheyeImageStitch.Joint.CENTER;
	final private static ACartesian3dTuple.ITransformer TRANSFORMER_DEFAULT = ACartesian3dTuple.ITransformer.DEFAULT;
	final private static EquirectangularProjection PROJECTION_DEFAULT = EquirectangularProjection.SIZE_1024;


	//
	private LabF32Image imageFront;
	private LabF32Image imageBack;
	private CameraUniversalOmni modelFront;
	private CameraUniversalOmni modelBack;
	private DualFisheyeDeviation deviationFront;
	private DualFisheyeDeviation deviationBack;
	private DualFisheyeImageStitch.Mode mode;
	private F32ImageInterpolation interpolation;
	private DualFisheyeImageStitch.Joint joint;
	private ACartesian3dTuple.ITransformer transformer;
	private EquirectangularProjection projection;


	//
	public DualFisheyeToEquirectangularConverter()
	{
		imageFront = null;
		imageBack = null;
		modelFront = null;
		modelBack = null;
		deviationFront = DEVIATION_DEFAULT;
		deviationBack = DEVIATION_DEFAULT;
		mode = MODE_DEFAULT;
		interpolation = INTERPOLATION_DEFAULT;
		joint = JOINT_DEFAULT;
		transformer = TRANSFORMER_DEFAULT;
		projection = PROJECTION_DEFAULT;
	}


	//
	public DualFisheyeToEquirectangularConverter images(LabF32Image imageFront, LabF32Image imageBack)
	{
		this.imageFront = imageFront;
		this.imageBack = imageBack;

		return this;
	}


	public DualFisheyeToEquirectangularConverter models(CameraUniversalOmni modelFront, CameraUniversalOmni modelBack)
	{
		this.modelFront = modelFront;
		this.modelBack = modelBack;

		return this;
	}


	public DualFisheyeToEquirectangularConverter deviations(DualFisheyeDeviation deviationFront, DualFisheyeDeviation deviationBack)
	{
		this.deviationFront = deviationFront;
		this.deviationBack = deviationBack;

		return this;
	}


	public DualFisheyeToEquirectangularConverter mode(DualFisheyeImageStitch.Mode mode)
	{
		this.mode = mode;

		return this;
	}


	public DualFisheyeToEquirectangularConverter interpolation(F32ImageInterpolation interpolation)
	{
		this.interpolation = interpolation;

		return this;
	}


	public DualFisheyeToEquirectangularConverter joint(DualFisheyeImageStitch.Joint joint)
	{
		this.joint = joint;

		return this;
	}


	public DualFisheyeToEquirectangularConverter transformer(ACartesian3dTuple.ITransformer transformer)
	{
		this.transformer = transformer;

		return this;
	}


	public DualFisheyeToEquirectangularConverter projection(EquirectangularProjection projection)
	{
		this.projection = projection;

		return this;
	}


	//
	public void convert(DualFisheyeToEquirectangularConverter.IProcessor processor, LabF32Image temp)
	{
		int width = GoProFusionCamera.WIDTH;
		int height = GoProFusionCamera.HEIGHT;
		double radiusFront = 1552;                                                                 // XXX
		double radiusBack = 1552;                                                                  // XXX

		FisheyeProjection.Config configFront = new FisheyeProjection.Config();
		configFront.WIDTH = width;
		configFront.HEIGHT = height;
		configFront.RECTANGLE = new Rectangle(4, 4, width - 4, height - 4); // !
		configFront.CIRCLE = new ImmutableCircle(modelFront.cx, modelFront.cy, radiusFront);                                      // ??? center
		FisheyeProjection projectionFisheyeFront = new FisheyeProjection(configFront, modelFront);

		FisheyeProjection.Config configBack = new FisheyeProjection.Config();
		configBack.WIDTH = width;
		configBack.HEIGHT = height;
		configBack.RECTANGLE = new Rectangle(4, 4, width - 4, height - 4); // !
		configBack.CIRCLE = new ImmutableCircle(modelBack.cx, modelBack.cy, radiusBack);                                         // ??? center
		FisheyeProjection projectionFisheyeBack = new FisheyeProjection(configBack, modelBack);

		DualFisheyeImageStitch.Entry entryFront = new DualFisheyeImageStitch.Entry(DualFisheyeType.FRONT, imageFront, projectionFisheyeFront, deviationFront);
		DualFisheyeImageStitch.Entry entryBack = new DualFisheyeImageStitch.Entry(DualFisheyeType.BACK, imageBack, projectionFisheyeBack, deviationBack);
		DualFisheyeImageStitch stitch = new DualFisheyeImageStitch(entryFront, entryBack, interpolation, joint);;

		Cartesian3dPoint pointCartesian3d = new Cartesian3dPoint();
		LabF32Color colorLabF32 = new LabF32Color();

		for (int j = 0; j < projection.HEIGHT; j++)
		{
			for (int i = 0; i < projection.WIDTH; i++)
			{
				projection.convert(i, j, pointCartesian3d);
				transformer.transform(pointCartesian3d);

				if (stitch.process(mode, pointCartesian3d, colorLabF32)) temp.set0(i, j, colorLabF32);
			}
		}

		processor.process();
	}


	//
	@FunctionalInterface
	public interface IProcessor
	{
		void process();
	}

}
