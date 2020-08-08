package pw.vodes.styx.connection.chat;

import org.pircbotx.PircBotX;

import pw.vodes.styx.Styx;


public class ChatReconnectThread extends Thread {

	int tries = 0;

	public ChatReconnectThread() {
		this.setName("Chat-Reconnect-Thread");
	}

	@Override
	public void run() {
		try {
			Thread.sleep(2000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		while (true) {
			try {
				if (Styx.getInstance().irc != null) {
					if (Styx.getInstance().irc.bot.isConnected()) {
						tries = 0;
						break;
					} else {
						tries++;
						Styx.getInstance().window.addtoChat("Attempting to reconnect... (" + tries + ")");
						Styx.getInstance().irc.bot.close();
						Styx.getInstance().irc.bot = new PircBotX(Styx.getInstance().irc.config);
						Styx.getInstance().irc.bot.startBot();
					}
				}
				this.sleep(3500L);
			} catch (Exception io) {
				io.printStackTrace();
			}
		}
	}

}
