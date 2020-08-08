package pw.vodes.styx.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Sys {
	
	private static String debug = "[INFO] ";
	private static String error = "[ERROR] ";
	private static String warn = "[WARN] ";
	
	public static void out(String message, String type) {
		String prefix = "";
		String timestamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
		
		if(type.equalsIgnoreCase("debug") || type.equalsIgnoreCase("info") || type.equalsIgnoreCase("infos")) {
			prefix = debug;
		} else if (type.equalsIgnoreCase("error")) {
			prefix = error;
		} else if (type.equalsIgnoreCase("warn")) {
			prefix = warn;
		}
		
		System.out.println("[" + timestamp + "] " + prefix + message);
	}
	
	public static void out(String message) {
		out(message, "info");
	}

}