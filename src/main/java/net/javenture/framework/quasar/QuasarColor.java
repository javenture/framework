package net.javenture.framework.quasar;


import net.javenture.framework.utf8.IUtf8StreamableEntry;
import net.javenture.framework.utf8.Utf8OutputStream;

import java.io.IOException;


/*
	2019/07/03
 */
public enum QuasarColor
	implements IQuasarColor
{
	/*
		https://v1.quasar-framework.org/style/color-palette
	 */
	PRIMARY("primary", QuasarStyle.TEXT_PRIMARY, QuasarStyle.BG_PRIMARY),
	SECONDARY("secondary", QuasarStyle.TEXT_SECONDARY, QuasarStyle.BG_SECONDARY),
	ACCENT("accent", QuasarStyle.TEXT_ACCENT, QuasarStyle.BG_ACCENT),
	POSITIVE("positive", QuasarStyle.TEXT_POSITIVE, QuasarStyle.BG_POSITIVE),
	NEGATIVE("negative", QuasarStyle.TEXT_NEGATIVE, QuasarStyle.BG_NEGATIVE),
	INFO("info", QuasarStyle.TEXT_INFO, QuasarStyle.BG_INFO),
	WARNING("warning", QuasarStyle.TEXT_WARNING, QuasarStyle.BG_WARNING),

	TRANSPARENT("transparent", QuasarStyle.TEXT_TRANSPARENT, QuasarStyle.BG_TRANSPARENT),
	WHITE("white", QuasarStyle.TEXT_WHITE, QuasarStyle.BG_WHITE),
	BLACK("black", QuasarStyle.TEXT_BLACK, QuasarStyle.BG_BLACK),

	RED("red", QuasarStyle.TEXT_RED, QuasarStyle.BG_RED),
	RED_1("red-1", QuasarStyle.TEXT_RED_1, QuasarStyle.BG_RED_1),
	RED_2("red-2", QuasarStyle.TEXT_RED_2, QuasarStyle.BG_RED_2),
	RED_3("red-3", QuasarStyle.TEXT_RED_3, QuasarStyle.BG_RED_3),
	RED_4("red-4", QuasarStyle.TEXT_RED_4, QuasarStyle.BG_RED_4),
	RED_5("red-5", QuasarStyle.TEXT_RED_5, QuasarStyle.BG_RED_5),
	RED_6("red-6", QuasarStyle.TEXT_RED_6, QuasarStyle.BG_RED_6),
	RED_7("red-7", QuasarStyle.TEXT_RED_7, QuasarStyle.BG_RED_7),
	RED_8("red-8", QuasarStyle.TEXT_RED_8, QuasarStyle.BG_RED_8),
	RED_9("red-9", QuasarStyle.TEXT_RED_9, QuasarStyle.BG_RED_9),
	RED_10("red-10", QuasarStyle.TEXT_RED_10, QuasarStyle.BG_RED_10),
	RED_11("red-11", QuasarStyle.TEXT_RED_11, QuasarStyle.BG_RED_11),
	RED_12("red-12", QuasarStyle.TEXT_RED_12, QuasarStyle.BG_RED_12),
	RED_13("red-13", QuasarStyle.TEXT_RED_13, QuasarStyle.BG_RED_13),
	RED_14("red-14", QuasarStyle.TEXT_RED_14, QuasarStyle.BG_RED_14),

	PINK("pink", QuasarStyle.TEXT_PINK, QuasarStyle.BG_PINK),
	PINK_1("pink-1", QuasarStyle.TEXT_PINK_1, QuasarStyle.BG_PINK_1),
	PINK_2("pink-2", QuasarStyle.TEXT_PINK_2, QuasarStyle.BG_PINK_2),
	PINK_3("pink-3", QuasarStyle.TEXT_PINK_3, QuasarStyle.BG_PINK_3),
	PINK_4("pink-4", QuasarStyle.TEXT_PINK_4, QuasarStyle.BG_PINK_4),
	PINK_5("pink-5", QuasarStyle.TEXT_PINK_5, QuasarStyle.BG_PINK_5),
	PINK_6("pink-6", QuasarStyle.TEXT_PINK_6, QuasarStyle.BG_PINK_6),
	PINK_7("pink-7", QuasarStyle.TEXT_PINK_7, QuasarStyle.BG_PINK_7),
	PINK_8("pink-8", QuasarStyle.TEXT_PINK_8, QuasarStyle.BG_PINK_8),
	PINK_9("pink-9", QuasarStyle.TEXT_PINK_9, QuasarStyle.BG_PINK_9),
	PINK_10("pink-10", QuasarStyle.TEXT_PINK_10, QuasarStyle.BG_PINK_10),
	PINK_11("pink-11", QuasarStyle.TEXT_PINK_11, QuasarStyle.BG_PINK_11),
	PINK_12("pink-12", QuasarStyle.TEXT_PINK_12, QuasarStyle.BG_PINK_12),
	PINK_13("pink-13", QuasarStyle.TEXT_PINK_13, QuasarStyle.BG_PINK_13),
	PINK_14("pink-14", QuasarStyle.TEXT_PINK_14, QuasarStyle.BG_PINK_14),

	PURPLE("purple", QuasarStyle.TEXT_PURPLE, QuasarStyle.BG_PURPLE),
	PURPLE_1("purple-1", QuasarStyle.TEXT_PURPLE_1, QuasarStyle.BG_PURPLE_1),
	PURPLE_2("purple-2", QuasarStyle.TEXT_PURPLE_2, QuasarStyle.BG_PURPLE_2),
	PURPLE_3("purple-3", QuasarStyle.TEXT_PURPLE_3, QuasarStyle.BG_PURPLE_3),
	PURPLE_4("purple-4", QuasarStyle.TEXT_PURPLE_4, QuasarStyle.BG_PURPLE_4),
	PURPLE_5("purple-5", QuasarStyle.TEXT_PURPLE_5, QuasarStyle.BG_PURPLE_5),
	PURPLE_6("purple-6", QuasarStyle.TEXT_PURPLE_6, QuasarStyle.BG_PURPLE_6),
	PURPLE_7("purple-7", QuasarStyle.TEXT_PURPLE_7, QuasarStyle.BG_PURPLE_7),
	PURPLE_8("purple-8", QuasarStyle.TEXT_PURPLE_8, QuasarStyle.BG_PURPLE_8),
	PURPLE_9("purple-9", QuasarStyle.TEXT_PURPLE_9, QuasarStyle.BG_PURPLE_9),
	PURPLE_10("purple-10", QuasarStyle.TEXT_PURPLE_10, QuasarStyle.BG_PURPLE_10),
	PURPLE_11("purple-11", QuasarStyle.TEXT_PURPLE_11, QuasarStyle.BG_PURPLE_11),
	PURPLE_12("purple-12", QuasarStyle.TEXT_PURPLE_12, QuasarStyle.BG_PURPLE_12),
	PURPLE_13("purple-13", QuasarStyle.TEXT_PURPLE_13, QuasarStyle.BG_PURPLE_13),
	PURPLE_14("purple-14", QuasarStyle.TEXT_PURPLE_14, QuasarStyle.BG_PURPLE_14),

	DEEP_PURPLE("deep-purple", QuasarStyle.TEXT_DEEP_PURPLE, QuasarStyle.BG_DEEP_PURPLE),
	DEEP_PURPLE_1("deep-purple-1", QuasarStyle.TEXT_DEEP_PURPLE_1, QuasarStyle.BG_DEEP_PURPLE_1),
	DEEP_PURPLE_2("deep-purple-2", QuasarStyle.TEXT_DEEP_PURPLE_2, QuasarStyle.BG_DEEP_PURPLE_2),
	DEEP_PURPLE_3("deep-purple-3", QuasarStyle.TEXT_DEEP_PURPLE_3, QuasarStyle.BG_DEEP_PURPLE_3),
	DEEP_PURPLE_4("deep-purple-4", QuasarStyle.TEXT_DEEP_PURPLE_4, QuasarStyle.BG_DEEP_PURPLE_4),
	DEEP_PURPLE_5("deep-purple-5", QuasarStyle.TEXT_DEEP_PURPLE_5, QuasarStyle.BG_DEEP_PURPLE_5),
	DEEP_PURPLE_6("deep-purple-6", QuasarStyle.TEXT_DEEP_PURPLE_6, QuasarStyle.BG_DEEP_PURPLE_6),
	DEEP_PURPLE_7("deep-purple-7", QuasarStyle.TEXT_DEEP_PURPLE_7, QuasarStyle.BG_DEEP_PURPLE_7),
	DEEP_PURPLE_8("deep-purple-8", QuasarStyle.TEXT_DEEP_PURPLE_8, QuasarStyle.BG_DEEP_PURPLE_8),
	DEEP_PURPLE_9("deep-purple-9", QuasarStyle.TEXT_DEEP_PURPLE_9, QuasarStyle.BG_DEEP_PURPLE_9),
	DEEP_PURPLE_10("deep-purple-10", QuasarStyle.TEXT_DEEP_PURPLE_10, QuasarStyle.BG_DEEP_PURPLE_10),
	DEEP_PURPLE_11("deep-purple-11", QuasarStyle.TEXT_DEEP_PURPLE_11, QuasarStyle.BG_DEEP_PURPLE_11),
	DEEP_PURPLE_12("deep-purple-12", QuasarStyle.TEXT_DEEP_PURPLE_12, QuasarStyle.BG_DEEP_PURPLE_12),
	DEEP_PURPLE_13("deep-purple-13", QuasarStyle.TEXT_DEEP_PURPLE_13, QuasarStyle.BG_DEEP_PURPLE_13),
	DEEP_PURPLE_14("deep-purple-14", QuasarStyle.TEXT_DEEP_PURPLE_14, QuasarStyle.BG_DEEP_PURPLE_14),

	INDIGO("indigo", QuasarStyle.TEXT_INDIGO, QuasarStyle.BG_INDIGO),
	INDIGO_1("indigo-1", QuasarStyle.TEXT_INDIGO_1, QuasarStyle.BG_INDIGO_1),
	INDIGO_2("indigo-2", QuasarStyle.TEXT_INDIGO_2, QuasarStyle.BG_INDIGO_2),
	INDIGO_3("indigo-3", QuasarStyle.TEXT_INDIGO_3, QuasarStyle.BG_INDIGO_3),
	INDIGO_4("indigo-4", QuasarStyle.TEXT_INDIGO_4, QuasarStyle.BG_INDIGO_4),
	INDIGO_5("indigo-5", QuasarStyle.TEXT_INDIGO_5, QuasarStyle.BG_INDIGO_5),
	INDIGO_6("indigo-6", QuasarStyle.TEXT_INDIGO_6, QuasarStyle.BG_INDIGO_6),
	INDIGO_7("indigo-7", QuasarStyle.TEXT_INDIGO_7, QuasarStyle.BG_INDIGO_7),
	INDIGO_8("indigo-8", QuasarStyle.TEXT_INDIGO_8, QuasarStyle.BG_INDIGO_8),
	INDIGO_9("indigo-9", QuasarStyle.TEXT_INDIGO_9, QuasarStyle.BG_INDIGO_9),
	INDIGO_10("indigo-10", QuasarStyle.TEXT_INDIGO_10, QuasarStyle.BG_INDIGO_10),
	INDIGO_11("indigo-11", QuasarStyle.TEXT_INDIGO_11, QuasarStyle.BG_INDIGO_11),
	INDIGO_12("indigo-12", QuasarStyle.TEXT_INDIGO_12, QuasarStyle.BG_INDIGO_12),
	INDIGO_13("indigo-13", QuasarStyle.TEXT_INDIGO_13, QuasarStyle.BG_INDIGO_13),
	INDIGO_14("indigo-14", QuasarStyle.TEXT_INDIGO_14, QuasarStyle.BG_INDIGO_14),

	BLUE("blue", QuasarStyle.TEXT_BLUE, QuasarStyle.BG_BLUE),
	BLUE_1("blue-1", QuasarStyle.TEXT_BLUE_1, QuasarStyle.BG_BLUE_1),
	BLUE_2("blue-2", QuasarStyle.TEXT_BLUE_2, QuasarStyle.BG_BLUE_2),
	BLUE_3("blue-3", QuasarStyle.TEXT_BLUE_3, QuasarStyle.BG_BLUE_3),
	BLUE_4("blue-4", QuasarStyle.TEXT_BLUE_4, QuasarStyle.BG_BLUE_4),
	BLUE_5("blue-5", QuasarStyle.TEXT_BLUE_5, QuasarStyle.BG_BLUE_5),
	BLUE_6("blue-6", QuasarStyle.TEXT_BLUE_6, QuasarStyle.BG_BLUE_6),
	BLUE_7("blue-7", QuasarStyle.TEXT_BLUE_7, QuasarStyle.BG_BLUE_7),
	BLUE_8("blue-8", QuasarStyle.TEXT_BLUE_8, QuasarStyle.BG_BLUE_8),
	BLUE_9("blue-9", QuasarStyle.TEXT_BLUE_9, QuasarStyle.BG_BLUE_9),
	BLUE_10("blue-10", QuasarStyle.TEXT_BLUE_10, QuasarStyle.BG_BLUE_10),
	BLUE_11("blue-11", QuasarStyle.TEXT_BLUE_11, QuasarStyle.BG_BLUE_11),
	BLUE_12("blue-12", QuasarStyle.TEXT_BLUE_12, QuasarStyle.BG_BLUE_12),
	BLUE_13("blue-13", QuasarStyle.TEXT_BLUE_13, QuasarStyle.BG_BLUE_13),
	BLUE_14("blue-14", QuasarStyle.TEXT_BLUE_14, QuasarStyle.BG_BLUE_14),

	LIGHT_BLUE("light-blue", QuasarStyle.TEXT_LIGHT_BLUE, QuasarStyle.BG_LIGHT_BLUE),
	LIGHT_BLUE_1("light-blue-1", QuasarStyle.TEXT_LIGHT_BLUE_1, QuasarStyle.BG_LIGHT_BLUE_1),
	LIGHT_BLUE_2("light-blue-2", QuasarStyle.TEXT_LIGHT_BLUE_2, QuasarStyle.BG_LIGHT_BLUE_2),
	LIGHT_BLUE_3("light-blue-3", QuasarStyle.TEXT_LIGHT_BLUE_3, QuasarStyle.BG_LIGHT_BLUE_3),
	LIGHT_BLUE_4("light-blue-4", QuasarStyle.TEXT_LIGHT_BLUE_4, QuasarStyle.BG_LIGHT_BLUE_4),
	LIGHT_BLUE_5("light-blue-5", QuasarStyle.TEXT_LIGHT_BLUE_5, QuasarStyle.BG_LIGHT_BLUE_5),
	LIGHT_BLUE_6("light-blue-6", QuasarStyle.TEXT_LIGHT_BLUE_6, QuasarStyle.BG_LIGHT_BLUE_6),
	LIGHT_BLUE_7("light-blue-7", QuasarStyle.TEXT_LIGHT_BLUE_7, QuasarStyle.BG_LIGHT_BLUE_7),
	LIGHT_BLUE_8("light-blue-8", QuasarStyle.TEXT_LIGHT_BLUE_8, QuasarStyle.BG_LIGHT_BLUE_8),
	LIGHT_BLUE_9("light-blue-9", QuasarStyle.TEXT_LIGHT_BLUE_9, QuasarStyle.BG_LIGHT_BLUE_9),
	LIGHT_BLUE_10("light-blue-10", QuasarStyle.TEXT_LIGHT_BLUE_10, QuasarStyle.BG_LIGHT_BLUE_10),
	LIGHT_BLUE_11("light-blue-11", QuasarStyle.TEXT_LIGHT_BLUE_11, QuasarStyle.BG_LIGHT_BLUE_11),
	LIGHT_BLUE_12("light-blue-12", QuasarStyle.TEXT_LIGHT_BLUE_12, QuasarStyle.BG_LIGHT_BLUE_12),
	LIGHT_BLUE_13("light-blue-13", QuasarStyle.TEXT_LIGHT_BLUE_13, QuasarStyle.BG_LIGHT_BLUE_13),
	LIGHT_BLUE_14("light-blue-14", QuasarStyle.TEXT_LIGHT_BLUE_14, QuasarStyle.BG_LIGHT_BLUE_14),

	CYAN("cyan", QuasarStyle.TEXT_CYAN, QuasarStyle.BG_CYAN),
	CYAN_1("cyan-1", QuasarStyle.TEXT_CYAN_1, QuasarStyle.BG_CYAN_1),
	CYAN_2("cyan-2", QuasarStyle.TEXT_CYAN_2, QuasarStyle.BG_CYAN_2),
	CYAN_3("cyan-3", QuasarStyle.TEXT_CYAN_3, QuasarStyle.BG_CYAN_3),
	CYAN_4("cyan-4", QuasarStyle.TEXT_CYAN_4, QuasarStyle.BG_CYAN_4),
	CYAN_5("cyan-5", QuasarStyle.TEXT_CYAN_5, QuasarStyle.BG_CYAN_5),
	CYAN_6("cyan-6", QuasarStyle.TEXT_CYAN_6, QuasarStyle.BG_CYAN_6),
	CYAN_7("cyan-7", QuasarStyle.TEXT_CYAN_7, QuasarStyle.BG_CYAN_7),
	CYAN_8("cyan-8", QuasarStyle.TEXT_CYAN_8, QuasarStyle.BG_CYAN_8),
	CYAN_9("cyan-9", QuasarStyle.TEXT_CYAN_9, QuasarStyle.BG_CYAN_9),
	CYAN_10("cyan-10", QuasarStyle.TEXT_CYAN_10, QuasarStyle.BG_CYAN_10),
	CYAN_11("cyan-11", QuasarStyle.TEXT_CYAN_11, QuasarStyle.BG_CYAN_11),
	CYAN_12("cyan-12", QuasarStyle.TEXT_CYAN_12, QuasarStyle.BG_CYAN_12),
	CYAN_13("cyan-13", QuasarStyle.TEXT_CYAN_13, QuasarStyle.BG_CYAN_13),
	CYAN_14("cyan-14", QuasarStyle.TEXT_CYAN_14, QuasarStyle.BG_CYAN_14),

	TEAL("teal", QuasarStyle.TEXT_TEAL, QuasarStyle.BG_TEAL),
	TEAL_1("teal-1", QuasarStyle.TEXT_TEAL_1, QuasarStyle.BG_TEAL_1),
	TEAL_2("teal-2", QuasarStyle.TEXT_TEAL_2, QuasarStyle.BG_TEAL_2),
	TEAL_3("teal-3", QuasarStyle.TEXT_TEAL_3, QuasarStyle.BG_TEAL_3),
	TEAL_4("teal-4", QuasarStyle.TEXT_TEAL_4, QuasarStyle.BG_TEAL_4),
	TEAL_5("teal-5", QuasarStyle.TEXT_TEAL_5, QuasarStyle.BG_TEAL_5),
	TEAL_6("teal-6", QuasarStyle.TEXT_TEAL_6, QuasarStyle.BG_TEAL_6),
	TEAL_7("teal-7", QuasarStyle.TEXT_TEAL_7, QuasarStyle.BG_TEAL_7),
	TEAL_8("teal-8", QuasarStyle.TEXT_TEAL_8, QuasarStyle.BG_TEAL_8),
	TEAL_9("teal-9", QuasarStyle.TEXT_TEAL_9, QuasarStyle.BG_TEAL_9),
	TEAL_10("teal-10", QuasarStyle.TEXT_TEAL_10, QuasarStyle.BG_TEAL_10),
	TEAL_11("teal-11", QuasarStyle.TEXT_TEAL_11, QuasarStyle.BG_TEAL_11),
	TEAL_12("teal-12", QuasarStyle.TEXT_TEAL_12, QuasarStyle.BG_TEAL_12),
	TEAL_13("teal-13", QuasarStyle.TEXT_TEAL_13, QuasarStyle.BG_TEAL_13),
	TEAL_14("teal-14", QuasarStyle.TEXT_TEAL_14, QuasarStyle.BG_TEAL_14),

	GREEN("green", QuasarStyle.TEXT_GREEN, QuasarStyle.BG_GREEN),
	GREEN_1("green-1", QuasarStyle.TEXT_GREEN_1, QuasarStyle.BG_GREEN_1),
	GREEN_2("green-2", QuasarStyle.TEXT_GREEN_2, QuasarStyle.BG_GREEN_2),
	GREEN_3("green-3", QuasarStyle.TEXT_GREEN_3, QuasarStyle.BG_GREEN_3),
	GREEN_4("green-4", QuasarStyle.TEXT_GREEN_4, QuasarStyle.BG_GREEN_4),
	GREEN_5("green-5", QuasarStyle.TEXT_GREEN_5, QuasarStyle.BG_GREEN_5),
	GREEN_6("green-6", QuasarStyle.TEXT_GREEN_6, QuasarStyle.BG_GREEN_6),
	GREEN_7("green-7", QuasarStyle.TEXT_GREEN_7, QuasarStyle.BG_GREEN_7),
	GREEN_8("green-8", QuasarStyle.TEXT_GREEN_8, QuasarStyle.BG_GREEN_8),
	GREEN_9("green-9", QuasarStyle.TEXT_GREEN_9, QuasarStyle.BG_GREEN_9),
	GREEN_10("green-10", QuasarStyle.TEXT_GREEN_10, QuasarStyle.BG_GREEN_10),
	GREEN_11("green-11", QuasarStyle.TEXT_GREEN_11, QuasarStyle.BG_GREEN_11),
	GREEN_12("green-12", QuasarStyle.TEXT_GREEN_12, QuasarStyle.BG_GREEN_12),
	GREEN_13("green-13", QuasarStyle.TEXT_GREEN_13, QuasarStyle.BG_GREEN_13),
	GREEN_14("green-14", QuasarStyle.TEXT_GREEN_14, QuasarStyle.BG_GREEN_14),

	LIGHT_GREEN("light-green", QuasarStyle.TEXT_LIGHT_GREEN, QuasarStyle.BG_LIGHT_GREEN),
	LIGHT_GREEN_1("light-green-1", QuasarStyle.TEXT_LIGHT_GREEN_1, QuasarStyle.BG_LIGHT_GREEN_1),
	LIGHT_GREEN_2("light-green-2", QuasarStyle.TEXT_LIGHT_GREEN_2, QuasarStyle.BG_LIGHT_GREEN_2),
	LIGHT_GREEN_3("light-green-3", QuasarStyle.TEXT_LIGHT_GREEN_3, QuasarStyle.BG_LIGHT_GREEN_3),
	LIGHT_GREEN_4("light-green-4", QuasarStyle.TEXT_LIGHT_GREEN_4, QuasarStyle.BG_LIGHT_GREEN_4),
	LIGHT_GREEN_5("light-green-5", QuasarStyle.TEXT_LIGHT_GREEN_5, QuasarStyle.BG_LIGHT_GREEN_5),
	LIGHT_GREEN_6("light-green-6", QuasarStyle.TEXT_LIGHT_GREEN_6, QuasarStyle.BG_LIGHT_GREEN_6),
	LIGHT_GREEN_7("light-green-7", QuasarStyle.TEXT_LIGHT_GREEN_7, QuasarStyle.BG_LIGHT_GREEN_7),
	LIGHT_GREEN_8("light-green-8", QuasarStyle.TEXT_LIGHT_GREEN_8, QuasarStyle.BG_LIGHT_GREEN_8),
	LIGHT_GREEN_9("light-green-9", QuasarStyle.TEXT_LIGHT_GREEN_9, QuasarStyle.BG_LIGHT_GREEN_9),
	LIGHT_GREEN_10("light-green-10", QuasarStyle.TEXT_LIGHT_GREEN_10, QuasarStyle.BG_LIGHT_GREEN_10),
	LIGHT_GREEN_11("light-green-11", QuasarStyle.TEXT_LIGHT_GREEN_11, QuasarStyle.BG_LIGHT_GREEN_11),
	LIGHT_GREEN_12("light-green-12", QuasarStyle.TEXT_LIGHT_GREEN_12, QuasarStyle.BG_LIGHT_GREEN_12),
	LIGHT_GREEN_13("light-green-13", QuasarStyle.TEXT_LIGHT_GREEN_13, QuasarStyle.BG_LIGHT_GREEN_13),
	LIGHT_GREEN_14("light-green-14", QuasarStyle.TEXT_LIGHT_GREEN_14, QuasarStyle.BG_LIGHT_GREEN_14),

	LIME("lime", QuasarStyle.TEXT_LIME, QuasarStyle.BG_LIME),
	LIME_1("lime-1", QuasarStyle.TEXT_LIME_1, QuasarStyle.BG_LIME_1),
	LIME_2("lime-2", QuasarStyle.TEXT_LIME_2, QuasarStyle.BG_LIME_2),
	LIME_3("lime-3", QuasarStyle.TEXT_LIME_3, QuasarStyle.BG_LIME_3),
	LIME_4("lime-4", QuasarStyle.TEXT_LIME_4, QuasarStyle.BG_LIME_4),
	LIME_5("lime-5", QuasarStyle.TEXT_LIME_5, QuasarStyle.BG_LIME_5),
	LIME_6("lime-6", QuasarStyle.TEXT_LIME_6, QuasarStyle.BG_LIME_6),
	LIME_7("lime-7", QuasarStyle.TEXT_LIME_7, QuasarStyle.BG_LIME_7),
	LIME_8("lime-8", QuasarStyle.TEXT_LIME_8, QuasarStyle.BG_LIME_8),
	LIME_9("lime-9", QuasarStyle.TEXT_LIME_9, QuasarStyle.BG_LIME_9),
	LIME_10("lime-10", QuasarStyle.TEXT_LIME_10, QuasarStyle.BG_LIME_10),
	LIME_11("lime-11", QuasarStyle.TEXT_LIME_11, QuasarStyle.BG_LIME_11),
	LIME_12("lime-12", QuasarStyle.TEXT_LIME_12, QuasarStyle.BG_LIME_12),
	LIME_13("lime-13", QuasarStyle.TEXT_LIME_13, QuasarStyle.BG_LIME_13),
	LIME_14("lime-14", QuasarStyle.TEXT_LIME_14, QuasarStyle.BG_LIME_14),

	YELLOW("yellow", QuasarStyle.TEXT_YELLOW, QuasarStyle.BG_YELLOW),
	YELLOW_1("yellow-1", QuasarStyle.TEXT_YELLOW_1, QuasarStyle.BG_YELLOW_1),
	YELLOW_2("yellow-2", QuasarStyle.TEXT_YELLOW_2, QuasarStyle.BG_YELLOW_2),
	YELLOW_3("yellow-3", QuasarStyle.TEXT_YELLOW_3, QuasarStyle.BG_YELLOW_3),
	YELLOW_4("yellow-4", QuasarStyle.TEXT_YELLOW_4, QuasarStyle.BG_YELLOW_4),
	YELLOW_5("yellow-5", QuasarStyle.TEXT_YELLOW_5, QuasarStyle.BG_YELLOW_5),
	YELLOW_6("yellow-6", QuasarStyle.TEXT_YELLOW_6, QuasarStyle.BG_YELLOW_6),
	YELLOW_7("yellow-7", QuasarStyle.TEXT_YELLOW_7, QuasarStyle.BG_YELLOW_7),
	YELLOW_8("yellow-8", QuasarStyle.TEXT_YELLOW_8, QuasarStyle.BG_YELLOW_8),
	YELLOW_9("yellow-9", QuasarStyle.TEXT_YELLOW_9, QuasarStyle.BG_YELLOW_9),
	YELLOW_10("yellow-10", QuasarStyle.TEXT_YELLOW_10, QuasarStyle.BG_YELLOW_10),
	YELLOW_11("yellow-11", QuasarStyle.TEXT_YELLOW_11, QuasarStyle.BG_YELLOW_11),
	YELLOW_12("yellow-12", QuasarStyle.TEXT_YELLOW_12, QuasarStyle.BG_YELLOW_12),
	YELLOW_13("yellow-13", QuasarStyle.TEXT_YELLOW_13, QuasarStyle.BG_YELLOW_13),
	YELLOW_14("yellow-14", QuasarStyle.TEXT_YELLOW_14, QuasarStyle.BG_YELLOW_14),

	AMBER("amber", QuasarStyle.TEXT_AMBER, QuasarStyle.BG_AMBER),
	AMBER_1("amber-1", QuasarStyle.TEXT_AMBER_1, QuasarStyle.BG_AMBER_1),
	AMBER_2("amber-2", QuasarStyle.TEXT_AMBER_2, QuasarStyle.BG_AMBER_2),
	AMBER_3("amber-3", QuasarStyle.TEXT_AMBER_3, QuasarStyle.BG_AMBER_3),
	AMBER_4("amber-4", QuasarStyle.TEXT_AMBER_4, QuasarStyle.BG_AMBER_4),
	AMBER_5("amber-5", QuasarStyle.TEXT_AMBER_5, QuasarStyle.BG_AMBER_5),
	AMBER_6("amber-6", QuasarStyle.TEXT_AMBER_6, QuasarStyle.BG_AMBER_6),
	AMBER_7("amber-7", QuasarStyle.TEXT_AMBER_7, QuasarStyle.BG_AMBER_7),
	AMBER_8("amber-8", QuasarStyle.TEXT_AMBER_8, QuasarStyle.BG_AMBER_8),
	AMBER_9("amber-9", QuasarStyle.TEXT_AMBER_9, QuasarStyle.BG_AMBER_9),
	AMBER_10("amber-10", QuasarStyle.TEXT_AMBER_10, QuasarStyle.BG_AMBER_10),
	AMBER_11("amber-11", QuasarStyle.TEXT_AMBER_11, QuasarStyle.BG_AMBER_11),
	AMBER_12("amber-12", QuasarStyle.TEXT_AMBER_12, QuasarStyle.BG_AMBER_12),
	AMBER_13("amber-13", QuasarStyle.TEXT_AMBER_13, QuasarStyle.BG_AMBER_13),
	AMBER_14("amber-14", QuasarStyle.TEXT_AMBER_14, QuasarStyle.BG_AMBER_14),

	ORANGE("orange", QuasarStyle.TEXT_ORANGE, QuasarStyle.BG_ORANGE),
	ORANGE_1("orange-1", QuasarStyle.TEXT_ORANGE_1, QuasarStyle.BG_ORANGE_1),
	ORANGE_2("orange-2", QuasarStyle.TEXT_ORANGE_2, QuasarStyle.BG_ORANGE_2),
	ORANGE_3("orange-3", QuasarStyle.TEXT_ORANGE_3, QuasarStyle.BG_ORANGE_3),
	ORANGE_4("orange-4", QuasarStyle.TEXT_ORANGE_4, QuasarStyle.BG_ORANGE_4),
	ORANGE_5("orange-5", QuasarStyle.TEXT_ORANGE_5, QuasarStyle.BG_ORANGE_5),
	ORANGE_6("orange-6", QuasarStyle.TEXT_ORANGE_6, QuasarStyle.BG_ORANGE_6),
	ORANGE_7("orange-7", QuasarStyle.TEXT_ORANGE_7, QuasarStyle.BG_ORANGE_7),
	ORANGE_8("orange-8", QuasarStyle.TEXT_ORANGE_8, QuasarStyle.BG_ORANGE_8),
	ORANGE_9("orange-9", QuasarStyle.TEXT_ORANGE_9, QuasarStyle.BG_ORANGE_9),
	ORANGE_10("orange-10", QuasarStyle.TEXT_ORANGE_10, QuasarStyle.BG_ORANGE_10),
	ORANGE_11("orange-11", QuasarStyle.TEXT_ORANGE_11, QuasarStyle.BG_ORANGE_11),
	ORANGE_12("orange-12", QuasarStyle.TEXT_ORANGE_12, QuasarStyle.BG_ORANGE_12),
	ORANGE_13("orange-13", QuasarStyle.TEXT_ORANGE_13, QuasarStyle.BG_ORANGE_13),
	ORANGE_14("orange-14", QuasarStyle.TEXT_ORANGE_14, QuasarStyle.BG_ORANGE_14),

	DEEP_ORANGE("deep-orange", QuasarStyle.TEXT_DEEP_ORANGE, QuasarStyle.BG_DEEP_ORANGE),
	DEEP_ORANGE_1("deep-orange-1", QuasarStyle.TEXT_DEEP_ORANGE_1, QuasarStyle.BG_DEEP_ORANGE_1),
	DEEP_ORANGE_2("deep-orange-2", QuasarStyle.TEXT_DEEP_ORANGE_2, QuasarStyle.BG_DEEP_ORANGE_2),
	DEEP_ORANGE_3("deep-orange-3", QuasarStyle.TEXT_DEEP_ORANGE_3, QuasarStyle.BG_DEEP_ORANGE_3),
	DEEP_ORANGE_4("deep-orange-4", QuasarStyle.TEXT_DEEP_ORANGE_4, QuasarStyle.BG_DEEP_ORANGE_4),
	DEEP_ORANGE_5("deep-orange-5", QuasarStyle.TEXT_DEEP_ORANGE_5, QuasarStyle.BG_DEEP_ORANGE_5),
	DEEP_ORANGE_6("deep-orange-6", QuasarStyle.TEXT_DEEP_ORANGE_6, QuasarStyle.BG_DEEP_ORANGE_6),
	DEEP_ORANGE_7("deep-orange-7", QuasarStyle.TEXT_DEEP_ORANGE_7, QuasarStyle.BG_DEEP_ORANGE_7),
	DEEP_ORANGE_8("deep-orange-8", QuasarStyle.TEXT_DEEP_ORANGE_8, QuasarStyle.BG_DEEP_ORANGE_8),
	DEEP_ORANGE_9("deep-orange-9", QuasarStyle.TEXT_DEEP_ORANGE_9, QuasarStyle.BG_DEEP_ORANGE_9),
	DEEP_ORANGE_10("deep-orange-10", QuasarStyle.TEXT_DEEP_ORANGE_10, QuasarStyle.BG_DEEP_ORANGE_10),
	DEEP_ORANGE_11("deep-orange-11", QuasarStyle.TEXT_DEEP_ORANGE_11, QuasarStyle.BG_DEEP_ORANGE_11),
	DEEP_ORANGE_12("deep-orange-12", QuasarStyle.TEXT_DEEP_ORANGE_12, QuasarStyle.BG_DEEP_ORANGE_12),
	DEEP_ORANGE_13("deep-orange-13", QuasarStyle.TEXT_DEEP_ORANGE_13, QuasarStyle.BG_DEEP_ORANGE_13),
	DEEP_ORANGE_14("deep-orange-14", QuasarStyle.TEXT_DEEP_ORANGE_14, QuasarStyle.BG_DEEP_ORANGE_14),

	BROWN("brown", QuasarStyle.TEXT_BROWN, QuasarStyle.BG_BROWN),
	BROWN_1("brown-1", QuasarStyle.TEXT_BROWN_1, QuasarStyle.BG_BROWN_1),
	BROWN_2("brown-2", QuasarStyle.TEXT_BROWN_2, QuasarStyle.BG_BROWN_2),
	BROWN_3("brown-3", QuasarStyle.TEXT_BROWN_3, QuasarStyle.BG_BROWN_3),
	BROWN_4("brown-4", QuasarStyle.TEXT_BROWN_4, QuasarStyle.BG_BROWN_4),
	BROWN_5("brown-5", QuasarStyle.TEXT_BROWN_5, QuasarStyle.BG_BROWN_5),
	BROWN_6("brown-6", QuasarStyle.TEXT_BROWN_6, QuasarStyle.BG_BROWN_6),
	BROWN_7("brown-7", QuasarStyle.TEXT_BROWN_7, QuasarStyle.BG_BROWN_7),
	BROWN_8("brown-8", QuasarStyle.TEXT_BROWN_8, QuasarStyle.BG_BROWN_8),
	BROWN_9("brown-9", QuasarStyle.TEXT_BROWN_9, QuasarStyle.BG_BROWN_9),
	BROWN_10("brown-10", QuasarStyle.TEXT_BROWN_10, QuasarStyle.BG_BROWN_10),
	BROWN_11("brown-11", QuasarStyle.TEXT_BROWN_11, QuasarStyle.BG_BROWN_11),
	BROWN_12("brown-12", QuasarStyle.TEXT_BROWN_12, QuasarStyle.BG_BROWN_12),
	BROWN_13("brown-13", QuasarStyle.TEXT_BROWN_13, QuasarStyle.BG_BROWN_13),
	BROWN_14("brown-14", QuasarStyle.TEXT_BROWN_14, QuasarStyle.BG_BROWN_14),

	GREY("grey", QuasarStyle.TEXT_GREY, QuasarStyle.BG_GREY),
	GREY_1("grey-1", QuasarStyle.TEXT_GREY_1, QuasarStyle.BG_GREY_1),
	GREY_2("grey-2", QuasarStyle.TEXT_GREY_2, QuasarStyle.BG_GREY_2),
	GREY_3("grey-3", QuasarStyle.TEXT_GREY_3, QuasarStyle.BG_GREY_3),
	GREY_4("grey-4", QuasarStyle.TEXT_GREY_4, QuasarStyle.BG_GREY_4),
	GREY_5("grey-5", QuasarStyle.TEXT_GREY_5, QuasarStyle.BG_GREY_5),
	GREY_6("grey-6", QuasarStyle.TEXT_GREY_6, QuasarStyle.BG_GREY_6),
	GREY_7("grey-7", QuasarStyle.TEXT_GREY_7, QuasarStyle.BG_GREY_7),
	GREY_8("grey-8", QuasarStyle.TEXT_GREY_8, QuasarStyle.BG_GREY_8),
	GREY_9("grey-9", QuasarStyle.TEXT_GREY_9, QuasarStyle.BG_GREY_9),
	GREY_10("grey-10", QuasarStyle.TEXT_GREY_10, QuasarStyle.BG_GREY_10),
	GREY_11("grey-11", QuasarStyle.TEXT_GREY_11, QuasarStyle.BG_GREY_11),
	GREY_12("grey-12", QuasarStyle.TEXT_GREY_12, QuasarStyle.BG_GREY_12),
	GREY_13("grey-13", QuasarStyle.TEXT_GREY_13, QuasarStyle.BG_GREY_13),
	GREY_14("grey-14", QuasarStyle.TEXT_GREY_14, QuasarStyle.BG_GREY_14),

	BLUE_GREY("blue-grey", QuasarStyle.TEXT_BLUE_GREY, QuasarStyle.BG_BLUE_GREY),
	BLUE_GREY_1("blue-grey-1", QuasarStyle.TEXT_BLUE_GREY_1, QuasarStyle.BG_BLUE_GREY_1),
	BLUE_GREY_2("blue-grey-2", QuasarStyle.TEXT_BLUE_GREY_2, QuasarStyle.BG_BLUE_GREY_2),
	BLUE_GREY_3("blue-grey-3", QuasarStyle.TEXT_BLUE_GREY_3, QuasarStyle.BG_BLUE_GREY_3),
	BLUE_GREY_4("blue-grey-4", QuasarStyle.TEXT_BLUE_GREY_4, QuasarStyle.BG_BLUE_GREY_4),
	BLUE_GREY_5("blue-grey-5", QuasarStyle.TEXT_BLUE_GREY_5, QuasarStyle.BG_BLUE_GREY_5),
	BLUE_GREY_6("blue-grey-6", QuasarStyle.TEXT_BLUE_GREY_6, QuasarStyle.BG_BLUE_GREY_6),
	BLUE_GREY_7("blue-grey-7", QuasarStyle.TEXT_BLUE_GREY_7, QuasarStyle.BG_BLUE_GREY_7),
	BLUE_GREY_8("blue-grey-8", QuasarStyle.TEXT_BLUE_GREY_8, QuasarStyle.BG_BLUE_GREY_8),
	BLUE_GREY_9("blue-grey-9", QuasarStyle.TEXT_BLUE_GREY_9, QuasarStyle.BG_BLUE_GREY_9),
	BLUE_GREY_10("blue-grey-10", QuasarStyle.TEXT_BLUE_GREY_10, QuasarStyle.BG_BLUE_GREY_10),
	BLUE_GREY_11("blue-grey-11", QuasarStyle.TEXT_BLUE_GREY_11, QuasarStyle.BG_BLUE_GREY_11),
	BLUE_GREY_12("blue-grey-12", QuasarStyle.TEXT_BLUE_GREY_12, QuasarStyle.BG_BLUE_GREY_12),
	BLUE_GREY_13("blue-grey-13", QuasarStyle.TEXT_BLUE_GREY_13, QuasarStyle.BG_BLUE_GREY_13),
	BLUE_GREY_14("blue-grey-14", QuasarStyle.TEXT_BLUE_GREY_14, QuasarStyle.BG_BLUE_GREY_14),
	;


	//
	final private String VALUE;
	final private IUtf8StreamableEntry ENTRY;

	final public QuasarStyle STYLE_TEXT;
	final public QuasarStyle STYLE_BACKGROUND;


	//
	QuasarColor(String value, QuasarStyle text, QuasarStyle background)
	{
		VALUE = value;
		ENTRY = IUtf8StreamableEntry.entry(value, true);
		STYLE_TEXT = text;
		STYLE_BACKGROUND = background;
	}


	//
	@Override
	public void toUtf8Stream(Utf8OutputStream destination)
		throws IOException
	{
		ENTRY.toUtf8Stream(destination);
	}


	public String value()
	{
		return VALUE;
	}


	public QuasarStyle styleText()
	{
		return STYLE_TEXT;
	}


	public QuasarStyle styleBackground()
	{
		return STYLE_BACKGROUND;
	}

}
