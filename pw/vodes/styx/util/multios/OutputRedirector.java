package pw.vodes.styx.util.multios;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import pw.vodes.styx.core.Core;

public class OutputRedirector extends Thread {

	InputStream in;
	File logFile;
	
	public OutputRedirector(InputStream in, File logFile) {
		this.in = in;
		this.logFile = logFile;
	}

	public void run() {
		try {
			InputStreamReader isr = new InputStreamReader(in);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null) {
				Core.getInstance().getFilemanager().writeFile(logFile, line, true);
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

}