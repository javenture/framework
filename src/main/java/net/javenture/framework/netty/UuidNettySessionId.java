package net.javenture.framework.netty;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.factory.Factories;
import net.javenture.framework.factory.FactoryObjectUtil;
import net.javenture.framework.factory.IMurmur3HashFactory;
import net.javenture.framework.factory.IStringFactory;
import net.javenture.framework.util.IParser;
import net.javenture.framework.util.StringUtil;
import net.javenture.framework.util.Uuid;
import net.javenture.framework.util.UuidUtil;
import net.javenture.framework.util.Validator;


/*
	2020/09/30
 */
final public class UuidNettySessionId
	implements INettySessionId<UuidNettySessionId>
{
	//
	final public static INettySessions.IGenerator<UuidNettySessionId> GENERATOR = () -> new UuidNettySessionId(Uuid.generate());

	final public static IStringFactory<UuidNettySessionId> FACTORY_STRING = new IStringFactory<>()
	{
		@Override
		public @NullDisallow String toString(@NullAllow UuidNettySessionId object)
		{
			return StringUtil.toString(object);
		}

		@Override
		public @NullDisallow Parser parser(@NullAllow UuidNettySessionId init, boolean nullable, boolean trim)
		{
			return new Parser(init, nullable, trim);
		}
	};

	final public static IMurmur3HashFactory<UuidNettySessionId> FACTORY_MURMUR3HASH = (object, destination) ->
	{
		if (IMurmur3HashFactory.updateNullHeader(object, destination)) UuidUtil.murmur3hash(object.VALUE, destination);
	};

	final public static Factories<UuidNettySessionId> FACTORIES = new Factories<>(FACTORY_STRING, FACTORY_MURMUR3HASH);


	//
	final private Uuid VALUE;


	//
	private UuidNettySessionId(Uuid value)
	{
		VALUE = value;
	}


	//
	@Override
	public String toString()
	{
		return VALUE.toString(true);
	}


	@Override
	public int compareTo(UuidNettySessionId object)
	{
		return VALUE.compareTo(object.VALUE);
	}


	@Override
	public Factories<UuidNettySessionId> factories()
	{
		return FACTORIES;
	}


	@Override
	public int hashCode()
	{
		return VALUE.hashCode();
	}


	@Override
	@SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
	public boolean equals(@NullAllow Object object)
	{
		return equals((UuidNettySessionId) object);
	}


	@Override
	public boolean equals(@NullAllow UuidNettySessionId object)
	{
		return
			this == object
			||
			object != null
			&&
			this.VALUE.equals(object.VALUE);
	}


	//
	final public static class Parser
		implements IParser<UuidNettySessionId>
	{
		//
		final private @NullAllow UuidNettySessionId INIT;
		final private boolean NULLABLE;
		final private boolean TRIM;
		final private Report<UuidNettySessionId> REPORT_INIT;
		final private Report<UuidNettySessionId> REPORT_ERROR;
		final private Report<UuidNettySessionId> REPORT_NULL;
		final private Uuid.Parser PARSER;

		//
		public Parser(@NullAllow UuidNettySessionId init, boolean nullable, boolean trim)
		{
			Validator.argument(nullable || init != null, "[init] null value disallowed");

			INIT = init;
			NULLABLE = nullable;
			TRIM = trim;
			REPORT_INIT = new Report<>(true, false, init);
			REPORT_ERROR = new Report<>(false, false, init);
			REPORT_NULL = new Report<>(true, init != null, null);
			PARSER = new Uuid.Parser(init != null ? init.VALUE : null, nullable, trim);
		}

		//
		@Override
		public @NullAllow UuidNettySessionId init()
		{
			return INIT;
		}

		@Override
		public boolean nullable()
		{
			return NULLABLE;
		}

		@Override
		public boolean trim()
		{
			return TRIM;
		}

		@Override
		public Report<UuidNettySessionId> parse(@NullAllow UuidNettySessionId value)
		{
			if (value != null)
			{
				return !FactoryObjectUtil.equal(value, INIT)
					? new Report<>(true, true, value)
					: REPORT_INIT;
			}
			else if (NULLABLE)
			{
				return REPORT_NULL;
			}

			return REPORT_ERROR;
		}

		@Override
		public Report<UuidNettySessionId> parse(@NullAllow CharSequence source)
		{
			Report<Uuid> report = PARSER.parse(source);

			if (report.EXISTS)
			{
				if (report.DEFINED)
				{
					Uuid uuid = report.VALUE;

					if (uuid != null) return new Report<>(true, true, new UuidNettySessionId(uuid));
					else if (NULLABLE) return REPORT_NULL;
				}
				else
				{
					return REPORT_INIT;
				}
			}

			return REPORT_ERROR;
		}

		//
		final public static Parser UNKNOWN = new Parser(UuidNettySessionId.UNKNOWN, false, false);
	}


	//
	final private static UuidNettySessionId UNKNOWN = new UuidNettySessionId(Uuid.UNKNOWN);

}
