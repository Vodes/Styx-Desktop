package pw.vodes.styx.util.threads;

import java.io.File;

import pw.vodes.styx.Styx;
import pw.vodes.styx.connection.discord.DiscordIntegration;
import pw.vodes.styx.core.Core;
import pw.vodes.styx.util.LogUtil;
import pw.vodes.styx.util.Sys;

public class ShutdownThread extends Thread {
	
	@Override
	public void run() {
		if(!Styx.getInstance().isOffline && Styx.getInstance().user != "") {
			if(Core.getInstance().getOptionmanager().getBoolean("DiscordRPC")) {
				DiscordIntegration.shutdown();
			}
//			Styx.getInstance().irc.bot.close();
		}
		try {
			for(File f : LogUtil.logdirectory.listFiles()) {
				if(f.getName().startsWith("log") && !f.getName().startsWith("log-")) {
					f.delete();
				}
			}
		} catch(Exception e) {
			Sys.out("Could not delete Player-Logs!");
			e.printStackTrace();
		}

		Sys.out("Saving options.");
		Core.getInstance().getOptionmanager().saveOptions();
	}

}
