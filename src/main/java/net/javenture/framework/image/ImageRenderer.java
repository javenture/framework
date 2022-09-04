package net.javenture.framework.image;


import net.javenture.framework.math.MathUtil;
import net.javenture.framework.util.Chain;
import net.javenture.framework.util.IntUtil;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


/*
	2020/05/20
 */
final public class ImageRenderer
{
	//
	final private Chain<IEntry> ENTRIES;


	//
	public ImageRenderer()
	{
		ENTRIES = new Chain<>();
	}


	//
	public void add(IEntry entry)
	{
		ENTRIES.add(entry);
	}


	public void render(BufferedImage image)
	{
		Graphics2D graphics = image.createGraphics();

		for (IEntry entry : ENTRIES) entry.render(graphics);
	}


	public void clear()
	{
		ENTRIES.clear();
	}


	//
	private interface IEntry
	{
		void render(Graphics2D graphics);
	}


	final public static class Point
		implements IEntry
	{
		//
		final private int X;
		final private int Y;
		final private int RADIUS;
		final private Color COLOR;

		//
		public Point(double x, double y, RgbI8Color color)
		{
			this(x, y, 0, color.r(), color.g(), color.b());
		}

		public Point(double x, double y, int r, int g, int b)
		{
			this(x, y, 0, r, g, b);
		}

		public Point(double x, double y, int radius, RgbI8Color color)
		{
			this(x, y, radius, color.r(), color.g(), color.b());
		}

		public Point(double x, double y, int radius, int r, int g, int b)
		{
			X = IntUtil.round(x);
			Y = IntUtil.round(y);
			RADIUS = radius;
			COLOR = new Color(r, g, b);
		}

		//
		@Override
		public void render(Graphics2D graphics)
		{
			graphics.setColor(COLOR);

			if (RADIUS == 0)
			{
				graphics.drawLine(X, Y, X, Y);
			}
			else
			{
				int r = RADIUS * 2 + 1;
				graphics.fillOval(X - RADIUS, Y - RADIUS, r, r);
			}
		}
	}


	final public static class Circle
		implements IEntry
	{
		//
		final private int X;
		final private int Y;
		final private int RADIUS;
		final private int STROKE;
		final private Color COLOR;

		//
		public Circle(double x, double y, double radius, RgbI8Color color)
		{
			this(x, y, radius, 1, color.r(), color.g(), color.b());
		}

		public Circle(double x, double y, double radius, int r, int g, int b)
		{
			this(x, y, radius, 1, r, g, b);
		}

		public Circle(double x, double y, double radius, int stroke, RgbI8Color color)
		{
			this(x, y, radius, stroke, color.r(), color.g(), color.b());
		}

		public Circle(double x, double y, double radius, int stroke, int r, int g, int b)
		{
			X = IntUtil.round(x);
			Y = IntUtil.round(y);
			RADIUS = IntUtil.round(radius);
			STROKE = stroke;
			COLOR = new Color(r, g, b);
		}

		//
		@Override
		public void render(Graphics2D graphics)
		{
			if (STROKE != 1) graphics.setStroke(new BasicStroke(STROKE));

			graphics.setColor(COLOR);
			int r = RADIUS * 2 + 1;
			graphics.drawOval(X - RADIUS, Y - RADIUS, r, r);
		}
	}


	final public static class Line
		implements IEntry
	{
		//
		final private int X0;
		final private int Y0;
		final private int X1;
		final private int Y1;
		final private int STROKE;
		final private Color COLOR;

		//
		public Line(double x0, double y0, double x1, double y1, RgbI8Color color)
		{
			this(x0, y0, x1, y1, 1, color.r(), color.g(), color.b());
		}

		public Line(double x0, double y0, double x1, double y1, int r, int g, int b)
		{
			this(x0, y0, x1, y1, 1, r, g, b);
		}

		public Line(double x0, double y0, double x1, double y1, int stroke, RgbI8Color color)
		{
			this(x0, y0, x1, y1, stroke, color.r(), color.g(), color.b());
		}

		public Line(double x0, double y0, double x1, double y1, int stroke, int r, int g, int b)
		{
			X0 = IntUtil.round(x0);
			Y0 = IntUtil.round(y0);
			X1 = IntUtil.round(x1);
			Y1 = IntUtil.round(y1);
			STROKE = stroke;
			COLOR = new Color(r, g, b);
		}

		//
		@Override
		public void render(Graphics2D graphics)
		{
			if (STROKE != 1) graphics.setStroke(new BasicStroke(STROKE));

			graphics.setColor(COLOR);
			graphics.drawLine(X0, Y0, X1, Y1);
		}
	}


	final public static class Direction
		implements IEntry
	{
		//
		final private int X;
		final private int Y;
		final private int RADIUS;
		final private double ANGLE;
		final private int STROKE;
		final private Color COLOR;

		//
		public Direction(double x, double y, int radius, double angle, RgbI8Color color)
		{
			this(x, y, radius, angle, 1, color.r(), color.g(), color.b());
		}

		public Direction(double x, double y, int radius, double angle, int r, int g, int b)
		{
			this(x, y, radius, angle, 1, r, g, b);
		}

		public Direction(double x, double y, int radius, double angle, int stroke, RgbI8Color color)
		{
			this(x, y, radius, angle, stroke, color.r(), color.g(), color.b());
		}

		public Direction(double x, double y, int radius, double angle, int stroke, int r, int g, int b)
		{
			X = IntUtil.round(x);
			Y = IntUtil.round(y);
			RADIUS = radius;
			ANGLE = angle;
			STROKE = stroke;
			COLOR = new Color(r, g, b);
		}

		//
		@Override
		public void render(Graphics2D graphics)
		{
			if (STROKE != 1) graphics.setStroke(new BasicStroke(STROKE));

			graphics.setColor(COLOR);
			int dx = (int) (MathUtil.cos(ANGLE) * RADIUS);
			int dy = (int) (MathUtil.sin(ANGLE) * RADIUS);
			graphics.drawLine(X, Y, X + dx, Y + dy);
		}
	}


	final public static class Rectangle
		implements IEntry
	{
		//
		final private int X0;
		final private int Y0;
		final private int X1;
		final private int Y1;
		final private int STROKE;
		final private Color COLOR;

		//
		public Rectangle(double x0, double y0, double x1, double y1, RgbI8Color color)
		{
			this(x0, y0, x1, y1, 1, color.r(), color.g(), color.b());
		}

		public Rectangle(double x0, double y0, double x1, double y1, int r, int g, int b)
		{
			this(x0, y0, x1, y1, 1, r, g, b);
		}

		public Rectangle(double x0, double y0, double x1, double y1, int stroke, RgbI8Color color)
		{
			this(x0, y0, x1, y1, stroke, color.r(), color.g(), color.b());
		}

		public Rectangle(double x0, double y0, double x1, double y1, int stroke, int r, int g, int b)
		{
			X0 = IntUtil.round(x0);
			Y0 = IntUtil.round(y0);
			X1 = IntUtil.round(x1);
			Y1 = IntUtil.round(y1);
			STROKE = stroke;
			COLOR = new Color(r, g, b);
		}

		//
		@Override
		public void render(Graphics2D graphics)
		{
			if (STROKE != 1) graphics.setStroke(new BasicStroke(STROKE));

			graphics.setColor(COLOR);
			graphics.drawLine(X0, Y0, X0, Y1);
			graphics.drawLine(X0, Y0, X1, Y0);
			graphics.drawLine(X1, Y0, X1, Y1);
			graphics.drawLine(X0, Y1, X1, Y1);
		}
	}

}
