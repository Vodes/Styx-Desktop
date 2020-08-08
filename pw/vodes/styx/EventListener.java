package pw.vodes.styx;

import java.io.IOException;

import javax.swing.JOptionPane;

import pw.vodes.styx.connection.discord.DiscordIntegration;
import pw.vodes.styx.core.Core;
import pw.vodes.styx.core.base.anime.AnimeEP;
import pw.vodes.styx.core.base.text.TextType;
import pw.vodes.styx.core.event.EventTarget;
import pw.vodes.styx.core.events.EventHandleOutput;
import pw.vodes.styx.core.events.EventPlayFile;
import pw.vodes.styx.core.events.EventPlayURL;
import pw.vodes.styx.core.events.EventRequestUIReload;
import pw.vodes.styx.core.events.EventTextCreated;
import pw.vodes.styx.util.PlayerDL;
import pw.vodes.styx.util.multios.PlayerUtil;
import pw.vodes.styx.util.threads.PlayingThread;

public class EventListener {

	@EventTarget
	public void onTextLoad(EventTextCreated ev) {
		if (ev.getType() == TextType.News) {
			Styx.getInstance().news = ev.getText();
		} else if (ev.getType() == TextType.Changelog) {
			Styx.getInstance().changelog = ev.getText();
		} else if (ev.getType() == TextType.Schedule) {
			Styx.getInstance().schedule = ev.getText();
		}
	}

	@EventTarget
	public void onPlayFile(EventPlayFile ev) {
		if(!Styx.getInstance().unsafe) {
			PlayerUtil.startPlayer(ev.getFile().getAbsolutePath());
		} else {
			JOptionPane.showMessageDialog(Styx.getInstance().window.frame, "You are not running the latest version. Please update by restarting with the Launcher!");
		}
//		if (Core.getInstance().getOptionmanager().getBoolean("MPV")) {
//			try {
//				Runtime rt = Runtime.getRuntime();
//				rt.exec("cmd.exe /c start " + PlayerDL.latestDirectory.getAbsolutePath() + "\\mpv.exe \""
//						+ ev.getFile().getAbsolutePath() + "\"");
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		} else {
//			Process process;
//			try {
//				process = new ProcessBuilder(PlayerDL.latestDirectory.getAbsolutePath() + "\\vlc.exe",
//						ev.getFile().getAbsolutePath()).start();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
	}

	@EventTarget
	public void onReloadRequest(EventRequestUIReload ev) {
		if(!Styx.getInstance().unsafe) {
			Styx.getInstance().reloadEpisodes(ev.getType());
		}
	}

	@EventTarget
	public void onPlayURL(EventPlayURL ev) {
		if(!Styx.getInstance().unsafe) {
			PlayerUtil.startPlayer(ev.getURL());
			Styx.getInstance().current = ev.getWatchable();
			DiscordIntegration.update("Watching", Styx.getInstance().current.getName());
			new PlayingThread().start();
		} else {
			JOptionPane.showMessageDialog(Styx.getInstance().window.frame, "You are not running the latest version. Please update by restarting with the Launcher!");
		}
//		Process process;
//		if (Core.getInstance().getOptionmanager().getBoolean("MPV")) {
//			try {
//				process = new ProcessBuilder(PlayerDL.latestDirectory.getAbsolutePath() + "\\mpv.exe", ev.getURL()).directory(PlayerDL.latestDirectory).start();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		} else {
//			try {
//				process = new ProcessBuilder(PlayerDL.latestDirectory.getAbsolutePath() + "\\vlc.exe", ev.getURL()).directory(PlayerDL.latestDirectory).start();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}

	}

}
