package pw.vodes.styx;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;

import org.apache.commons.io.output.TeeOutputStream;

import pw.vodes.styx.util.LogUtil;
import pw.vodes.styx.util.threads.ShutdownThread;

public class Main {
		
	public static void main(String[] args) {
		System.setProperty("http.agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.29 Safari/537.36");
		Runtime.getRuntime().addShutdownHook(new ShutdownThread());
		LogUtil.setupLog();
		Styx.getInstance().init();
	}

}
