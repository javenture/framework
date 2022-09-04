package net.javenture.framework.math;


/*
	2020/04/10
 */
@SuppressWarnings("SuspiciousNameCombination")
public enum Cartesian3dRotation
{
	//
	X_90((source, destination) -> destination.set(source.x, -source.z, source.y)),
	X_180((source, destination) -> destination.set(source.x, -source.y, -source.z)),
	X_270((source, destination) -> destination.set(source.x, source.z, -source.y)),

	Y_90((source, destination) -> destination.set(source.z, source.y, -source.x)),
	Y_180((source, destination) -> destination.set(-source.x, source.y, -source.z)),
	Y_270((source, destination) -> destination.set(-source.z, source.y, source.x)),

	Z_90((source, destination) -> destination.set(-source.y, source.x, source.z)),
	Z_180((source, destination) -> destination.set(-source.x, -source.y, source.z)),
	Z_270((source, destination) -> destination.set(source.y, -source.x, source.z));


	//
	final IAction ACTION;


	//
	Cartesian3dRotation(IAction action)
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
