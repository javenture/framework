package net.javenture.framework.xml;


import net.javenture.framework.utf8.Utf8OutputStream;

import java.io.IOException;
import java.util.ArrayList;


/*
	OK: 2017/05/25
 */
final class XmlBuilder
{
	final private static char[] DECLARATION = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>".toCharArray();


	//
	private XmlBuilder()
	{
	}


	//
	static void execute(AXmlEntry root, Utf8OutputStream stream) throws IOException
	{
		Part first;

		{
			int level = 0;

			Part tag_start = new Part(Part.Type.TAG_START, root, level);
			Part tag_end = new Part(Part.Type.TAG_END, root, level);

			tag_start.next = tag_end;
			tag_end.next = null;

			first = tag_start;
		}

		// tree -> list
		{
			Part current = first;

			while (current != null)
			{
				if (current.TYPE == Part.Type.TAG_START) // ELEMENT
				{
					ArrayList<AXmlEntry> childs = ((XmlElement) current.ENTRY).CHILDS;
					entries(current, childs);
				}

				current = current.next;
			}
		}

		// stream
		{
			stream.write(DECLARATION);
			stream.write('\r');
			stream.write('\n');
			stream.write('\r');
			stream.write('\n');

			//
			Part current = first;

			while (current != null)
			{
				AXmlEntry entry = current.ENTRY;

				switch (current.TYPE)
				{
					case TAG_START:
					{
						if (current.LEVEL != 0)
						{
							stream.write('\r');
							stream.write('\n');

							for (int i = 0; i < current.LEVEL; i++) stream.write('\t');
						}

						//
						XmlElement element = (XmlElement) entry;

						stream.write('<');
						stream.write(element.NAME);

						// attributes
						for (XmlAttribute attribute : element.ATTRIBUTES)
						{
							stream.write(' ');
							stream.write(attribute.NAME);
							stream.write('=');
							stream.write('"');

							for (XmlAttribute.Value value : attribute.VALUES)
							{
								if (value.SECURE)
								{
									for (char c : value.ARRAY)
									{
										// http://wonko.com/post/html-escaping
										// https://www.owasp.org/index.php/XSS_(Cross_Site_Scripting)_Prevention_Cheat_Sheet

										switch (c)
										{
											case '"': stream.write(XmlEntity.QUOT.VALUE); break;
											case '&': stream.write(XmlEntity.AMP.VALUE); break;
											case '\'': stream.write(XmlEntity.APOS.VALUE); break;
											case '<': stream.write(XmlEntity.LT.VALUE); break;
											case '>': stream.write(XmlEntity.GT.VALUE); break;
											default: stream.write(c);
										}
									}
								}
								else
								{
									stream.write(value.ARRAY);
								}
							}

							stream.write('"');
						}

						stream.write('>');

						break;
					}

					case TAG_END:
					{
						if (current.LEVEL != 0)
						{
							stream.write('\r');
							stream.write('\n');

							for (int i = 0; i < current.LEVEL; i++) stream.write('\t');
						}

						//
						XmlElement element = (XmlElement) entry;

						stream.write('<');
						stream.write('/');
						stream.write(element.NAME);
						stream.write('>');

						break;
					}

					case CONTENT:
					{
						switch (entry.TYPE)
						{
							case COMMENT:
							{
								XmlComment comment = (XmlComment) entry;

								//
								stream.write('<');
								stream.write('!');
								stream.write('-');
								stream.write('-');
								stream.write(' ');

								for (char c : comment.VALUE)
								{
									switch (c)
									{
										case '-': stream.write(XmlReference.HYPHEN.VALUE); break;
										default: stream.write(c);
									}
								}

								stream.write(' ');
								stream.write('-');
								stream.write('-');
								stream.write('>');

								break;
							}

							case TEXT:
							{
								XmlText text = (XmlText) entry;

								if (text.SECURE)
								{
									for (char c : text.VALUE)
									{
										switch (c)
										{
											case '"': stream.write(XmlEntity.QUOT.VALUE); break;
											case '&': stream.write(XmlEntity.AMP.VALUE); break;
											case '\'': stream.write(XmlEntity.APOS.VALUE); break;
											case '<': stream.write(XmlEntity.LT.VALUE); break;
											case '>': stream.write(XmlEntity.GT.VALUE); break;
											default: stream.write(c);
										}
									}
								}
								else
								{
									stream.write(text.VALUE);
								}

								break;
							}

							case ENTITY:
							{
								XmlEntity entity = (XmlEntity) entry;
								stream.write(entity.VALUE);

								break;
							}

							case REFERENCE:
							{
								XmlReference reference = (XmlReference) entry;
								stream.write(reference.VALUE);

								break;
							}
						}

						break;
					}
				}

				current = current.next;
			}

			// stream.write('\r');                       // XXX: ?
			// stream.write('\n');
		}

		// recycle
		{
			Part current = first;

			while (current.next != null)
			{
				Part next = current.next;
				current.next = null;
				current = next;
			}
		}
	}


	private static void entries(Part current, ArrayList<AXmlEntry> entries)
	{
		if (!entries.isEmpty())
		{
			int level = current.LEVEL + 1;
			Part previous = current;
			Part next = current.next;

			for (AXmlEntry entry : entries)
			{
				switch (entry.TYPE)
				{
					case MARKUP:
					case CONTENT:
					{
						Part tag_start = new Part(Part.Type.TAG_START, entry, level);
						Part tag_end = new Part(Part.Type.TAG_END, entry, level);

						tag_start.next = tag_end;
						tag_end.next = next;

						previous.next = tag_start;
						previous = tag_end;

						break;
					}

					case COMMENT:
					case TEXT:
					case ENTITY:
					case REFERENCE:
					{
						Part content = new Part(Part.Type.CONTENT, entry, level);

						content.next = next;

						previous.next = content;
						previous = content;

						break;
					}
				}
			}
		}
	}


	//
	final private static class Part
	{
		enum Type
		{
			TAG_START,
			TAG_END,
			CONTENT
		}

		//
		final private Type TYPE;
		final private AXmlEntry ENTRY;
		final private int LEVEL;

		private Part next;

		//
		private Part(Type type, AXmlEntry entry, int level)
		{
			TYPE = type;
			ENTRY = entry;
			LEVEL = level;

			next = null;
		}
	}

}
