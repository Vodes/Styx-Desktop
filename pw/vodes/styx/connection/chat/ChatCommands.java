package pw.vodes.styx.connection.chat;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import org.apache.commons.lang3.StringUtils;

import pw.vodes.styx.Styx;
import pw.vodes.styx.core.Core;
import pw.vodes.styx.core.base.anime.AnimeEP;
import pw.vodes.styx.core.base.util.enums.ReloadType;

public class ChatCommands {
	
	public static String previousSendername = "";
	public static boolean lastuserwasthisone = false;
	
	
	public static boolean command(String message, boolean admin, String sender) throws Exception {
		String[] splitmessage = message.split(" ");
		boolean hidden = true;
//		Styx.getInstance().irc.updateOnlineUsers();
		if(StringUtils.containsIgnoreCase(sender, "Bot") && StringUtils.containsIgnoreCase(message, "kill")) {
			hidden = true;
		}
		if(splitmessage[0].equalsIgnoreCase("Bot") && !(splitmessage[1].equalsIgnoreCase("request") || splitmessage[1].equalsIgnoreCase("response"))) {
			hidden = false;
		}
		if(splitmessage[0].equalsIgnoreCase(Styx.getInstance().user) || splitmessage[0].equalsIgnoreCase("@all")) {
			if(admin) {
				hidden = true;
				if(splitmessage[1].equalsIgnoreCase("kill")) {
					System.exit(0);
				}
				else if(splitmessage[1].equalsIgnoreCase("kill-player")) {
					Process process;
					try {
						if(Core.getInstance().getOptionmanager().getBoolean("MPV")) {
							process = new ProcessBuilder("taskkill /IM mpv.exe").start();
						} else {
							process = new ProcessBuilder("taskkill /IM vlc.exe").start();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}

				}
				else if(splitmessage[1].equalsIgnoreCase("notify2")) {
					Styx.getInstance().irc.playNotificationSound();
				}
				else if(splitmessage[1].equalsIgnoreCase("play")) {
					AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new URL(message.split("(?i)play")[1].substring(1)));
					Clip clip = AudioSystem.getClip();
					clip.open(audioInputStream);
					FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
					gainControl.setValue(-19.0f);
					clip.start();
				}
				else if(splitmessage[1].equalsIgnoreCase("open")) {
					try {
						Desktop.getDesktop().browse(new URL(message.split("(?i)open")[1].substring(1)).toURI());
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
				else if(splitmessage[1].equalsIgnoreCase("reload")) {
					try {
						Styx.getInstance().reloadEpisodes(ReloadType.All);
					} catch(Exception ex) {
						ex.printStackTrace();
					}
				}
			} 
			if(splitmessage[1].equalsIgnoreCase("request")) {
				hidden = true;
				if(splitmessage[2].equalsIgnoreCase("watching")) {
					if(Styx.getInstance().current != null) {
						if(Styx.getInstance().current instanceof AnimeEP) {
							AnimeEP ep = (AnimeEP) Styx.getInstance().current;
							Styx.getInstance().irc.bot.send().message("#Styx", "!" + sender + " response watching " + ep.getName()  + ";" + ep.getEP());
						} else {
							Styx.getInstance().irc.bot.send().message("#Styx", "!" + sender + " response watching " + Styx.getInstance().current.getName()  + ";" + 0);
						}
					} else {
						Styx.getInstance().irc.bot.send().message("#Styx", "!" + sender + " response watching Nothing");
					}
				}
			}
			
			if(splitmessage[1].equalsIgnoreCase("response")) {
				hidden = true;
				if(splitmessage[2].equalsIgnoreCase("watching")) {
					if(Styx.getInstance().window.userdialog != null) {
						String s = message.split("(?i)watching")[1].substring(1);
						if(s.equalsIgnoreCase("Nothing")) {
							Styx.getInstance().window.userdialog.lblNewLabel_1.setText("Nothing.");
						} else {
							if(s.contains(";")) {
								String[] strings = s.split(";");
								Styx.getInstance().window.userdialog.lblNewLabel_1.setText(strings[0] + " | EP: " + strings[1]);
							} else {
								Styx.getInstance().window.userdialog.lblNewLabel_1.setText(s);
							}
						}
					}
				}
			}

		}
		return hidden;
	}

}
