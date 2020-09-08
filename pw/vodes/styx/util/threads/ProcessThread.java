package pw.vodes.styx.util.threads;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import pw.vodes.styx.util.LogUtil;
import pw.vodes.styx.util.multios.OutputRedirector;

public class ProcessThread extends Thread {
	
	private ProcessBuilder processbuilder;
	private File logFile = new File(LogUtil.logdirectory, "log" + new Random().nextInt(9999) + ".txt");
	
	public ProcessThread(ProcessBuilder processbuilder) {
		this.processbuilder = processbuilder;
	}
	
	@Override
	public void run() {
		Process p;
		try {
			logFile.createNewFile();
			p = processbuilder.start();
			new OutputRedirector(p.getInputStream(), logFile).start();
			new OutputRedirector(p.getErrorStream(), logFile).start();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		try {
			p.waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		logFile.deleteOnExit();
		super.run();
	}

}
