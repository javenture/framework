package net.javenture.framework.math;


/*
	2020/04/02
 */
@SuppressWarnings("SuspiciousNameCombination")
public enum Cartesian3dOrientationTransformation
{
	//
	XYZ_YZX((source, destination) -> destination.set(source.z, source.x, source.y)),
	XYZ_ZXY((source, destination) -> destination.set(source.y, source.z, source.x)),
	YZX_XYZ((source, destination) -> destination.set(source.y, source.z, source.x)),
	YZX_ZXY((source, destination) -> destination.set(source.z, source.x, source.y)),
	ZXY_XYZ((source, destination) -> destination.set(source.z, source.x, source.y)),
	ZXY_YZX((source, destination) -> destination.set(source.y, source.z, source.x));


	//
	final IAction ACTION;


	//
	Cartesian3dOrientationTransformation(IAction action)
	{
		ACTION = action;
	}


	//
	@FunctionalInterface
	interface IAction
	{
		void execute(ACartesian3dTuple source, ACartesian3dTuple destination);
	}

}
