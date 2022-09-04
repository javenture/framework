package net.javenture.framework.image;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.factory.Factories;
import net.javenture.framework.math.ACartesian2dTuple;
import net.javenture.framework.math.MathUtil;
import net.javenture.framework.util.IntUtil;


/*
	2020/10/20
 */
public enum F32ImageInterpolation
	implements IF32ImageInterpolation // !
{
	/*
		https://www.panotools.org/dersch/interpolator/interpolator.html
	 */

	//
	NEAREST(Nearest::interpolate, Nearest::interpolate),
	LINEAR_4(Linear4::interpolate, Linear4::interpolate),
	CUBIC_16(Cubic16::interpolate, Cubic16::interpolate),
	SPLINE_16(Spline16::interpolate, Spline16::interpolate),
	SPLINE_36(Spline36::interpolate, Spline36::interpolate),
	SINC_64(Sinc64::interpolate, Sinc64::interpolate);


	//
	final private ISingleAction ACTION_SINGLE;
	final private IMultiAction ACTION_MULTI;


	//
	F32ImageInterpolation(ISingleAction actionSingle, IMultiAction actionMulti)
	{
		ACTION_SINGLE = actionSingle;
		ACTION_MULTI = actionMulti;
	}


	//
	@Override
	final public
	@NullAllow
	<V extends Float, I extends AF32Image<V, C, CH>, C extends AF32Color<V, CH>, CH extends IF32ColorChannel>
	Float
	interpolate(I image, double x, double y, CH channel)
	{
		return ACTION_SINGLE.execute(image, x, y, channel);
	}


	@Override
	final public
	<V extends Float, I extends AF32Image<V, C, CH>, C extends AF32Color<V, CH>, CH extends IF32ColorChannel>
	boolean
	interpolate(I image, double x, double y, C destination, CH[] channels) // ! CH[]
	{
		return ACTION_MULTI.execute(image, x, y, destination, channels);
	}









	@Override
	public boolean defined()
	{


		throw new UnsupportedOperationException();
	}

	@Override
	public int id()
	{


		throw new UnsupportedOperationException();
	}

	@Override
	public Factories<IF32ImageInterpolation> factories()
	{


		throw new UnsupportedOperationException();
	}

	@Override
	public boolean equals(IF32ImageInterpolation object)
	{


		throw new UnsupportedOperationException();
	}









	//
	@FunctionalInterface
	private interface ISingleAction
	{
		@NullAllow
		<V extends Float, I extends AF32Image<V, C, CH>, C extends AF32Color<V, CH>, CH extends IF32ColorChannel>
		Float
		execute(I image, double x, double y, CH channel);
	}


	@FunctionalInterface
	private interface IMultiAction
	{
		<V extends Float, I extends AF32Image<V, C, CH>, C extends AF32Color<V, CH>, CH extends IF32ColorChannel>
		boolean
		execute(I image, double x, double y, C destination, CH[] channels); // ! CH[]
	}
















	final public static class Nearest
	{
		/*
		    -------
			| v00 |
			-------
		 */

		//
		private Nearest()
		{
		}

		//
		public static
		@NullAllow
		<V extends Float, I extends AF32Image<V, C, CH>, C extends AF32Color<V, CH>, CH extends IF32ColorChannel>
		Float
		interpolate(I image, ACartesian2dTuple tuple, CH channel)
		{
			return interpolate(image, tuple.x, tuple.y, channel);
		}

		public static
		@NullAllow
		<V extends Float, I extends AF32Image<V, C, CH>, C extends AF32Color<V, CH>, CH extends IF32ColorChannel>
		Float
		interpolate(I image, double x, double y, CH channel)
		{
			int x0 = IntUtil.round(x);
			int y0 = IntUtil.round(y);

			return image.contains(x0, y0)
				? image.get0(x0, y0, channel)
				: null;
		}

		static
		<V extends Float, I extends AF32Image<V, C, CH>, C extends AF32Color<V, CH>, CH extends IF32ColorChannel>
		float
		interpolate0(I image, ACartesian2dTuple tuple, CH channel)
		{
			return interpolate0(image, tuple.x, tuple.y, channel);
		}

		static
		<V extends Float, I extends AF32Image<V, C, CH>, C extends AF32Color<V, CH>, CH extends IF32ColorChannel>
		float
		interpolate0(I image, double x, double y, CH channel)
		{
			int x0 = IntUtil.round(x);
			int y0 = IntUtil.round(y);
			int ordinal = channel.ordinal();

			return image.array0(x0, y0)[ordinal];
		}

		public static
		<V extends Float, I extends AF32Image<V, C, CH>, C extends AF32Color<V, CH>, CH extends IF32ColorChannel>
		boolean
		interpolate(I image, ACartesian2dTuple tuple, C destination, CH[] channels)
		{
			return interpolate(image, tuple.x, tuple.y, destination, channels);
		}

		public static
		<V extends Float, I extends AF32Image<V, C, CH>, C extends AF32Color<V, CH>, CH extends IF32ColorChannel>
		boolean
		interpolate(I image, double x, double y, C destination, CH[] channels)
		{
			int x0 = IntUtil.round(x);
			int y0 = IntUtil.round(y);

			if (image.contains(x0, y0))
			{
				for (CH channel : channels)
				{
					float value = image.get0(x0, y0, channel);
					destination.set0(channel, value);
				}

				return true;
			}
			else
			{
				return false;
			}
		}

		static
		<V extends Float, I extends AF32Image<V, C, CH>, C extends AF32Color<V, CH>, CH extends IF32ColorChannel>
		void
		interpolate0(I image, ACartesian2dTuple tuple, C destination, CH[] channels)
		{
			interpolate0(image, tuple.x, tuple.y, destination, channels);
		}

		static
		<V extends Float, I extends AF32Image<V, C, CH>, C extends AF32Color<V, CH>, CH extends IF32ColorChannel>
		void
		interpolate0(I image, double x, double y, C destination, CH[] channels)
		{
			int x0 = IntUtil.round(x);
			int y0 = IntUtil.round(y);
			float[] array00 = image.array0(x0, y0);

			for (CH channel : channels)
			{
				int ordinal = channel.ordinal();
				float value = array00[ordinal];
				destination.set0(channel, value);
			}
		}
	}


	final public static class Linear4
	{
		/*
			-------------
			| v00 | v10 |
			-------------
			| v01 | v11 |
			-------------
		 */

		//
		private Linear4()
		{
		}

		//
		public static
		@NullAllow
		<V extends Float, I extends AF32Image<V, C, CH>, C extends AF32Color<V, CH>, CH extends IF32ColorChannel>
		Float
		interpolate(I image, ACartesian2dTuple tuple, CH channel)
		{
			return interpolate(image, tuple.x, tuple.y, channel);
		}

		public static
		@NullAllow
		<V extends Float, I extends AF32Image<V, C, CH>, C extends AF32Color<V, CH>, CH extends IF32ColorChannel>
		Float
		interpolate(I image, double x, double y, CH channel)
		{
			ImageBorder<V, C, CH> border = image.BORDER;
			int x0 = (int) x;
			int y0 = (int) y;

			if (border.contains(x0, y0))
			{
				int x1 = x0 + 1;
				int y1 = y0 + 1;

				double h2 = x - x0;
				double v2 = y - y0;
				double h = 1 - h2;
				double v = 1 - v2;
				double hv = h * v;
				double h2v = h2 * v;
				double hv2 = h * v2;
				double h2v2 = h2 * v2;

				double v00 = border.get(x0, y0, channel);
				double v10 = border.get(x1, y0, channel);
				double v01 = border.get(x0, y1, channel);
				double v11 = border.get(x1, y1, channel);

				return (float) (v00 * hv + v10 * h2v + v01 * hv2 + v11 * h2v2);
			}
			else
			{
				return null;
			}
		}

		static
		<V extends Float, I extends AF32Image<V, C, CH>, C extends AF32Color<V, CH>, CH extends IF32ColorChannel>
		float
		interpolate0(I image, ACartesian2dTuple tuple, CH channel)
		{
			return interpolate0(image, tuple.x, tuple.y, channel);
		}

		static
		<V extends Float, I extends AF32Image<V, C, CH>, C extends AF32Color<V, CH>, CH extends IF32ColorChannel>
		float
		interpolate0(I image, double x, double y, CH channel)
		{
			int x0 = (int) x;
			int y0 = (int) y;

			int x1 = x0 + 1;
			int y1 = y0 + 1;

			double h2 = x - x0;
			double v2 = y - y0;
			double h = 1 - h2;
			double v = 1 - v2;
			double hv = h * v;
			double h2v = h2 * v;
			double hv2 = h * v2;
			double h2v2 = h2 * v2;

			int ordinal = channel.ordinal();
			float v00 = image.array0(x0, y0)[ordinal];
			float v10 = image.array0(x1, y0)[ordinal];
			float v01 = image.array0(x0, y1)[ordinal];
			float v11 = image.array0(x1, y1)[ordinal];

			return (float) (v00 * hv + v10 * h2v + v01 * hv2 + v11 * h2v2);
		}

		public static
		<V extends Float, I extends AF32Image<V, C, CH>, C extends AF32Color<V, CH>, CH extends IF32ColorChannel>
		boolean
		interpolate(I image, ACartesian2dTuple tuple, C destination, CH[] channels)
		{
			return interpolate(image, tuple.x, tuple.y, destination, channels);
		}

		public static
		<V extends Float, I extends AF32Image<V, C, CH>, C extends AF32Color<V, CH>, CH extends IF32ColorChannel>
		boolean
		interpolate(I image, double x, double y, C destination, CH[] channels)
		{
			ImageBorder<V, C, CH> border = image.BORDER;
			int x0 = (int) x;
			int y0 = (int) y;

			if (border.contains(x0, y0))
			{
				int x1 = x0 + 1;
				int y1 = y0 + 1;

				double h2 = x - x0;
				double v2 = y - y0;
				double h = 1 - h2;
				double v = 1 - v2;
				double hv = h * v;
				double h2v = h2 * v;
				double hv2 = h * v2;
				double h2v2 = h2 * v2;

				for (CH channel : channels)
				{
					double v00 = border.get(x0, y0, channel);
					double v10 = border.get(x1, y0, channel);
					double v01 = border.get(x0, y1, channel);
					double v11 = border.get(x1, y1, channel);
					double value = v00 * hv + v10 * h2v + v01 * hv2 + v11 * h2v2;
					destination.set0(channel, value);
				}

				return true;
			}
			else
			{
				return false;
			}
		}

		static
		<V extends Float, I extends AF32Image<V, C, CH>, C extends AF32Color<V, CH>, CH extends IF32ColorChannel>
		void
		interpolate0(I image, ACartesian2dTuple tuple, C destination, CH[] channels)
		{
			interpolate0(image, tuple.x, tuple.y, destination, channels);
		}

		static
		<V extends Float, I extends AF32Image<V, C, CH>, C extends AF32Color<V, CH>, CH extends IF32ColorChannel>
		void
		interpolate0(I image, double x, double y, C destination, CH[] channels)
		{
			int x0 = (int) x;
			int y0 = (int) y;

			int x1 = x0 + 1;
			int y1 = y0 + 1;

			double h2 = x - x0;
			double v2 = y - y0;
			double h = 1 - h2;
			double v = 1 - v2;
			double hv = h * v;
			double h2v = h2 * v;
			double hv2 = h * v2;
			double h2v2 = h2 * v2;

			float[] array00 = image.array0(x0, y0);
			float[] array10 = image.array0(x1, y0);
			float[] array01 = image.array0(x0, y1);
			float[] array11 = image.array0(x1, y1);

			for (CH channel : channels)
			{
				int ordinal = channel.ordinal();
				double value = array00[ordinal] * hv + array10[ordinal] * h2v + array01[ordinal] * hv2 + array11[ordinal] * h2v2;
				destination.set0(channel, value);
			}
		}
	}


	final public static class Cubic16
	{
		/*
			-------------------------
			| v00 | v10 | v20 | v30 |
			-------------------------
			| v01 | v11 | v21 | v31 |
			-------------------------
			| v02 | v12 | v22 | v32 |
			-------------------------
			| v03 | v13 | v23 | v33 |
			-------------------------

			weight = ((A + 2.0) * x - (A + 3.0)) * x * x + 1.0; 0 < x < 1
			weight = ((A * x - 5.0 * A) * x + 8.0 * A) * x - 4.0 * A; 1 < x < 2
            A = -0.5 | -0.75
		 */
		final private static double A = -0.75;

		//
		private Cubic16()
		{
		}

		//
		public static
		@NullAllow
		<V extends Float, I extends AF32Image<V, C, CH>, C extends AF32Color<V, CH>, CH extends IF32ColorChannel>
		Float
		interpolate(I image, ACartesian2dTuple tuple, CH channel)
		{
			return interpolate(image, tuple.x, tuple.y, channel);
		}

		public static
		@NullAllow
		<V extends Float, I extends AF32Image<V, C, CH>, C extends AF32Color<V, CH>, CH extends IF32ColorChannel>
		Float
		interpolate(I image, double x, double y, CH channel)
		{
			ImageBorder<V, C, CH> border = image.BORDER;
			int x1 = (int) x;
			int y1 = (int) y;

			if (border.contains(x1, y1))
			{
				int x0 = x1 - 1;
				int x2 = x1 + 1;
				int x3 = x1 + 2;
				int y0 = y1 - 1;
				int y2 = y1 + 1;
				int y3 = y1 + 2;

				double wh0 = weight(x - x0);
				double wh1 = weight(x - x1);
				double wh2 = weight(x2 - x);
				double wh3 = weight(x3 - x);
				double wv0 = weight(y - y0);
				double wv1 = weight(y - y1);
				double wv2 = weight(y2 - y);
				double wv3 = weight(y3 - y);

				double v00 = border.get(x0, y0, channel);
				double v10 = border.get(x1, y0, channel);
				double v20 = border.get(x2, y0, channel);
				double v30 = border.get(x3, y0, channel);
				double v01 = border.get(x0, y1, channel);
				double v11 = border.get(x1, y1, channel);
				double v21 = border.get(x2, y1, channel);
				double v31 = border.get(x3, y1, channel);
				double v02 = border.get(x0, y2, channel);
				double v12 = border.get(x1, y2, channel);
				double v22 = border.get(x2, y2, channel);
				double v32 = border.get(x3, y2, channel);
				double v03 = border.get(x0, y3, channel);
				double v13 = border.get(x1, y3, channel);
				double v23 = border.get(x2, y3, channel);
				double v33 = border.get(x3, y3, channel);

				double result =
					(v00 * wh0 + v10 * wh1 + v20 * wh2 + v30 * wh3) * wv0
					+
					(v01 * wh0 + v11 * wh1 + v21 * wh2 + v31 * wh3) * wv1
					+
					(v02 * wh0 + v12 * wh1 + v22 * wh2 + v32 * wh3) * wv2
					+
					(v03 * wh0 + v13 * wh1 + v23 * wh2 + v33 * wh3) * wv3;

				return (float) result;

			}
			else
			{
				return null;
			}
		}

		static
		<V extends Float, I extends AF32Image<V, C, CH>, C extends AF32Color<V, CH>, CH extends IF32ColorChannel>
		float
		interpolate0(I image, ACartesian2dTuple tuple, CH channel)
		{
			return interpolate0(image, tuple.x, tuple.y, channel);
		}

		static
		<V extends Float, I extends AF32Image<V, C, CH>, C extends AF32Color<V, CH>, CH extends IF32ColorChannel>
		float
		interpolate0(I image, double x, double y, CH channel)
		{
			int x1 = (int) x;
			int y1 = (int) y;

			int x0 = x1 - 1;
			int x2 = x1 + 1;
			int x3 = x1 + 2;
			int y0 = y1 - 1;
			int y2 = y1 + 1;
			int y3 = y1 + 2;

			double wh0 = weight(x - x0);
			double wh1 = weight(x - x1);
			double wh2 = weight(x2 - x);
			double wh3 = weight(x3 - x);
			double wv0 = weight(y - y0);
			double wv1 = weight(y - y1);
			double wv2 = weight(y2 - y);
			double wv3 = weight(y3 - y);

			int ordinal = channel.ordinal();
			double v00 = image.array0(x0, y0)[ordinal];
			double v10 = image.array0(x1, y0)[ordinal];
			double v20 = image.array0(x2, y0)[ordinal];
			double v30 = image.array0(x3, y0)[ordinal];
			double v01 = image.array0(x0, y1)[ordinal];
			double v11 = image.array0(x1, y1)[ordinal];
			double v21 = image.array0(x2, y1)[ordinal];
			double v31 = image.array0(x3, y1)[ordinal];
			double v02 = image.array0(x0, y2)[ordinal];
			double v12 = image.array0(x1, y2)[ordinal];
			double v22 = image.array0(x2, y2)[ordinal];
			double v32 = image.array0(x3, y2)[ordinal];
			double v03 = image.array0(x0, y3)[ordinal];
			double v13 = image.array0(x1, y3)[ordinal];
			double v23 = image.array0(x2, y3)[ordinal];
			double v33 = image.array0(x3, y3)[ordinal];

			double result =
				(v00 * wh0 + v10 * wh1 + v20 * wh2 + v30 * wh3) * wv0
				+
				(v01 * wh0 + v11 * wh1 + v21 * wh2 + v31 * wh3) * wv1
				+
				(v02 * wh0 + v12 * wh1 + v22 * wh2 + v32 * wh3) * wv2
				+
				(v03 * wh0 + v13 * wh1 + v23 * wh2 + v33 * wh3) * wv3;

			return (float) result;
		}

		public static
		<V extends Float, I extends AF32Image<V, C, CH>, C extends AF32Color<V, CH>, CH extends IF32ColorChannel>
		boolean
		interpolate(I image, ACartesian2dTuple tuple, C destination, CH[] channels)
		{
			return interpolate(image, tuple.x, tuple.y, destination, channels);
		}

		public static
		<V extends Float, I extends AF32Image<V, C, CH>, C extends AF32Color<V, CH>, CH extends IF32ColorChannel>
		boolean
		interpolate(I image, double x, double y, C destination, CH[] channels)
		{
			ImageBorder<V, C, CH> border = image.BORDER;
			int x1 = (int) x;
			int y1 = (int) y;

			if (border.contains(x1, y1))
			{
				int x0 = x1 - 1;
				int x2 = x1 + 1;
				int x3 = x1 + 2;
				int y0 = y1 - 1;
				int y2 = y1 + 1;
				int y3 = y1 + 2;

				double wh0 = weight(x - x0);
				double wh1 = weight(x - x1);
				double wh2 = weight(x2 - x);
				double wh3 = weight(x3 - x);
				double wv0 = weight(y - y0);
				double wv1 = weight(y - y1);
				double wv2 = weight(y2 - y);
				double wv3 = weight(y3 - y);

				for (CH channel : channels)
				{
					double v00 = border.get(x0, y0, channel);
					double v10 = border.get(x1, y0, channel);
					double v20 = border.get(x2, y0, channel);
					double v30 = border.get(x3, y0, channel);
					double v01 = border.get(x0, y1, channel);
					double v11 = border.get(x1, y1, channel);
					double v21 = border.get(x2, y1, channel);
					double v31 = border.get(x3, y1, channel);
					double v02 = border.get(x0, y2, channel);
					double v12 = border.get(x1, y2, channel);
					double v22 = border.get(x2, y2, channel);
					double v32 = border.get(x3, y2, channel);
					double v03 = border.get(x0, y3, channel);
					double v13 = border.get(x1, y3, channel);
					double v23 = border.get(x2, y3, channel);
					double v33 = border.get(x3, y3, channel);

					double value =
						(v00 * wh0 + v10 * wh1 + v20 * wh2 + v30 * wh3) * wv0
						+
						(v01 * wh0 + v11 * wh1 + v21 * wh2 + v31 * wh3) * wv1
						+
						(v02 * wh0 + v12 * wh1 + v22 * wh2 + v32 * wh3) * wv2
						+
						(v03 * wh0 + v13 * wh1 + v23 * wh2 + v33 * wh3) * wv3;

					destination.set0(channel, value);
				}

				return true;
			}
			else
			{
				return false;
			}
		}

		static
		<V extends Float, I extends AF32Image<V, C, CH>, C extends AF32Color<V, CH>, CH extends IF32ColorChannel>
		void
		interpolate0(I image, ACartesian2dTuple tuple, C destination, CH[] channels)
		{
			interpolate0(image, tuple.x, tuple.y, destination, channels);
		}

		static
		<V extends Float, I extends AF32Image<V, C, CH>, C extends AF32Color<V, CH>, CH extends IF32ColorChannel>
		void
		interpolate0(I image, double x, double y, C destination, CH[] channels)
		{
			int x1 = (int) x;
			int y1 = (int) y;

			int x0 = x1 - 1;
			int x2 = x1 + 1;
			int x3 = x1 + 2;
			int y0 = y1 - 1;
			int y2 = y1 + 1;
			int y3 = y1 + 2;

			double wh0 = weight(x - x0);
			double wh1 = weight(x - x1);
			double wh2 = weight(x2 - x);
			double wh3 = weight(x3 - x);
			double wv0 = weight(y - y0);
			double wv1 = weight(y - y1);
			double wv2 = weight(y2 - y);
			double wv3 = weight(y3 - y);

			float[] array00 = image.array0(x0, y0);
			float[] array10 = image.array0(x1, y0);
			float[] array20 = image.array0(x2, y0);
			float[] array30 = image.array0(x3, y0);
			float[] array01 = image.array0(x0, y1);
			float[] array11 = image.array0(x1, y1);
			float[] array21 = image.array0(x2, y1);
			float[] array31 = image.array0(x3, y1);
			float[] array02 = image.array0(x0, y2);
			float[] array12 = image.array0(x1, y2);
			float[] array22 = image.array0(x2, y2);
			float[] array32 = image.array0(x3, y2);
			float[] array03 = image.array0(x0, y3);
			float[] array13 = image.array0(x1, y3);
			float[] array23 = image.array0(x2, y3);
			float[] array33 = image.array0(x3, y3);

			for (CH channel : channels)
			{
				int ordinal = channel.ordinal();
				double v00 = array00[ordinal];
				double v10 = array10[ordinal];
				double v20 = array20[ordinal];
				double v30 = array30[ordinal];
				double v01 = array01[ordinal];
				double v11 = array11[ordinal];
				double v21 = array21[ordinal];
				double v31 = array31[ordinal];
				double v02 = array02[ordinal];
				double v12 = array12[ordinal];
				double v22 = array22[ordinal];
				double v32 = array32[ordinal];
				double v03 = array03[ordinal];
				double v13 = array13[ordinal];
				double v23 = array23[ordinal];
				double v33 = array33[ordinal];

				double value =
					(v00 * wh0 + v10 * wh1 + v20 * wh2 + v30 * wh3) * wv0
					+
					(v01 * wh0 + v11 * wh1 + v21 * wh2 + v31 * wh3) * wv1
					+
					(v02 * wh0 + v12 * wh1 + v22 * wh2 + v32 * wh3) * wv2
					+
					(v03 * wh0 + v13 * wh1 + v23 * wh2 + v33 * wh3) * wv3;

				destination.set0(channel, value);
			}
		}

		private static double weight(double d)
		{
			if (d == 0) return 1;
			else if (d > 0 && d < 1) return ((A + 2.0) * d - (A + 3.0)) * d * d + 1.0;
			else if (d > 1 && d < 2) return ((A * d - 5.0 * A) * d + 8.0 * A) * d - 4.0 * A;
			else return 0;
		}
	}


	final static class Spline16
	{
		/*
			-------------------------
			| v00 | v10 | v20 | v30 |
			-------------------------
			| v01 | v11 | v21 | v31 |
			-------------------------
			| v02 | v12 | v22 | v32 |
			-------------------------
			| v03 | v13 | v23 | v33 |
			-------------------------

			weight = ((x - 9.0 / 5.0) * x - 1.0 / 5.0) * x + 1.0; 0 < x < 1
			weight = ((-1.0 / 3.0 * (x - 1) + 4.0 / 5.0) * (x - 1) - 7.0 / 15.0) * (x - 1); 1 < x < 2
		 */

		//
		private Spline16()
		{
		}

		//
		public static
		@NullAllow
		<V extends Float, I extends AF32Image<V, C, CH>, C extends AF32Color<V, CH>, CH extends IF32ColorChannel>
		Float
		interpolate(I image, ACartesian2dTuple tuple, CH channel)
		{
			return interpolate(image, tuple.x, tuple.y, channel);
		}

		public static
		@NullAllow
		<V extends Float, I extends AF32Image<V, C, CH>, C extends AF32Color<V, CH>, CH extends IF32ColorChannel>
		Float
		interpolate(I image, double x, double y, CH channel)
		{
			ImageBorder<V, C, CH> border = image.BORDER;
			int x1 = (int) x;
			int y1 = (int) y;

			if (border.contains(x1, y1))
			{
				int x0 = x1 - 1;
				int x2 = x1 + 1;
				int x3 = x1 + 2;
				int y0 = y1 - 1;
				int y2 = y1 + 1;
				int y3 = y1 + 2;

				double wh0 = weight(x - x0);
				double wh1 = weight(x - x1);
				double wh2 = weight(x2 - x);
				double wh3 = weight(x3 - x);
				double wv0 = weight(y - y0);
				double wv1 = weight(y - y1);
				double wv2 = weight(y2 - y);
				double wv3 = weight(y3 - y);

				double v00 = border.get(x0, y0, channel);
				double v10 = border.get(x1, y0, channel);
				double v20 = border.get(x2, y0, channel);
				double v30 = border.get(x3, y0, channel);
				double v01 = border.get(x0, y1, channel);
				double v11 = border.get(x1, y1, channel);
				double v21 = border.get(x2, y1, channel);
				double v31 = border.get(x3, y1, channel);
				double v02 = border.get(x0, y2, channel);
				double v12 = border.get(x1, y2, channel);
				double v22 = border.get(x2, y2, channel);
				double v32 = border.get(x3, y2, channel);
				double v03 = border.get(x0, y3, channel);
				double v13 = border.get(x1, y3, channel);
				double v23 = border.get(x2, y3, channel);
				double v33 = border.get(x3, y3, channel);

				double result =
					(v00 * wh0 + v10 * wh1 + v20 * wh2 + v30 * wh3) * wv0
					+
					(v01 * wh0 + v11 * wh1 + v21 * wh2 + v31 * wh3) * wv1
					+
					(v02 * wh0 + v12 * wh1 + v22 * wh2 + v32 * wh3) * wv2
					+
					(v03 * wh0 + v13 * wh1 + v23 * wh2 + v33 * wh3) * wv3;

				return (float) result;

			}
			else
			{
				return null;
			}
		}

		static
		<V extends Float, I extends AF32Image<V, C, CH>, C extends AF32Color<V, CH>, CH extends IF32ColorChannel>
		float
		interpolate0(I image, ACartesian2dTuple tuple, CH channel)
		{
			return interpolate0(image, tuple.x, tuple.y, channel);
		}

		static
		<V extends Float, I extends AF32Image<V, C, CH>, C extends AF32Color<V, CH>, CH extends IF32ColorChannel>
		float
		interpolate0(I image, double x, double y, CH channel)
		{
			int x1 = (int) x;
			int y1 = (int) y;

			int x0 = x1 - 1;
			int x2 = x1 + 1;
			int x3 = x1 + 2;
			int y0 = y1 - 1;
			int y2 = y1 + 1;
			int y3 = y1 + 2;

			double wh0 = weight(x - x0);
			double wh1 = weight(x - x1);
			double wh2 = weight(x2 - x);
			double wh3 = weight(x3 - x);
			double wv0 = weight(y - y0);
			double wv1 = weight(y - y1);
			double wv2 = weight(y2 - y);
			double wv3 = weight(y3 - y);

			int ordinal = channel.ordinal();
			double v00 = image.array0(x0, y0)[ordinal];
			double v10 = image.array0(x1, y0)[ordinal];
			double v20 = image.array0(x2, y0)[ordinal];
			double v30 = image.array0(x3, y0)[ordinal];
			double v01 = image.array0(x0, y1)[ordinal];
			double v11 = image.array0(x1, y1)[ordinal];
			double v21 = image.array0(x2, y1)[ordinal];
			double v31 = image.array0(x3, y1)[ordinal];
			double v02 = image.array0(x0, y2)[ordinal];
			double v12 = image.array0(x1, y2)[ordinal];
			double v22 = image.array0(x2, y2)[ordinal];
			double v32 = image.array0(x3, y2)[ordinal];
			double v03 = image.array0(x0, y3)[ordinal];
			double v13 = image.array0(x1, y3)[ordinal];
			double v23 = image.array0(x2, y3)[ordinal];
			double v33 = image.array0(x3, y3)[ordinal];

			double result =
				(v00 * wh0 + v10 * wh1 + v20 * wh2 + v30 * wh3) * wv0
				+
				(v01 * wh0 + v11 * wh1 + v21 * wh2 + v31 * wh3) * wv1
				+
				(v02 * wh0 + v12 * wh1 + v22 * wh2 + v32 * wh3) * wv2
				+
				(v03 * wh0 + v13 * wh1 + v23 * wh2 + v33 * wh3) * wv3;

			return (float) result;
		}

		public static
		<V extends Float, I extends AF32Image<V, C, CH>, C extends AF32Color<V, CH>, CH extends IF32ColorChannel>
		boolean
		interpolate(I image, ACartesian2dTuple tuple, C destination, CH[] channels)
		{
			return interpolate(image, tuple.x, tuple.y, destination, channels);
		}

		public static
		<V extends Float, I extends AF32Image<V, C, CH>, C extends AF32Color<V, CH>, CH extends IF32ColorChannel>
		boolean
		interpolate(I image, double x, double y, C destination, CH[] channels)
		{
			ImageBorder<V, C, CH> border = image.BORDER;
			int x1 = (int) x;
			int y1 = (int) y;

			if (border.contains(x1, y1))
			{
				int x0 = x1 - 1;
				int x2 = x1 + 1;
				int x3 = x1 + 2;
				int y0 = y1 - 1;
				int y2 = y1 + 1;
				int y3 = y1 + 2;

				double wh0 = weight(x - x0);
				double wh1 = weight(x - x1);
				double wh2 = weight(x2 - x);
				double wh3 = weight(x3 - x);
				double wv0 = weight(y - y0);
				double wv1 = weight(y - y1);
				double wv2 = weight(y2 - y);
				double wv3 = weight(y3 - y);

				for (CH channel : channels)
				{
					double v00 = border.get(x0, y0, channel);
					double v10 = border.get(x1, y0, channel);
					double v20 = border.get(x2, y0, channel);
					double v30 = border.get(x3, y0, channel);
					double v01 = border.get(x0, y1, channel);
					double v11 = border.get(x1, y1, channel);
					double v21 = border.get(x2, y1, channel);
					double v31 = border.get(x3, y1, channel);
					double v02 = border.get(x0, y2, channel);
					double v12 = border.get(x1, y2, channel);
					double v22 = border.get(x2, y2, channel);
					double v32 = border.get(x3, y2, channel);
					double v03 = border.get(x0, y3, channel);
					double v13 = border.get(x1, y3, channel);
					double v23 = border.get(x2, y3, channel);
					double v33 = border.get(x3, y3, channel);

					double value =
						(v00 * wh0 + v10 * wh1 + v20 * wh2 + v30 * wh3) * wv0
						+
						(v01 * wh0 + v11 * wh1 + v21 * wh2 + v31 * wh3) * wv1
						+
						(v02 * wh0 + v12 * wh1 + v22 * wh2 + v32 * wh3) * wv2
						+
						(v03 * wh0 + v13 * wh1 + v23 * wh2 + v33 * wh3) * wv3;

					destination.set0(channel, value);
				}

				return true;
			}
			else
			{
				return false;
			}
		}

		static
		<V extends Float, I extends AF32Image<V, C, CH>, C extends AF32Color<V, CH>, CH extends IF32ColorChannel>
		void
		interpolate0(I image, ACartesian2dTuple tuple, C destination, CH[] channels)
		{
			interpolate0(image, tuple.x, tuple.y, destination, channels);
		}

		static
		<V extends Float, I extends AF32Image<V, C, CH>, C extends AF32Color<V, CH>, CH extends IF32ColorChannel>
		void
		interpolate0(I image, double x, double y, C destination, CH[] channels)
		{
			int x1 = (int) x;
			int y1 = (int) y;

			int x0 = x1 - 1;
			int x2 = x1 + 1;
			int x3 = x1 + 2;
			int y0 = y1 - 1;
			int y2 = y1 + 1;
			int y3 = y1 + 2;

			double wh0 = weight(x - x0);
			double wh1 = weight(x - x1);
			double wh2 = weight(x2 - x);
			double wh3 = weight(x3 - x);
			double wv0 = weight(y - y0);
			double wv1 = weight(y - y1);
			double wv2 = weight(y2 - y);
			double wv3 = weight(y3 - y);

			float[] array00 = image.array0(x0, y0);
			float[] array10 = image.array0(x1, y0);
			float[] array20 = image.array0(x2, y0);
			float[] array30 = image.array0(x3, y0);
			float[] array01 = image.array0(x0, y1);
			float[] array11 = image.array0(x1, y1);
			float[] array21 = image.array0(x2, y1);
			float[] array31 = image.array0(x3, y1);
			float[] array02 = image.array0(x0, y2);
			float[] array12 = image.array0(x1, y2);
			float[] array22 = image.array0(x2, y2);
			float[] array32 = image.array0(x3, y2);
			float[] array03 = image.array0(x0, y3);
			float[] array13 = image.array0(x1, y3);
			float[] array23 = image.array0(x2, y3);
			float[] array33 = image.array0(x3, y3);

			for (CH channel : channels)
			{
				int ordinal = channel.ordinal();
				double v00 = array00[ordinal];
				double v10 = array10[ordinal];
				double v20 = array20[ordinal];
				double v30 = array30[ordinal];
				double v01 = array01[ordinal];
				double v11 = array11[ordinal];
				double v21 = array21[ordinal];
				double v31 = array31[ordinal];
				double v02 = array02[ordinal];
				double v12 = array12[ordinal];
				double v22 = array22[ordinal];
				double v32 = array32[ordinal];
				double v03 = array03[ordinal];
				double v13 = array13[ordinal];
				double v23 = array23[ordinal];
				double v33 = array33[ordinal];

				double value =
					(v00 * wh0 + v10 * wh1 + v20 * wh2 + v30 * wh3) * wv0
					+
					(v01 * wh0 + v11 * wh1 + v21 * wh2 + v31 * wh3) * wv1
					+
					(v02 * wh0 + v12 * wh1 + v22 * wh2 + v32 * wh3) * wv2
					+
					(v03 * wh0 + v13 * wh1 + v23 * wh2 + v33 * wh3) * wv3;

				destination.set0(channel, value);
			}
		}

		private static double weight(double d)
		{
			if (d == 0) return 1;
			else if (d > 0 && d < 1) return ((d - 9.0 / 5.0) * d - 1.0 / 5.0) * d + 1.0;
			else if (d > 1 && d < 2) return ((-1.0 / 3.0 * (d - 1) + 4.0 / 5.0) * (d - 1) - 7.0 / 15.0) * (d - 1);
			else return 0;
		}
	}


	final public static class Spline36
	{
		/*
			-------------------------------------
			| v00 | v10 | v20 | v30 | v40 | v50 |
			-------------------------------------
			| v01 | v11 | v21 | v31 | v41 | v51 |
			-------------------------------------
			| v02 | v12 | v22 | v32 | v42 | v52 |
			-------------------------------------
			| v03 | v13 | v23 | v33 | v43 | v53 |
			-------------------------------------
			| v04 | v14 | v24 | v34 | v44 | v54 |
			-------------------------------------
			| v05 | v15 | v25 | v35 | v45 | v55 |
			-------------------------------------

			weight = ((13.0 / 11.0 * x - 453.0 / 209.0) * x - 3.0 / 209.0) * x + 1.0; 0 < x < 1
			weight = ((-6.0 / 11.0 * (x - 1) + 270.0 / 209.0) * (x - 1) - 156.0 / 209.0) * (x - 1); 1 < x < 2
			weight = ((1.0 / 11.0 * (x - 2) - 45.0 / 209.0) * (x - 2) + 26.0 / 209.0) * (x - 2); 2 < x < 3
		 */

		//
		private Spline36()
		{
		}

		//
		public static
		@NullAllow
		<V extends Float, I extends AF32Image<V, C, CH>, C extends AF32Color<V, CH>, CH extends IF32ColorChannel>
		Float
		interpolate(I image, ACartesian2dTuple tuple, CH channel)
		{
			return interpolate(image, tuple.x, tuple.y, channel);
		}

		public static
		@NullAllow
		<V extends Float, I extends AF32Image<V, C, CH>, C extends AF32Color<V, CH>, CH extends IF32ColorChannel>
		Float
		interpolate(I image, double x, double y, CH channel)
		{
			ImageBorder<V, C, CH> border = image.BORDER;
			int x2 = (int) x;
			int y2 = (int) y;

			if (border.contains(x2, y2))
			{
				int x0 = x2 - 2;
				int y0 = y2 - 2;
				int x1 = x2 - 1;
				int y1 = y2 - 1;
				int x3 = x2 + 1;
				int y3 = y2 + 1;
				int x4 = x2 + 2;
				int y4 = y2 + 2;
				int x5 = x2 + 3;
				int y5 = y2 + 3;

				double wh0 = weight(x - x0);
				double wh1 = weight(x - x1);
				double wh2 = weight(x - x2);
				double wh3 = weight(x3 - x);
				double wh4 = weight(x4 - x);
				double wh5 = weight(x5 - x);
				double wv0 = weight(y - y0);
				double wv1 = weight(y - y1);
				double wv2 = weight(y - y2);
				double wv3 = weight(y3 - y);
				double wv4 = weight(y4 - y);
				double wv5 = weight(y5 - y);

				double v00 = border.get(x0, y0, channel);
				double v10 = border.get(x1, y0, channel);
				double v20 = border.get(x2, y0, channel);
				double v30 = border.get(x3, y0, channel);
				double v40 = border.get(x4, y0, channel);
				double v50 = border.get(x5, y0, channel);
				double v01 = border.get(x0, y1, channel);
				double v11 = border.get(x1, y1, channel);
				double v21 = border.get(x2, y1, channel);
				double v31 = border.get(x3, y1, channel);
				double v41 = border.get(x4, y1, channel);
				double v51 = border.get(x5, y1, channel);
				double v02 = border.get(x0, y2, channel);
				double v12 = border.get(x1, y2, channel);
				double v22 = border.get(x2, y2, channel);
				double v32 = border.get(x3, y2, channel);
				double v42 = border.get(x4, y2, channel);
				double v52 = border.get(x5, y2, channel);
				double v03 = border.get(x0, y3, channel);
				double v13 = border.get(x1, y3, channel);
				double v23 = border.get(x2, y3, channel);
				double v33 = border.get(x3, y3, channel);
				double v43 = border.get(x4, y3, channel);
				double v53 = border.get(x5, y3, channel);
				double v04 = border.get(x0, y4, channel);
				double v14 = border.get(x1, y4, channel);
				double v24 = border.get(x2, y4, channel);
				double v34 = border.get(x3, y4, channel);
				double v44 = border.get(x4, y4, channel);
				double v54 = border.get(x5, y4, channel);
				double v05 = border.get(x0, y5, channel);
				double v15 = border.get(x1, y5, channel);
				double v25 = border.get(x2, y5, channel);
				double v35 = border.get(x3, y5, channel);
				double v45 = border.get(x4, y5, channel);
				double v55 = border.get(x5, y5, channel);

				double result =
					(v00 * wh0 + v10 * wh1 + v20 * wh2 + v30 * wh3 + v40 * wh4 + v50 * wh5) * wv0
					+
					(v01 * wh0 + v11 * wh1 + v21 * wh2 + v31 * wh3 + v41 * wh4 + v51 * wh5) * wv1
					+
					(v02 * wh0 + v12 * wh1 + v22 * wh2 + v32 * wh3 + v42 * wh4 + v52 * wh5) * wv2
					+
					(v03 * wh0 + v13 * wh1 + v23 * wh2 + v33 * wh3 + v43 * wh4 + v53 * wh5) * wv3
					+
					(v04 * wh0 + v14 * wh1 + v24 * wh2 + v34 * wh3 + v44 * wh4 + v54 * wh5) * wv4
					+
					(v05 * wh0 + v15 * wh1 + v25 * wh2 + v35 * wh3 + v45 * wh4 + v55 * wh5) * wv5;

				return (float) result;
			}
			else
			{
				return null;
			}
		}

		static
		<V extends Float, I extends AF32Image<V, C, CH>, C extends AF32Color<V, CH>, CH extends IF32ColorChannel>
		float
		interpolate0(I image, ACartesian2dTuple tuple, CH channel)
		{
			return interpolate0(image, tuple.x, tuple.y, channel);
		}

		static
		<V extends Float, I extends AF32Image<V, C, CH>, C extends AF32Color<V, CH>, CH extends IF32ColorChannel>
		float
		interpolate0(I image, double x, double y, CH channel)
		{
			int x2 = (int) x;
			int y2 = (int) y;

			int x0 = x2 - 2;
			int y0 = y2 - 2;
			int x1 = x2 - 1;
			int y1 = y2 - 1;
			int x3 = x2 + 1;
			int y3 = y2 + 1;
			int x4 = x2 + 2;
			int y4 = y2 + 2;
			int x5 = x2 + 3;
			int y5 = y2 + 3;

			double wh0 = weight(x - x0);
			double wh1 = weight(x - x1);
			double wh2 = weight(x - x2);
			double wh3 = weight(x3 - x);
			double wh4 = weight(x4 - x);
			double wh5 = weight(x5 - x);
			double wv0 = weight(y - y0);
			double wv1 = weight(y - y1);
			double wv2 = weight(y - y2);
			double wv3 = weight(y3 - y);
			double wv4 = weight(y4 - y);
			double wv5 = weight(y5 - y);

			int ordinal = channel.ordinal();
			double v00 = image.array0(x0, y0)[ordinal];
			double v10 = image.array0(x1, y0)[ordinal];
			double v20 = image.array0(x2, y0)[ordinal];
			double v30 = image.array0(x3, y0)[ordinal];
			double v40 = image.array0(x4, y0)[ordinal];
			double v50 = image.array0(x5, y0)[ordinal];
			double v01 = image.array0(x0, y1)[ordinal];
			double v11 = image.array0(x1, y1)[ordinal];
			double v21 = image.array0(x2, y1)[ordinal];
			double v31 = image.array0(x3, y1)[ordinal];
			double v41 = image.array0(x4, y1)[ordinal];
			double v51 = image.array0(x5, y1)[ordinal];
			double v02 = image.array0(x0, y2)[ordinal];
			double v12 = image.array0(x1, y2)[ordinal];
			double v22 = image.array0(x2, y2)[ordinal];
			double v32 = image.array0(x3, y2)[ordinal];
			double v42 = image.array0(x4, y2)[ordinal];
			double v52 = image.array0(x5, y2)[ordinal];
			double v03 = image.array0(x0, y3)[ordinal];
			double v13 = image.array0(x1, y3)[ordinal];
			double v23 = image.array0(x2, y3)[ordinal];
			double v33 = image.array0(x3, y3)[ordinal];
			double v43 = image.array0(x4, y3)[ordinal];
			double v53 = image.array0(x5, y3)[ordinal];
			double v04 = image.array0(x0, y4)[ordinal];
			double v14 = image.array0(x1, y4)[ordinal];
			double v24 = image.array0(x2, y4)[ordinal];
			double v34 = image.array0(x3, y4)[ordinal];
			double v44 = image.array0(x4, y4)[ordinal];
			double v54 = image.array0(x5, y4)[ordinal];
			double v05 = image.array0(x0, y5)[ordinal];
			double v15 = image.array0(x1, y5)[ordinal];
			double v25 = image.array0(x2, y5)[ordinal];
			double v35 = image.array0(x3, y5)[ordinal];
			double v45 = image.array0(x4, y5)[ordinal];
			double v55 = image.array0(x5, y5)[ordinal];

			double result =
				(v00 * wh0 + v10 * wh1 + v20 * wh2 + v30 * wh3 + v40 * wh4 + v50 * wh5) * wv0
				+
				(v01 * wh0 + v11 * wh1 + v21 * wh2 + v31 * wh3 + v41 * wh4 + v51 * wh5) * wv1
				+
				(v02 * wh0 + v12 * wh1 + v22 * wh2 + v32 * wh3 + v42 * wh4 + v52 * wh5) * wv2
				+
				(v03 * wh0 + v13 * wh1 + v23 * wh2 + v33 * wh3 + v43 * wh4 + v53 * wh5) * wv3
				+
				(v04 * wh0 + v14 * wh1 + v24 * wh2 + v34 * wh3 + v44 * wh4 + v54 * wh5) * wv4
				+
				(v05 * wh0 + v15 * wh1 + v25 * wh2 + v35 * wh3 + v45 * wh4 + v55 * wh5) * wv5;

			return (float) result;
		}

		public static
		<V extends Float, I extends AF32Image<V, C, CH>, C extends AF32Color<V, CH>, CH extends IF32ColorChannel>
		boolean
		interpolate(I image, ACartesian2dTuple tuple, C destination, CH[] channels)
		{
			return interpolate(image, tuple.x, tuple.y, destination, channels);
		}

		public static
		<V extends Float, I extends AF32Image<V, C, CH>, C extends AF32Color<V, CH>, CH extends IF32ColorChannel>
		boolean
		interpolate(I image, double x, double y, C destination, CH[] channels)
		{
			ImageBorder<V, C, CH> border = image.BORDER;
			int x2 = (int) x;
			int y2 = (int) y;

			if (border.contains(x2, y2))
			{
				int x0 = x2 - 2;
				int y0 = y2 - 2;
				int x1 = x2 - 1;
				int y1 = y2 - 1;
				int x3 = x2 + 1;
				int y3 = y2 + 1;
				int x4 = x2 + 2;
				int y4 = y2 + 2;
				int x5 = x2 + 3;
				int y5 = y2 + 3;

				double wh0 = weight(x - x0);
				double wh1 = weight(x - x1);
				double wh2 = weight(x - x2);
				double wh3 = weight(x3 - x);
				double wh4 = weight(x4 - x);
				double wh5 = weight(x5 - x);
				double wv0 = weight(y - y0);
				double wv1 = weight(y - y1);
				double wv2 = weight(y - y2);
				double wv3 = weight(y3 - y);
				double wv4 = weight(y4 - y);
				double wv5 = weight(y5 - y);

				for (CH channel : channels)
				{
					double v00 = border.get(x0, y0, channel);
					double v10 = border.get(x1, y0, channel);
					double v20 = border.get(x2, y0, channel);
					double v30 = border.get(x3, y0, channel);
					double v40 = border.get(x4, y0, channel);
					double v50 = border.get(x5, y0, channel);
					double v01 = border.get(x0, y1, channel);
					double v11 = border.get(x1, y1, channel);
					double v21 = border.get(x2, y1, channel);
					double v31 = border.get(x3, y1, channel);
					double v41 = border.get(x4, y1, channel);
					double v51 = border.get(x5, y1, channel);
					double v02 = border.get(x0, y2, channel);
					double v12 = border.get(x1, y2, channel);
					double v22 = border.get(x2, y2, channel);
					double v32 = border.get(x3, y2, channel);
					double v42 = border.get(x4, y2, channel);
					double v52 = border.get(x5, y2, channel);
					double v03 = border.get(x0, y3, channel);
					double v13 = border.get(x1, y3, channel);
					double v23 = border.get(x2, y3, channel);
					double v33 = border.get(x3, y3, channel);
					double v43 = border.get(x4, y3, channel);
					double v53 = border.get(x5, y3, channel);
					double v04 = border.get(x0, y4, channel);
					double v14 = border.get(x1, y4, channel);
					double v24 = border.get(x2, y4, channel);
					double v34 = border.get(x3, y4, channel);
					double v44 = border.get(x4, y4, channel);
					double v54 = border.get(x5, y4, channel);
					double v05 = border.get(x0, y5, channel);
					double v15 = border.get(x1, y5, channel);
					double v25 = border.get(x2, y5, channel);
					double v35 = border.get(x3, y5, channel);
					double v45 = border.get(x4, y5, channel);
					double v55 = border.get(x5, y5, channel);

					double value =
						(v00 * wh0 + v10 * wh1 + v20 * wh2 + v30 * wh3 + v40 * wh4 + v50 * wh5) * wv0
						+
						(v01 * wh0 + v11 * wh1 + v21 * wh2 + v31 * wh3 + v41 * wh4 + v51 * wh5) * wv1
						+
						(v02 * wh0 + v12 * wh1 + v22 * wh2 + v32 * wh3 + v42 * wh4 + v52 * wh5) * wv2
						+
						(v03 * wh0 + v13 * wh1 + v23 * wh2 + v33 * wh3 + v43 * wh4 + v53 * wh5) * wv3
						+
						(v04 * wh0 + v14 * wh1 + v24 * wh2 + v34 * wh3 + v44 * wh4 + v54 * wh5) * wv4
						+
						(v05 * wh0 + v15 * wh1 + v25 * wh2 + v35 * wh3 + v45 * wh4 + v55 * wh5) * wv5;

					destination.set0(channel, value);
				}

				return true;
			}
			else
			{
				return false;
			}
		}

		static
		<V extends Float, I extends AF32Image<V, C, CH>, C extends AF32Color<V, CH>, CH extends IF32ColorChannel>
		void
		interpolate0(I image, ACartesian2dTuple tuple, C destination, CH[] channels)
		{
			interpolate0(image, tuple.x, tuple.y, destination, channels);
		}

		static
		<V extends Float, I extends AF32Image<V, C, CH>, C extends AF32Color<V, CH>, CH extends IF32ColorChannel>
		void
		interpolate0(I image, double x, double y, C destination, CH[] channels)
		{
			int x2 = (int) x;
			int y2 = (int) y;

			int x0 = x2 - 2;
			int y0 = y2 - 2;
			int x1 = x2 - 1;
			int y1 = y2 - 1;
			int x3 = x2 + 1;
			int y3 = y2 + 1;
			int x4 = x2 + 2;
			int y4 = y2 + 2;
			int x5 = x2 + 3;
			int y5 = y2 + 3;

			double wh0 = weight(x - x0);
			double wh1 = weight(x - x1);
			double wh2 = weight(x - x2);
			double wh3 = weight(x3 - x);
			double wh4 = weight(x4 - x);
			double wh5 = weight(x5 - x);
			double wv0 = weight(y - y0);
			double wv1 = weight(y - y1);
			double wv2 = weight(y - y2);
			double wv3 = weight(y3 - y);
			double wv4 = weight(y4 - y);
			double wv5 = weight(y5 - y);

			float[] array00 = image.array0(x0, y0);
			float[] array10 = image.array0(x1, y0);
			float[] array20 = image.array0(x2, y0);
			float[] array30 = image.array0(x3, y0);
			float[] array40 = image.array0(x4, y0);
			float[] array50 = image.array0(x5, y0);
			float[] array01 = image.array0(x0, y1);
			float[] array11 = image.array0(x1, y1);
			float[] array21 = image.array0(x2, y1);
			float[] array31 = image.array0(x3, y1);
			float[] array41 = image.array0(x4, y1);
			float[] array51 = image.array0(x5, y1);
			float[] array02 = image.array0(x0, y2);
			float[] array12 = image.array0(x1, y2);
			float[] array22 = image.array0(x2, y2);
			float[] array32 = image.array0(x3, y2);
			float[] array42 = image.array0(x4, y2);
			float[] array52 = image.array0(x5, y2);
			float[] array03 = image.array0(x0, y3);
			float[] array13 = image.array0(x1, y3);
			float[] array23 = image.array0(x2, y3);
			float[] array33 = image.array0(x3, y3);
			float[] array43 = image.array0(x4, y3);
			float[] array53 = image.array0(x5, y3);
			float[] array04 = image.array0(x0, y4);
			float[] array14 = image.array0(x1, y4);
			float[] array24 = image.array0(x2, y4);
			float[] array34 = image.array0(x3, y4);
			float[] array44 = image.array0(x4, y4);
			float[] array54 = image.array0(x5, y4);
			float[] array05 = image.array0(x0, y5);
			float[] array15 = image.array0(x1, y5);
			float[] array25 = image.array0(x2, y5);
			float[] array35 = image.array0(x3, y5);
			float[] array45 = image.array0(x4, y5);
			float[] array55 = image.array0(x5, y5);

			for (CH channel : channels)
			{
				int ordinal = channel.ordinal();
				double v00 = array00[ordinal];
				double v10 = array10[ordinal];
				double v20 = array20[ordinal];
				double v30 = array30[ordinal];
				double v40 = array40[ordinal];
				double v50 = array50[ordinal];
				double v01 = array01[ordinal];
				double v11 = array11[ordinal];
				double v21 = array21[ordinal];
				double v31 = array31[ordinal];
				double v41 = array41[ordinal];
				double v51 = array51[ordinal];
				double v02 = array02[ordinal];
				double v12 = array12[ordinal];
				double v22 = array22[ordinal];
				double v32 = array32[ordinal];
				double v42 = array42[ordinal];
				double v52 = array52[ordinal];
				double v03 = array03[ordinal];
				double v13 = array13[ordinal];
				double v23 = array23[ordinal];
				double v33 = array33[ordinal];
				double v43 = array43[ordinal];
				double v53 = array53[ordinal];
				double v04 = array04[ordinal];
				double v14 = array14[ordinal];
				double v24 = array24[ordinal];
				double v34 = array34[ordinal];
				double v44 = array44[ordinal];
				double v54 = array54[ordinal];
				double v05 = array05[ordinal];
				double v15 = array15[ordinal];
				double v25 = array25[ordinal];
				double v35 = array35[ordinal];
				double v45 = array45[ordinal];
				double v55 = array55[ordinal];

				double value =
					(v00 * wh0 + v10 * wh1 + v20 * wh2 + v30 * wh3 + v40 * wh4 + v50 * wh5) * wv0
					+
					(v01 * wh0 + v11 * wh1 + v21 * wh2 + v31 * wh3 + v41 * wh4 + v51 * wh5) * wv1
					+
					(v02 * wh0 + v12 * wh1 + v22 * wh2 + v32 * wh3 + v42 * wh4 + v52 * wh5) * wv2
					+
					(v03 * wh0 + v13 * wh1 + v23 * wh2 + v33 * wh3 + v43 * wh4 + v53 * wh5) * wv3
					+
					(v04 * wh0 + v14 * wh1 + v24 * wh2 + v34 * wh3 + v44 * wh4 + v54 * wh5) * wv4
					+
					(v05 * wh0 + v15 * wh1 + v25 * wh2 + v35 * wh3 + v45 * wh4 + v55 * wh5) * wv5;

				destination.set0(channel, value);
			}
		}

		private static double weight(double d)
		{
			if (d == 0) return 1;
			else if (d > 0 && d < 1) return ((13.0 / 11.0 * d - 453.0 / 209.0) * d - 3.0 / 209.0) * d + 1.0;
			else if (d > 1 && d < 2) return ((-6.0 / 11.0 * (d - 1) + 270.0 / 209.0) * (d - 1) - 156.0 / 209.0) * (d - 1);
			else if (d > 2 && d < 3) return ((1.0 / 11.0 * (d - 2) - 45.0 / 209.0) * (d - 2) + 26.0 / 209.0) * (d - 2);
			else return 0;
		}
	}


	final public static class Sinc64
	{
		/*
			https://en.wikipedia.org/wiki/Lanczos_resampling
		 */
		final private static int A = 4;

		//
		private Sinc64()
		{
		}

		//
		public static
		@NullAllow
		<V extends Float, I extends AF32Image<V, C, CH>, C extends AF32Color<V, CH>, CH extends IF32ColorChannel>
		Float
		interpolate(I image, ACartesian2dTuple tuple, CH channel)
		{
			return interpolate(image, tuple.x, tuple.y, channel);
		}

		public static
		@NullAllow
		<V extends Float, I extends AF32Image<V, C, CH>, C extends AF32Color<V, CH>, CH extends IF32ColorChannel>
		Float
		interpolate(I image, double x, double y, CH channel)
		{
			ImageBorder<V, C, CH> border = image.BORDER;
			int x0 = (int) x;
			int y0 = (int) y;

			if (border.contains(x0, y0))
			{
				int xMin = x0 - A + 1;
				int xMax = x0 + A;
				int yMin = y0 - A + 1;
				int yMax = y0 + A;

				double[] wh = new double[A * 2];
				double[] wv = new double[A * 2];

				for (int i = xMin; i <= xMax; i++) wh[i - xMin] = weight(x - i);

				for (int j = yMin; j <= yMax; j++) wv[j - yMin] = weight(y - j);

				//
				double result = 0;

				for (int j = yMin; j <= yMax; j++)
				{
					double value2 = 0;

					for (int i = xMin; i <= xMax; i++)
					{
						double v = border.get(i, j, channel);
						value2 += v * wh[i - xMin];
					}

					result += value2 * wv[j - yMin];
				}

				return (float) result;
			}
			else
			{
				return null;
			}
		}

		static
		<V extends Float, I extends AF32Image<V, C, CH>, C extends AF32Color<V, CH>, CH extends IF32ColorChannel>
		float
		interpolate0(I image, ACartesian2dTuple tuple, CH channel)
		{
			return interpolate0(image, tuple.x, tuple.y, channel);
		}

		static
		<V extends Float, I extends AF32Image<V, C, CH>, C extends AF32Color<V, CH>, CH extends IF32ColorChannel>
		float
		interpolate0(I image, double x, double y, CH channel)
		{
			int x0 = (int) x;
			int y0 = (int) y;

			int xMin = x0 - A + 1;
			int xMax = x0 + A;
			int yMin = y0 - A + 1;
			int yMax = y0 + A;

			double[] wh = new double[A * 2];
			double[] wv = new double[A * 2];

			for (int i = xMin; i <= xMax; i++) wh[i - xMin] = weight(x - i);

			for (int j = yMin; j <= yMax; j++) wv[j - yMin] = weight(y - j);

			//
			double result = 0;

			for (int j = yMin; j <= yMax; j++)
			{
				double value2 = 0;

				for (int i = xMin; i <= xMax; i++)
				{
					double v = image.get0(i, j, channel);
					value2 += v * wh[i - xMin];
				}

				result += value2 * wv[j - yMin];
			}

			return (float) result;
		}

		public static
		<V extends Float, I extends AF32Image<V, C, CH>, C extends AF32Color<V, CH>, CH extends IF32ColorChannel>
		boolean
		interpolate(I image, ACartesian2dTuple tuple, C destination, CH[] channels)
		{
			return interpolate(image, tuple.x, tuple.y, destination, channels);
		}

		public static
		<V extends Float, I extends AF32Image<V, C, CH>, C extends AF32Color<V, CH>, CH extends IF32ColorChannel>
		boolean
		interpolate(I image, double x, double y, C destination, CH[] channels)
		{
			ImageBorder<V, C, CH> border = image.BORDER;
			int x0 = (int) x;
			int y0 = (int) y;

			if (border.contains(x0, y0))
			{
				int xMin = x0 - A + 1;
				int xMax = x0 + A;
				int yMin = y0 - A + 1;
				int yMax = y0 + A;

				double[] wh = new double[A * 2];
				double[] wv = new double[A * 2];

				for (int i = xMin; i <= xMax; i++) wh[i - xMin] = weight(x - i);

				for (int j = yMin; j <= yMax; j++) wv[j - yMin] = weight(y - j);

				for (CH channel : channels)
				{
					double value = 0;

					for (int j = yMin; j <= yMax; j++)
					{
						double value2 = 0;

						for (int i = xMin; i <= xMax; i++)
						{
							double v = border.get(i, j, channel);
							value2 += v * wh[i - xMin];
						}

						value += value2 * wv[j - yMin];
					}

					destination.set0(channel, value);
				}

				return true;
			}
			else
			{
				return false;
			}
		}

		static
		<V extends Float, I extends AF32Image<V, C, CH>, C extends AF32Color<V, CH>, CH extends IF32ColorChannel>
		void
		interpolate0(I image, ACartesian2dTuple tuple, C destination, CH[] channels)
		{
			interpolate0(image, tuple.x, tuple.y, destination, channels);
		}

		static
		<V extends Float, I extends AF32Image<V, C, CH>, C extends AF32Color<V, CH>, CH extends IF32ColorChannel>
		void
		interpolate0(I image, double x, double y, C destination, CH[] channels)
		{
			int x0 = (int) x;
			int y0 = (int) y;
			int xMin = x0 - A + 1;
			int xMax = x0 + A;
			int yMin = y0 - A + 1;
			int yMax = y0 + A;

			double[] wh = new double[A * 2];
			double[] wv = new double[A * 2];

			for (int i = xMin; i <= xMax; i++) wh[i - xMin] = weight(x - i);

			for (int j = yMin; j <= yMax; j++) wv[j - yMin] = weight(y - j);

			for (CH channel : channels)
			{
				double value = 0;

				for (int j = yMin; j <= yMax; j++)
				{
					double value2 = 0;

					for (int i = xMin; i <= xMax; i++)
					{
						double v = image.get0(i, j, channel);
						value2 += v * wh[i - xMin];
					}

					value += value2 * wv[j - yMin];
				}

				destination.set0(channel, value);
			}
		}

		private static double weight(double d)
		{
			if (d == 0)
			{
				return 1;
			}
			else
			{
				double d2 = d * Math.PI;

				return A * MathUtil.sin(d2) * MathUtil.sin(d2 / A) / (d2 * d2);
			}
		}
	}

}
