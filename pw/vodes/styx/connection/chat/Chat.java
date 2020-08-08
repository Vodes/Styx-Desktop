package pw.vodes.styx.connection.chat;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.exception.IrcException;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.ConnectEvent;
import org.pircbotx.hooks.events.DisconnectEvent;
import org.pircbotx.hooks.events.JoinEvent;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.QuitEvent;

import pw.vodes.styx.Styx;
import pw.vodes.styx.core.Core;
import pw.vodes.styx.ui.updating.UpdateOnlineUsersThread;
import pw.vodes.styx.util.Sys;

public class Chat extends ListenerAdapter {

	public static PircBotX bot;

	public static Configuration config;

	public static long lastmessage = 0;

	public static void startChat() {

		bot = new PircBotX(config);

		try {
			try {
				bot.startBot();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IrcException e) {
			e.printStackTrace();
		}
	}

	public static void closeChat() {
		bot.stopBotReconnect();
		bot.sendIRC().quitServer("Quit");
	}

	@Override
	public void onConnect(ConnectEvent event) throws Exception {
		Sys.out("Chat connected.");
		Styx.getInstance().window.addtoChat("------- Connected! -------");
		lastmessage = System.currentTimeMillis();
		ChatCommands.previousSendername = "";
		ChatCommands.lastuserwasthisone = false;
		super.onConnect(event);
	}

	@Override
	public void onJoin(JoinEvent event) throws Exception {
		if (!event.getUser().getNick().equalsIgnoreCase(Styx.getInstance().user)) {

			String senderName = event.getUser().getNick();
			if (senderName.startsWith("AWCP")) {
				senderName = senderName.substring(4);
			}
			if(senderName.contains("_")) {
				senderName = senderName.split("_")[0];
			}
			
			lastmessage = System.currentTimeMillis();
			Styx.getInstance().window.addtoChat("[" + this.getTimeStamp() + "] " + senderName + " has joined!");

			if (Core.getInstance().getOptionmanager().getBoolean("Notification-Sound-Action")) {
				this.playNotificationSound();
			}
		}
		ChatCommands.previousSendername = "";
		ChatCommands.lastuserwasthisone = false;
		try {
			UpdateOnlineUsersThread.update();
		} catch(Exception e) {
		}
		super.onJoin(event);
	}

	@Override
	public void onMessage(MessageEvent event) throws Exception {
		String senderName = event.getUser().getNick();
		if (senderName.startsWith("AWCP")) {
			senderName = senderName.substring(4);
		}
		if(senderName.contains("_")) {
			senderName = senderName.split("_")[0];
		}
		if (event.getMessage().startsWith("!")) {
			boolean admin = false;
			if (StringUtils.containsIgnoreCase(senderName, "Alex")
					|| StringUtils.containsIgnoreCase(senderName, "Vodes") || senderName.equalsIgnoreCase("Bot")) {
				admin = true;
			}
			if(ChatCommands.command(event.getMessage().substring(1), admin, senderName)) {
				return;
			}
		}
		if (isFirstMessageByUser(senderName)) {
			Styx.getInstance().window.addtoChat("[" + this.getTimeStamp() + "] " + senderName + ": ");
			Styx.getInstance().window.addtoChat(event.getMessage().trim());
		} else {
			Styx.getInstance().window.addtoChat(event.getMessage().trim());
		}
		ChatCommands.previousSendername = senderName;
		ChatCommands.lastuserwasthisone = false;
		lastmessage = System.currentTimeMillis();

		if (Core.getInstance().getOptionmanager().getBoolean("Notification-Sound")) {
			this.playNotificationSound();
		}
		super.onMessage(event);
	}

	@Override
	public void onQuit(QuitEvent event) throws Exception {
		if (!event.getUser().getNick().equalsIgnoreCase(Styx.getInstance().user)) {

			String senderName = event.getUser().getNick();
			if (senderName.startsWith("AWCP")) {
				senderName = senderName.substring(4);
			}
			if(senderName.contains("_")) {
				senderName = senderName.split("_")[0];
			}

			Styx.getInstance().window.addtoChat("[" + this.getTimeStamp() + "] " + senderName + " has left!");
		}
		ChatCommands.previousSendername = "";
		ChatCommands.lastuserwasthisone = false;
		try {
			UpdateOnlineUsersThread.update();
		} catch(Exception e) {
		}
		super.onQuit(event);
	}

	@Override
	public void onDisconnect(DisconnectEvent event) throws Exception {
		Styx.getInstance().window.addtoChat("Disconnected: " + event.getDisconnectException().getMessage());
		ChatReconnectThread crt = new ChatReconnectThread();
		crt.start();
		ChatCommands.previousSendername = "";
		ChatCommands.lastuserwasthisone = false;
		super.onDisconnect(event);
	}

	public String getTimeStamp() {
		return new SimpleDateFormat("HH:mm").format(new Date());
	}

	public void playNotificationSound() throws Exception {
		try {
			URL url = new URL("https://vodes.pw/awcp/notif.wav");
			URLConnection connection = url.openConnection();
			connection.setRequestProperty("User-Agent","Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.7; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
			BufferedInputStream buf = new BufferedInputStream(connection.getInputStream());
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(buf);
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch(Exception io) {
			io.printStackTrace();
		}

	}

	public boolean isFirstMessageByUser(String user) {
		if ((System.currentTimeMillis() - this.lastmessage) > (2 * 60000)) {
			return true;
		}
		if (ChatCommands.lastuserwasthisone) {
			return true;
		}
		if (user.equalsIgnoreCase(ChatCommands.previousSendername)) {
			return false;
		}
		return true;
	}

}
