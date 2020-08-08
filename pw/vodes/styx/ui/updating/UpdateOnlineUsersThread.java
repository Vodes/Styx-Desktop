package pw.vodes.styx.ui.updating;

import java.util.ArrayList;

import org.pircbotx.User;

import pw.vodes.styx.Styx;
import pw.vodes.styx.util.Sys;

public class UpdateOnlineUsersThread extends Thread {

	@Override
	public void run() {
		while (true) {
			try {
				UpdateOnlineUsersThread.update();
			} catch(Exception e) {
			}

			try {
				this.sleep(30000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void update() {
		ArrayList<String> usernames = new ArrayList<String>();
		for (User user : Styx.getInstance().irc.bot.getUserChannelDao().getChannel("#Styx").getUsers()) {
			String username = user.getNick();
			if(username.contains("_")) {
				username = username.split("_")[0];
			}
			usernames.add(username);
		}
		Styx.getInstance().window.setOnlineUsers(usernames);
	}

}
