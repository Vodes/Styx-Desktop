package pw.vodes.styx.util.multios;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import pw.vodes.styx.core.Core;
import pw.vodes.styx.util.LogUtil;
import pw.vodes.styx.util.PlayerDL;
import pw.vodes.styx.util.Sys;
import pw.vodes.styx.util.threads.ProcessThread;

public class PlayerUtil {

//	public static void startPlayer(File f) {
//		ProcessBuilder p;
//		try {
//			File logFile = new File(LogUtil.logdirectory, "player.txt");
//			if(logFile.exists()) {
//				logFile.delete();
//			}
//			if (Core.getInstance().getOptionmanager().getBoolean("MPV")) {
//				if (CommandLineUtil.getOS() == OS.Windows) {
//					p = new ProcessBuilder(PlayerDL.latestDirectory.getAbsolutePath() + File.separator + "mpv.exe", f.getAbsolutePath()).directory(PlayerDL.latestDirectory);
//					new ProcessThread(p).start();
//				} else {
//					CommandLineUtil.runCommand("mpv \"" + f.getAbsolutePath() + "\"");
//				}
//			} else {
//				if (CommandLineUtil.getOS() == OS.Windows) {
//					p = new ProcessBuilder(PlayerDL.latestDirectory.getAbsolutePath() + File.separator + "vlc.exe", f.getAbsolutePath()).directory(PlayerDL.latestDirectory);
//					new ProcessThread(p).start();
//				} else {
//					CommandLineUtil.runCommand("vlc \"" + f.getAbsolutePath() + "\"");
//				}
//			}
//		} catch (Exception io) {
//
//		}
//
//	}

	public static void startPlayer(String path) {
		ProcessBuilder p;
		try {
			File logFile = new File(LogUtil.logdirectory, "player.txt");
			if(logFile.exists()) {
				logFile.delete();
			}
			ArrayList<String> commandList = new ArrayList<>();
			if (Core.getInstance().getOptionmanager().getBoolean("MPV")) {
				if (CommandLineUtil.getOS() == OS.Windows) {
					commandList.add(PlayerDL.latestDirectory.getAbsolutePath() + File.separator + "mpv.exe");
					commandList.add("\"" + path + "\"");
				} else {
					commandList.add("mpv " + "\"" + path + "\"");
//					commandList.add("\"" + path + "\"");
				}
				CommandLineUtil.runCommand(commandList);
			} else {
				if (CommandLineUtil.getOS() == OS.Windows) {
					commandList.add(PlayerDL.latestDirectory.getAbsolutePath() + File.separator + "vlc.exe");
					commandList.add("\"" + path + "\"");
				} else {
					commandList.add("vlc " + "\"" + path + "\"");
//					commandList.add("\"" + path + "\"");
				}
				CommandLineUtil.runCommand(commandList);
			}
		} catch (Exception io) {

		}

	}

}
