package net.javenture.framework.json;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.annotation.SingleThread;
import net.javenture.framework.exception.NotImplementedException;
import net.javenture.framework.hex.HexUtil;
import net.javenture.framework.utf8.Utf8Util;
import net.javenture.framework.util.CharContainer;
import net.javenture.framework.util.CharUtil;
import net.javenture.framework.util.IParser;
import net.javenture.framework.util.StringUtil;
import net.javenture.framework.model.AModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


/*
	2019/07/31
 */
@SingleThread
final public class JsonDocument
{
	//
	final private int LENGTH;
	final private IDocument DOCUMENT;
	final private IFragment FRAGMENT;
	final private Cache CACHE;
	final private JsonObject ROOT;

	private int position;
	private int row;                                                  // XXX: ?
	private int column;                                               // XXX: ?


	//
	public JsonDocument(@NullDisallow byte[] value)
		throws Exception
	{
		this(Utf8Util.parse(value));
	}


	public JsonDocument(@NullDisallow char[] array)
		throws Exception
	{
		this
		(
			array.length,
			index -> array[index],
			(from, to) -> new String(array, from, to - from)
		);
	}


	public JsonDocument(@NullDisallow String value)
		throws Exception
	{
		this(value.length(), value::charAt, value::substring);
	}


	private JsonDocument(int length, IDocument document, IFragment fragment)
		throws Exception
	{
		LENGTH = length;
		DOCUMENT = document;
		FRAGMENT = fragment;
		CACHE = new Cache();
		position = 0;
		row = 0;
		column = 0;

		//
		ArrayList<Token> tokens = new ArrayList<>();

		while (true)
		{
			parseWhitespace();

			if (available())
			{
				// left brace
				{
					Token token = parseLeftBrace();

					if (token != null)
					{
						tokens.add(token);

						continue;
					}
				}

				// right brace
				{
					Token token = parseRightBrace();

					if (token != null)
					{
						tokens.add(token);

						continue;
					}
				}

				// left bracket
				{
					Token token = parseLeftBracket();

					if (token != null)
					{
						tokens.add(token);

						continue;
					}
				}

				// right bracket
				{
					Token token = parseRightBracket();

					if (token != null)
					{
						tokens.add(token);

						continue;
					}
				}

				// colon
				{
					Token token = parseColon();

					if (token != null)
					{
						tokens.add(token);

						continue;
					}
				}

				// comma
				{
					Token token = parseComma();

					if (token != null)
					{
						tokens.add(token);

						continue;
					}
				}

				// null
				{
					Token token = parseNull();

					if (token != null)
					{
						tokens.add(token);

						continue;
					}
				}

				// true
				{
					Token token = parseTrue();

					if (token != null)
					{
						tokens.add(token);

						continue;
					}
				}

				// false
				{
					Token token = parseFalse();

					if (token != null)
					{
						tokens.add(token);

						continue;
					}
				}

				// number
				{
					Token token = parseNumber();

					if (token != null)
					{
						tokens.add(token);

						continue;
					}
				}

				// string
				{
					Token token = parseString();

					if (token != null)
					{
						tokens.add(token);

						continue;
					}
				}

				//
				throw new Exception("not found");                                                      // XXX
			}
			else
			{
				break;
			}
		}

		//
		int size = tokens.size();

		if (size != 0 && tokens.get(0).TYPE == Type.BRACE_LEFT)
		{
			JsonObject root = new JsonObject();
			Stack<ITask> stack = new Stack<>();
			stack.add(new ObjectTask(root));
			int i = 1;

			while (!stack.empty() && i < size)
			{
				Token token = tokens.get(i);
				Type type = token.TYPE;
				ITask task0 = stack.peek();

				if (task0.entity() == Entity.OBJECT)
				{
					ObjectTask task = (ObjectTask) task0;

					switch (task.stage)
					{
						// BRACE_RIGHT | STRING
						case 0:
						{
							if (type == Type.BRACE_RIGHT)
							{
								stack.pop();
							}
							else if (type == Type.STRING)
							{
								task.name = token.SEQUENCE;
								task.stage = 1;
							}
							else
							{
								throw new Exception();
							}

							break;
						}

						// COLON
						case 1:
						{
							if (type == Type.COLON)
							{
								task.stage = 2;
							}
							else
							{
								throw new Exception();
							}

							break;
						}

						// BRACE_LEFT | BRACKET_LEFT | NULL | TRUE | FALSE | NUMBER | STRING
						case 2:
						{
							IJsonEntry entry;

							if (type == Type.BRACE_LEFT)
							{
								JsonObject object = new JsonObject();
								stack.add(new ObjectTask(object));
								entry = object;
							}
							else if (type == Type.BRACKET_LEFT)
							{
								JsonArray array = new JsonArray();
								stack.add(new ArrayTask(array));
								entry = array;
							}
							else if (type == Type.NULL)
							{
								entry = NullJsonValue.INSTANCE;
							}
							else if (type == Type.TRUE)
							{
								entry = BooleanJsonValue.TRUE;
							}
							else if (type == Type.FALSE)
							{
								entry = BooleanJsonValue.FALSE;
							}
							else if (type == Type.NUMBER)
							{
								entry = new NumberJsonValue(token.SEQUENCE);
							}
							else if (type == Type.STRING)
							{
								entry = new StringJsonValue(token.SEQUENCE);
							}
							else
							{
								throw new Exception();
							}

							//
							task.OBJECT.add(task.name, entry);
							task.name = null;
							task.stage = 3;

							break;
						}

						// BRACE_RIGHT | COMMA
						case 3:
						{
							if (type == Type.BRACE_RIGHT)
							{
								stack.pop();
							}
							else if (type == Type.COMMA)
							{
								task.stage = 4;
							}
							else
							{
								throw new Exception();
							}

							break;
						}

						// STRING
						case 4:
						{
							if (type == Type.STRING)
							{
								task.name = token.SEQUENCE;
								task.stage = 1;
							}
							else
							{
								throw new Exception();
							}

							break;
						}

						default: throw new RuntimeException();
					}
				}
				else if (task0.entity() == Entity.ARRAY)
				{
					ArrayTask task = (ArrayTask) task0;

					switch (task.stage)
					{
						// BRACKET_RIGHT | BRACE_LEFT | BRACKET_LEFT | NULL | TRUE | FALSE | NUMBER | STRING
						case 0:
						{
							if (type == Type.BRACKET_RIGHT)
							{
								stack.pop();
							}
							else
							{
								IJsonEntry entry;

								if (type == Type.BRACE_LEFT)
								{
									JsonObject object = new JsonObject();
									stack.add(new ObjectTask(object));
									entry = object;
								}
								else if (type == Type.BRACKET_LEFT)
								{
									JsonArray array = new JsonArray();
									stack.add(new ArrayTask(array));
									entry = array;
								}
								else if (type == Type.NULL)
								{
									entry = NullJsonValue.INSTANCE;
								}
								else if (type == Type.TRUE)
								{
									entry = BooleanJsonValue.TRUE;
								}
								else if (type == Type.FALSE)
								{
									entry = BooleanJsonValue.FALSE;
								}
								else if (type == Type.NUMBER)
								{
									entry = new NumberJsonValue(token.SEQUENCE);
								}
								else if (type == Type.STRING)
								{
									entry = new StringJsonValue(token.SEQUENCE);                                      // XXX: HashMap - value cache
								}
								else
								{
									throw new Exception();
								}

								//
								task.ARRAY.add(entry);
								task.stage = 1;
							}

							break;
						}

						// BRACKET_RIGHT | COMMA
						case 1:
						{
							if (type == Type.BRACKET_RIGHT)
							{
								stack.pop();
							}
							else if (type == Type.COMMA)
							{
								task.stage = 2;
							}
							else
							{
								throw new Exception();
							}

							break;
						}

						// BRACE_LEFT | BRACKET_LEFT | NULL | TRUE | FALSE | NUMBER | STRING
						case 2:
						{
							IJsonEntry entry;

							if (type == Type.BRACE_LEFT)
							{
								JsonObject object = new JsonObject();
								stack.add(new ObjectTask(object));
								entry = object;
							}
							else if (type == Type.BRACKET_LEFT)
							{
								JsonArray array = new JsonArray();
								stack.add(new ArrayTask(array));
								entry = array;
							}
							else if (type == Type.NULL)
							{
								entry = NullJsonValue.INSTANCE;
							}
							else if (type == Type.TRUE)
							{
								entry = BooleanJsonValue.TRUE;
							}
							else if (type == Type.FALSE)
							{
								entry = BooleanJsonValue.FALSE;
							}
							else if (type == Type.NUMBER)
							{
								entry = new NumberJsonValue(token.SEQUENCE);
							}
							else if (type == Type.STRING)
							{
								entry = new StringJsonValue(token.SEQUENCE);
							}
							else
							{
								throw new Exception();
							}

							//
							task.ARRAY.add(entry);
							task.stage = 1;

							break;
						}

						default: throw new RuntimeException();
					}
				}

				i++;
			}

			if (stack.empty() && i == size) ROOT = root;
			else throw new Exception();
		}
		else
		{
			throw new Exception();
		}
	}


	//
	public @NullDisallow JsonObject root()
	{
		return ROOT;
	}


	public @NullDisallow <V> IParser.Report<V> get(AModel<V> model)
	{
		return model.parse(this);
	}


	public @NullAllow IJsonEntry get(@NullDisallow String name)
	{
		return ROOT.get(name);
	}


	public @NullAllow IJsonEntry get(List<String> names)
	{
		int size = names.size();

		if (size == 0)
		{
			return ROOT;
		}
		else if (size == 1)
		{
			return ROOT.get(names.get(0));
		}
		else
		{
			int limit = size - 1;
			JsonObject object = get0(names, limit);

			return object != null
				? object.get(names.get(limit))
				: null;
		}
	}


	public @NullAllow JsonObject getJsonObject(@NullDisallow String name)
	{
		return ROOT.getJsonObject(name);
	}


	public @NullAllow JsonObject getJsonObject(List<String> names)
	{
		int size = names.size();

		return size == 0 ? ROOT : get0(names, size);
	}


	public @NullAllow JsonArray getJsonArray(@NullDisallow String name)
	{
		return ROOT.getJsonArray(name);
	}


	public @NullAllow JsonArray getJsonArray(List<String> names)
	{
		int size = names.size();

		if (size == 0)
		{
			return null;
		}
		else if (size == 1)
		{
			return ROOT.getJsonArray(names.get(0));
		}
		else
		{
			int limit = size - 1;
			JsonObject object = get0(names, limit);

			return object != null
				? object.getJsonArray(names.get(limit))
				: null;
		}
	}


	public @NullAllow StringJsonValue getJsonString(@NullDisallow String name)
	{
		return ROOT.getJsonString(name);
	}


	public @NullAllow StringJsonValue getJsonString(List<String> names)
	{
		int size = names.size();

		if (size == 0)
		{
			return null;
		}
		else if (size == 1)
		{
			return ROOT.getJsonString(names.get(0));
		}
		else
		{
			int limit = size - 1;
			JsonObject object = get0(names, limit);

			return object != null
				? object.getJsonString(names.get(limit))
				: null;
		}
	}


	public @NullAllow NumberJsonValue getJsonNumber(@NullDisallow String name)
	{
		return ROOT.getJsonNumber(name);
	}


	public @NullAllow NumberJsonValue getJsonNumber(List<String> names)
	{
		int size = names.size();

		if (size == 0)
		{
			return null;
		}
		else if (size == 1)
		{
			return ROOT.getJsonNumber(names.get(0));
		}
		else
		{
			int limit = size - 1;
			JsonObject object = get0(names, limit);

			return object != null
				? object.getJsonNumber(names.get(limit))
				: null;
		}
	}


	public @NullAllow BooleanJsonValue getJsonBoolean(@NullDisallow String name)
	{
		return ROOT.getJsonBoolean(name);
	}


	public @NullAllow BooleanJsonValue getJsonBoolean(List<String> names)
	{
		int size = names.size();

		if (size == 0)
		{
			return null;
		}
		else if (size == 1)
		{
			return ROOT.getJsonBoolean(names.get(0));
		}
		else
		{
			int limit = size - 1;
			JsonObject object = get0(names, limit);

			return object != null
				? object.getJsonBoolean(names.get(limit))
				: null;
		}
	}


	public boolean isJsonNull(@NullDisallow String name)
	{
		return ROOT.isJsonNull(name);
	}


	public boolean isJsonNull(List<String> names)
	{
		int size = names.size();

		if (size == 0)
		{
			return false;
		}
		else if (size == 1)
		{
			return ROOT.isJsonNull(names.get(0));
		}
		else
		{
			int limit = size - 1;
			JsonObject object = get0(names, limit);

			return object != null && object.isJsonNull(names.get(limit));
		}
	}


	private @NullAllow JsonObject get0(List<String> names, int length)
	{
		JsonObject object0 = CACHE.get(names, length);

		if (object0 != null) return object0;

		//
		JsonObject result = ROOT;

		for (int i = 0; i < length; i++)
		{
			JsonObject object = result.getJsonObject(names.get(i));

			if (object != null) result = object;
			else return null;
		}

		//
		CACHE.set(names, length, result);

		return result;
	}



	private @NullAllow Token parseLeftBrace()
	{
		if (get() == '{')
		{
			forward(1);

			return Token.BRACE_LEFT;
		}

		return null;
	}


	private @NullAllow Token parseRightBrace()
	{
		if (get() == '}')
		{
			forward(1);

			return Token.BRACE_RIGHT;
		}

		return null;
	}


	private @NullAllow Token parseLeftBracket()
	{
		if (get() == '[')
		{
			forward(1);

			return Token.BRACKET_LEFT;
		}

		return null;
	}


	private @NullAllow Token parseRightBracket()
	{
		if (get() == ']')
		{
			forward(1);

			return Token.BRACKET_RIGHT;
		}

		return null;
	}


	private @NullAllow Token parseColon()
	{
		if (get() == ':')
		{
			forward(1);

			return Token.COLON;
		}

		return null;
	}


	private @NullAllow Token parseComma()
	{
		if (get() == ',')
		{
			forward(1);

			return Token.COMMA;
		}

		return null;
	}


	private @NullAllow Token parseNull()
	{
		int position = position();

		if
		(
			exist(position + 3)
			&&
			get(position) == 'n'
			&&
			get(position + 1) == 'u'
			&&
			get(position + 2) == 'l'
			&&
			get(position + 3) == 'l'
		)
		{
			forward(4);

			return Token.NULL;
		}

		return null;
	}


	private @NullAllow Token parseTrue()
	{
		int position = position();

		if
		(
			exist(position + 3)
			&&
			get(position) == 't'
			&&
			get(position + 1) == 'r'
			&&
			get(position + 2) == 'u'
			&&
			get(position + 3) == 'e'
		)
		{
			forward(4);

			return Token.TRUE;
		}

		return null;
	}


	private @NullAllow Token parseFalse()
	{
		int position = position();

		if
		(
			exist(position + 4)
			&&
			get(position) == 'f'
			&&
			get(position + 1) == 'a'
			&&
			get(position + 2) == 'l'
			&&
			get(position + 3) == 's'
			&&
			get(position + 4) == 'e'
		)
		{
			forward(5);

			return Token.FALSE;
		}

		return null;
	}


	private @NullAllow Token parseNumber()
	{
		char c0 = get();

		if
		(
			c0 == '-'
			||
			c0 >= '0' && c0 <= '9'
		)
		{
			int from = position();
			int to = from + 1;

			while (true)
			{
				if (exist(to))
				{
					boolean found = false;
					char c = get(to);

					switch (c)
					{
						case '+':
						case '-':
						case '.':
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
						case 'E':
						case 'e':
						{
							found = true;
						}
					}

					if (found) to++;
					else break;
				}
				else
				{
					break;
				}
			}

			//
			forward(to - from);

			return new Token(Type.NUMBER, new Frame(from, to));                                        // XXX: substring
		}

		return null;
	}


	private @NullAllow Token parseString()
		throws Exception                                                                          // XXX
	{
		if (get() == '\"')
		{
			CharContainer container = new CharContainer();                                                                          // XXX: global object with capacity -> avoid allocation
			int from = position() + 1;
			int to = from;

			while (true)
			{
				if (exist(to))
				{
					char c0 = get(to);

					if (c0 == '\"')
					{
						break;
					}
					else
					{
						if (c0 == '\\')
						{
							if (exist(to + 1))
							{
								char c1 = get(to + 1);

								if (c1 == '"')
								{
									container.add('\"');
									to += 2;
								}
								else if (c1 == '\\')
								{
									container.add('\\');
									to += 2;
								}
								else if (c1 == '/')
								{
									container.add('/');
									to += 2;
								}
								else if (c1 == 'b')
								{
									container.add('\b');
									to += 2;
								}
								else if (c1 == 'f')
								{
									container.add('\f');
									to += 2;
								}
								else if (c1 == 'n')
								{
									container.add('\n');
									to += 2;
								}
								else if (c1 == 'r')
								{
									container.add('\r');
									to += 2;
								}
								else if (c1 == 't')
								{
									container.add('\t');
									to += 2;
								}
								else if (c1 == 'u')
								{
									if (exist(to + 5))
									{
										Byte byte0 = HexUtil.convert(get(to + 2), get(to + 3));
										Byte byte1 = HexUtil.convert(get(to + 4), get(to + 5));

										if (byte0 != null && byte1 != null)
										{
											container.add(CharUtil.parse(byte0, byte1));
											to += 6;
										}
										else
										{
											throw new Exception();
										}
									}
									else
									{
										throw new Exception();
									}
								}
								else
								{
									throw new Exception();
								}
							}
							else
							{
								throw new Exception();
							}
						}
						else if (c0 >= 0x20)
						{
							container.add(c0);
							to++;
						}
						else
						{
							throw new Exception();
						}
					}
				}
				else
				{
					throw new Exception();
				}
			}

			//
			int length = to - from;
			forward(length + 2);

			return length != 0
				? new Token(Type.STRING, container.string())
				: new Token(Type.STRING, "");
		}

		return null;
	}


	private void parseWhitespace()
	{
		while (true)
		{
			int position = position();

			if (exist(position))
			{
				char c = get(position);

				if (c == '\n')
				{
					forward(1);
					row++;
					column = 0;
				}
				else if
				(
					c == '\t'
					||
					c == '\r'
					||
					c == ' '
				)
				{
					forward(1);
					column++;
				}
				else
				{
					break;
				}
			}
			else
			{
				break;
			}
		}
	}


	private boolean available()
	{
		return position < LENGTH;
	}


	private boolean exist(int index)
	{
		return index < LENGTH;
	}


	private int position()
	{
		return position;
	}


	private void forward(int value)
	{
		position += value;
	}


	private char get()
	{
		return DOCUMENT.get(position);
	}


	private char get(int index)
	{
		return DOCUMENT.get(index);
	}


	//
	@FunctionalInterface
	private interface IDocument
	{
		char get(int index);
	}


	@FunctionalInterface
	private interface IFragment
	{
		String get(int from, int to);
	}


	final private class Frame
		implements CharSequence
	{
		//
		final private int FROM;
		final private int TO;
		final private int LENGTH;

		//
		private Frame(int from, int to)
		{
			FROM = from;
			TO = to;
			LENGTH = to - from;
		}

		//
		@Override
		public int length()
		{
			return LENGTH;
		}

		@Override
		public char charAt(int index)
		{
			return DOCUMENT.get(FROM + index);
		}

		@Override
		public CharSequence subSequence(int start, int end)
		{
			throw new NotImplementedException();
		}

		@Override
		public String toString()
		{
			return FRAGMENT.get(FROM, TO);
		}
	}


	private enum Type
	{
		BRACE_LEFT, // {
		BRACE_RIGHT, // }
		BRACKET_LEFT, // [
		BRACKET_RIGHT, // ]
		COLON,
		COMMA,
		NULL,
		TRUE,
		FALSE,
		NUMBER,
		STRING
	}


	final private static class Token
	{
		//
		final private static Token BRACE_LEFT = new Token(Type.BRACE_LEFT);
		final private static Token BRACE_RIGHT = new Token(Type.BRACE_RIGHT);
		final private static Token BRACKET_LEFT = new Token(Type.BRACKET_LEFT);
		final private static Token BRACKET_RIGHT = new Token(Type.BRACKET_RIGHT);
		final private static Token COLON = new Token(Type.COLON);
		final private static Token COMMA = new Token(Type.COMMA);
		final private static Token NULL = new Token(Type.NULL);
		final private static Token TRUE = new Token(Type.TRUE);
		final private static Token FALSE = new Token(Type.FALSE);

		//
		final private @NullDisallow Type TYPE;
		final private @NullAllow CharSequence SEQUENCE;

		//
		private Token(Type type)
		{
			this(type, null);
		}

		private Token(Type type, CharSequence sequence)
		{
			TYPE = type;
			SEQUENCE = sequence;
		}
	}


	private enum Entity
	{
		OBJECT,
		ARRAY,
		VALUE
	}


	private interface ITask
	{
		Entity entity();
	}


	final private static class ObjectTask
		implements ITask
	{
		//
		final private JsonObject OBJECT;
		private int stage;
		private CharSequence name;

		//
		private ObjectTask(JsonObject object)
		{
			OBJECT = object;
			stage = 0;
			name = null;
		}

		@Override
		public Entity entity()
		{
			return Entity.OBJECT;
		}
	}


	final private static class ArrayTask
		implements ITask
	{
		//
		final private JsonArray ARRAY;
		private int stage;

		//
		private ArrayTask(JsonArray array)
		{
			ARRAY = array;
			stage = 0;
		}

		@Override
		public Entity entity()
		{
			return Entity.ARRAY;
		}
	}


	final private static class Cache
	{
		//
		private @NullAllow String[] names;
		private @NullAllow JsonObject object;

		//
		private Cache()
		{
			names = null;
			object = null;
		}

		//
		private @NullAllow JsonObject get(List<String> names, int limit)
		{
			if (this.object != null && this.names.length == limit)
			{
				boolean error = false;

				for (int i = 0; i < limit; i++)
				{
					String name = names.get(i);

					if (!StringUtil.equal(name, this.names[i]))
					{
						error = true;

						break;
					}
				}

				if (!error) return this.object;
			}

			return null;
		}

		private void set(List<String> names, int limit, JsonObject object)
		{
			this.names = new String[limit];

			for (int i = 0; i < limit; i++) this.names[i] = names.get(i);

			this.object = object;
		}
	}

}
