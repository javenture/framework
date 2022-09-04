package net.javenture.framework.html;


import net.javenture.framework.css.Color;


@Deprecated
final public class Palette                         // XXX: Palette -> MaterialPalette
{
	// Paper Red
	final public static Primary PAPER_RED_50 = new Primary("#ffebee", "PaperRed50", "50", true, true); // --paper-red-50: #ffebee;
	final public static Primary PAPER_RED_100 = new Primary("#ffcdd2", "PaperRed100", "100", true, true); // --paper-red-100: #ffcdd2;
	final public static Primary PAPER_RED_200 = new Primary("#ef9a9a", "PaperRed200", "200", true, true); // --paper-red-200: #ef9a9a;
	final public static Primary PAPER_RED_300 = new Primary("#e57373", "PaperRed300", "300", true, true); // --paper-red-300: #e57373;
	final public static Primary PAPER_RED_400 = new Primary("#ef5350", "PaperRed400", "400", true, true); // --paper-red-400: #ef5350;
	final public static Primary PAPER_RED_500 = new Primary("#f44336", "PaperRed500", "500", true, true); // --paper-red-500: #f44336;
	final public static Primary PAPER_RED_600 = new Primary("#e53935", "PaperRed600", "600", true, true); // --paper-red-600: #e53935;
	final public static Primary PAPER_RED_700 = new Primary("#d32f2f", "PaperRed700", "700", true, true); // --paper-red-700: #d32f2f;
	final public static Primary PAPER_RED_800 = new Primary("#c62828", "PaperRed800", "800", true, true); // --paper-red-800: #c62828;
	final public static Primary PAPER_RED_900 = new Primary("#b71c1c", "PaperRed900", "900", true, true); // --paper-red-900: #b71c1c;
	final public static Accent PAPER_RED_A100 = new Accent("#ff8a80", "PaperRedA100", "A100", true, true); // --paper-red-a100: #ff8a80;
	final public static Accent PAPER_RED_A200 = new Accent("#ff5252", "PaperRedA200", "A200", true, true); // --paper-red-a200: #ff5252;
	final public static Accent PAPER_RED_A400 = new Accent("#ff1744", "PaperRedA400", "A400", true, true); // --paper-red-a400: #ff1744;
	final public static Accent PAPER_RED_A700 = new Accent("#d50000", "PaperRedA700", "A700", true, true); // --paper-red-a700: #d50000;

	final public static PrimaryGroup PAPER_RED = new PrimaryGroup
	(
		"Paper Red",
		PAPER_RED_50,
		PAPER_RED_100,
		PAPER_RED_200,
		PAPER_RED_300,
		PAPER_RED_400,
		PAPER_RED_500,
		PAPER_RED_600,
		PAPER_RED_700,
		PAPER_RED_800,
		PAPER_RED_900
	);

	final public static AccentGroup PAPER_RED_A = new AccentGroup
	(
		"Paper Red",
		PAPER_RED_A100,
		PAPER_RED_A200,
		PAPER_RED_A400,
		PAPER_RED_A700
	);

	// Paper Pink
	final public static Primary PAPER_PINK_50 = new Primary("#fce4ec", "PaperPink50", "50", true, true); // --paper-pink-50: #fce4ec;
	final public static Primary PAPER_PINK_100 = new Primary("#f8bbd0", "PaperPink100", "100", true, true); // --paper-pink-100: #f8bbd0;
	final public static Primary PAPER_PINK_200 = new Primary("#f48fb1", "PaperPink200", "200", true, true); // --paper-pink-200: #f48fb1;
	final public static Primary PAPER_PINK_300 = new Primary("#f06292", "PaperPink300", "300", true, true); // --paper-pink-300: #f06292;
	final public static Primary PAPER_PINK_400 = new Primary("#ec407a", "PaperPink400", "400", true, true); // --paper-pink-400: #ec407a;
	final public static Primary PAPER_PINK_500 = new Primary("#e91e63", "PaperPink500", "500", true, true); // --paper-pink-500: #e91e63;
	final public static Primary PAPER_PINK_600 = new Primary("#d81b60", "PaperPink600", "600", true, true); // --paper-pink-600: #d81b60;
	final public static Primary PAPER_PINK_700 = new Primary("#c2185b", "PaperPink700", "700", true, true); // --paper-pink-700: #c2185b;
	final public static Primary PAPER_PINK_800 = new Primary("#ad1457", "PaperPink800", "800", true, true); // --paper-pink-800: #ad1457;
	final public static Primary PAPER_PINK_900 = new Primary("#880e4f", "PaperPink900", "900", true, true); // --paper-pink-900: #880e4f;
	final public static Accent PAPER_PINK_A100 = new Accent("#ff80ab", "PaperPinkA100", "A100", true, true); // --paper-pink-a100: #ff80ab;
	final public static Accent PAPER_PINK_A200 = new Accent("#ff4081", "PaperPinkA200", "A200", true, true); // --paper-pink-a200: #ff4081;
	final public static Accent PAPER_PINK_A400 = new Accent("#f50057", "PaperPinkA400", "A400", true, true); // --paper-pink-a400: #f50057;
	final public static Accent PAPER_PINK_A700 = new Accent("#c51162", "PaperPinkA700", "A700", true, true); // --paper-pink-a700: #c51162;

	final public static PrimaryGroup PAPER_PINK = new PrimaryGroup
	(
		"Paper Pink",
		PAPER_PINK_50,
		PAPER_PINK_100,
		PAPER_PINK_200,
		PAPER_PINK_300,
		PAPER_PINK_400,
		PAPER_PINK_500,
		PAPER_PINK_600,
		PAPER_PINK_700,
		PAPER_PINK_800,
		PAPER_PINK_900
	);

	final public static AccentGroup PAPER_PINK_A = new AccentGroup
	(
		"Paper Pink",
		PAPER_PINK_A100,
		PAPER_PINK_A200,
		PAPER_PINK_A400,
		PAPER_PINK_A700
	);

	// Paper Purple
	final public static Primary PAPER_PURPLE_50 = new Primary("#f3e5f5", "PaperPurple50", "50", true, true); // --paper-purple-50: #f3e5f5;
	final public static Primary PAPER_PURPLE_100 = new Primary("#e1bee7", "PaperPurple100", "100", true, true); // --paper-purple-100: #e1bee7;
	final public static Primary PAPER_PURPLE_200 = new Primary("#ce93d8", "PaperPurple200", "200", true, true); // --paper-purple-200: #ce93d8;
	final public static Primary PAPER_PURPLE_300 = new Primary("#ba68c8", "PaperPurple300", "300", true, true); // --paper-purple-300: #ba68c8;
	final public static Primary PAPER_PURPLE_400 = new Primary("#ab47bc", "PaperPurple400", "400", true, true); // --paper-purple-400: #ab47bc;
	final public static Primary PAPER_PURPLE_500 = new Primary("#9c27b0", "PaperPurple500", "500", true, true); // --paper-purple-500: #9c27b0;
	final public static Primary PAPER_PURPLE_600 = new Primary("#8e24aa", "PaperPurple600", "600", true, true); // --paper-purple-600: #8e24aa;
	final public static Primary PAPER_PURPLE_700 = new Primary("#7b1fa2", "PaperPurple700", "700", true, true); // --paper-purple-700: #7b1fa2;
	final public static Primary PAPER_PURPLE_800 = new Primary("#6a1b9a", "PaperPurple800", "800", true, true); // --paper-purple-800: #6a1b9a;
	final public static Primary PAPER_PURPLE_900 = new Primary("#4a148c", "PaperPurple900", "900", true, true); // --paper-purple-900: #4a148c;
	final public static Accent PAPER_PURPLE_A100 = new Accent("#ea80fc", "PaperPurpleA100", "A100", true, true); // --paper-purple-a100: #ea80fc;
	final public static Accent PAPER_PURPLE_A200 = new Accent("#e040fb", "PaperPurpleA200", "A200", true, true); // --paper-purple-a200: #e040fb;
	final public static Accent PAPER_PURPLE_A400 = new Accent("#d500f9", "PaperPurpleA400", "A400", true, true); // --paper-purple-a400: #d500f9;
	final public static Accent PAPER_PURPLE_A700 = new Accent("#aa00ff", "PaperPurpleA700", "A700", true, true); // --paper-purple-a700: #aa00ff;

	final public static PrimaryGroup PAPER_PURPLE = new PrimaryGroup
	(
		"Paper Purple",
		PAPER_PURPLE_50,
		PAPER_PURPLE_100,
		PAPER_PURPLE_200,
		PAPER_PURPLE_300,
		PAPER_PURPLE_400,
		PAPER_PURPLE_500,
		PAPER_PURPLE_600,
		PAPER_PURPLE_700,
		PAPER_PURPLE_800,
		PAPER_PURPLE_900
	);

	final public static AccentGroup PAPER_PURPLE_A = new AccentGroup
	(
		"Paper Purple",
		PAPER_PURPLE_A100,
		PAPER_PURPLE_A200,
		PAPER_PURPLE_A400,
		PAPER_PURPLE_A700
	);

	// Paper Deep Purple
	final public static Primary PAPER_DEEP_PURPLE_50 = new Primary("#ede7f6", "PaperDeepPurple50", "50", true, true); // --paper-deep-purple-50: #ede7f6;
	final public static Primary PAPER_DEEP_PURPLE_100 = new Primary("#d1c4e9", "PaperDeepPurple100", "100", true, true); // --paper-deep-purple-100: #d1c4e9;
	final public static Primary PAPER_DEEP_PURPLE_200 = new Primary("#b39ddb", "PaperDeepPurple200", "200", true, true); // --paper-deep-purple-200: #b39ddb;
	final public static Primary PAPER_DEEP_PURPLE_300 = new Primary("#9575cd", "PaperDeepPurple300", "300", true, true); // --paper-deep-purple-300: #9575cd;
	final public static Primary PAPER_DEEP_PURPLE_400 = new Primary("#7e57c2", "PaperDeepPurple400", "400", true, true); // --paper-deep-purple-400: #7e57c2;
	final public static Primary PAPER_DEEP_PURPLE_500 = new Primary("#673ab7", "PaperDeepPurple500", "500", true, true); // --paper-deep-purple-500: #673ab7;
	final public static Primary PAPER_DEEP_PURPLE_600 = new Primary("#5e35b1", "PaperDeepPurple600", "600", true, true); // --paper-deep-purple-600: #5e35b1;
	final public static Primary PAPER_DEEP_PURPLE_700 = new Primary("#512da8", "PaperDeepPurple700", "700", true, true); // --paper-deep-purple-700: #512da8;
	final public static Primary PAPER_DEEP_PURPLE_800 = new Primary("#4527a0", "PaperDeepPurple800", "800", true, true); // --paper-deep-purple-800: #4527a0;
	final public static Primary PAPER_DEEP_PURPLE_900 = new Primary("#311b92", "PaperDeepPurple900", "900", true, true); // --paper-deep-purple-900: #311b92;
	final public static Accent PAPER_DEEP_PURPLE_A100 = new Accent("#b388ff", "PaperDeepPurpleA100", "A100", true, true); // --paper-deep-purple-a100: #b388ff;
	final public static Accent PAPER_DEEP_PURPLE_A200 = new Accent("#7c4dff", "PaperDeepPurpleA200", "A200", true, true); // --paper-deep-purple-a200: #7c4dff;
	final public static Accent PAPER_DEEP_PURPLE_A400 = new Accent("#651fff", "PaperDeepPurpleA400", "A400", true, true); // --paper-deep-purple-a400: #651fff;
	final public static Accent PAPER_DEEP_PURPLE_A700 = new Accent("#6200ea", "PaperDeepPurpleA700", "A700", true, true); // --paper-deep-purple-a700: #6200ea;

	final public static PrimaryGroup PAPER_DEEP_PURPLE = new PrimaryGroup
	(
		"Paper Deep Purple",
		PAPER_DEEP_PURPLE_50,
		PAPER_DEEP_PURPLE_100,
		PAPER_DEEP_PURPLE_200,
		PAPER_DEEP_PURPLE_300,
		PAPER_DEEP_PURPLE_400,
		PAPER_DEEP_PURPLE_500,
		PAPER_DEEP_PURPLE_600,
		PAPER_DEEP_PURPLE_700,
		PAPER_DEEP_PURPLE_800,
		PAPER_DEEP_PURPLE_900
	);

	final public static AccentGroup PAPER_DEEP_PURPLE_A = new AccentGroup
	(
		"Paper Deep Purple",
		PAPER_DEEP_PURPLE_A100,
		PAPER_DEEP_PURPLE_A200,
		PAPER_DEEP_PURPLE_A400,
		PAPER_DEEP_PURPLE_A700
	);

	// Paper Indigo
	final public static Primary PAPER_INDIGO_50 = new Primary("#e8eaf6", "PaperIndigo50", "50", true, true); // --paper-indigo-50: #e8eaf6;
	final public static Primary PAPER_INDIGO_100 = new Primary("#c5cae9", "PaperIndigo100", "100", true, true); // --paper-indigo-100: #c5cae9;
	final public static Primary PAPER_INDIGO_200 = new Primary("#9fa8da", "PaperIndigo200", "200", true, true); // --paper-indigo-200: #9fa8da;
	final public static Primary PAPER_INDIGO_300 = new Primary("#7986cb", "PaperIndigo300", "300", true, true); // --paper-indigo-300: #7986cb;
	final public static Primary PAPER_INDIGO_400 = new Primary("#5c6bc0", "PaperIndigo400", "400", true, true); // --paper-indigo-400: #5c6bc0;
	final public static Primary PAPER_INDIGO_500 = new Primary("#3f51b5", "PaperIndigo500", "500", true, true); // --paper-indigo-500: #3f51b5;
	final public static Primary PAPER_INDIGO_600 = new Primary("#3949ab", "PaperIndigo600", "600", true, true); // --paper-indigo-600: #3949ab;
	final public static Primary PAPER_INDIGO_700 = new Primary("#303f9f", "PaperIndigo700", "700", true, true); // --paper-indigo-700: #303f9f;
	final public static Primary PAPER_INDIGO_800 = new Primary("#283593", "PaperIndigo800", "800", true, true); // --paper-indigo-800: #283593;
	final public static Primary PAPER_INDIGO_900 = new Primary("#1a237e", "PaperIndigo900", "900", true, true); // --paper-indigo-900: #1a237e;
	final public static Accent PAPER_INDIGO_A100 = new Accent("#8c9eff", "PaperIndigoA100", "A100", true, true); // --paper-indigo-a100: #8c9eff;
	final public static Accent PAPER_INDIGO_A200 = new Accent("#536dfe", "PaperIndigoA200", "A200", true, true); // --paper-indigo-a200: #536dfe;
	final public static Accent PAPER_INDIGO_A400 = new Accent("#3d5afe", "PaperIndigoA400", "A400", true, true); // --paper-indigo-a400: #3d5afe;
	final public static Accent PAPER_INDIGO_A700 = new Accent("#304ffe", "PaperIndigoA700", "A700", true, true); // --paper-indigo-a700: #304ffe;

	final public static PrimaryGroup PAPER_INDIGO = new PrimaryGroup
	(
		"Paper Indigo",
		PAPER_INDIGO_50,
		PAPER_INDIGO_100,
		PAPER_INDIGO_200,
		PAPER_INDIGO_300,
		PAPER_INDIGO_400,
		PAPER_INDIGO_500,
		PAPER_INDIGO_600,
		PAPER_INDIGO_700,
		PAPER_INDIGO_800,
		PAPER_INDIGO_900
	);

	final public static AccentGroup PAPER_INDIGO_A = new AccentGroup
	(
		"Paper Indigo",
		PAPER_INDIGO_A100,
		PAPER_INDIGO_A200,
		PAPER_INDIGO_A400,
		PAPER_INDIGO_A700
	);

	// Paper Blue
	final public static Primary PAPER_BLUE_50 = new Primary("#e3f2fd", "PaperBlue50", "50", true, true); // --paper-blue-50: #e3f2fd;
	final public static Primary PAPER_BLUE_100 = new Primary("#bbdefb", "PaperBlue100", "100", true, true); // --paper-blue-100: #bbdefb;
	final public static Primary PAPER_BLUE_200 = new Primary("#90caf9", "PaperBlue200", "200", true, true); // --paper-blue-200: #90caf9;
	final public static Primary PAPER_BLUE_300 = new Primary("#64b5f6", "PaperBlue300", "300", true, true); // --paper-blue-300: #64b5f6;
	final public static Primary PAPER_BLUE_400 = new Primary("#42a5f5", "PaperBlue400", "400", true, true); // --paper-blue-400: #42a5f5;
	final public static Primary PAPER_BLUE_500 = new Primary("#2196f3", "PaperBlue500", "500", true, true); // --paper-blue-500: #2196f3;
	final public static Primary PAPER_BLUE_600 = new Primary("#1e88e5", "PaperBlue600", "600", true, true); // --paper-blue-600: #1e88e5;
	final public static Primary PAPER_BLUE_700 = new Primary("#1976d2", "PaperBlue700", "700", true, true); // --paper-blue-700: #1976d2;
	final public static Primary PAPER_BLUE_800 = new Primary("#1565c0", "PaperBlue800", "800", true, true); // --paper-blue-800: #1565c0;
	final public static Primary PAPER_BLUE_900 = new Primary("#0d47a1", "PaperBlue900", "900", true, true); // --paper-blue-900: #0d47a1;
	final public static Accent PAPER_BLUE_A100 = new Accent("#82b1ff", "PaperBlueA100", "A100", true, true); // --paper-blue-a100: #82b1ff;
	final public static Accent PAPER_BLUE_A200 = new Accent("#448aff", "PaperBlueA200", "A200", true, true); // --paper-blue-a200: #448aff;
	final public static Accent PAPER_BLUE_A400 = new Accent("#2979ff", "PaperBlueA400", "A400", true, true); // --paper-blue-a400: #2979ff;
	final public static Accent PAPER_BLUE_A700 = new Accent("#2962ff", "PaperBlueA700", "A700", true, true); // --paper-blue-a700: #2962ff;

	final public static PrimaryGroup PAPER_BLUE = new PrimaryGroup
	(
		"Paper Blue",
		PAPER_BLUE_50,
		PAPER_BLUE_100,
		PAPER_BLUE_200,
		PAPER_BLUE_300,
		PAPER_BLUE_400,
		PAPER_BLUE_500,
		PAPER_BLUE_600,
		PAPER_BLUE_700,
		PAPER_BLUE_800,
		PAPER_BLUE_900
	);

	final public static AccentGroup PAPER_BLUE_A = new AccentGroup
	(
		"Paper Blue",
		PAPER_BLUE_A100,
		PAPER_BLUE_A200,
		PAPER_BLUE_A400,
		PAPER_BLUE_A700
	);

	// Paper Light Blue
	final public static Primary PAPER_LIGHT_BLUE_50 = new Primary("#e1f5fe", "PaperLightBlue50", "50", true, true); // --paper-light-blue-50: #e1f5fe;
	final public static Primary PAPER_LIGHT_BLUE_100 = new Primary("#b3e5fc", "PaperLightBlue100", "100", true, true); // --paper-light-blue-100: #b3e5fc;
	final public static Primary PAPER_LIGHT_BLUE_200 = new Primary("#81d4fa", "PaperLightBlue200", "200", true, true); // --paper-light-blue-200: #81d4fa;
	final public static Primary PAPER_LIGHT_BLUE_300 = new Primary("#4fc3f7", "PaperLightBlue300", "300", true, true); // --paper-light-blue-300: #4fc3f7;
	final public static Primary PAPER_LIGHT_BLUE_400 = new Primary("#29b6f6", "PaperLightBlue400", "400", true, true); // --paper-light-blue-400: #29b6f6;
	final public static Primary PAPER_LIGHT_BLUE_500 = new Primary("#03a9f4", "PaperLightBlue500", "500", true, true); // --paper-light-blue-500: #03a9f4;
	final public static Primary PAPER_LIGHT_BLUE_600 = new Primary("#039be5", "PaperLightBlue600", "600", true, true); // --paper-light-blue-600: #039be5;
	final public static Primary PAPER_LIGHT_BLUE_700 = new Primary("#0288d1", "PaperLightBlue700", "700", true, true); // --paper-light-blue-700: #0288d1;
	final public static Primary PAPER_LIGHT_BLUE_800 = new Primary("#0277bd", "PaperLightBlue800", "800", true, true); // --paper-light-blue-800: #0277bd;
	final public static Primary PAPER_LIGHT_BLUE_900 = new Primary("#01579b", "PaperLightBlue900", "900", true, true); // --paper-light-blue-900: #01579b;
	final public static Accent PAPER_LIGHT_BLUE_A100 = new Accent("#80d8ff", "PaperLightBlueA100", "A100", true, true); // --paper-light-blue-a100: #80d8ff;
	final public static Accent PAPER_LIGHT_BLUE_A200 = new Accent("#40c4ff", "PaperLightBlueA200", "A200", true, true); // --paper-light-blue-a200: #40c4ff;
	final public static Accent PAPER_LIGHT_BLUE_A400 = new Accent("#00b0ff", "PaperLightBlueA400", "A400", true, true); // --paper-light-blue-a400: #00b0ff;
	final public static Accent PAPER_LIGHT_BLUE_A700 = new Accent("#0091ea", "PaperLightBlueA700", "A700", true, true); // --paper-light-blue-a700: #0091ea;

	final public static PrimaryGroup PAPER_LIGHT_BLUE = new PrimaryGroup
	(
		"Paper Light Blue",
		PAPER_LIGHT_BLUE_50,
		PAPER_LIGHT_BLUE_100,
		PAPER_LIGHT_BLUE_200,
		PAPER_LIGHT_BLUE_300,
		PAPER_LIGHT_BLUE_400,
		PAPER_LIGHT_BLUE_500,
		PAPER_LIGHT_BLUE_600,
		PAPER_LIGHT_BLUE_700,
		PAPER_LIGHT_BLUE_800,
		PAPER_LIGHT_BLUE_900
	);

	final public static AccentGroup PAPER_LIGHT_BLUE_A = new AccentGroup
	(
		"Paper Light Blue",
		PAPER_LIGHT_BLUE_A100,
		PAPER_LIGHT_BLUE_A200,
		PAPER_LIGHT_BLUE_A400,
		PAPER_LIGHT_BLUE_A700
	);

	// Paper Cyan
	final public static Primary PAPER_CYAN_50 = new Primary("#e0f7fa", "PaperCyan50", "50", true, true); // --paper-cyan-50: #e0f7fa;
	final public static Primary PAPER_CYAN_100 = new Primary("#b2ebf2", "PaperCyan100", "100", true, true); // --paper-cyan-100: #b2ebf2;
	final public static Primary PAPER_CYAN_200 = new Primary("#80deea", "PaperCyan200", "200", true, true); // --paper-cyan-200: #80deea;
	final public static Primary PAPER_CYAN_300 = new Primary("#4dd0e1", "PaperCyan300", "300", true, true); // --paper-cyan-300: #4dd0e1;
	final public static Primary PAPER_CYAN_400 = new Primary("#26c6da", "PaperCyan400", "400", true, true); // --paper-cyan-400: #26c6da;
	final public static Primary PAPER_CYAN_500 = new Primary("#00bcd4", "PaperCyan500", "500", true, true); // --paper-cyan-500: #00bcd4;
	final public static Primary PAPER_CYAN_600 = new Primary("#00acc1", "PaperCyan600", "600", true, true); // --paper-cyan-600: #00acc1;
	final public static Primary PAPER_CYAN_700 = new Primary("#0097a7", "PaperCyan700", "700", true, true); // --paper-cyan-700: #0097a7;
	final public static Primary PAPER_CYAN_800 = new Primary("#00838f", "PaperCyan800", "800", true, true); // --paper-cyan-800: #00838f;
	final public static Primary PAPER_CYAN_900 = new Primary("#006064", "PaperCyan900", "900", true, true); // --paper-cyan-900: #006064;
	final public static Accent PAPER_CYAN_A100 = new Accent("#84ffff", "PaperCyanA100", "A100", true, true); // --paper-cyan-a100: #84ffff;
	final public static Accent PAPER_CYAN_A200 = new Accent("#18ffff", "PaperCyanA200", "A200", true, true); // --paper-cyan-a200: #18ffff;
	final public static Accent PAPER_CYAN_A400 = new Accent("#00e5ff", "PaperCyanA400", "A400", true, true); // --paper-cyan-a400: #00e5ff;
	final public static Accent PAPER_CYAN_A700 = new Accent("#00b8d4", "PaperCyanA700", "A700", true, true); // --paper-cyan-a700: #00b8d4;

	final public static PrimaryGroup PAPER_CYAN = new PrimaryGroup
	(
		"Paper Cyan",
		PAPER_CYAN_50,
		PAPER_CYAN_100,
		PAPER_CYAN_200,
		PAPER_CYAN_300,
		PAPER_CYAN_400,
		PAPER_CYAN_500,
		PAPER_CYAN_600,
		PAPER_CYAN_700,
		PAPER_CYAN_800,
		PAPER_CYAN_900
	);

	final public static AccentGroup PAPER_CYAN_A = new AccentGroup
	(
		"Paper Cyan",
		PAPER_CYAN_A100,
		PAPER_CYAN_A200,
		PAPER_CYAN_A400,
		PAPER_CYAN_A700
	);

	// Paper Teal
	final public static Primary PAPER_TEAL_50 = new Primary("#e0f2f1", "PaperTeal50", "50", true, true); // --paper-teal-50: #e0f2f1;
	final public static Primary PAPER_TEAL_100 = new Primary("#b2dfdb", "PaperTeal100", "100", true, true); // --paper-teal-100: #b2dfdb;
	final public static Primary PAPER_TEAL_200 = new Primary("#80cbc4", "PaperTeal200", "200", true, true); // --paper-teal-200: #80cbc4;
	final public static Primary PAPER_TEAL_300 = new Primary("#4db6ac", "PaperTeal300", "300", true, true); // --paper-teal-300: #4db6ac;
	final public static Primary PAPER_TEAL_400 = new Primary("#26a69a", "PaperTeal400", "400", true, true); // --paper-teal-400: #26a69a;
	final public static Primary PAPER_TEAL_500 = new Primary("#009688", "PaperTeal500", "500", true, true); // --paper-teal-500: #009688;
	final public static Primary PAPER_TEAL_600 = new Primary("#00897b", "PaperTeal600", "600", true, true); // --paper-teal-600: #00897b;
	final public static Primary PAPER_TEAL_700 = new Primary("#00796b", "PaperTeal700", "700", true, true); // --paper-teal-700: #00796b;
	final public static Primary PAPER_TEAL_800 = new Primary("#00695c", "PaperTeal800", "800", true, true); // --paper-teal-800: #00695c;
	final public static Primary PAPER_TEAL_900 = new Primary("#004d40", "PaperTeal900", "900", true, true); // --paper-teal-900: #004d40;
	final public static Accent PAPER_TEAL_A100 = new Accent("#a7ffeb", "PaperTealA100", "A100", true, true); // --paper-teal-a100: #a7ffeb;
	final public static Accent PAPER_TEAL_A200 = new Accent("#64ffda", "PaperTealA200", "A200", true, true); // --paper-teal-a200: #64ffda;
	final public static Accent PAPER_TEAL_A400 = new Accent("#1de9b6", "PaperTealA400", "A400", true, true); // --paper-teal-a400: #1de9b6;
	final public static Accent PAPER_TEAL_A700 = new Accent("#00bfa5", "PaperTealA700", "A700", true, true); // --paper-teal-a700: #00bfa5;

	final public static PrimaryGroup PAPER_TEAL = new PrimaryGroup
	(
		"Paper Teal",
		PAPER_TEAL_50,
		PAPER_TEAL_100,
		PAPER_TEAL_200,
		PAPER_TEAL_300,
		PAPER_TEAL_400,
		PAPER_TEAL_500,
		PAPER_TEAL_600,
		PAPER_TEAL_700,
		PAPER_TEAL_800,
		PAPER_TEAL_900
	);

	final public static AccentGroup PAPER_TEAL_A = new AccentGroup
	(
		"Paper Teal",
		PAPER_TEAL_A100,
		PAPER_TEAL_A200,
		PAPER_TEAL_A400,
		PAPER_TEAL_A700
	);

	// Paper Green
	final public static Primary PAPER_GREEN_50 = new Primary("#e8f5e9", "PaperGreen50", "50", true, true); // --paper-green-50: #e8f5e9;
	final public static Primary PAPER_GREEN_100 = new Primary("#c8e6c9", "PaperGreen100", "100", true, true); // --paper-green-100: #c8e6c9;
	final public static Primary PAPER_GREEN_200 = new Primary("#a5d6a7", "PaperGreen200", "200", true, true); // --paper-green-200: #a5d6a7;
	final public static Primary PAPER_GREEN_300 = new Primary("#81c784", "PaperGreen300", "300", true, true); // --paper-green-300: #81c784;
	final public static Primary PAPER_GREEN_400 = new Primary("#66bb6a", "PaperGreen400", "400", true, true); // --paper-green-400: #66bb6a;
	final public static Primary PAPER_GREEN_500 = new Primary("#4caf50", "PaperGreen500", "500", true, true); // --paper-green-500: #4caf50;
	final public static Primary PAPER_GREEN_600 = new Primary("#43a047", "PaperGreen600", "600", true, true); // --paper-green-600: #43a047;
	final public static Primary PAPER_GREEN_700 = new Primary("#388e3c", "PaperGreen700", "700", true, true); // --paper-green-700: #388e3c;
	final public static Primary PAPER_GREEN_800 = new Primary("#2e7d32", "PaperGreen800", "800", true, true); // --paper-green-800: #2e7d32;
	final public static Primary PAPER_GREEN_900 = new Primary("#1b5e20", "PaperGreen900", "900", true, true); // --paper-green-900: #1b5e20;
	final public static Accent PAPER_GREEN_A100 = new Accent("#b9f6ca", "PaperGreenA100", "A100", true, true); // --paper-green-a100: #b9f6ca;
	final public static Accent PAPER_GREEN_A200 = new Accent("#69f0ae", "PaperGreenA200", "A200", true, true); // --paper-green-a200: #69f0ae;
	final public static Accent PAPER_GREEN_A400 = new Accent("#00e676", "PaperGreenA400", "A400", true, true); // --paper-green-a400: #00e676;
	final public static Accent PAPER_GREEN_A700 = new Accent("#00c853", "PaperGreenA700", "A700", true, true); // --paper-green-a700: #00c853;

	final public static PrimaryGroup PAPER_GREEN = new PrimaryGroup
	(
		"Paper Green",
		PAPER_GREEN_50,
		PAPER_GREEN_100,
		PAPER_GREEN_200,
		PAPER_GREEN_300,
		PAPER_GREEN_400,
		PAPER_GREEN_500,
		PAPER_GREEN_600,
		PAPER_GREEN_700,
		PAPER_GREEN_800,
		PAPER_GREEN_900
	);

	final public static AccentGroup PAPER_GREEN_A = new AccentGroup
	(
		"Paper Green",
		PAPER_GREEN_A100,
		PAPER_GREEN_A200,
		PAPER_GREEN_A400,
		PAPER_GREEN_A700
	);

	// Paper Light Green
	final public static Primary PAPER_LIGHT_GREEN_50 = new Primary("#f1f8e9", "PaperLightGreen50", "50", true, true); // --paper-light-green-50: #f1f8e9;
	final public static Primary PAPER_LIGHT_GREEN_100 = new Primary("#dcedc8", "PaperLightGreen100", "100", true, true); // --paper-light-green-100: #dcedc8;
	final public static Primary PAPER_LIGHT_GREEN_200 = new Primary("#c5e1a5", "PaperLightGreen200", "200", true, true); // --paper-light-green-200: #c5e1a5;
	final public static Primary PAPER_LIGHT_GREEN_300 = new Primary("#aed581", "PaperLightGreen300", "300", true, true); // --paper-light-green-300: #aed581;
	final public static Primary PAPER_LIGHT_GREEN_400 = new Primary("#9ccc65", "PaperLightGreen400", "400", true, true); // --paper-light-green-400: #9ccc65;
	final public static Primary PAPER_LIGHT_GREEN_500 = new Primary("#8bc34a", "PaperLightGreen500", "500", true, true); // --paper-light-green-500: #8bc34a;
	final public static Primary PAPER_LIGHT_GREEN_600 = new Primary("#7cb342", "PaperLightGreen600", "600", true, true); // --paper-light-green-600: #7cb342;
	final public static Primary PAPER_LIGHT_GREEN_700 = new Primary("#689f38", "PaperLightGreen700", "700", true, true); // --paper-light-green-700: #689f38;
	final public static Primary PAPER_LIGHT_GREEN_800 = new Primary("#558b2f", "PaperLightGreen800", "800", true, true); // --paper-light-green-800: #558b2f;
	final public static Primary PAPER_LIGHT_GREEN_900 = new Primary("#33691e", "PaperLightGreen900", "900", true, true); // --paper-light-green-900: #33691e;
	final public static Accent PAPER_LIGHT_GREEN_A100 = new Accent("#ccff90", "PaperLightGreenA100", "A100", true, true); // --paper-light-green-a100: #ccff90;
	final public static Accent PAPER_LIGHT_GREEN_A200 = new Accent("#b2ff59", "PaperLightGreenA200", "A200", true, true); // --paper-light-green-a200: #b2ff59;
	final public static Accent PAPER_LIGHT_GREEN_A400 = new Accent("#76ff03", "PaperLightGreenA400", "A400", true, true); // --paper-light-green-a400: #76ff03;
	final public static Accent PAPER_LIGHT_GREEN_A700 = new Accent("#64dd17", "PaperLightGreenA700", "A700", true, true); // --paper-light-green-a700: #64dd17;

	final public static PrimaryGroup PAPER_LIGHT_GREEN = new PrimaryGroup
	(
		"Paper Light Green",
		PAPER_LIGHT_GREEN_50,
		PAPER_LIGHT_GREEN_100,
		PAPER_LIGHT_GREEN_200,
		PAPER_LIGHT_GREEN_300,
		PAPER_LIGHT_GREEN_400,
		PAPER_LIGHT_GREEN_500,
		PAPER_LIGHT_GREEN_600,
		PAPER_LIGHT_GREEN_700,
		PAPER_LIGHT_GREEN_800,
		PAPER_LIGHT_GREEN_900
	);

	final public static AccentGroup PAPER_LIGHT_GREEN_A = new AccentGroup
	(
		"Paper Light Green",
		PAPER_LIGHT_GREEN_A100,
		PAPER_LIGHT_GREEN_A200,
		PAPER_LIGHT_GREEN_A400,
		PAPER_LIGHT_GREEN_A700
	);

	// Paper Lime
	final public static Primary PAPER_LIME_50 = new Primary("#f9fbe7", "PaperLime50", "50", true, true); // --paper-lime-50: #f9fbe7;
	final public static Primary PAPER_LIME_100 = new Primary("#f0f4c3", "PaperLime100", "100", true, true); // --paper-lime-100: #f0f4c3;
	final public static Primary PAPER_LIME_200 = new Primary("#e6ee9c", "PaperLime200", "200", true, true); // --paper-lime-200: #e6ee9c;
	final public static Primary PAPER_LIME_300 = new Primary("#dce775", "PaperLime300", "300", true, true); // --paper-lime-300: #dce775;
	final public static Primary PAPER_LIME_400 = new Primary("#d4e157", "PaperLime400", "400", true, true); // --paper-lime-400: #d4e157;
	final public static Primary PAPER_LIME_500 = new Primary("#cddc39", "PaperLime500", "500", true, true); // --paper-lime-500: #cddc39;
	final public static Primary PAPER_LIME_600 = new Primary("#c0ca33", "PaperLime600", "600", true, true); // --paper-lime-600: #c0ca33;
	final public static Primary PAPER_LIME_700 = new Primary("#afb42b", "PaperLime700", "700", true, true); // --paper-lime-700: #afb42b;
	final public static Primary PAPER_LIME_800 = new Primary("#9e9d24", "PaperLime800", "800", true, true); // --paper-lime-800: #9e9d24;
	final public static Primary PAPER_LIME_900 = new Primary("#827717", "PaperLime900", "900", true, true); // --paper-lime-900: #827717;
	final public static Accent PAPER_LIME_A100 = new Accent("#f4ff81", "PaperLimeA100", "A100", true, true); // --paper-lime-a100: #f4ff81;
	final public static Accent PAPER_LIME_A200 = new Accent("#eeff41", "PaperLimeA200", "A200", true, true); // --paper-lime-a200: #eeff41;
	final public static Accent PAPER_LIME_A400 = new Accent("#c6ff00", "PaperLimeA400", "A400", true, true); // --paper-lime-a400: #c6ff00;
	final public static Accent PAPER_LIME_A700 = new Accent("#aeea00", "PaperLimeA700", "A700", true, true); // --paper-lime-a700: #aeea00;

	final public static PrimaryGroup PAPER_LIME = new PrimaryGroup
	(
		"Paper Lime",
		PAPER_LIME_50,
		PAPER_LIME_100,
		PAPER_LIME_200,
		PAPER_LIME_300,
		PAPER_LIME_400,
		PAPER_LIME_500,
		PAPER_LIME_600,
		PAPER_LIME_700,
		PAPER_LIME_800,
		PAPER_LIME_900
	);

	final public static AccentGroup PAPER_LIME_A = new AccentGroup
	(
		"Paper Lime",
		PAPER_LIME_A100,
		PAPER_LIME_A200,
		PAPER_LIME_A400,
		PAPER_LIME_A700
	);

	// Paper Yellow
	final public static Primary PAPER_YELLOW_50 = new Primary("#fffde7", "PaperYellow50", "50", true, true); // --paper-yellow-50: #fffde7;
	final public static Primary PAPER_YELLOW_100 = new Primary("#fff9c4", "PaperYellow100", "100", true, true); // --paper-yellow-100: #fff9c4;
	final public static Primary PAPER_YELLOW_200 = new Primary("#fff59d", "PaperYellow200", "200", true, true); // --paper-yellow-200: #fff59d;
	final public static Primary PAPER_YELLOW_300 = new Primary("#fff176", "PaperYellow300", "300", true, true); // --paper-yellow-300: #fff176;
	final public static Primary PAPER_YELLOW_400 = new Primary("#ffee58", "PaperYellow400", "400", true, true); // --paper-yellow-400: #ffee58;
	final public static Primary PAPER_YELLOW_500 = new Primary("#ffeb3b", "PaperYellow500", "500", true, true); // --paper-yellow-500: #ffeb3b;
	final public static Primary PAPER_YELLOW_600 = new Primary("#fdd835", "PaperYellow600", "600", true, true); // --paper-yellow-600: #fdd835;
	final public static Primary PAPER_YELLOW_700 = new Primary("#fbc02d", "PaperYellow700", "700", true, true); // --paper-yellow-700: #fbc02d;
	final public static Primary PAPER_YELLOW_800 = new Primary("#f9a825", "PaperYellow800", "800", true, true); // --paper-yellow-800: #f9a825;
	final public static Primary PAPER_YELLOW_900 = new Primary("#f57f17", "PaperYellow900", "900", true, true); // --paper-yellow-900: #f57f17;
	final public static Accent PAPER_YELLOW_A100 = new Accent("#ffff8d", "PaperYellowA100", "A100", true, true); // --paper-yellow-a100: #ffff8d;
	final public static Accent PAPER_YELLOW_A200 = new Accent("#ffff00", "PaperYellowA200", "A200", true, true); // --paper-yellow-a200: #ffff00;
	final public static Accent PAPER_YELLOW_A400 = new Accent("#ffea00", "PaperYellowA400", "A400", true, true); // --paper-yellow-a400: #ffea00;
	final public static Accent PAPER_YELLOW_A700 = new Accent("#ffd600", "PaperYellowA700", "A700", true, true); // --paper-yellow-a700: #ffd600;

	final public static PrimaryGroup PAPER_YELLOW = new PrimaryGroup
	(
		"Paper Yellow",
		PAPER_YELLOW_50,
		PAPER_YELLOW_100,
		PAPER_YELLOW_200,
		PAPER_YELLOW_300,
		PAPER_YELLOW_400,
		PAPER_YELLOW_500,
		PAPER_YELLOW_600,
		PAPER_YELLOW_700,
		PAPER_YELLOW_800,
		PAPER_YELLOW_900
	);

	final public static AccentGroup PAPER_YELLOW_A = new AccentGroup
	(
		"Paper Yellow",
		PAPER_YELLOW_A100,
		PAPER_YELLOW_A200,
		PAPER_YELLOW_A400,
		PAPER_YELLOW_A700
	);

	// Paper Amber
	final public static Primary PAPER_AMBER_50 = new Primary("#fff8e1", "PaperAmber50", "50", true, true); // --paper-amber-50: #fff8e1;
	final public static Primary PAPER_AMBER_100 = new Primary("#ffecb3", "PaperAmber100", "100", true, true); // --paper-amber-100: #ffecb3;
	final public static Primary PAPER_AMBER_200 = new Primary("#ffe082", "PaperAmber200", "200", true, true); // --paper-amber-200: #ffe082;
	final public static Primary PAPER_AMBER_300 = new Primary("#ffd54f", "PaperAmber300", "300", true, true); // --paper-amber-300: #ffd54f;
	final public static Primary PAPER_AMBER_400 = new Primary("#ffca28", "PaperAmber400", "400", true, true); // --paper-amber-400: #ffca28;
	final public static Primary PAPER_AMBER_500 = new Primary("#ffc107", "PaperAmber500", "500", true, true); // --paper-amber-500: #ffc107;
	final public static Primary PAPER_AMBER_600 = new Primary("#ffb300", "PaperAmber600", "600", true, true); // --paper-amber-600: #ffb300;
	final public static Primary PAPER_AMBER_700 = new Primary("#ffa000", "PaperAmber700", "700", true, true); // --paper-amber-700: #ffa000;
	final public static Primary PAPER_AMBER_800 = new Primary("#ff8f00", "PaperAmber800", "800", true, true); // --paper-amber-800: #ff8f00;
	final public static Primary PAPER_AMBER_900 = new Primary("#ff6f00", "PaperAmber900", "900", true, true); // --paper-amber-900: #ff6f00;
	final public static Accent PAPER_AMBER_A100 = new Accent("#ffe57f", "PaperAmberA100", "A100", true, true); // --paper-amber-a100: #ffe57f;
	final public static Accent PAPER_AMBER_A200 = new Accent("#ffd740", "PaperAmberA200", "A200", true, true); // --paper-amber-a200: #ffd740;
	final public static Accent PAPER_AMBER_A400 = new Accent("#ffc400", "PaperAmberA400", "A400", true, true); // --paper-amber-a400: #ffc400;
	final public static Accent PAPER_AMBER_A700 = new Accent("#ffab00", "PaperAmberA700", "A700", true, true); // --paper-amber-a700: #ffab00;

	final public static PrimaryGroup PAPER_AMBER = new PrimaryGroup
	(
		"Paper Amber",
		PAPER_AMBER_50,
		PAPER_AMBER_100,
		PAPER_AMBER_200,
		PAPER_AMBER_300,
		PAPER_AMBER_400,
		PAPER_AMBER_500,
		PAPER_AMBER_600,
		PAPER_AMBER_700,
		PAPER_AMBER_800,
		PAPER_AMBER_900
	);

	final public static AccentGroup PAPER_AMBER_A = new AccentGroup
	(
		"Paper Amber",
		PAPER_AMBER_A100,
		PAPER_AMBER_A200,
		PAPER_AMBER_A400,
		PAPER_AMBER_A700
	);

	// Paper Orange
	final public static Primary PAPER_ORANGE_50 = new Primary("#fff3e0", "PaperOrange50", "50", true, true); // --paper-orange-50: #fff3e0;
	final public static Primary PAPER_ORANGE_100 = new Primary("#ffe0b2", "PaperOrange100", "100", true, true); // --paper-orange-100: #ffe0b2;
	final public static Primary PAPER_ORANGE_200 = new Primary("#ffcc80", "PaperOrange200", "200", true, true); // --paper-orange-200: #ffcc80;
	final public static Primary PAPER_ORANGE_300 = new Primary("#ffb74d", "PaperOrange300", "300", true, true); // --paper-orange-300: #ffb74d;
	final public static Primary PAPER_ORANGE_400 = new Primary("#ffa726", "PaperOrange400", "400", true, true); // --paper-orange-400: #ffa726;
	final public static Primary PAPER_ORANGE_500 = new Primary("#ff9800", "PaperOrange500", "500", true, true); // --paper-orange-500: #ff9800;
	final public static Primary PAPER_ORANGE_600 = new Primary("#fb8c00", "PaperOrange600", "600", true, true); // --paper-orange-600: #fb8c00;
	final public static Primary PAPER_ORANGE_700 = new Primary("#f57c00", "PaperOrange700", "700", true, true); // --paper-orange-700: #f57c00;
	final public static Primary PAPER_ORANGE_800 = new Primary("#ef6c00", "PaperOrange800", "800", true, true); // --paper-orange-800: #ef6c00;
	final public static Primary PAPER_ORANGE_900 = new Primary("#e65100", "PaperOrange900", "900", true, true); // --paper-orange-900: #e65100;
	final public static Accent PAPER_ORANGE_A100 = new Accent("#ffd180", "PaperOrangeA100", "A100", true, true); // --paper-orange-a100: #ffd180;
	final public static Accent PAPER_ORANGE_A200 = new Accent("#ffab40", "PaperOrangeA200", "A200", true, true); // --paper-orange-a200: #ffab40;
	final public static Accent PAPER_ORANGE_A400 = new Accent("#ff9100", "PaperOrangeA400", "A400", true, true); // --paper-orange-a400: #ff9100;
	final public static Accent PAPER_ORANGE_A700 = new Accent("#ff6500", "PaperOrangeA700", "A700", true, true); // --paper-orange-a700: #ff6500;

	final public static PrimaryGroup PAPER_ORANGE = new PrimaryGroup
	(
		"Paper Orange",
		PAPER_ORANGE_50,
		PAPER_ORANGE_100,
		PAPER_ORANGE_200,
		PAPER_ORANGE_300,
		PAPER_ORANGE_400,
		PAPER_ORANGE_500,
		PAPER_ORANGE_600,
		PAPER_ORANGE_700,
		PAPER_ORANGE_800,
		PAPER_ORANGE_900
	);

	final public static AccentGroup PAPER_ORANGE_A = new AccentGroup
	(
		"Paper Orange",
		PAPER_ORANGE_A100,
		PAPER_ORANGE_A200,
		PAPER_ORANGE_A400,
		PAPER_ORANGE_A700
	);

	// Paper Deep Orange
	final public static Primary PAPER_DEEP_ORANGE_50 = new Primary("#fbe9e7", "PaperDeepOrange50", "50", true, true); // --paper-deep-orange-50: #fbe9e7;
	final public static Primary PAPER_DEEP_ORANGE_100 = new Primary("#ffccbc", "PaperDeepOrange100", "100", true, true); // --paper-deep-orange-100: #ffccbc;
	final public static Primary PAPER_DEEP_ORANGE_200 = new Primary("#ffab91", "PaperDeepOrange200", "200", true, true); // --paper-deep-orange-200: #ffab91;
	final public static Primary PAPER_DEEP_ORANGE_300 = new Primary("#ff8a65", "PaperDeepOrange300", "300", true, true); // --paper-deep-orange-300: #ff8a65;
	final public static Primary PAPER_DEEP_ORANGE_400 = new Primary("#ff7043", "PaperDeepOrange400", "400", true, true); // --paper-deep-orange-400: #ff7043;
	final public static Primary PAPER_DEEP_ORANGE_500 = new Primary("#ff5722", "PaperDeepOrange500", "500", true, true); // --paper-deep-orange-500: #ff5722;
	final public static Primary PAPER_DEEP_ORANGE_600 = new Primary("#f4511e", "PaperDeepOrange600", "600", true, true); // --paper-deep-orange-600: #f4511e;
	final public static Primary PAPER_DEEP_ORANGE_700 = new Primary("#e64a19", "PaperDeepOrange700", "700", true, true); // --paper-deep-orange-700: #e64a19;
	final public static Primary PAPER_DEEP_ORANGE_800 = new Primary("#d84315", "PaperDeepOrange800", "800", true, true); // --paper-deep-orange-800: #d84315;
	final public static Primary PAPER_DEEP_ORANGE_900 = new Primary("#bf360c", "PaperDeepOrange900", "900", true, true); // --paper-deep-orange-900: #bf360c;
	final public static Accent PAPER_DEEP_ORANGE_A100 = new Accent("#ff9e80", "PaperDeepOrangeA100", "A100", true, true); // --paper-deep-orange-a100: #ff9e80;
	final public static Accent PAPER_DEEP_ORANGE_A200 = new Accent("#ff6e40", "PaperDeepOrangeA200", "A200", true, true); // --paper-deep-orange-a200: #ff6e40;
	final public static Accent PAPER_DEEP_ORANGE_A400 = new Accent("#ff3d00", "PaperDeepOrangeA400", "A400", true, true); // --paper-deep-orange-a400: #ff3d00;
	final public static Accent PAPER_DEEP_ORANGE_A700 = new Accent("#dd2c00", "PaperDeepOrangeA700", "A700", true, true); // --paper-deep-orange-a700: #dd2c00;

	final public static PrimaryGroup PAPER_DEEP_ORANGE = new PrimaryGroup
	(
		"Paper Deep Orange",
		PAPER_DEEP_ORANGE_50,
		PAPER_DEEP_ORANGE_100,
		PAPER_DEEP_ORANGE_200,
		PAPER_DEEP_ORANGE_300,
		PAPER_DEEP_ORANGE_400,
		PAPER_DEEP_ORANGE_500,
		PAPER_DEEP_ORANGE_600,
		PAPER_DEEP_ORANGE_700,
		PAPER_DEEP_ORANGE_800,
		PAPER_DEEP_ORANGE_900
	);

	final public static AccentGroup PAPER_DEEP_ORANGE_A = new AccentGroup
	(
		"Paper Deep Orange",
		PAPER_DEEP_ORANGE_A100,
		PAPER_DEEP_ORANGE_A200,
		PAPER_DEEP_ORANGE_A400,
		PAPER_DEEP_ORANGE_A700
	);

	// Paper Brown
	final public static Primary PAPER_BROWN_50 = new Primary("#efebe9", "PaperBrown50", "50", true, true); // --paper-brown-50: #efebe9;
	final public static Primary PAPER_BROWN_100 = new Primary("#d7ccc8", "PaperBrown100", "100", true, true); // --paper-brown-100: #d7ccc8;
	final public static Primary PAPER_BROWN_200 = new Primary("#bcaaa4", "PaperBrown200", "200", true, true); // --paper-brown-200: #bcaaa4;
	final public static Primary PAPER_BROWN_300 = new Primary("#a1887f", "PaperBrown300", "300", true, true); // --paper-brown-300: #a1887f;
	final public static Primary PAPER_BROWN_400 = new Primary("#8d6e63", "PaperBrown400", "400", true, true); // --paper-brown-400: #8d6e63;
	final public static Primary PAPER_BROWN_500 = new Primary("#795548", "PaperBrown500", "500", true, true); // --paper-brown-500: #795548;
	final public static Primary PAPER_BROWN_600 = new Primary("#6d4c41", "PaperBrown600", "600", true, true); // --paper-brown-600: #6d4c41;
	final public static Primary PAPER_BROWN_700 = new Primary("#5d4037", "PaperBrown700", "700", true, true); // --paper-brown-700: #5d4037;
	final public static Primary PAPER_BROWN_800 = new Primary("#4e342e", "PaperBrown800", "800", true, true); // --paper-brown-800: #4e342e;
	final public static Primary PAPER_BROWN_900 = new Primary("#3e2723", "PaperBrown900", "900", true, true); // --paper-brown-900: #3e2723;

	final public static PrimaryGroup PAPER_BROWN = new PrimaryGroup
	(
		"Paper Brown",
		PAPER_BROWN_50,
		PAPER_BROWN_100,
		PAPER_BROWN_200,
		PAPER_BROWN_300,
		PAPER_BROWN_400,
		PAPER_BROWN_500,
		PAPER_BROWN_600,
		PAPER_BROWN_700,
		PAPER_BROWN_800,
		PAPER_BROWN_900
	);

	// Paper Grey
	final public static Primary PAPER_GREY_50 = new Primary("#fafafa", "PaperGrey50", "50", false, true); // --paper-grey-50: #fafafa;
	final public static Primary PAPER_GREY_100 = new Primary("#f5f5f5", "PaperGrey100", "100", true, true); // --paper-grey-100: #f5f5f5;
	final public static Primary PAPER_GREY_200 = new Primary("#eeeeee", "PaperGrey200", "200", true, true); // --paper-grey-200: #eeeeee;
	final public static Primary PAPER_GREY_300 = new Primary("#e0e0e0", "PaperGrey300", "300", true, true); // --paper-grey-300: #e0e0e0;
	final public static Primary PAPER_GREY_400 = new Primary("#bdbdbd", "PaperGrey400", "400", true, true); // --paper-grey-400: #bdbdbd;
	final public static Primary PAPER_GREY_500 = new Primary("#9e9e9e", "PaperGrey500", "500", true, true); // --paper-grey-500: #9e9e9e;
	final public static Primary PAPER_GREY_600 = new Primary("#757575", "PaperGrey600", "600", true, true); // --paper-grey-600: #757575;
	final public static Primary PAPER_GREY_700 = new Primary("#616161", "PaperGrey700", "700", true, true); // --paper-grey-700: #616161;
	final public static Primary PAPER_GREY_800 = new Primary("#424242", "PaperGrey800", "800", true, true); // --paper-grey-800: #424242;
	final public static Primary PAPER_GREY_900 = new Primary("#212121", "PaperGrey900", "900", true, true); // --paper-grey-900: #212121;

	final public static PrimaryGroup PAPER_GREY = new PrimaryGroup
	(
		"Paper Grey",
		PAPER_GREY_50,
		PAPER_GREY_100,
		PAPER_GREY_200,
		PAPER_GREY_300,
		PAPER_GREY_400,
		PAPER_GREY_500,
		PAPER_GREY_600,
		PAPER_GREY_700,
		PAPER_GREY_800,
		PAPER_GREY_900
	);

	// Paper Blue Grey
	final public static Primary PAPER_BLUE_GREY_50 = new Primary("#eceff1", "PaperBlueGrey50", "50", false, true); // --paper-blue-grey-50: #eceff1;
	final public static Primary PAPER_BLUE_GREY_100 = new Primary("#cfd8dc", "PaperBlueGrey100", "100", true, true); // --paper-blue-grey-100: #cfd8dc;
	final public static Primary PAPER_BLUE_GREY_200 = new Primary("#b0bec5", "PaperBlueGrey200", "200", true, true); // --paper-blue-grey-200: #b0bec5;
	final public static Primary PAPER_BLUE_GREY_300 = new Primary("#90a4ae", "PaperBlueGrey300", "300", true, true); // --paper-blue-grey-300: #90a4ae;
	final public static Primary PAPER_BLUE_GREY_400 = new Primary("#78909c", "PaperBlueGrey400", "400", true, true); // --paper-blue-grey-400: #78909c;
	final public static Primary PAPER_BLUE_GREY_500 = new Primary("#607d8b", "PaperBlueGrey500", "500", true, true); // --paper-blue-grey-500: #607d8b;
	final public static Primary PAPER_BLUE_GREY_600 = new Primary("#546e7a", "PaperBlueGrey600", "600", true, true); // --paper-blue-grey-600: #546e7a;
	final public static Primary PAPER_BLUE_GREY_700 = new Primary("#455a64", "PaperBlueGrey700", "700", true, true); // --paper-blue-grey-700: #455a64;
	final public static Primary PAPER_BLUE_GREY_800 = new Primary("#37474f", "PaperBlueGrey800", "800", true, true); // --paper-blue-grey-800: #37474f;
	final public static Primary PAPER_BLUE_GREY_900 = new Primary("#263238", "PaperBlueGrey900", "900", true, true); // --paper-blue-grey-900: #263238;

	final public static PrimaryGroup PAPER_BLUE_GREY = new PrimaryGroup
	(
		"Paper Blue Grey",
		PAPER_BLUE_GREY_50,
		PAPER_BLUE_GREY_100,
		PAPER_BLUE_GREY_200,
		PAPER_BLUE_GREY_300,
		PAPER_BLUE_GREY_400,
		PAPER_BLUE_GREY_500,
		PAPER_BLUE_GREY_600,
		PAPER_BLUE_GREY_700,
		PAPER_BLUE_GREY_800,
		PAPER_BLUE_GREY_900
	);

	// Google Red
	final public static Primary GOOGLE_RED_100 = new Primary("#f4c7c3", "GoogleRed100", "100", true, true); // --google-red-100: #f4c7c3;
	final public static Primary GOOGLE_RED_300 = new Primary("#e67c73", "GoogleRed300", "300", true, true); // --google-red-300: #e67c73;
	final public static Primary GOOGLE_RED_500 = new Primary("#db4437", "GoogleRed500", "500", true, true); // --google-red-500: #db4437;
	final public static Primary GOOGLE_RED_700 = new Primary("#c53929", "GoogleRed700", "700", true, true); // --google-red-700: #c53929;

	final public static PrimaryGroup GOOGLE_RED = new PrimaryGroup
	(
		"Google Red",
		GOOGLE_RED_100,
		GOOGLE_RED_300,
		GOOGLE_RED_500,
		GOOGLE_RED_700
	);

	// Google Blue
	final public static Primary GOOGLE_BLUE_100 = new Primary("#c6dafc", "GoogleBlue100", "100", true, true); // --google-blue-100: #c6dafc;
	final public static Primary GOOGLE_BLUE_300 = new Primary("#7baaf7", "GoogleBlue300", "300", true, true); // --google-blue-300: #7baaf7;
	final public static Primary GOOGLE_BLUE_500 = new Primary("#4285f4", "GoogleBlue500", "500", true, true); // --google-blue-500: #4285f4;
	final public static Primary GOOGLE_BLUE_700 = new Primary("#3367d6", "GoogleBlue700", "700", true, true); // --google-blue-700: #3367d6;

	final public static PrimaryGroup GOOGLE_BLUE = new PrimaryGroup
	(
		"Google Blue",
		GOOGLE_BLUE_100,
		GOOGLE_BLUE_300,
		GOOGLE_BLUE_500,
		GOOGLE_BLUE_700
	);

	// Google Green
	final public static Primary GOOGLE_GREEN_100 = new Primary("#b7e1cd", "GoogleGreen100", "100", true, true); // --google-green-100: #b7e1cd;
	final public static Primary GOOGLE_GREEN_300 = new Primary("#57bb8a", "GoogleGreen300", "300", true, true); // --google-green-300: #57bb8a;
	final public static Primary GOOGLE_GREEN_500 = new Primary("#0f9d58", "GoogleGreen500", "500", true, true); // --google-green-500: #0f9d58;
	final public static Primary GOOGLE_GREEN_700 = new Primary("#0b8043", "GoogleGreen700", "700", true, true); // --google-green-700: #0b8043;

	final public static PrimaryGroup GOOGLE_GREEN = new PrimaryGroup
	(
		"Google Green",
		GOOGLE_GREEN_100,
		GOOGLE_GREEN_300,
		GOOGLE_GREEN_500,
		GOOGLE_GREEN_700
	);

	// Google Yellow
	final public static Primary GOOGLE_YELLOW_100 = new Primary("#fce8b2", "GoogleYellow100", "100", true, true); // --google-yellow-100: #fce8b2;
	final public static Primary GOOGLE_YELLOW_300 = new Primary("#f7cb4d", "GoogleYellow300", "300", true, true); // --google-yellow-300: #f7cb4d;
	final public static Primary GOOGLE_YELLOW_500 = new Primary("#f4b400", "GoogleYellow500", "500", true, true); // --google-yellow-500: #f4b400;
	final public static Primary GOOGLE_YELLOW_700 = new Primary("#f09300", "GoogleYellow700", "700", true, true); // --google-yellow-700: #f09300;

	final public static PrimaryGroup GOOGLE_YELLOW = new PrimaryGroup
	(
		"Google Yellow",
		GOOGLE_YELLOW_100,
		GOOGLE_YELLOW_300,
		GOOGLE_YELLOW_500,
		GOOGLE_YELLOW_700
	);

	//
	final public PrimaryGroup[] PRIMARY =
	{
		PAPER_RED,
		PAPER_PINK,
		PAPER_PURPLE,
		PAPER_DEEP_PURPLE,
		PAPER_INDIGO,
		PAPER_BLUE,
		PAPER_LIGHT_BLUE,
		PAPER_CYAN,
		PAPER_TEAL,
		PAPER_GREEN,
		PAPER_LIGHT_GREEN,
		PAPER_LIME,
		PAPER_YELLOW,
		PAPER_AMBER,
		PAPER_ORANGE,
		PAPER_DEEP_ORANGE,
		PAPER_BROWN,
		PAPER_GREY,
		PAPER_BLUE_GREY,
		GOOGLE_RED,
		GOOGLE_BLUE,
		GOOGLE_GREEN,
		GOOGLE_YELLOW
	};

	final public AccentGroup[] ACCENT =
	{
		PAPER_RED_A,
		PAPER_PINK_A,
		PAPER_PURPLE_A,
		PAPER_DEEP_PURPLE_A,
		PAPER_INDIGO_A,
		PAPER_BLUE_A,
		PAPER_LIGHT_BLUE_A,
		PAPER_CYAN_A,
		PAPER_TEAL_A,
		PAPER_GREEN_A,
		PAPER_LIGHT_GREEN_A,
		PAPER_LIME_A,
		PAPER_YELLOW_A,
		PAPER_AMBER_A,
		PAPER_ORANGE_A,
		PAPER_DEEP_ORANGE_A
	};


	//
	final public static class Primary
		extends Color.Preset
	{
		final public String CODE;
		final public boolean LIGHT;
		final public boolean DARK;

		private Primary(String hex, String name, String code, boolean light, boolean dark)
		{
			super(hex, name);

			CODE = code;
			LIGHT = light;
			DARK = dark;
		}
	}


	final public static class Accent
		extends Color.Preset
	{
		final public String CODE;
		final public boolean LIGHT;
		final public boolean DARK;

		private Accent(String hex, String name, String code, boolean light, boolean dark)
		{
			super(hex, name);

			CODE = code;
			LIGHT = light;
			DARK = dark;
		}
	}


	final public static class PrimaryGroup
	{
		final public String NAME;

		final private Primary[] ENTRIES;

		//
		private PrimaryGroup(String name, Primary ... entries)
		{
			NAME = name;
			ENTRIES = entries;
		}

		//
		public Primary[] entries()
		{
			return ENTRIES.clone();
		}
	}


	final public static class AccentGroup
	{
		final public String NAME;

		final private Accent[] ENTRIES;

		//
		private AccentGroup(String name, Accent ... entries)
		{
			NAME = name;
			ENTRIES = entries;
		}

		//
		public Accent[] entries()
		{
			return ENTRIES.clone();
		}
	}

}
