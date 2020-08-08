package pw.vodes.styx.util.threads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.lang3.StringUtils;

import pw.vodes.styx.Main;
import pw.vodes.styx.Styx;
import pw.vodes.styx.connection.discord.DiscordIntegration;
import pw.vodes.styx.core.Core;
import pw.vodes.styx.util.Sys;
import pw.vodes.styx.util.multios.CommandLineUtil;
import pw.vodes.styx.util.multios.OS;

public class PlayingThread extends Thread {

	@SuppressWarnings("static-access")
	@Override
	public void run() {
		try {
			this.sleep(10000L);
		} catch (Exception e) {
			e.printStackTrace();
		}
		while (true) {
			try {
				if (!isProcessRunning((Core.getInstance().getOptionmanager().getBoolean("MPV") ? "mpv.exe" : "vlc.exe"))) {
					Sys.out((Core.getInstance().getOptionmanager().getBoolean("MPV") ? "MPV" : "VLC")
							+ " has been closed. Resetting Watching-Status.");
					Styx.getInstance().current = null;
					if (Core.getInstance().getOptionmanager().getBoolean("DiscordRPC")) {
						DiscordIntegration.reset();
					}
					break;
				}
				this.sleep(4500L);
			} catch (Exception io) {

			}
		}
	}

	public boolean isProcessRunning(String process) throws Exception {
		String name = CommandLineUtil.getOS() == OS.Windows ? process : StringUtils.removeIgnoreCase(process, ".exe");
		return CommandLineUtil.isProcessRunning(name);
//		String line;
//		String pidInfo = "";
//
//		Process p;
//
////		if (Main.linuxmode) {
////			p = Runtime.getRuntime().exec("ps -e");
////		} else {
//			p = Runtime.getRuntime().exec(System.getenv("windir") + "\\system32\\" + "tasklist.exe");
////		}
//
//		BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
//
//		while ((line = input.readLine()) != null) {
//			pidInfo += line;
//		}
//
//		input.close();
//
//		if (StringUtils.containsIgnoreCase(pidInfo, /*(Main.linuxmode ? process.replace(".exe", "") : */process)) {
//			return true;
//		}
//
//		return false;
	}

}
