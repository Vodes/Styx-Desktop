package pw.vodes.styx.util.multios;

import java.io.File;
import java.util.ArrayList;

import pw.vodes.styx.core.Core;
import pw.vodes.styx.util.LogUtil;
import pw.vodes.styx.util.Sys;
import pw.vodes.styx.util.mpv.MpvDownloader;

public class PlayerUtil {

	public static void startPlayer(String path) {
		ProcessBuilder p;
		try {
			File logFile = new File(LogUtil.logdirectory, "player.txt");
			if(logFile.exists()) {
				logFile.delete();
			}
			ArrayList<String> commandList = new ArrayList<>();
			boolean preferGer = Core.getInstance().getOptionmanager().getBoolean("Prefer-German");
			boolean preferDub = Core.getInstance().getOptionmanager().getBoolean("Prefer-Dub");
			if (CommandLineUtil.getOS() == OS.Windows) {
				commandList.add(MpvDownloader.getLatestDir().getAbsolutePath() + File.separator + "mpv.exe");
				commandList.add("--profile=" + Core.getInstance().getOptionmanager().getString("mpv-Profile").replaceAll("-", "_"));
				if(preferGer) {
					commandList.add("--slang=ger,deu,eng,en");
				}
				if(preferDub) {
					commandList.add("--alang=eng,en,jp,jpn");
					commandList.add("--slang=mul,und,jp,jpn,eng,en");
				}
				commandList.add("\"" + path + "\"");
			} else {
				commandList.add("mpv --profile=" + Core.getInstance().getOptionmanager().getString("mpv-Profile").replaceAll("-", "_") + " " + (preferGer ? "--slang=ger,deu,eng,en " : "") + (preferDub ? "--alang=eng,en,jp,jpn --slang=mul,und,jp,jpn,eng,en " : "") + "\"" + path + "\"");
			}
			for(String s : commandList) {
				Sys.out(s);
			}
			CommandLineUtil.runCommand(commandList);
		} catch (Exception io) {

		}

	}

}
