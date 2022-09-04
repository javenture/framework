package net.javenture.framework.netty;


import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.util.Validator;

import java.io.File;
import java.util.concurrent.ConcurrentHashMap;


/*
	2022/01/20

	https://www.iana.org/assignments/media-types/media-types.xhtml
 */
final public class Media
{
	//
	final private static Object LOCK = new Object();
	final private static ConcurrentHashMap<String, Media> VALUES = new ConcurrentHashMap<>(256);
	final public static Media DEFAULT = new Media("", "application/octet-stream", false);


	//
	final public @NullDisallow String EXTENSION;
	final public @NullDisallow String MIME;
	final public boolean CHARSET;
	final String HEADER;


	//
	private Media(String extension, String mime, boolean charset)
	{
		EXTENSION = "." + extension; // ! "."
		MIME = mime;
		CHARSET = charset;
		HEADER = charset ? mime + "; charset=" + NettyDefault.CHARSET : mime;
	}


	//
	@Override
	public String toString()
	{
		return MIME;
	}


	//
	public static Media register(@NullDisallow String extension, @NullDisallow String mime, boolean charset)
	{
		Validator.notNull(extension, "[extension]");
		Validator.argument(!extension.isEmpty(), "[extension] is empty");
		Validator.notNull(mime, "[mime]");
		Validator.argument(!mime.isEmpty(), "[mime] is empty");

		synchronized (LOCK)
		{
			Validator.argument(!VALUES.containsKey(extension), "MimeType [mime] (", mime, ") with [extension] (", extension, ") already registered");
			Media media = new Media(extension, mime, charset);
			VALUES.put(extension, media);

			return media;
		}
	}


	public static Media search(@NullDisallow String extension)
	{
		Validator.notNull(extension, "[extension]");

		if (!extension.isEmpty())
		{
			Media media = VALUES.get(extension);

			if (media != null) return media;
		}

		return DEFAULT;
	}


	public static Media parse(@NullDisallow File file)
	{
		return parse(file.getName());
	}


	public static Media parse(@NullDisallow String file)
	{
		int index = file.lastIndexOf('.');

		return index >= 0
			? search(file.substring(index + 1))
			: DEFAULT;
	}


	// text
	final public static Media CSS = Media.register("css", "text/css", true);
	final public static Media HTM = Media.register("htm", "text/html", true);
	final public static Media HTML = Media.register("html", "text/html", true);
	final public static Media TXT = Media.register("txt", "text/plain", true);
	final public static Media XML = Media.register("xml", "text/xml", true);

	// image
	final public static Media APNG = Media.register("apng", "image/apng", false);
	final public static Media AVIF = Media.register("avif", "image/avif", false);
	final public static Media BMP = Media.register("bmp", "image/x-ms-bmp", false);
	final public static Media CR2 = Media.register("cr2", "image/x-canon-cr2", false);
	final public static Media DNG = Media.register("dng", "image/x-adobe-dng", false);
	final public static Media GIF = Media.register("gif", "image/gif", false);
	final public static Media ICO = Media.register("ico", "image/x-icon", false);
	final public static Media JPEG = Media.register("jpeg", "image/jpeg", false);
	final public static Media JPG = Media.register("jpg", "image/jpeg", false);
	final public static Media GPR = Media.register("gpr", "image/gpr", false);
	final public static Media PNG = Media.register("png", "image/png", false);
	final public static Media SVG = Media.register("svg", "image/svg+xml", false);
	final public static Media SVGZ = Media.register("svgz", "image/svg+xml", false);
	final public static Media TIFF = Media.register("tiff", "image/tiff", false);
	final public static Media TIF = Media.register("tif", "image/tiff", false);
	final public static Media WEBP = Media.register("webp", "image/webp", false);

	// audio
	final public static Media M4A = Media.register("m4a", "audio/x-m4a", false);
	final public static Media MP3 = Media.register("mp3", "audio/mpeg", false);
	final public static Media MIDI = Media.register("midi", "audio/midi", false);
	final public static Media MID = Media.register("mid", "audio/midi", false);
	final public static Media OGG = Media.register("ogg", "audio/ogg", false);
	final public static Media RA = Media.register("ra", "audio/x-realaudio", false);

	// video
	final public static Media ASF = Media.register("asf", "video/x-ms-asf", false);
	final public static Media ASX = Media.register("asx", "video/x-ms-asf", false);
	final public static Media AVI = Media.register("avi", "video/x-msvideo", false);
	final public static Media FLV = Media.register("flv", "video/x-flv", false);
	final public static Media M4V = Media.register("m4v", "video/x-m4v", false);
	final public static Media MOV = Media.register("mov", "video/quicktime", false);
	final public static Media MP4 = Media.register("mp4", "video/mp4", false);
	final public static Media MPEG = Media.register("mpeg", "video/mpeg", false);
	final public static Media MPG = Media.register("mpg", "video/mpeg", false);
	final public static Media TREEGPP = Media.register("3gpp", "video/3gpp", false);
	final public static Media TREEGP = Media.register("3gp", "video/3gpp", false);
	final public static Media TS = Media.register("ts", "video/mp2t", false);
	final public static Media WEBM = Media.register("webm", "video/webm", false);
	final public static Media WMV = Media.register("wmv", "video/x-ms-wmv", false);

	// font
	final public static Media OTF = Media.register("otf", "font/otf", false);
	final public static Media TTF = Media.register("ttf", "font/ttf", false);
	final public static Media WOFF = Media.register("woff", "font/woff", false);
	final public static Media WOFF2 = Media.register("woff2", "font/woff2", false);

	// application
	final public static Media SEVENZ = Media.register("7z", "application/x-7z-compressed", false);
	final public static Media AI = Media.register("ai", "application/postscript", false);
	final public static Media ATOM = Media.register("atom", "application/atom+xml", true);
	final public static Media BIN = Media.register("bin", "application/octet-stream", false);
	final public static Media CRT = Media.register("crt", "application/x-x509-ca-cert", false);
	final public static Media DEB = Media.register("deb", "application/octet-stream", false);
	final public static Media DER = Media.register("der", "application/x-x509-ca-cert", false);
	final public static Media DLL = Media.register("dll", "application/octet-stream", false);
	final public static Media DMG = Media.register("dmg", "application/octet-stream", false);
	final public static Media DOC = Media.register("doc", "application/msword", false);
	final public static Media DOCX = Media.register("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document", false);
	final public static Media EAR = Media.register("ear", "application/java-archive", false);
	final public static Media EOT = Media.register("eot", "application/vnd.ms-fontobject", false);
	final public static Media EPS = Media.register("eps", "application/postscript", false);
	final public static Media EXE = Media.register("exe", "application/octet-stream", false);
	final public static Media GZ = Media.register("gz", "application/gzip", false);
	final public static Media IMG = Media.register("img", "application/octet-stream", false);
	final public static Media ISO = Media.register("iso", "application/octet-stream", false);
	final public static Media JAR = Media.register("jar", "application/java-archive", false);
	final public static Media JS = Media.register("js", "application/javascript", true);
	final public static Media JSON = Media.register("json", "application/json", true);
	final public static Media MANIFEST = Media.register("webmanifest", "application/manifest+json", true);
	final public static Media MSI = Media.register("msi", "application/octet-stream", false);
	final public static Media PDF = Media.register("pdf", "application/pdf", false);
	final public static Media PEM = Media.register("pem", "application/x-x509-ca-cert", false);
	final public static Media PS = Media.register("ps", "application/postscript", false);
	final public static Media PPT = Media.register("ppt", "application/vnd.ms-powerpoint", false);
	final public static Media PPTX = Media.register("pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation", false);
	final public static Media RTF = Media.register("rtf", "application/rtf", false);
	final public static Media RAR = Media.register("rar", "application/x-rar-compressed", false);
	final public static Media RPM = Media.register("rpm", "application/x-redhat-package-manager", false);
	final public static Media RSS = Media.register("rss", "application/rss+xml", true);
	final public static Media WAR = Media.register("war", "application/java-archive", false);
	final public static Media XLS = Media.register("xls", "application/vnd.ms-excel", false);
	final public static Media XLSX = Media.register("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", false);
	final public static Media ZIP = Media.register("zip", "application/zip", false);

}
