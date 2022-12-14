package net.javenture.framework.quasar;


import net.javenture.framework.css.ICssClass;
import net.javenture.framework.utf8.IUtf8StreamableEntry;
import net.javenture.framework.utf8.Utf8OutputStream;

import java.io.IOException;


/*
	2019/08/06
 */
public enum QuasarStyle
	implements ICssClass
{
	/*
		https://quasar.dev/style/color-palette
	 */
	TEXT_PRIMARY("text-primary"),                                             // XXX: -> COLOR_TEXT_PRIMARY
	TEXT_SECONDARY("text-secondary"),
	TEXT_ACCENT("text-accent"),
	TEXT_POSITIVE("text-positive"),
	TEXT_NEGATIVE("text-negative"),
	TEXT_INFO("text-info"),
	TEXT_WARNING("text-warning"),

	TEXT_TRANSPARENT("text-transparent"),
	TEXT_WHITE("text-white"),
	TEXT_BLACK("text-black"),

	TEXT_RED("text-red"),
	TEXT_RED_1("text-red-1"),
	TEXT_RED_2("text-red-2"),
	TEXT_RED_3("text-red-3"),
	TEXT_RED_4("text-red-4"),
	TEXT_RED_5("text-red-5"),
	TEXT_RED_6("text-red-6"),
	TEXT_RED_7("text-red-7"),
	TEXT_RED_8("text-red-8"),
	TEXT_RED_9("text-red-9"),
	TEXT_RED_10("text-red-10"),
	TEXT_RED_11("text-red-11"),
	TEXT_RED_12("text-red-12"),
	TEXT_RED_13("text-red-13"),
	TEXT_RED_14("text-red-14"),

	TEXT_PINK("text-pink"),
	TEXT_PINK_1("text-pink-1"),
	TEXT_PINK_2("text-pink-2"),
	TEXT_PINK_3("text-pink-3"),
	TEXT_PINK_4("text-pink-4"),
	TEXT_PINK_5("text-pink-5"),
	TEXT_PINK_6("text-pink-6"),
	TEXT_PINK_7("text-pink-7"),
	TEXT_PINK_8("text-pink-8"),
	TEXT_PINK_9("text-pink-9"),
	TEXT_PINK_10("text-pink-10"),
	TEXT_PINK_11("text-pink-11"),
	TEXT_PINK_12("text-pink-12"),
	TEXT_PINK_13("text-pink-13"),
	TEXT_PINK_14("text-pink-14"),

	TEXT_PURPLE("text-purple"),
	TEXT_PURPLE_1("text-purple-1"),
	TEXT_PURPLE_2("text-purple-2"),
	TEXT_PURPLE_3("text-purple-3"),
	TEXT_PURPLE_4("text-purple-4"),
	TEXT_PURPLE_5("text-purple-5"),
	TEXT_PURPLE_6("text-purple-6"),
	TEXT_PURPLE_7("text-purple-7"),
	TEXT_PURPLE_8("text-purple-8"),
	TEXT_PURPLE_9("text-purple-9"),
	TEXT_PURPLE_10("text-purple-10"),
	TEXT_PURPLE_11("text-purple-11"),
	TEXT_PURPLE_12("text-purple-12"),
	TEXT_PURPLE_13("text-purple-13"),
	TEXT_PURPLE_14("text-purple-14"),

	TEXT_DEEP_PURPLE("text-deep-purple"),
	TEXT_DEEP_PURPLE_1("text-deep-purple-1"),
	TEXT_DEEP_PURPLE_2("text-deep-purple-2"),
	TEXT_DEEP_PURPLE_3("text-deep-purple-3"),
	TEXT_DEEP_PURPLE_4("text-deep-purple-4"),
	TEXT_DEEP_PURPLE_5("text-deep-purple-5"),
	TEXT_DEEP_PURPLE_6("text-deep-purple-6"),
	TEXT_DEEP_PURPLE_7("text-deep-purple-7"),
	TEXT_DEEP_PURPLE_8("text-deep-purple-8"),
	TEXT_DEEP_PURPLE_9("text-deep-purple-9"),
	TEXT_DEEP_PURPLE_10("text-deep-purple-10"),
	TEXT_DEEP_PURPLE_11("text-deep-purple-11"),
	TEXT_DEEP_PURPLE_12("text-deep-purple-12"),
	TEXT_DEEP_PURPLE_13("text-deep-purple-13"),
	TEXT_DEEP_PURPLE_14("text-deep-purple-14"),

	TEXT_INDIGO("text-indigo"),
	TEXT_INDIGO_1("text-indigo-1"),
	TEXT_INDIGO_2("text-indigo-2"),
	TEXT_INDIGO_3("text-indigo-3"),
	TEXT_INDIGO_4("text-indigo-4"),
	TEXT_INDIGO_5("text-indigo-5"),
	TEXT_INDIGO_6("text-indigo-6"),
	TEXT_INDIGO_7("text-indigo-7"),
	TEXT_INDIGO_8("text-indigo-8"),
	TEXT_INDIGO_9("text-indigo-9"),
	TEXT_INDIGO_10("text-indigo-10"),
	TEXT_INDIGO_11("text-indigo-11"),
	TEXT_INDIGO_12("text-indigo-12"),
	TEXT_INDIGO_13("text-indigo-13"),
	TEXT_INDIGO_14("text-indigo-14"),

	TEXT_BLUE("text-blue"),
	TEXT_BLUE_1("text-blue-1"),
	TEXT_BLUE_2("text-blue-2"),
	TEXT_BLUE_3("text-blue-3"),
	TEXT_BLUE_4("text-blue-4"),
	TEXT_BLUE_5("text-blue-5"),
	TEXT_BLUE_6("text-blue-6"),
	TEXT_BLUE_7("text-blue-7"),
	TEXT_BLUE_8("text-blue-8"),
	TEXT_BLUE_9("text-blue-9"),
	TEXT_BLUE_10("text-blue-10"),
	TEXT_BLUE_11("text-blue-11"),
	TEXT_BLUE_12("text-blue-12"),
	TEXT_BLUE_13("text-blue-13"),
	TEXT_BLUE_14("text-blue-14"),

	TEXT_LIGHT_BLUE("text-light-blue"),
	TEXT_LIGHT_BLUE_1("text-light-blue-1"),
	TEXT_LIGHT_BLUE_2("text-light-blue-2"),
	TEXT_LIGHT_BLUE_3("text-light-blue-3"),
	TEXT_LIGHT_BLUE_4("text-light-blue-4"),
	TEXT_LIGHT_BLUE_5("text-light-blue-5"),
	TEXT_LIGHT_BLUE_6("text-light-blue-6"),
	TEXT_LIGHT_BLUE_7("text-light-blue-7"),
	TEXT_LIGHT_BLUE_8("text-light-blue-8"),
	TEXT_LIGHT_BLUE_9("text-light-blue-9"),
	TEXT_LIGHT_BLUE_10("text-light-blue-10"),
	TEXT_LIGHT_BLUE_11("text-light-blue-11"),
	TEXT_LIGHT_BLUE_12("text-light-blue-12"),
	TEXT_LIGHT_BLUE_13("text-light-blue-13"),
	TEXT_LIGHT_BLUE_14("text-light-blue-14"),

	TEXT_CYAN("text-cyan"),
	TEXT_CYAN_1("text-cyan-1"),
	TEXT_CYAN_2("text-cyan-2"),
	TEXT_CYAN_3("text-cyan-3"),
	TEXT_CYAN_4("text-cyan-4"),
	TEXT_CYAN_5("text-cyan-5"),
	TEXT_CYAN_6("text-cyan-6"),
	TEXT_CYAN_7("text-cyan-7"),
	TEXT_CYAN_8("text-cyan-8"),
	TEXT_CYAN_9("text-cyan-9"),
	TEXT_CYAN_10("text-cyan-10"),
	TEXT_CYAN_11("text-cyan-11"),
	TEXT_CYAN_12("text-cyan-12"),
	TEXT_CYAN_13("text-cyan-13"),
	TEXT_CYAN_14("text-cyan-14"),

	TEXT_TEAL("text-teal"),
	TEXT_TEAL_1("text-teal-1"),
	TEXT_TEAL_2("text-teal-2"),
	TEXT_TEAL_3("text-teal-3"),
	TEXT_TEAL_4("text-teal-4"),
	TEXT_TEAL_5("text-teal-5"),
	TEXT_TEAL_6("text-teal-6"),
	TEXT_TEAL_7("text-teal-7"),
	TEXT_TEAL_8("text-teal-8"),
	TEXT_TEAL_9("text-teal-9"),
	TEXT_TEAL_10("text-teal-10"),
	TEXT_TEAL_11("text-teal-11"),
	TEXT_TEAL_12("text-teal-12"),
	TEXT_TEAL_13("text-teal-13"),
	TEXT_TEAL_14("text-teal-14"),

	TEXT_GREEN("text-green"),
	TEXT_GREEN_1("text-green-1"),
	TEXT_GREEN_2("text-green-2"),
	TEXT_GREEN_3("text-green-3"),
	TEXT_GREEN_4("text-green-4"),
	TEXT_GREEN_5("text-green-5"),
	TEXT_GREEN_6("text-green-6"),
	TEXT_GREEN_7("text-green-7"),
	TEXT_GREEN_8("text-green-8"),
	TEXT_GREEN_9("text-green-9"),
	TEXT_GREEN_10("text-green-10"),
	TEXT_GREEN_11("text-green-11"),
	TEXT_GREEN_12("text-green-12"),
	TEXT_GREEN_13("text-green-13"),
	TEXT_GREEN_14("text-green-14"),

	TEXT_LIGHT_GREEN("text-light-green"),
	TEXT_LIGHT_GREEN_1("text-light-green-1"),
	TEXT_LIGHT_GREEN_2("text-light-green-2"),
	TEXT_LIGHT_GREEN_3("text-light-green-3"),
	TEXT_LIGHT_GREEN_4("text-light-green-4"),
	TEXT_LIGHT_GREEN_5("text-light-green-5"),
	TEXT_LIGHT_GREEN_6("text-light-green-6"),
	TEXT_LIGHT_GREEN_7("text-light-green-7"),
	TEXT_LIGHT_GREEN_8("text-light-green-8"),
	TEXT_LIGHT_GREEN_9("text-light-green-9"),
	TEXT_LIGHT_GREEN_10("text-light-green-10"),
	TEXT_LIGHT_GREEN_11("text-light-green-11"),
	TEXT_LIGHT_GREEN_12("text-light-green-12"),
	TEXT_LIGHT_GREEN_13("text-light-green-13"),
	TEXT_LIGHT_GREEN_14("text-light-green-14"),

	TEXT_LIME("text-lime"),
	TEXT_LIME_1("text-lime-1"),
	TEXT_LIME_2("text-lime-2"),
	TEXT_LIME_3("text-lime-3"),
	TEXT_LIME_4("text-lime-4"),
	TEXT_LIME_5("text-lime-5"),
	TEXT_LIME_6("text-lime-6"),
	TEXT_LIME_7("text-lime-7"),
	TEXT_LIME_8("text-lime-8"),
	TEXT_LIME_9("text-lime-9"),
	TEXT_LIME_10("text-lime-10"),
	TEXT_LIME_11("text-lime-11"),
	TEXT_LIME_12("text-lime-12"),
	TEXT_LIME_13("text-lime-13"),
	TEXT_LIME_14("text-lime-14"),

	TEXT_YELLOW("text-yellow"),
	TEXT_YELLOW_1("text-yellow-1"),
	TEXT_YELLOW_2("text-yellow-2"),
	TEXT_YELLOW_3("text-yellow-3"),
	TEXT_YELLOW_4("text-yellow-4"),
	TEXT_YELLOW_5("text-yellow-5"),
	TEXT_YELLOW_6("text-yellow-6"),
	TEXT_YELLOW_7("text-yellow-7"),
	TEXT_YELLOW_8("text-yellow-8"),
	TEXT_YELLOW_9("text-yellow-9"),
	TEXT_YELLOW_10("text-yellow-10"),
	TEXT_YELLOW_11("text-yellow-11"),
	TEXT_YELLOW_12("text-yellow-12"),
	TEXT_YELLOW_13("text-yellow-13"),
	TEXT_YELLOW_14("text-yellow-14"),

	TEXT_AMBER("text-amber"),
	TEXT_AMBER_1("text-amber-1"),
	TEXT_AMBER_2("text-amber-2"),
	TEXT_AMBER_3("text-amber-3"),
	TEXT_AMBER_4("text-amber-4"),
	TEXT_AMBER_5("text-amber-5"),
	TEXT_AMBER_6("text-amber-6"),
	TEXT_AMBER_7("text-amber-7"),
	TEXT_AMBER_8("text-amber-8"),
	TEXT_AMBER_9("text-amber-9"),
	TEXT_AMBER_10("text-amber-10"),
	TEXT_AMBER_11("text-amber-11"),
	TEXT_AMBER_12("text-amber-12"),
	TEXT_AMBER_13("text-amber-13"),
	TEXT_AMBER_14("text-amber-14"),

	TEXT_ORANGE("text-orange"),
	TEXT_ORANGE_1("text-orange-1"),
	TEXT_ORANGE_2("text-orange-2"),
	TEXT_ORANGE_3("text-orange-3"),
	TEXT_ORANGE_4("text-orange-4"),
	TEXT_ORANGE_5("text-orange-5"),
	TEXT_ORANGE_6("text-orange-6"),
	TEXT_ORANGE_7("text-orange-7"),
	TEXT_ORANGE_8("text-orange-8"),
	TEXT_ORANGE_9("text-orange-9"),
	TEXT_ORANGE_10("text-orange-10"),
	TEXT_ORANGE_11("text-orange-11"),
	TEXT_ORANGE_12("text-orange-12"),
	TEXT_ORANGE_13("text-orange-13"),
	TEXT_ORANGE_14("text-orange-14"),

	TEXT_DEEP_ORANGE("text-deep-orange"),
	TEXT_DEEP_ORANGE_1("text-deep-orange-1"),
	TEXT_DEEP_ORANGE_2("text-deep-orange-2"),
	TEXT_DEEP_ORANGE_3("text-deep-orange-3"),
	TEXT_DEEP_ORANGE_4("text-deep-orange-4"),
	TEXT_DEEP_ORANGE_5("text-deep-orange-5"),
	TEXT_DEEP_ORANGE_6("text-deep-orange-6"),
	TEXT_DEEP_ORANGE_7("text-deep-orange-7"),
	TEXT_DEEP_ORANGE_8("text-deep-orange-8"),
	TEXT_DEEP_ORANGE_9("text-deep-orange-9"),
	TEXT_DEEP_ORANGE_10("text-deep-orange-10"),
	TEXT_DEEP_ORANGE_11("text-deep-orange-11"),
	TEXT_DEEP_ORANGE_12("text-deep-orange-12"),
	TEXT_DEEP_ORANGE_13("text-deep-orange-13"),
	TEXT_DEEP_ORANGE_14("text-deep-orange-14"),

	TEXT_BROWN("text-brown"),
	TEXT_BROWN_1("text-brown-1"),
	TEXT_BROWN_2("text-brown-2"),
	TEXT_BROWN_3("text-brown-3"),
	TEXT_BROWN_4("text-brown-4"),
	TEXT_BROWN_5("text-brown-5"),
	TEXT_BROWN_6("text-brown-6"),
	TEXT_BROWN_7("text-brown-7"),
	TEXT_BROWN_8("text-brown-8"),
	TEXT_BROWN_9("text-brown-9"),
	TEXT_BROWN_10("text-brown-10"),
	TEXT_BROWN_11("text-brown-11"),
	TEXT_BROWN_12("text-brown-12"),
	TEXT_BROWN_13("text-brown-13"),
	TEXT_BROWN_14("text-brown-14"),

	TEXT_GREY("text-grey"),
	TEXT_GREY_1("text-grey-1"),
	TEXT_GREY_2("text-grey-2"),
	TEXT_GREY_3("text-grey-3"),
	TEXT_GREY_4("text-grey-4"),
	TEXT_GREY_5("text-grey-5"),
	TEXT_GREY_6("text-grey-6"),
	TEXT_GREY_7("text-grey-7"),
	TEXT_GREY_8("text-grey-8"),
	TEXT_GREY_9("text-grey-9"),
	TEXT_GREY_10("text-grey-10"),
	TEXT_GREY_11("text-grey-11"),
	TEXT_GREY_12("text-grey-12"),
	TEXT_GREY_13("text-grey-13"),
	TEXT_GREY_14("text-grey-14"),

	TEXT_BLUE_GREY("text-blue-grey"),
	TEXT_BLUE_GREY_1("text-blue-grey-1"),
	TEXT_BLUE_GREY_2("text-blue-grey-2"),
	TEXT_BLUE_GREY_3("text-blue-grey-3"),
	TEXT_BLUE_GREY_4("text-blue-grey-4"),
	TEXT_BLUE_GREY_5("text-blue-grey-5"),
	TEXT_BLUE_GREY_6("text-blue-grey-6"),
	TEXT_BLUE_GREY_7("text-blue-grey-7"),
	TEXT_BLUE_GREY_8("text-blue-grey-8"),
	TEXT_BLUE_GREY_9("text-blue-grey-9"),
	TEXT_BLUE_GREY_10("text-blue-grey-10"),
	TEXT_BLUE_GREY_11("text-blue-grey-11"),
	TEXT_BLUE_GREY_12("text-blue-grey-12"),
	TEXT_BLUE_GREY_13("text-blue-grey-13"),
	TEXT_BLUE_GREY_14("text-blue-grey-14"),

	BG_PRIMARY("bg-primary"),
	BG_SECONDARY("bg-secondary"),
	BG_ACCENT("bg-accent"),
	BG_POSITIVE("bg-positive"),
	BG_NEGATIVE("bg-negative"),
	BG_INFO("bg-info"),
	BG_WARNING("bg-warning"),

	BG_TRANSPARENT("bg-transparent"),
	BG_WHITE("bg-white"),
	BG_BLACK("bg-black"),

	BG_RED("bg-red"),
	BG_RED_1("bg-red-1"),
	BG_RED_2("bg-red-2"),
	BG_RED_3("bg-red-3"),
	BG_RED_4("bg-red-4"),
	BG_RED_5("bg-red-5"),
	BG_RED_6("bg-red-6"),
	BG_RED_7("bg-red-7"),
	BG_RED_8("bg-red-8"),
	BG_RED_9("bg-red-9"),
	BG_RED_10("bg-red-10"),
	BG_RED_11("bg-red-11"),
	BG_RED_12("bg-red-12"),
	BG_RED_13("bg-red-13"),
	BG_RED_14("bg-red-14"),

	BG_PINK("bg-pink"),
	BG_PINK_1("bg-pink-1"),
	BG_PINK_2("bg-pink-2"),
	BG_PINK_3("bg-pink-3"),
	BG_PINK_4("bg-pink-4"),
	BG_PINK_5("bg-pink-5"),
	BG_PINK_6("bg-pink-6"),
	BG_PINK_7("bg-pink-7"),
	BG_PINK_8("bg-pink-8"),
	BG_PINK_9("bg-pink-9"),
	BG_PINK_10("bg-pink-10"),
	BG_PINK_11("bg-pink-11"),
	BG_PINK_12("bg-pink-12"),
	BG_PINK_13("bg-pink-13"),
	BG_PINK_14("bg-pink-14"),

	BG_PURPLE("bg-purple"),
	BG_PURPLE_1("bg-purple-1"),
	BG_PURPLE_2("bg-purple-2"),
	BG_PURPLE_3("bg-purple-3"),
	BG_PURPLE_4("bg-purple-4"),
	BG_PURPLE_5("bg-purple-5"),
	BG_PURPLE_6("bg-purple-6"),
	BG_PURPLE_7("bg-purple-7"),
	BG_PURPLE_8("bg-purple-8"),
	BG_PURPLE_9("bg-purple-9"),
	BG_PURPLE_10("bg-purple-10"),
	BG_PURPLE_11("bg-purple-11"),
	BG_PURPLE_12("bg-purple-12"),
	BG_PURPLE_13("bg-purple-13"),
	BG_PURPLE_14("bg-purple-14"),

	BG_DEEP_PURPLE("bg-deep-purple"),
	BG_DEEP_PURPLE_1("bg-deep-purple-1"),
	BG_DEEP_PURPLE_2("bg-deep-purple-2"),
	BG_DEEP_PURPLE_3("bg-deep-purple-3"),
	BG_DEEP_PURPLE_4("bg-deep-purple-4"),
	BG_DEEP_PURPLE_5("bg-deep-purple-5"),
	BG_DEEP_PURPLE_6("bg-deep-purple-6"),
	BG_DEEP_PURPLE_7("bg-deep-purple-7"),
	BG_DEEP_PURPLE_8("bg-deep-purple-8"),
	BG_DEEP_PURPLE_9("bg-deep-purple-9"),
	BG_DEEP_PURPLE_10("bg-deep-purple-10"),
	BG_DEEP_PURPLE_11("bg-deep-purple-11"),
	BG_DEEP_PURPLE_12("bg-deep-purple-12"),
	BG_DEEP_PURPLE_13("bg-deep-purple-13"),
	BG_DEEP_PURPLE_14("bg-deep-purple-14"),

	BG_INDIGO("bg-indigo"),
	BG_INDIGO_1("bg-indigo-1"),
	BG_INDIGO_2("bg-indigo-2"),
	BG_INDIGO_3("bg-indigo-3"),
	BG_INDIGO_4("bg-indigo-4"),
	BG_INDIGO_5("bg-indigo-5"),
	BG_INDIGO_6("bg-indigo-6"),
	BG_INDIGO_7("bg-indigo-7"),
	BG_INDIGO_8("bg-indigo-8"),
	BG_INDIGO_9("bg-indigo-9"),
	BG_INDIGO_10("bg-indigo-10"),
	BG_INDIGO_11("bg-indigo-11"),
	BG_INDIGO_12("bg-indigo-12"),
	BG_INDIGO_13("bg-indigo-13"),
	BG_INDIGO_14("bg-indigo-14"),

	BG_BLUE("bg-blue"),
	BG_BLUE_1("bg-blue-1"),
	BG_BLUE_2("bg-blue-2"),
	BG_BLUE_3("bg-blue-3"),
	BG_BLUE_4("bg-blue-4"),
	BG_BLUE_5("bg-blue-5"),
	BG_BLUE_6("bg-blue-6"),
	BG_BLUE_7("bg-blue-7"),
	BG_BLUE_8("bg-blue-8"),
	BG_BLUE_9("bg-blue-9"),
	BG_BLUE_10("bg-blue-10"),
	BG_BLUE_11("bg-blue-11"),
	BG_BLUE_12("bg-blue-12"),
	BG_BLUE_13("bg-blue-13"),
	BG_BLUE_14("bg-blue-14"),

	BG_LIGHT_BLUE("bg-light-blue"),
	BG_LIGHT_BLUE_1("bg-light-blue-1"),
	BG_LIGHT_BLUE_2("bg-light-blue-2"),
	BG_LIGHT_BLUE_3("bg-light-blue-3"),
	BG_LIGHT_BLUE_4("bg-light-blue-4"),
	BG_LIGHT_BLUE_5("bg-light-blue-5"),
	BG_LIGHT_BLUE_6("bg-light-blue-6"),
	BG_LIGHT_BLUE_7("bg-light-blue-7"),
	BG_LIGHT_BLUE_8("bg-light-blue-8"),
	BG_LIGHT_BLUE_9("bg-light-blue-9"),
	BG_LIGHT_BLUE_10("bg-light-blue-10"),
	BG_LIGHT_BLUE_11("bg-light-blue-11"),
	BG_LIGHT_BLUE_12("bg-light-blue-12"),
	BG_LIGHT_BLUE_13("bg-light-blue-13"),
	BG_LIGHT_BLUE_14("bg-light-blue-14"),

	BG_CYAN("bg-cyan"),
	BG_CYAN_1("bg-cyan-1"),
	BG_CYAN_2("bg-cyan-2"),
	BG_CYAN_3("bg-cyan-3"),
	BG_CYAN_4("bg-cyan-4"),
	BG_CYAN_5("bg-cyan-5"),
	BG_CYAN_6("bg-cyan-6"),
	BG_CYAN_7("bg-cyan-7"),
	BG_CYAN_8("bg-cyan-8"),
	BG_CYAN_9("bg-cyan-9"),
	BG_CYAN_10("bg-cyan-10"),
	BG_CYAN_11("bg-cyan-11"),
	BG_CYAN_12("bg-cyan-12"),
	BG_CYAN_13("bg-cyan-13"),
	BG_CYAN_14("bg-cyan-14"),

	BG_TEAL("bg-teal"),
	BG_TEAL_1("bg-teal-1"),
	BG_TEAL_2("bg-teal-2"),
	BG_TEAL_3("bg-teal-3"),
	BG_TEAL_4("bg-teal-4"),
	BG_TEAL_5("bg-teal-5"),
	BG_TEAL_6("bg-teal-6"),
	BG_TEAL_7("bg-teal-7"),
	BG_TEAL_8("bg-teal-8"),
	BG_TEAL_9("bg-teal-9"),
	BG_TEAL_10("bg-teal-10"),
	BG_TEAL_11("bg-teal-11"),
	BG_TEAL_12("bg-teal-12"),
	BG_TEAL_13("bg-teal-13"),
	BG_TEAL_14("bg-teal-14"),

	BG_GREEN("bg-green"),
	BG_GREEN_1("bg-green-1"),
	BG_GREEN_2("bg-green-2"),
	BG_GREEN_3("bg-green-3"),
	BG_GREEN_4("bg-green-4"),
	BG_GREEN_5("bg-green-5"),
	BG_GREEN_6("bg-green-6"),
	BG_GREEN_7("bg-green-7"),
	BG_GREEN_8("bg-green-8"),
	BG_GREEN_9("bg-green-9"),
	BG_GREEN_10("bg-green-10"),
	BG_GREEN_11("bg-green-11"),
	BG_GREEN_12("bg-green-12"),
	BG_GREEN_13("bg-green-13"),
	BG_GREEN_14("bg-green-14"),

	BG_LIGHT_GREEN("bg-light-green"),
	BG_LIGHT_GREEN_1("bg-light-green-1"),
	BG_LIGHT_GREEN_2("bg-light-green-2"),
	BG_LIGHT_GREEN_3("bg-light-green-3"),
	BG_LIGHT_GREEN_4("bg-light-green-4"),
	BG_LIGHT_GREEN_5("bg-light-green-5"),
	BG_LIGHT_GREEN_6("bg-light-green-6"),
	BG_LIGHT_GREEN_7("bg-light-green-7"),
	BG_LIGHT_GREEN_8("bg-light-green-8"),
	BG_LIGHT_GREEN_9("bg-light-green-9"),
	BG_LIGHT_GREEN_10("bg-light-green-10"),
	BG_LIGHT_GREEN_11("bg-light-green-11"),
	BG_LIGHT_GREEN_12("bg-light-green-12"),
	BG_LIGHT_GREEN_13("bg-light-green-13"),
	BG_LIGHT_GREEN_14("bg-light-green-14"),

	BG_LIME("bg-lime"),
	BG_LIME_1("bg-lime-1"),
	BG_LIME_2("bg-lime-2"),
	BG_LIME_3("bg-lime-3"),
	BG_LIME_4("bg-lime-4"),
	BG_LIME_5("bg-lime-5"),
	BG_LIME_6("bg-lime-6"),
	BG_LIME_7("bg-lime-7"),
	BG_LIME_8("bg-lime-8"),
	BG_LIME_9("bg-lime-9"),
	BG_LIME_10("bg-lime-10"),
	BG_LIME_11("bg-lime-11"),
	BG_LIME_12("bg-lime-12"),
	BG_LIME_13("bg-lime-13"),
	BG_LIME_14("bg-lime-14"),

	BG_YELLOW("bg-yellow"),
	BG_YELLOW_1("bg-yellow-1"),
	BG_YELLOW_2("bg-yellow-2"),
	BG_YELLOW_3("bg-yellow-3"),
	BG_YELLOW_4("bg-yellow-4"),
	BG_YELLOW_5("bg-yellow-5"),
	BG_YELLOW_6("bg-yellow-6"),
	BG_YELLOW_7("bg-yellow-7"),
	BG_YELLOW_8("bg-yellow-8"),
	BG_YELLOW_9("bg-yellow-9"),
	BG_YELLOW_10("bg-yellow-10"),
	BG_YELLOW_11("bg-yellow-11"),
	BG_YELLOW_12("bg-yellow-12"),
	BG_YELLOW_13("bg-yellow-13"),
	BG_YELLOW_14("bg-yellow-14"),

	BG_AMBER("bg-amber"),
	BG_AMBER_1("bg-amber-1"),
	BG_AMBER_2("bg-amber-2"),
	BG_AMBER_3("bg-amber-3"),
	BG_AMBER_4("bg-amber-4"),
	BG_AMBER_5("bg-amber-5"),
	BG_AMBER_6("bg-amber-6"),
	BG_AMBER_7("bg-amber-7"),
	BG_AMBER_8("bg-amber-8"),
	BG_AMBER_9("bg-amber-9"),
	BG_AMBER_10("bg-amber-10"),
	BG_AMBER_11("bg-amber-11"),
	BG_AMBER_12("bg-amber-12"),
	BG_AMBER_13("bg-amber-13"),
	BG_AMBER_14("bg-amber-14"),

	BG_ORANGE("bg-orange"),
	BG_ORANGE_1("bg-orange-1"),
	BG_ORANGE_2("bg-orange-2"),
	BG_ORANGE_3("bg-orange-3"),
	BG_ORANGE_4("bg-orange-4"),
	BG_ORANGE_5("bg-orange-5"),
	BG_ORANGE_6("bg-orange-6"),
	BG_ORANGE_7("bg-orange-7"),
	BG_ORANGE_8("bg-orange-8"),
	BG_ORANGE_9("bg-orange-9"),
	BG_ORANGE_10("bg-orange-10"),
	BG_ORANGE_11("bg-orange-11"),
	BG_ORANGE_12("bg-orange-12"),
	BG_ORANGE_13("bg-orange-13"),
	BG_ORANGE_14("bg-orange-14"),

	BG_DEEP_ORANGE("bg-deep-orange"),
	BG_DEEP_ORANGE_1("bg-deep-orange-1"),
	BG_DEEP_ORANGE_2("bg-deep-orange-2"),
	BG_DEEP_ORANGE_3("bg-deep-orange-3"),
	BG_DEEP_ORANGE_4("bg-deep-orange-4"),
	BG_DEEP_ORANGE_5("bg-deep-orange-5"),
	BG_DEEP_ORANGE_6("bg-deep-orange-6"),
	BG_DEEP_ORANGE_7("bg-deep-orange-7"),
	BG_DEEP_ORANGE_8("bg-deep-orange-8"),
	BG_DEEP_ORANGE_9("bg-deep-orange-9"),
	BG_DEEP_ORANGE_10("bg-deep-orange-10"),
	BG_DEEP_ORANGE_11("bg-deep-orange-11"),
	BG_DEEP_ORANGE_12("bg-deep-orange-12"),
	BG_DEEP_ORANGE_13("bg-deep-orange-13"),
	BG_DEEP_ORANGE_14("bg-deep-orange-14"),

	BG_BROWN("bg-brown"),
	BG_BROWN_1("bg-brown-1"),
	BG_BROWN_2("bg-brown-2"),
	BG_BROWN_3("bg-brown-3"),
	BG_BROWN_4("bg-brown-4"),
	BG_BROWN_5("bg-brown-5"),
	BG_BROWN_6("bg-brown-6"),
	BG_BROWN_7("bg-brown-7"),
	BG_BROWN_8("bg-brown-8"),
	BG_BROWN_9("bg-brown-9"),
	BG_BROWN_10("bg-brown-10"),
	BG_BROWN_11("bg-brown-11"),
	BG_BROWN_12("bg-brown-12"),
	BG_BROWN_13("bg-brown-13"),
	BG_BROWN_14("bg-brown-14"),

	BG_GREY("bg-grey"),
	BG_GREY_1("bg-grey-1"),
	BG_GREY_2("bg-grey-2"),
	BG_GREY_3("bg-grey-3"),
	BG_GREY_4("bg-grey-4"),
	BG_GREY_5("bg-grey-5"),
	BG_GREY_6("bg-grey-6"),
	BG_GREY_7("bg-grey-7"),
	BG_GREY_8("bg-grey-8"),
	BG_GREY_9("bg-grey-9"),
	BG_GREY_10("bg-grey-10"),
	BG_GREY_11("bg-grey-11"),
	BG_GREY_12("bg-grey-12"),
	BG_GREY_13("bg-grey-13"),
	BG_GREY_14("bg-grey-14"),

	BG_BLUE_GREY("bg-blue-grey"),
	BG_BLUE_GREY_1("bg-blue-grey-1"),
	BG_BLUE_GREY_2("bg-blue-grey-2"),
	BG_BLUE_GREY_3("bg-blue-grey-3"),
	BG_BLUE_GREY_4("bg-blue-grey-4"),
	BG_BLUE_GREY_5("bg-blue-grey-5"),
	BG_BLUE_GREY_6("bg-blue-grey-6"),
	BG_BLUE_GREY_7("bg-blue-grey-7"),
	BG_BLUE_GREY_8("bg-blue-grey-8"),
	BG_BLUE_GREY_9("bg-blue-grey-9"),
	BG_BLUE_GREY_10("bg-blue-grey-10"),
	BG_BLUE_GREY_11("bg-blue-grey-11"),
	BG_BLUE_GREY_12("bg-blue-grey-12"),
	BG_BLUE_GREY_13("bg-blue-grey-13"),
	BG_BLUE_GREY_14("bg-blue-grey-14"),

	/*
		http://quasar-framework.org/components/typography.html
	 */
	TEXT_H1("text-h1"),
	TEXT_H2("text-h2"),
	TEXT_H3("text-h3"),
	TEXT_H4("text-h4"),
	TEXT_H5("text-h5"),
	TEXT_H6("text-h6"),
	TEXT_SUBTITLE1("text-subtitle1"),
	TEXT_SUBTITLE2("text-subtitle2"),
	TEXT_BODY2("text-body2"),
	TEXT_BODY1("text-body1"),
	TEXT_CAPTION("text-caption"),
	TEXT_OVERLINE("text-overline"),

	TEXT_WEIGHT_THIN("text-weight-thin"),
	TEXT_WEIGHT_LIGHT("text-weight-light"),
	TEXT_WEIGHT_REGULAR("text-weight-regular"),
	TEXT_WEIGHT_MEDIUM("text-weight-medium"),
	TEXT_WEIGHT_BOLD("text-weight-bold"),
	TEXT_WEIGHT_BOLDER("text-weight-bolder"),

	TEXT_BOLD("text-bold"),
	TEXT_ITALIC("text-italic"),

	TEXT_LEFT("text-left"),
	TEXT_RIGHT("text-right"),
	TEXT_CENTER("text-center"),
	TEXT_JUSTIFY("text-justify"),

	TEXT_NO_WRAP("text-no-wrap"),

	TEXT_UPPERCASE("text-uppercase"),
	TEXT_LOWERCASE("text-lowercase"),
	TEXT_CAPITALIZE("text-capitalize"),

	/*
		http://quasar-framework.org/components/spacing.html

		xs:  4px
		sm:  8px
		md: 16px
		lg: 24px
		xl: 48px
	 */
	PADDING_TOP_NONE("q-pt-none"),
	PADDING_TOP_XS("q-pt-xs"),
	PADDING_TOP_SM("q-pt-sm"),
	PADDING_TOP_MD("q-pt-md"),
	PADDING_TOP_LG("q-pt-lg"),
	PADDING_TOP_XL("q-pt-xl"),

	PADDING_RIGHT_NONE("q-pr-none"),
	PADDING_RIGHT_XS("q-pr-xs"),
	PADDING_RIGHT_SM("q-pr-sm"),
	PADDING_RIGHT_MD("q-pr-md"),
	PADDING_RIGHT_LG("q-pr-lg"),
	PADDING_RIGHT_XL("q-pr-xl"),

	PADDING_BOTTOM_NONE("q-pb-none"),
	PADDING_BOTTOM_XS("q-pb-xs"),
	PADDING_BOTTOM_SM("q-pb-sm"),
	PADDING_BOTTOM_MD("q-pb-md"),
	PADDING_BOTTOM_LG("q-pb-lg"),
	PADDING_BOTTOM_XL("q-pb-xl"),

	PADDING_LEFT_NONE("q-pl-none"),
	PADDING_LEFT_XS("q-pl-xs"),
	PADDING_LEFT_SM("q-pl-sm"),
	PADDING_LEFT_MD("q-pl-md"),
	PADDING_LEFT_LG("q-pl-lg"),
	PADDING_LEFT_XL("q-pl-xl"),

	PADDING_ALL_NONE("q-pa-none"),
	PADDING_ALL_XS("q-pa-xs"),
	PADDING_ALL_SM("q-pa-sm"),
	PADDING_ALL_MD("q-pa-md"),
	PADDING_ALL_LG("q-pa-lg"),
	PADDING_ALL_XL("q-pa-xl"),

	PADDING_HORIZONTAL_NONE("q-px-none"),
	PADDING_HORIZONTAL_XS("q-px-xs"),
	PADDING_HORIZONTAL_SM("q-px-sm"),
	PADDING_HORIZONTAL_MD("q-px-md"),
	PADDING_HORIZONTAL_LG("q-px-lg"),
	PADDING_HORIZONTAL_XL("q-px-xl"),

	PADDING_VERTICAL_NONE("q-py-none"),
	PADDING_VERTICAL_XS("q-py-xs"),
	PADDING_VERTICAL_SM("q-py-sm"),
	PADDING_VERTICAL_MD("q-py-md"),
	PADDING_VERTICAL_LG("q-py-lg"),
	PADDING_VERTICAL_XL("q-py-xl"),

	MARGIN_TOP_NONE("q-mt-none"),
	MARGIN_TOP_XS("q-mt-xs"),
	MARGIN_TOP_SM("q-mt-sm"),
	MARGIN_TOP_MD("q-mt-md"),
	MARGIN_TOP_LG("q-mt-lg"),
	MARGIN_TOP_XL("q-mt-xl"),

	MARGIN_RIGHT_NONE("q-mr-none"),
	MARGIN_RIGHT_XS("q-mr-xs"),
	MARGIN_RIGHT_SM("q-mr-sm"),
	MARGIN_RIGHT_MD("q-mr-md"),
	MARGIN_RIGHT_LG("q-mr-lg"),
	MARGIN_RIGHT_XL("q-mr-xl"),

	MARGIN_BOTTOM_NONE("q-mb-none"),
	MARGIN_BOTTOM_XS("q-mb-xs"),
	MARGIN_BOTTOM_SM("q-mb-sm"),
	MARGIN_BOTTOM_MD("q-mb-md"),
	MARGIN_BOTTOM_LG("q-mb-lg"),
	MARGIN_BOTTOM_XL("q-mb-xl"),

	MARGIN_LEFT_NONE("q-ml-none"),
	MARGIN_LEFT_XS("q-ml-xs"),
	MARGIN_LEFT_SM("q-ml-sm"),
	MARGIN_LEFT_MD("q-ml-md"),
	MARGIN_LEFT_LG("q-ml-lg"),
	MARGIN_LEFT_XL("q-ml-xl"),

	MARGIN_ALL_NONE("q-ma-none"),
	MARGIN_ALL_XS("q-ma-xs"),
	MARGIN_ALL_SM("q-ma-sm"),
	MARGIN_ALL_MD("q-ma-md"),
	MARGIN_ALL_LG("q-ma-lg"),
	MARGIN_ALL_XL("q-ma-xl"),

	MARGIN_HORIZONTAL_NONE("q-mx-none"),
	MARGIN_HORIZONTAL_XS("q-mx-xs"),
	MARGIN_HORIZONTAL_SM("q-mx-sm"),
	MARGIN_HORIZONTAL_MD("q-mx-md"),
	MARGIN_HORIZONTAL_LG("q-mx-lg"),
	MARGIN_HORIZONTAL_XL("q-mx-xl"),

	MARGIN_VERTICAL_NONE("q-my-none"),
	MARGIN_VERTICAL_XS("q-my-xs"),
	MARGIN_VERTICAL_SM("q-my-sm"),
	MARGIN_VERTICAL_MD("q-my-md"),
	MARGIN_VERTICAL_LG("q-my-lg"),
	MARGIN_VERTICAL_XL("q-my-xl"),

	/*
		http://quasar-framework.org/components/flex-css.html
	 */
	ROW("row"),
	ROW_INLINE("row inline"),
	COLUMN("column"),
	COLUMN_INLINE("column inline"),
	ROW_REVERSE("row reverse"),
	COLUMN_REVERSE("column reverse"),

	WRAP("wrap"),
	NO_WRAP("no-wrap"),
	REVERSE_WRAP("reverse-wrap"),

	JUSTIFY_START("justify-start"),
	JUSTIFY_END("justify-end"),
	JUSTIFY_CENTER("justify-center"),
	JUSTIFY_BETWEEN("justify-between"),
	JUSTIFY_AROUND("justify-around"),
	JUSTIFY_EVENLY("justify-evenly"),                                                           // TEST

	ITEMS_START("items-start"),
	ITEMS_END("items-end"),
	ITEMS_CENTER("items-center"),
	ITEMS_STRETCH("items-stretch"),
	ITEMS_BASELINE("items-baseline"),

	CONTENT_START("content-start"),
	CONTENT_END("content-end"),
	CONTENT_CENTER("content-center"),
	CONTENT_STRETCH("content-stretch"),
	CONTENT_BETWEEN("content-between"),
	CONTENT_AROUND("content-around"),

	COL("col"),
	COL_SHRINK("col-shrink"),

	COL_AUTO("col-auto"),
	COL_1("col-1"),
	COL_2("col-2"),
	COL_3("col-3"),
	COL_4("col-4"),
	COL_5("col-5"),
	COL_6("col-6"),
	COL_7("col-7"),
	COL_8("col-8"),
	COL_9("col-9"),
	COL_10("col-10"),
	COL_11("col-11"),
	COL_12("col-12"),

	COL_XS_AUTO("col-xs-auto"),
	COL_XS_1("col-xs-1"),
	COL_XS_2("col-xs-2"),
	COL_XS_3("col-xs-3"),
	COL_XS_4("col-xs-4"),
	COL_XS_5("col-xs-5"),
	COL_XS_6("col-xs-6"),
	COL_XS_7("col-xs-7"),
	COL_XS_8("col-xs-8"),
	COL_XS_9("col-xs-9"),
	COL_XS_10("col-xs-10"),
	COL_XS_11("col-xs-11"),
	COL_XS_12("col-xs-12"),

	COL_SM_AUTO("col-sm-auto"),
	COL_SM_1("col-sm-1"),
	COL_SM_2("col-sm-2"),
	COL_SM_3("col-sm-3"),
	COL_SM_4("col-sm-4"),
	COL_SM_5("col-sm-5"),
	COL_SM_6("col-sm-6"),
	COL_SM_7("col-sm-7"),
	COL_SM_8("col-sm-8"),
	COL_SM_9("col-sm-9"),
	COL_SM_10("col-sm-10"),
	COL_SM_11("col-sm-11"),
	COL_SM_12("col-sm-12"),

	COL_MD_AUTO("col-md-auto"),
	COL_MD_1("col-md-1"),
	COL_MD_2("col-md-2"),
	COL_MD_3("col-md-3"),
	COL_MD_4("col-md-4"),
	COL_MD_5("col-md-5"),
	COL_MD_6("col-md-6"),
	COL_MD_7("col-md-7"),
	COL_MD_8("col-md-8"),
	COL_MD_9("col-md-9"),
	COL_MD_10("col-md-10"),
	COL_MD_11("col-md-11"),
	COL_MD_12("col-md-12"),

	COL_LG_AUTO("col-lg-auto"),
	COL_LG_1("col-lg-1"),
	COL_LG_2("col-lg-2"),
	COL_LG_3("col-lg-3"),
	COL_LG_4("col-lg-4"),
	COL_LG_5("col-lg-5"),
	COL_LG_6("col-lg-6"),
	COL_LG_7("col-lg-7"),
	COL_LG_8("col-lg-8"),
	COL_LG_9("col-lg-9"),
	COL_LG_10("col-lg-10"),
	COL_LG_11("col-lg-11"),
	COL_LG_12("col-lg-12"),

	COL_XL_AUTO("col-xl-auto"),
	COL_XL_1("col-xl-1"),
	COL_XL_2("col-xl-2"),
	COL_XL_3("col-xl-3"),
	COL_XL_4("col-xl-4"),
	COL_XL_5("col-xl-5"),
	COL_XL_6("col-xl-6"),
	COL_XL_7("col-xl-7"),
	COL_XL_8("col-xl-8"),
	COL_XL_9("col-xl-9"),
	COL_XL_10("col-xl-10"),
	COL_XL_11("col-xl-11"),
	COL_XL_12("col-xl-12"),

	OFFSET_1("offset-1"),
	OFFSET_2("offset-2"),
	OFFSET_3("offset-3"),
	OFFSET_4("offset-4"),
	OFFSET_5("offset-5"),
	OFFSET_6("offset-6"),
	OFFSET_7("offset-7"),
	OFFSET_8("offset-8"),
	OFFSET_9("offset-9"),
	OFFSET_10("offset-10"),
	OFFSET_11("offset-11"),
	OFFSET_12("offset-12"),

	OFFSET_XS_AUTO("offset-xs-auto"),
	OFFSET_XS_1("offset-xs-1"),
	OFFSET_XS_2("offset-xs-2"),
	OFFSET_XS_3("offset-xs-3"),
	OFFSET_XS_4("offset-xs-4"),
	OFFSET_XS_5("offset-xs-5"),
	OFFSET_XS_6("offset-xs-6"),
	OFFSET_XS_7("offset-xs-7"),
	OFFSET_XS_8("offset-xs-8"),
	OFFSET_XS_9("offset-xs-9"),
	OFFSET_XS_10("offset-xs-10"),
	OFFSET_XS_11("offset-xs-11"),
	OFFSET_XS_12("offset-xs-12"),

	OFFSET_SM_AUTO("offset-sm-auto"),
	OFFSET_SM_1("offset-sm-1"),
	OFFSET_SM_2("offset-sm-2"),
	OFFSET_SM_3("offset-sm-3"),
	OFFSET_SM_4("offset-sm-4"),
	OFFSET_SM_5("offset-sm-5"),
	OFFSET_SM_6("offset-sm-6"),
	OFFSET_SM_7("offset-sm-7"),
	OFFSET_SM_8("offset-sm-8"),
	OFFSET_SM_9("offset-sm-9"),
	OFFSET_SM_10("offset-sm-10"),
	OFFSET_SM_11("offset-sm-11"),
	OFFSET_SM_12("offset-sm-12"),

	OFFSET_MD_AUTO("offset-md-auto"),
	OFFSET_MD_1("offset-md-1"),
	OFFSET_MD_2("offset-md-2"),
	OFFSET_MD_3("offset-md-3"),
	OFFSET_MD_4("offset-md-4"),
	OFFSET_MD_5("offset-md-5"),
	OFFSET_MD_6("offset-md-6"),
	OFFSET_MD_7("offset-md-7"),
	OFFSET_MD_8("offset-md-8"),
	OFFSET_MD_9("offset-md-9"),
	OFFSET_MD_10("offset-md-10"),
	OFFSET_MD_11("offset-md-11"),
	OFFSET_MD_12("offset-md-12"),

	OFFSET_LG_AUTO("offset-lg-auto"),
	OFFSET_LG_1("offset-lg-1"),
	OFFSET_LG_2("offset-lg-2"),
	OFFSET_LG_3("offset-lg-3"),
	OFFSET_LG_4("offset-lg-4"),
	OFFSET_LG_5("offset-lg-5"),
	OFFSET_LG_6("offset-lg-6"),
	OFFSET_LG_7("offset-lg-7"),
	OFFSET_LG_8("offset-lg-8"),
	OFFSET_LG_9("offset-lg-9"),
	OFFSET_LG_10("offset-lg-10"),
	OFFSET_LG_11("offset-lg-11"),
	OFFSET_LG_12("offset-lg-12"),

	OFFSET_XL_AUTO("offset-xl-auto"),
	OFFSET_XL_1("offset-xl-1"),
	OFFSET_XL_2("offset-xl-2"),
	OFFSET_XL_3("offset-xl-3"),
	OFFSET_XL_4("offset-xl-4"),
	OFFSET_XL_5("offset-xl-5"),
	OFFSET_XL_6("offset-xl-6"),
	OFFSET_XL_7("offset-xl-7"),
	OFFSET_XL_8("offset-xl-8"),
	OFFSET_XL_9("offset-xl-9"),
	OFFSET_XL_10("offset-xl-10"),
	OFFSET_XL_11("offset-xl-11"),
	OFFSET_XL_12("offset-xl-12"),

	SELF_START("self-start"),
	SELF_CENTER("self-center"),
	SELF_BASELINE("self-baseline"),
	SELF_END("self-end"),
	SELF_STRETCH("self-stretch"),

	ORDER_FIRST("order-first"),
	ORDER_LAST("order-last"),
	ORDER_NONE("order-none"),

	GUTTER_NONE("q-gutter-none"),
	GUTTER_XS("q-gutter-xs"), //  8px (top & left)
	GUTTER_SM("q-gutter-sm"), // 16px (top & left)
	GUTTER_MD("q-gutter-md"), // 32px (top & left)
	GUTTER_LG("q-gutter-lg"), // 48px (top & left)
	GUTTER_XL("q-gutter-xl"), // 64px (top & left)

	GUTTER_X_NONE("q-gutter-x-none"),
	GUTTER_X_XS("q-gutter-x-xs"), //  8px (left)
	GUTTER_X_SM("q-gutter-x-sm"), // 16px (left)
	GUTTER_X_MD("q-gutter-x-md"), // 32px (left)
	GUTTER_X_LG("q-gutter-x-lg"), // 48px (left)
	GUTTER_X_XL("q-gutter-x-xl"), // 64px (left)

	GUTTER_Y_NONE("q-gutter-y-none"),
	GUTTER_Y_XS("q-gutter-y-xs"), //  8px (top)
	GUTTER_Y_SM("q-gutter-y-sm"), // 16px (top)
	GUTTER_Y_MD("q-gutter-y-md"), // 32px (top)
	GUTTER_Y_LG("q-gutter-y-lg"), // 48px (top)
	GUTTER_Y_XL("q-gutter-y-xl"), // 64px (top)

	/*
		http://quasar-framework.org/components/shadows.html
	 */
	NO_SHADOW("no-shadow"),
	INSET_SHADOW("inset-shadow"),
	SHADOW_TRANSITION("shadow-transition"),

	SHADOW_1("shadow-1"),
	SHADOW_2("shadow-2"),
	SHADOW_3("shadow-3"),
	SHADOW_4("shadow-4"),
	SHADOW_5("shadow-5"),
	SHADOW_6("shadow-6"),
	SHADOW_7("shadow-7"),
	SHADOW_8("shadow-8"),
	SHADOW_9("shadow-9"),
	SHADOW_10("shadow-10"),
	SHADOW_11("shadow-11"),
	SHADOW_12("shadow-12"),
	SHADOW_13("shadow-13"),
	SHADOW_14("shadow-14"),
	SHADOW_15("shadow-15"),
	SHADOW_16("shadow-16"),
	SHADOW_17("shadow-17"),
	SHADOW_18("shadow-18"),
	SHADOW_19("shadow-19"),
	SHADOW_20("shadow-20"),
	SHADOW_21("shadow-21"),
	SHADOW_22("shadow-22"),
	SHADOW_23("shadow-23"),
	SHADOW_24("shadow-24"),

	SHADOW_UP_1("shadow-up-1"),
	SHADOW_UP_2("shadow-up-2"),
	SHADOW_UP_3("shadow-up-3"),
	SHADOW_UP_4("shadow-up-4"),
	SHADOW_UP_5("shadow-up-5"),
	SHADOW_UP_6("shadow-up-6"),
	SHADOW_UP_7("shadow-up-7"),
	SHADOW_UP_8("shadow-up-8"),
	SHADOW_UP_9("shadow-up-9"),
	SHADOW_UP_10("shadow-up-10"),
	SHADOW_UP_11("shadow-up-11"),
	SHADOW_UP_12("shadow-up-12"),
	SHADOW_UP_13("shadow-up-13"),
	SHADOW_UP_14("shadow-up-14"),
	SHADOW_UP_15("shadow-up-15"),
	SHADOW_UP_16("shadow-up-16"),
	SHADOW_UP_17("shadow-up-17"),
	SHADOW_UP_18("shadow-up-18"),
	SHADOW_UP_19("shadow-up-19"),
	SHADOW_UP_20("shadow-up-20"),
	SHADOW_UP_21("shadow-up-21"),
	SHADOW_UP_22("shadow-up-22"),
	SHADOW_UP_23("shadow-up-23"),
	SHADOW_UP_24("shadow-up-24"),

	/*
		http://quasar-framework.org/components/visibility.html
	 */
	NO_BOX_SHADOW("no-box-shadow"),
	NO_OUTLINE("no-outline"),

	NO_BORDER("no-border"),
	NO_BORDER_RADIUS("no-border-radius"),
	ROUNDED_BORDERS("rounded-borders"),

	ELLIPSIS("ellipsis"),
	ELLIPSIS_2_LINES("ellipsis-2-lines"),
	ELLIPSIS_3_LINES("ellipsis-3-lines"),

	READONLY("readonly"),
	DISABLED("disabled"),
	HIDDEN("hidden"),
	INVISIBLE("invisible"),
	TRANSPARENT("transparent"),

	OVERFLOW_AUTO("overflow-auto"),
	OVERFLOW_HIDDEN("overflow-hidden"),

	DIMMED("dimmed"),
	LIGHT_DIMMED("light-dimmed"),

	Z_TOP("z-top"),
	Z_MAX("z-max"),

	XS("xs"),
	SM("sm"),
	MD("md"),
	LG("lg"),
	XL("xl"),

	XS_HIDE("xs-hide"),
	SM_HIDE("sm-hide"),
	MD_HIDE("md-hide"),
	LG_HIDE("lg-hide"),
	XL_HIDE("xl-hide"),

	LT_SM("lt-sm"),
	LT_MD("lt-md"),
	LT_LG("lt-lg"),
	LT_XL("lt-xl"),

	GT_XS("gt-xs"),
	GT_SM("gt-sm"),
	GT_MD("gt-md"),
	GT_LG("gt-lg"),

	DESKTOP_ONLY("desktop-only"),
	MOBILE_ONLY("mobile-only"),
	CORDOVA_ONLY("cordova-only"),
	ELECTRON_ONLY("electron-only"),
	TOUCH_ONLY("touch-only"),
	PLATFORM_ANDROID_ONLY("platform-android-only"),
	PLATFORM_IOS_ONLY("platform-ios-only"),
	WITHIN_IFRAME_ONLY("within-iframe-only"),

	DESKTOP_HIDE("desktop-hide"),
	MOBILE_HIDE("mobile-hide"),
	CORDOVA_HIDE("cordova-hide"),
	ELECTRON_HIDE("electron-hide"),
	TOUCH_HIDE("touch-hide"),
	PLATFORM_ANDROID_HIDE("platform-android-hide"),
	PLATFORM_IOS_HIDE("platform-ios-hide"),
	WITHIN_IFRAME_HIDE("within-iframe-hide"),

	ORIENTATION_PORTRAIT("orientation-portrait"),
	ORIENTATION_LANDSCAPE("orientation-landscape"),

	PRINT_ONLY("print-only"),
	PRINT_HIDE("print-hide"),

	/*
		http://quasar-framework.org/components/positioning.html
	 */
	FULLSCREEN("fullscreen"),

	FIXED("fixed"),
	FIXED_CENTER("fixed-center"),
	FIXED_TOP("fixed-top"),
	FIXED_RIGHT("fixed-right"),
	FIXED_BOTTOM("fixed-bottom"),
	FIXED_LEFT("fixed-left"),
	FIXED_TOP_LEFT("fixed-top-left"),
	FIXED_TOP_RIGHT("fixed-top-right"),
	FIXED_BOTTOM_LEFT("fixed-bottom-left"),
	FIXED_BOTTOM_RIGHT("fixed-bottom-right"),

	ABSOLUTE("absolute"),
	ABSOLUTE_CENTER("absolute-center"),
	ABSOLUTE_TOP("absolute-top"),
	ABSOLUTE_RIGHT("absolute-right"),
	ABSOLUTE_BOTTOM("absolute-bottom"),
	ABSOLUTE_LEFT("absolute-left"),
	ABSOLUTE_TOP_LEFT("absolute-top-left"),
	ABSOLUTE_TOP_RIGHT("absolute-top-right"),
	ABSOLUTE_BOTTOM_LEFT("absolute-bottom-left"),
	ABSOLUTE_BOTTOM_RIGHT("absolute-bottom-right"),

	RELATIVE_POSITION("relative-position"),

	FLOAT_LEFT("float-left"),
	FLOAT_RIGHT("float-right"),

	ON_LEFT("on-left"),
	ON_RIGHT("on-right"),

	VERTICAL_TOP("vertical-top"),
	VERTICAL_MIDDLE("vertical-middle"),
	VERTICAL_BOTTOM("vertical-bottom"),

	/*
		http://quasar-framework.org/components/other-helper-classes.html
	 */
	NON_SELECTABLE("non-selectable"),
	SCROLL("scroll"),
	NO_SCROLL("no-scroll"),
	NO_POINTER_EVENTS("no-pointer-events"),
	ALL_POINTER_EVENTS("all-pointer-events"),
	CURSOR_POINTER("cursor-pointer"),
	CURSOR_INHERIT("cursor-inherit"),
	CURSOR_NONE("cursor-none"),
	CURSOR_NOT_ALLOWED("cursor-not-allowed"),

	/*
		http://quasar-framework.org/components/other-helper-classes.html
	 */
	BLOCK("block"),
	INLINE_BLOCK("inline-block"),

	/*
		http://quasar-framework.org/components/other-helper-classes.html
	 */
	FIT("fit"),
	FULL_HEIGHT("full-height"),
	FULL_WIDTH("full-width"),
	WINDOW_HEIGHT("window-height"),
	WINDOW_WIDTH("window-width"),

	/*
		http://quasar-framework.org/components/other-helper-classes.html
	 */
	ROTATE_45("rotate-45"),
	ROTATE_90("rotate-90"),
	ROTATE_135("rotate-135"),
	ROTATE_180("rotate-180"),
	ROTATE_205("rotate-205"),
	ROTATE_270("rotate-270"),
	ROTATE_315("rotate-315"),
	FLIP_HORIZONTAL("flip-horizontal"),
	FLIP_VERTICAL("flip-vertical"),

	/*
		http://quasar-framework.org/components/other-helper-classes.html
	 */
	GROUP("group"),
	GENERIC_MARGIN("generic-margin"),
	;


	//
	final private String VALUE;
	final private IUtf8StreamableEntry ENTRY;


	//
	QuasarStyle(String value)
	{
		VALUE = value;
		ENTRY = IUtf8StreamableEntry.entry(value, true);
	}


	//
	@Override
	final public void toUtf8Stream(Utf8OutputStream destination)
		throws IOException
	{
		ENTRY.toUtf8Stream(destination);
	}


	public String value()
	{
		return VALUE;
	}

}
