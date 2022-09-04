package net.javenture.framework.quasar.element;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.html.AHtmlContent;
import net.javenture.framework.html.AHtmlElement;
import net.javenture.framework.html.HtmlAttribute;


/*
	2019/07/06
 */
final public class QuasarLayout
	extends AHtmlContent<QuasarLayout>
{
	//
	final public static Config CONFIG = new Config(Type.PAIR, "q-layout");


	//
	public QuasarLayout()
	{
	}


	public QuasarLayout(AHtmlElement parent)
	{
		super(parent);
	}


	//
	@Override
	public Config config()
	{
		return CONFIG;
	}


	public QuasarLayout view(View value)
	{
		return attribute(value);
	}


	public QuasarLayout view(Header header, Middle middle, Footer footer)
	{
		return attribute(Attributes.VIEW, header + " " + middle + " " + footer);
	}


	//
	public enum Header
	{
		hHh,
		hHr,
		hhh,
		hhr,
		lHh,
		lHr,
		lhh,
		lhr
	}


	public enum Middle
	{
		LpR,
		Lpr,
		lpR,
		lpr
	}


	public enum Footer
	{
		fFf,
		fFr,
		fff,
		ffr,
		lFf,
		lFr,
		lff,
		lfr
	}


	final public static class View
		extends HtmlAttribute.Preset
	{
		//
		public View(Header header, Middle middle, Footer footer)
		{
			super(Attributes.VIEW, header + " " + middle + " " + footer);
		}
	}


	final public static class Attributes
	{
		final public static HtmlAttribute.Template VIEW = new HtmlAttribute.Template("view");
	}


	final public static class Structure
	{
		//
		final private @NullDisallow QuasarLayout ROOT;
		final private @NullDisallow QuasarHeader HEADER;
		final private @NullDisallow QuasarDrawer LEFT;
		final private @NullDisallow QuasarDrawer RIGHT;
		final private @NullDisallow QuasarPageContainer CONTAINER;
		final private @NullDisallow QuasarPageContent CONTENT;
		final private @NullDisallow QuasarFooter FOOTER;
		final private @NullDisallow QuasarPageSticky STICKY;

		//
		public Structure()
		{
			this(null);
		}

		public Structure(@NullAllow AHtmlElement parent)
		{
			ROOT = parent != null
				? new QuasarLayout(parent)
				: new QuasarLayout();

			HEADER = new QuasarHeader(ROOT) // ! #0
				.activity(false);

			LEFT = new QuasarDrawer(ROOT) // ! #1
				.activity(false)
				.side(QuasarDrawer.Side.LEFT);

			RIGHT = new QuasarDrawer(ROOT) // ! #2
				.activity(false)
				.side(QuasarDrawer.Side.RIGHT);

			CONTAINER = new QuasarPageContainer(ROOT); // ! #3
			CONTENT = new QuasarPageContent(CONTAINER); // ! #4

			FOOTER = new QuasarFooter(ROOT) // ! #5
				.activity(false);

			STICKY = new QuasarPageSticky(ROOT) // ! #6
				.activity(false);
		}

		//
		public Structure view(View value)
		{
			ROOT.view(value);

			return this;
		}

		public Structure view(Header header, Middle middle, Footer footer)
		{
			ROOT.view(header, middle, footer);

			return this;
		}

		public QuasarLayout root()
		{
			return ROOT;
		}

		public QuasarHeader header()
		{
			return HEADER.activity(true);
		}

		public QuasarDrawer left()
		{
			return LEFT.activity(true);
		}

		public QuasarDrawer right()
		{
			return RIGHT.activity(true);
		}

		public QuasarPageContainer container()
		{
			return CONTAINER;
		}

		public QuasarPageContent content()
		{
			return CONTENT;
		}

		public QuasarFooter footer()
		{
			return FOOTER.activity(true);
		}

		public QuasarPageSticky sticky()
		{
			return STICKY.activity(true);
		}
	}

}
