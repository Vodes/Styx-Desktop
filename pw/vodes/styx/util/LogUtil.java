package pw.vodes.styx.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;

import org.apache.commons.io.output.TeeOutputStream;

public class LogUtil {
	
	
	public static File logdirectory = new File(System.getProperty("user.home"), "Vodes" + File.separator + "Logs");
	private static File logfile = new File(logdirectory, "latest.txt");
	
	public static void setupLog() {
		if(!logdirectory.exists()) {
			logdirectory.mkdirs();
		}
		
		if(logfile.exists()) {
			logfile.delete();
		}
		
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(logfile));
			writer.close();
			TeeOutputStream multiOutput = new TeeOutputStream(System.out, new PrintStream(logfile));
			PrintStream output = new PrintStream(multiOutput, true);
			System.setOut(output);
			System.setErr(output);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
