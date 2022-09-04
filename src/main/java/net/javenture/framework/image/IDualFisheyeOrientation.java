package net.javenture.framework.image;


import net.javenture.framework.math.ACartesian3dTuple;


/*
	2020/04/06
 */
public interface IDualFisheyeOrientation
{
	//
	void local(ACartesian3dTuple tuple);
	void global(ACartesian3dTuple tuple);


	//
	IDualFisheyeOrientation FRONT = new IDualFisheyeOrientation()
	{
		@Override
		public void local(ACartesian3dTuple tuple)
		{
			// blank
		}

		@Override
		public void global(ACartesian3dTuple tuple)
		{
			// blank
		}
	};


	IDualFisheyeOrientation BACK = new IDualFisheyeOrientation()
	{
		@Override
		public void local(ACartesian3dTuple tuple)
		{
			tuple.x = -tuple.x;
			tuple.y = -tuple.y;
		}

		@Override
		public void global(ACartesian3dTuple tuple)
		{
			tuple.x = -tuple.x;
			tuple.y = -tuple.y;
		}
	};

}
