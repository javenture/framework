package net.javenture.framework.netty;


import net.javenture.framework.annotation.Immutable;
import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.exception.NotImplementedException;
import net.javenture.framework.factory.IByteFactory;
import net.javenture.framework.factory.Factories;
import net.javenture.framework.factory.IFactoryObject;
import net.javenture.framework.model.AModel;
import net.javenture.framework.script.StringScriptValue;
import net.javenture.framework.script.ScriptArray;
import net.javenture.framework.script.ScriptObject;
import net.javenture.framework.script.ScriptProperty;
import net.javenture.framework.utf8.IUtf8StreamableEntry;
import net.javenture.framework.utf8.Utf8Byte;
import net.javenture.framework.utf8.Utf8OutputStream;
import net.javenture.framework.utf8.Utf8UrlDecoder;
import net.javenture.framework.utf8.Utf8UrlEncoder;
import net.javenture.framework.util.IParser;
import net.javenture.framework.util.IntUtil;
import net.javenture.framework.util.ObjectArrayUtil;
import net.javenture.framework.util.StringArrayUtil;
import net.javenture.framework.util.StringFragment;
import net.javenture.framework.util.StringFragmenter;
import net.javenture.framework.util.StringSplitter;
import net.javenture.framework.util.TextUtil;
import net.javenture.framework.util.Validator;

import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpRequest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/*
	2020/10/07
 */
@Immutable
final public class NettyUrl
	implements IFactoryObject<NettyUrl>, IUtf8StreamableEntry                                  // XXX: ?
{
	/*
		https://jsperf.com/url-parsing
		http://www.perlmonks.org/?node_id=111272
		http://blog.stevenlevithan.com/archives/parseuri

		/^(?:([^:\/#?]+:)?(?:\/\/(?:(?:[^:@\/#?]+)(?::(?:[^:@\/#?]+))?@)?([^:\/#?\]\[]+|\[[^\/\]@#?]+])(?::([0-9]+))?)?)?(\/?(?:[^\/?#]+\/+)*)?([^?#]*)?(?:\?([^#]+))?(?:#(.*))?/

	 */

	//
	final private static Pattern PATTERN = Pattern.compile
	(
		"^" +
		"(?:" +
			"(?:" +
				"(" + // scheme (#1)
					"[^:/#?]+" +
				")" +
				"://" +
			")?" +
			"(?:" +
				"(" + // host (#2)
					"[^:/#?\\]\\[]+|\\[[^/\\]@#?]+]" +
				")?" +
				"(?:" +
					":" +
					"(" + // port (#3)
						"[\\d]+" +
					")" +
				")?" +
			")?" +
		")?" +
		"(" + // path (#4)
			"/" +
			"(?:" +
				"[^/?#]+/+" +
			")*" +
		")?" +
		"(" + // file (#5)
			"[^/?#][^/?]*?" +
		")?" +
		"(?:" +
			"\\?" +
			"(" + // query (#6)
				"[^#]*" +
			")" +
		")?" +
		"(?:" +
			"#" +
			"(" + // fragment (#7)
				".*" +
			")" +
		")?" +
		"$"
	);

	final public static IByteFactory<NettyUrl> FACTORY_BYTE = new IByteFactory<>()
	{
		@Override
		public void toStream(NettyUrl object, OutputStream destination)
			throws Exception
		{
			// XXX
			throw new UnsupportedOperationException();


			// if (setNullHeader(object, destination)) StringUtil.FACTORY_BYTE.toStream(object.STRING, destination);
		}

		@Override
		public NettyUrl fromStream(InputStream source)
			throws Exception
		{
			// XXX
			throw new UnsupportedOperationException();


			//return getNullHeader(source) ? new NettyUri(StringUtil.FACTORY_BYTE.fromStream(source)) : null;
		}
	};

	final public static Factories<NettyUrl> FACTORIES = new Factories<>(FACTORY_BYTE);


	//
	final @NullDisallow Scheme SCHEME;
	final @NullDisallow Host HOST;
	final @NullDisallow Port PORT;
	final @NullDisallow Path PATH;
	final @NullDisallow File FILE;
	final @NullDisallow Query QUERY;
	final @NullDisallow Fragment FRAGMENT;


	//
	public NettyUrl(@NullDisallow HttpRequest request)
	{
		this
		(
			request.headers().get(HttpHeaderNames.HOST) +
			request.uri()
		);
	}


	public NettyUrl(@NullDisallow String url)
	{
		Validator.notNull(url, "[url]");
		Matcher matcher = PATTERN.matcher(url);

		if (matcher.matches())
		{
			SCHEME = Scheme.parse(matcher.group(RegexGroup.SCHEME));
			HOST = Host.parse(matcher.group(RegexGroup.HOST));
			PORT = Port.parse(matcher.group(RegexGroup.PORT));
			PATH = Path.parse(matcher.group(RegexGroup.PATH));
			FILE = File.parse(matcher.group(RegexGroup.FILE));
			QUERY = Query.parse(matcher.group(RegexGroup.QUERY));
			FRAGMENT = Fragment.parse(matcher.group(RegexGroup.FRAGMENT));
		}
		else
		{
			SCHEME = Scheme.DEFAULT;
			HOST = Host.DEFAULT;
			PORT = Port.DEFAULT;
			PATH = Path.DEFAULT;
			FILE = File.DEFAULT;
			QUERY = Query.DEFAULT;
			FRAGMENT = Fragment.DEFAULT;
		}
	}


	private NettyUrl
	(
		@NullDisallow Scheme scheme,
		@NullDisallow Host host,
		@NullDisallow Port port,
		@NullDisallow Path path,
		@NullDisallow File file,
		@NullDisallow Query query,
		@NullDisallow Fragment fragment
	)
	{
		SCHEME = scheme;
		HOST = host;
		PORT = port;
		PATH = path;
		FILE = file;
		QUERY = query;
		FRAGMENT = fragment;
	}


	//
	@Override
	public void toUtf8Stream(Utf8OutputStream destination)
		throws IOException                                                                                 // XXX: ?
	{
		if (SCHEME.exists())
		{
			destination.write(SCHEME.VALUE);
			destination.write(Utf8Byte.COLON, Utf8Byte.SLASH, Utf8Byte.SLASH);
		}

		if (HOST.exists()) destination.write(HOST.VALUE);

		if (PORT.exists())
		{
			destination.write(Utf8Byte.COLON);
			destination.write("" + PORT.VALUE); // !
		}

		if (PATH.exists())
		{
			destination.write(Utf8Byte.SLASH);

			for (String s : PATH)
			{
				destination.write(s);
				destination.write(Utf8Byte.SLASH);
			}
		}

		if (FILE.exists()) destination.write(FILE.VALUE);

		if (QUERY.exists())
		{
			destination.write(Utf8Byte.QUESTION);
			int last = QUERY.size() - 1;
			int i = 0;

			for (NettyParameters.Entry entry : QUERY)
			{
				destination.write(Utf8UrlEncoder.encode(entry.NAME));

				if (!entry.VALUE.isEmpty())
				{
					destination.write(Utf8Byte.EQUAL);
					destination.write(Utf8UrlEncoder.encode(entry.VALUE));
				}

				if (i++ < last) destination.write(Utf8Byte.SEMICOLON);
			}
		}

		if (FRAGMENT.exists())
		{
			destination.write(Utf8Byte.HASHTAG);
			destination.write(FRAGMENT.VALUE);
		}
	}


	@Override
	public Factories<NettyUrl> factories()
	{
		return FACTORIES;
	}


	@Override
	public int hashCode()
	{
		throw new NotImplementedException();
	}


	@Override
	@SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
	public boolean equals(Object object)
	{
		return equals((NettyUrl) object);
	}


	@Override
	public boolean equals(NettyUrl object)
	{
		if (object == this) return true;

		if (object == null) return false;

		return
			this.FRAGMENT.equals(object.FRAGMENT)
			&&
			//this.QUERY.equals(object.QUERY)                                                                                                       // XXX
			//&&
			this.FILE.equals(object.FILE)
			&&
			this.PATH.equals(object.PATH)
			&&
			this.PORT.equals(object.PORT)
			&&
			this.HOST.equals(object.HOST)
			&&
			this.SCHEME.equals(object.SCHEME);
	}


	@Override
	public String toString()
	{
		return toString(SCHEME, HOST, PORT, PATH, FILE, QUERY, FRAGMENT);
	}


	public String toString(boolean local)
	{
		return local
			? toString(Scheme.DEFAULT, Host.DEFAULT, Port.DEFAULT, PATH, FILE, QUERY, FRAGMENT)
			: toString(SCHEME, HOST, PORT, PATH, FILE, QUERY, FRAGMENT);
	}


	public @NullDisallow Scheme scheme()
	{
		return SCHEME;
	}


	public @NullDisallow Host host()
	{
		return HOST;
	}


	public @NullDisallow Port port()
	{
		return PORT;
	}


	public @NullDisallow Path path()
	{
		return PATH;
	}


	public @NullDisallow File file()
	{
		return FILE;
	}


	public @NullDisallow Query query()
	{
		return QUERY;
	}


	public @NullDisallow Fragment fragment()
	{
		return FRAGMENT;
	}


	public boolean exists()
	{
		return
			SCHEME.exists()
			||
			HOST.exists()
			||
			PORT.exists()
			||
			PATH.exists()
			||
			FILE.exists()
			||
			QUERY.exists()
			||
			FRAGMENT.exists();
	}


	public NettyUrl local()
	{
		return new NettyUrl(Scheme.DEFAULT, Host.DEFAULT, Port.DEFAULT, PATH, FILE, QUERY, FRAGMENT);
	}


	public ScriptObject toScriptObject(String name)
	{
		ScriptObject result = new ScriptObject(name);

		if (SCHEME.exists()) result.add(new ScriptProperty(VueName.SCHEME, SCHEME.VALUE, true));

		if (HOST.exists()) result.add(new ScriptProperty(VueName.HOST, HOST.VALUE, true));

		if (PORT.exists()) result.add(new ScriptProperty(VueName.PORT, PORT.VALUE, true));

		if (PATH.exists())
		{
			ScriptArray array = new ScriptArray(VueName.PATH);
			result.add(array);

			for (String s : PATH) array.add(new StringScriptValue(s, true));
		}

		if (FILE.exists()) result.add(new ScriptProperty(VueName.FILE, FILE.VALUE, true));

		if (QUERY.exists())
		{
			ScriptArray array = new ScriptArray(VueName.QUERY);
			result.add(array);

			for (NettyParameters.Entry entry : QUERY)
			{
				array.add
				(
					new ScriptObject()
						.add(new ScriptProperty(VueName.QUERY_NAME, entry.NAME, true))
						.add(new ScriptProperty(VueName.QUERY_VALUE, entry.VALUE, true))
				);
			}
		}

		if (FRAGMENT.exists()) result.add(new ScriptProperty(VueName.FRAGMENT, FRAGMENT.VALUE, true));

		return result;
	}


	public java.io.File toFile(String parent)
	{
		StringFragmenter fragmenter = new StringFragmenter(); // !

		if (PATH.exists())
		{
			fragmenter.add(java.io.File.separatorChar);

			for (String s : PATH)
			{
				fragmenter.add(s);
				fragmenter.add(java.io.File.separatorChar);
			}
		}

		if (FILE.exists()) fragmenter.add(FILE);

		//
		String child = fragmenter.toString();

		return new java.io.File(parent, child);
	}


	//
	static String toString
	(
		@NullDisallow Scheme scheme,
		@NullDisallow Host host,
		@NullDisallow Port port,
		@NullDisallow Path path,
		@NullDisallow File file,
		@NullDisallow Query query,
		@NullDisallow Fragment fragment
	)
	{
		StringFragmenter fragmenter = new StringFragmenter(RegexGroup.COUNT);
		fragmenter.add(scheme, host, port, path, file, query, fragment);

		return fragmenter.toString();
	}


	//
	final public static class Scheme
	{
		//
		final private static String SUFFIX = "://";

		//
		final private @NullDisallow String VALUE; // ! private

		//
		private Scheme()
		{
			VALUE = "";
		}

		public Scheme(@NullDisallow String value)
		{
			Validator.notNull(value, "[value]");
			VALUE = value;
		}

		//
		@Override
		@SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
		public boolean equals(@NullAllow Object object)
		{
			return equals((Scheme) object);
		}

		public boolean equals(@NullAllow Scheme object)
		{
			return
				this == object
				||
				object != null
				&&
				this.VALUE.equals(object.VALUE);
		}

		@Override
		public int hashCode()
		{
			return VALUE.hashCode();
		}

		@Override
		public String toString()
		{
			return !VALUE.isEmpty() ? VALUE + SUFFIX : "";
		}

		public boolean exists()
		{
			return !VALUE.isEmpty();
		}

		public String value()
		{
			return VALUE;
		}

		//
		private static Scheme parse(@NullAllow String s)
		{
			return s != null ? new Scheme(s) : Scheme.DEFAULT;
		}

		//
		final public static Scheme DEFAULT = new Scheme();
		final public static Scheme HTTP = new Scheme("http");
		final public static Scheme HTTPS = new Scheme("https");
	}


	final public static class Host
	{
		//
		final private static char DELIMITER = '.';

		//
		final private @NullDisallow String VALUE; // ! private
		final private @NullDisallow String[] ARRAY;

		//
		private Host()
		{
			VALUE = "";
			ARRAY = StringArrayUtil.BLANK;
		}

		public Host(@NullDisallow String value)
		{
			Validator.notNull(value, "[value]");

			if (!value.isEmpty())
			{
				StringSplitter splitter = new StringSplitter(value, DELIMITER);
				int size = splitter.size();
				String[] array = new String[size];
				int i = size - 1;

				for (CharSequence sequence : splitter) array[i--] = sequence.toString();

				//
				VALUE = value;
				ARRAY = array;
			}
			else
			{
				VALUE = "";
				ARRAY = StringArrayUtil.BLANK;
			}
		}

		//
		@Override
		@SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
		public boolean equals(@NullAllow Object object)
		{
			return equals((Host) object);
		}

		public boolean equals(@NullAllow Host object)
		{
			return
				this == object
				||
				object != null
				&&
				this.VALUE.equals(object.VALUE);
		}

		@Override
		public int hashCode()
		{
			return VALUE.hashCode();
		}

		@Override
		public String toString()
		{
			return VALUE;
		}

		public boolean exists()
		{
			return !VALUE.isEmpty();
		}

		public String value()
		{
			return VALUE;
		}

		public int size()
		{
			return ARRAY.length;
		}

		public String get(int index)
		{
			return ARRAY[index];
		}

		//
		private static Host parse(@NullAllow String s)
		{
			return s != null ? new Host(s) : Host.DEFAULT;
		}

		//
		final public static Host DEFAULT = new Host();
		final public static Host LOCALHOST = new Host("localhost");
	}


	final public static class Port
	{
		//
		final private static String PREFIX = ":";
		final private static int UNKNOWN = -1;

		//
		final private int VALUE; // ! private

		//
		private Port()
		{
			VALUE = UNKNOWN;
		}

		public Port(int value)
		{
			VALUE = value;
		}

		//
		@Override
		@SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
		public boolean equals(@NullAllow Object object)
		{
			return equals((Port) object);
		}

		public boolean equals(@NullAllow Port object)
		{
			return
				this == object
				||
				object != null
				&&
				this.VALUE == object.VALUE;
		}

		@Override
		public int hashCode()
		{
			return VALUE;
		}

		@Override
		public String toString()
		{
			return VALUE != UNKNOWN ? PREFIX + VALUE : "";
		}

		public boolean exists()
		{
			return VALUE != UNKNOWN;
		}

		public int value()
		{
			return VALUE;
		}

		//
		private static Port parse(@NullAllow String s)
		{
			return s != null ? new Port(IntUtil.parse(s, UNKNOWN)) : Port.DEFAULT;
		}

		//
		final public static Port DEFAULT = new Port();
	}


	final public static class Path
		implements Iterable<String>
	{
		//
		final private static char DELIMITER = '/';

		//
		final private boolean ROOT;
		final private @NullDisallow String[] ARRAY; // ! private

		//
		private Path()
		{
			this(false);
		}

		public Path(boolean root)
		{
			ROOT = root;
			ARRAY = StringArrayUtil.BLANK;
		}

		public Path(String... values)
		{
			this(values, false);
		}

		private Path(String[] values, boolean wrap)
		{
			ROOT = false;
			ARRAY = wrap ? values : values.clone();
		}

		//
		@Override
		@SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
		public boolean equals(@NullAllow Object object)
		{
			return equals((Path) object);
		}

		public boolean equals(@NullAllow Path object)
		{
			return
				this == object
				||
				object != null
				&&
				this.ROOT == object.ROOT
				&&
				StringArrayUtil.equal(this.ARRAY, object.ARRAY);
		}

		@Override
		public int hashCode()
		{
			return Boolean.hashCode(ROOT) + StringArrayUtil.FACTORY_MURMUR3HASH.hash(ARRAY).getInt();                                             // ???
		}

		@Override
		public String toString()
		{
			if (ROOT)
			{
				return "" + DELIMITER;
			}
			else if (ARRAY.length != 0)
			{
				StringFragmenter fragmenter = new StringFragmenter(); // ! new StringFragmenter()
				fragmenter.add(DELIMITER);

				for (String s : ARRAY)
				{
					fragmenter.add(s);
					fragmenter.add(DELIMITER);
				}

				return fragmenter.toString();
			}
			else
			{
				return "";
			}
		}

		@Override
		public Iterator<String> iterator()
		{
			return ObjectArrayUtil.iterator(ARRAY);
		}

		public boolean exists()
		{
			return ROOT || ARRAY.length != 0;
		}

		public boolean root()
		{
			return ROOT;
		}

		public int size()
		{
			return ARRAY.length;
		}

		public String get(int index)
		{
			return ARRAY[index];
		}

		//
		private static Path parse(@NullAllow String s)
		{
			if (s != null)
			{
				int length = s.length();

				if (length == 1) // "/"
				{
					return new Path(true);
				}
				else
				{
					StringFragment fragment = new StringFragment(s, 1, length - 1);
					StringSplitter splitter = new StringSplitter(fragment, DELIMITER);
					String[] array = splitter.array();

					return new Path(array, true);
				}
			}
			else
			{
				return Path.DEFAULT;
			}
		}

		//
		final public static Path DEFAULT = new Path();
	}


	final public static class File
	{
		//
		final private @NullDisallow String VALUE; // ! private
		final private @NullDisallow String NAME;
		final private @NullDisallow String EXTENSION;

		//
		private File()
		{
			VALUE = "";
			NAME = "";
			EXTENSION = "";
		}

		public File(@NullDisallow String value)
		{
			String name;
			String extension;
			int index = value.lastIndexOf('.');

			if (index >= 0)
			{
				name = value.substring(0, index);
				extension = value.substring(index + 1);
			}
			else
			{
				name = value;
				extension = "";
			}

			//
			VALUE = value;
			NAME = name;
			EXTENSION = extension;
		}

		//
		@Override
		@SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
		public boolean equals(@NullAllow Object object)
		{
			return equals((File) object);
		}

		public boolean equals(@NullAllow File object)
		{
			return
				this == object
				||
				object != null
				&&
				this.VALUE.equals(object.VALUE);
		}

		@Override
		public int hashCode()
		{
			return VALUE.hashCode();
		}

		@Override
		public String toString()
		{
			return VALUE;
		}

		public boolean exists()
		{
			return !VALUE.isEmpty();
		}

		public String value()
		{
			return VALUE;
		}

		public String name()
		{
			return NAME;
		}

		public String extension()
		{
			return EXTENSION;
		}

		public Media media()
		{
			return Media.search(EXTENSION);
		}

		//
		private static File parse(@NullAllow String s)
		{
			return s != null ? new File(s) : File.DEFAULT;
		}

		//
		final public static File DEFAULT = new File();
	}


	final public static class Query
		implements Iterable<NettyParameters.Entry>
	{
		//
		final private static String PREFIX = "?";
		final private static char DELIMITER = ';';                                                                    // XXX: support ';' and '&'

		//
		final private @NullDisallow NettyParameters PARAMETERS; // ! private

		//
		private Query()
		{
			PARAMETERS = new NettyParameters();
		}

		public Query(@NullDisallow NettyParameters parameters)
		{
			Validator.notNull(parameters, "[parameters]");
			PARAMETERS = parameters;
		}

		//
		@Override
		@SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
		public boolean equals(@NullAllow Object object)
		{
			return equals((Query) object);
		}

		public boolean equals(@NullAllow Query object)
		{
			throw new UnsupportedOperationException();                                                                                             // XXX
		}

		@Override
		public int hashCode()
		{
			throw new UnsupportedOperationException();                                                                                             // XXX
		}

		@Override
		public String toString()
		{
			if (PARAMETERS.exists())
			{
				StringFragmenter fragmenter = new StringFragmenter(); // ! new StringFragmenter()
				fragmenter.add(PREFIX);
				int last = PARAMETERS.size() - 1;
				int i = 0;

				for (NettyParameters.Entry entry : PARAMETERS)
				{
					fragmenter.add(Utf8UrlEncoder.encode(entry.NAME));

					if (!entry.VALUE.isEmpty())
					{
						fragmenter.add('=');
						fragmenter.add(Utf8UrlEncoder.encode(entry.VALUE));
					}

					if (i++ < last) fragmenter.add(DELIMITER);
				}

				return fragmenter.toString();
			}
			else
			{
				return "";
			}
		}

		@Override
		public Iterator<NettyParameters.Entry> iterator()
		{
			return PARAMETERS.iterator();
		}

		public boolean exists()
		{
			return PARAMETERS.exists();
		}

		public boolean contains(@NullDisallow String name)
		{
			return PARAMETERS.contains(name);
		}

		public boolean contains(@NullDisallow String name, @NullDisallow String value)
		{
			return PARAMETERS.contains(name, value);
		}

		public int size()
		{
			return PARAMETERS.size();
		}

		public @NullAllow String getString(@NullDisallow String name)
		{
			return PARAMETERS.getString(name);
		}

		public @NullAllow String getString(@NullDisallow String name, @NullAllow String init)
		{
			return PARAMETERS.getString(name, init);
		}

		public @NullAllow Boolean getBoolean(@NullDisallow String name)
			throws Exception
		{
			return PARAMETERS.getBoolean(name);
		}

		public boolean getBoolean(@NullDisallow String name, boolean init)
		{
			return PARAMETERS.getBoolean(name, init);
		}

		public @NullAllow Boolean getBoolean(@NullDisallow String name, @NullAllow Boolean init)
		{
			return PARAMETERS.getBoolean(name, init);
		}

		public @NullAllow Short getShort(@NullDisallow String name)
			throws Exception
		{
			return PARAMETERS.getShort(name);
		}

		public short getShort(@NullDisallow String name, short init)
		{
			return PARAMETERS.getShort(name, init);
		}

		public @NullAllow Short getShort(@NullDisallow String name, @NullAllow Short init)
		{
			return PARAMETERS.getShort(name, init);
		}

		public @NullAllow Integer getInt(@NullDisallow String name)
			throws Exception
		{
			return PARAMETERS.getInt(name);
		}

		public int getInt(@NullDisallow String name, int init)
		{
			return PARAMETERS.getInt(name, init);
		}

		public @NullAllow Integer getInt(@NullDisallow String name, @NullAllow Integer init)
		{
			return PARAMETERS.getInt(name, init);
		}

		public @NullAllow Long getLong(@NullDisallow String name)
			throws Exception
		{
			return PARAMETERS.getLong(name);
		}

		public long getLong(@NullDisallow String name, long init)
		{
			return PARAMETERS.getLong(name, init);
		}

		public @NullAllow Long getLong(@NullDisallow String name, @NullAllow Long init)
		{
			return PARAMETERS.getLong(name, init);
		}

		public @NullAllow Float getFloat(@NullDisallow String name)
			throws Exception
		{
			return PARAMETERS.getFloat(name);
		}

		public float getFloat(@NullDisallow String name, float init)
		{
			return PARAMETERS.getFloat(name, init);
		}

		public @NullAllow Float getFloat(@NullDisallow String name, @NullAllow Float init)
		{
			return PARAMETERS.getFloat(name, init);
		}

		public @NullAllow Double getDouble(@NullDisallow String name)
			throws Exception
		{
			return PARAMETERS.getDouble(name);
		}

		public double getDouble(@NullDisallow String name, double init)
		{
			return PARAMETERS.getDouble(name, init);
		}

		public @NullAllow Double getDouble(@NullDisallow String name, @NullAllow Double init)
		{
			return PARAMETERS.getDouble(name, init);
		}

		public @NullAllow byte[] getBytes(@NullDisallow String name)
			throws Exception
		{
			return PARAMETERS.getBytes(name);
		}

		public @NullAllow byte[] getBytes(@NullDisallow String name, @NullAllow byte[] init)
		{
			return PARAMETERS.getBytes(name, init);
		}

		public @NullDisallow <V> IParser.Report<V> parse(@NullDisallow String name, @NullDisallow IParser<V> parser)
		{
			return PARAMETERS.parse(name, parser);
		}

		public @NullDisallow <V> IParser.Report<V> parse(@NullDisallow AModel<V> model)
		{
			return PARAMETERS.parse(model);
		}

		//
		private static Query parse(@NullAllow String s)
		{
			if (s != null)
			{
				NettyParameters parameters = new NettyParameters();
				int from = 0;
				int to = s.length();

				while (from < to)
				{
					int index = TextUtil.index(s, DELIMITER, from, to);

					if (index == -1) index = to;

					if (index - from != 0)
					{
						int relation = TextUtil.index(s, '=', from, index);

						if (relation != from)
						{
							String name;
							String value;

							if (relation == -1)
							{
								name = Utf8UrlDecoder.decode(s, from, index);
								value = "";
							}
							else if (relation == index - 1)
							{
								name = Utf8UrlDecoder.decode(s, from, relation);
								value = "";
							}
							else
							{
								name = Utf8UrlDecoder.decode(s, from, relation);
								value = Utf8UrlDecoder.decode(s, relation + 1, index);
							}

							if (name != null && value != null) parameters.add(name, value);
						}
					}

					from = index + 1;
				}

				return new Query(parameters);
			}
			else
			{
				return Query.DEFAULT;
			}
		}

		//
		final public static Query DEFAULT = new Query();
	}


	final public static class Fragment
	{
		//
		final private static String PREFIX = "#";

		//
		final private @NullDisallow String VALUE; // ! private

		//
		private Fragment()
		{
			VALUE = "";
		}

		public Fragment(@NullDisallow String value)
		{
			Validator.notNull(value, "[value]");
			VALUE = value;
		}

		//
		@Override
		@SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
		public boolean equals(@NullAllow Object object)
		{
			return equals((Fragment) object);
		}

		public boolean equals(@NullAllow Fragment object)
		{
			return
				this == object
				||
				object != null
				&&
				this.VALUE.equals(object.VALUE);
		}

		@Override
		public int hashCode()
		{
			return VALUE.hashCode();
		}

		@Override
		public String toString()
		{
			return !VALUE.isEmpty() ? PREFIX + VALUE : "";
		}

		public boolean exists()
		{
			return !VALUE.isEmpty();
		}

		public String value()
		{
			return VALUE;
		}

		//
		private static Fragment parse(@NullAllow String s)
		{
			return s != null ? new Fragment(s) : Fragment.DEFAULT;
		}

		//
		final public static Fragment DEFAULT = new Fragment();
	}


	final public static class Builder
	{
		//
		private NettyUrl url;

		//
		public Builder()
		{
		}

		public Builder(NettyUrl value)
		{
			url = value;
		}

		//
		public NettyUrl url()
		{
			return url;
		}

		public Builder scheme(@NullDisallow Scheme value)
		{
			Validator.notNull(value, "[value]");
			url = new NettyUrl(value, url.HOST, url.PORT, url.PATH, url.FILE, url.QUERY, url.FRAGMENT);

			return this;
		}

		public Builder host(@NullDisallow Host value)
		{
			Validator.notNull(value, "[value]");
			url = new NettyUrl(url.SCHEME, value, url.PORT, url.PATH, url.FILE, url.QUERY, url.FRAGMENT);

			return this;
		}

		public Builder port(@NullDisallow Port value)
		{
			Validator.notNull(value, "[value]");
			url = new NettyUrl(url.SCHEME, url.HOST, value, url.PATH, url.FILE, url.QUERY, url.FRAGMENT);

			return this;
		}

		public Builder path(@NullDisallow Path value)
		{
			Validator.notNull(value, "[value]");
			url = new NettyUrl(url.SCHEME, url.HOST, url.PORT, value, url.FILE, url.QUERY, url.FRAGMENT);

			return this;
		}

		public Builder file(@NullDisallow File value)
		{
			Validator.notNull(value, "[value]");
			url = new NettyUrl(url.SCHEME, url.HOST, url.PORT, url.PATH, value, url.QUERY, url.FRAGMENT);

			return this;
		}

		public Builder query(@NullDisallow Query value)
		{
			Validator.notNull(value, "[value]");
			url = new NettyUrl(url.SCHEME, url.HOST, url.PORT, url.PATH, url.FILE, value, url.FRAGMENT);

			return this;
		}

		public Builder fragment(@NullDisallow Fragment value)
		{
			Validator.notNull(value, "[value]");
			url = new NettyUrl(url.SCHEME, url.HOST, url.PORT, url.PATH, url.FILE, url.QUERY, value);

			return this;
		}
	}


	final private static class RegexGroup
	{
		final private static int COUNT = 7;
		final private static int SCHEME = 1;
		final private static int HOST = 2;
		final private static int PORT = 3;
		final private static int PATH = 4;
		final private static int FILE = 5;
		final private static int QUERY = 6;
		final private static int FRAGMENT = 7;
	}


	final private static class VueName
	{
		final private static String SCHEME = "scheme";
		final private static String HOST = "host";
		final private static String PORT = "port";
		final private static String PATH = "path";
		final private static String FILE = "file";
		final private static String QUERY = "query";
		final private static String QUERY_NAME = "name";
		final private static String QUERY_VALUE = "value";
		final private static String FRAGMENT = "fragment";
	}

}
