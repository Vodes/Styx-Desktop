package pw.vodes.styx.connection.discord;

import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import priv.globals.Globals;
import pw.vodes.styx.Styx;
import pw.vodes.styx.core.Core;

public class DiscordIntegration {
	
	private static DiscordRPC lib;
	
	public static void init() {
		lib.discordInitialize(Globals.discordToken, null, true);
		DiscordRichPresence rpc = new DiscordRichPresence.Builder("User: " + Styx.getInstance().user).setDetails("Idle").setBigImage("styx", "v" + Globals.version).build();
		lib.discordUpdatePresence(rpc);
	}
	
	public static void shutdown() {
		try {
			lib.discordShutdown();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void update(String details, String state) {
		try {
			DiscordRichPresence rpc = new DiscordRichPresence.Builder(state).setDetails(details).setBigImage("styx", "v" + Globals.version).build();
			lib.discordUpdatePresence(rpc);
		} catch(Exception e) {
			e.printStackTrace();
		}

	}
	
	public static void reset() {
		try {
			DiscordRichPresence rpc = new DiscordRichPresence.Builder("User: " + Styx.getInstance().user).setDetails("Idle").setBigImage("styx", "v" + Globals.version).build();
			lib.discordUpdatePresence(rpc);
		} catch(Exception e) {
			e.printStackTrace();
		}

	}

}
