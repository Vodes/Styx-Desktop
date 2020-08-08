package pw.vodes.styx.util.threads;

import java.util.Random;

import org.pircbotx.Configuration;

import priv.globals.Globals;
import pw.vodes.styx.Styx;
import pw.vodes.styx.connection.chat.Chat;
import pw.vodes.styx.ui.updating.UpdateOnlineUsersThread;

public class ChatStartThread extends Thread {
	
	@Override
	public void run() {
		Styx.getInstance().irc = new Chat();
		Styx.getInstance().irc.config = new Configuration.Builder()
	            .setName(Styx.getInstance().user + "_" + new Random().nextInt(999))
	            .setRealName("Styx v" + Globals.version)
	            .addServer(Globals.ircIP)
	            .setServerPassword(Globals.ircPW)
	            .addAutoJoinChannel("#Styx")
	            .addListener(new Chat())
	            .setLogin("Styx")
	            .buildConfiguration();
		new UpdateOnlineUsersThread().start();
		Styx.getInstance().irc.startChat();
	}
	

}
