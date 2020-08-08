package pw.vodes.styx;

import java.io.File;

import javax.swing.JOptionPane;

import org.apache.commons.lang3.StringUtils;

import priv.globals.Globals;
import pw.vodes.styx.authentication.Auth;
import pw.vodes.styx.connection.chat.Chat;
import pw.vodes.styx.connection.discord.DiscordIntegration;
import pw.vodes.styx.core.Core;
import pw.vodes.styx.core.base.Watchable;
import pw.vodes.styx.core.base.options.optiontypes.OptionBoolean;
import pw.vodes.styx.core.base.options.optiontypes.OptionString;
import pw.vodes.styx.core.base.options.optiontypes.OptionStringArray;
import pw.vodes.styx.core.base.util.ConnectionUtil;
import pw.vodes.styx.core.base.util.enums.ReloadType;
import pw.vodes.styx.core.event.EventManager;
import pw.vodes.styx.ui.WindowMain;
import pw.vodes.styx.ui.loading.PictureLoadingQueue;
import pw.vodes.styx.ui.updating.DownloadingUpdateThread;
import pw.vodes.styx.util.FileImporting;
import pw.vodes.styx.util.PlayerDL;
import pw.vodes.styx.util.ThemeUtil;
import pw.vodes.styx.util.multios.CommandLineUtil;
import pw.vodes.styx.util.multios.OS;
import pw.vodes.styx.util.threads.ChatStartThread;
import pw.vodes.styx.util.threads.LiveScheduleThread;
import pw.vodes.styx.util.threads.TextUpdateThread;

public class Styx {

	private static Styx instance;

	public static Styx getInstance() {
		if (instance == null) {
			instance = new Styx();
		}
		return instance;
	}

	public void init() {
		Core.startInstance();
		Core.getInstance().setWorkDirectory(new File(System.getProperty("user.home"), "Vodes" + File.separator + "Styx-N"));
		addOptions();
		Core.getInstance().loadOptions();
		eventlistener = new EventListener();
		EventManager.register(eventlistener);
		isOffline = ConnectionUtil.isOffline();
		if(!isOffline) {
			Auth.checkHWID();
			if(Core.getInstance().getOptionmanager().getBoolean("DiscordRPC")) {
				DiscordIntegration.init();
			}
		}
		Core.getInstance().init(Globals.newsURL, Globals.changelogURL, Globals.scheduleURL, seasonURL, movieURL, animeURL);
		Core.getInstance().getFilemanager().downloadDirectory = new File(Core.getInstance().getOptionmanager().getString("Download-Path"));
		FileImporting.doImport();
		ThemeUtil.setTheme(Core.getInstance().getOptionmanager().getCurrent("Theme"));
		picloadqueue = new PictureLoadingQueue();
		window = new WindowMain();
		window.initialize();
		window.frame.setVisible(true);
		if(!isOffline) {
			unsafe = !isLatestVersion();
		}
		if(CommandLineUtil.getOS() == OS.Windows) {
			PlayerDL.checkDownloadedPlayers();
		}
		deleteOldVersions();
		new DownloadingUpdateThread().start();
		new TextUpdateThread().start();
		if(!isOffline && !unsafe) {
			new ChatStartThread().start();
			new LiveScheduleThread().start();
		}
	}

	public WindowMain window;
	public EventListener eventlistener;
	public PictureLoadingQueue picloadqueue;
	public String news = "", changelog = "", schedule = "", user = "";
	public String animeURL = "", movieURL = "", seasonURL = "";
	public boolean isDownloading = false, isOffline = false, unsafe = false;
	public Watchable current;
	public Chat irc;
	
	private void addOptions() {
		Core.getInstance().getOptionmanager().options.add(new OptionBoolean("DiscordRPC", true));
		Core.getInstance().getOptionmanager().options.add(new OptionBoolean("Notification-Sound", true));
		Core.getInstance().getOptionmanager().options.add(new OptionBoolean("Notification-Sound-Action", true));
		Core.getInstance().getOptionmanager().options.add(new OptionBoolean("Auto-Seen", true));
		Core.getInstance().getOptionmanager().options.add(new OptionBoolean("MPV", true));
		Core.getInstance().getOptionmanager().options.add(new OptionString("Download-Path", Core.getInstance().getFilemanager().downloadDirectory.getAbsolutePath()));
		Core.getInstance().getOptionmanager().options.add(new OptionStringArray("Theme", "Oceanic", new String[] {"White", "Dark", "Oceanic"}));
	}
	
	public void reloadEpisodes(ReloadType type) {
		Core.getInstance().reloadEpisodes();
		if(type == ReloadType.All) {
			if(!isOffline) {
				Styx.getInstance().window.watchpanel.removeAll();
				Styx.getInstance().window.season_tabbed.removeAll();
				Styx.getInstance().window.movie_tabbed.removeAll();
				Styx.getInstance().window.setupWatchPanel();
				Styx.getInstance().window.watchpanel.repaint();
			}
		}
		Styx.getInstance().window.locals_panel.removeAll();
		Styx.getInstance().window.setupLocals();
		Styx.getInstance().window.locals_panel.repaint();
	}
	
	private void deleteOldVersions() {
		for(File f : Core.getInstance().getWorkDirectory().listFiles()) {
			if(StringUtils.containsIgnoreCase(f.getName(), "Styx v") && StringUtils.containsIgnoreCase(f.getName(), ".jar") && !StringUtils.containsIgnoreCase(f.getName(), "v" + Globals.version)) {
				f.delete();
			}
		}
	}
	
	private boolean isLatestVersion() {
		String webReadout = Core.getInstance().getFilemanager().readURLToLine(Globals.versionURL);
		if(webReadout != null && !webReadout.isEmpty()) {
			double availableVersion = Double.parseDouble(webReadout.split("v")[1].split(".jar")[0]);
			if(Double.parseDouble(Globals.version) < availableVersion) {
				JOptionPane.showMessageDialog(Styx.getInstance().window.frame, "You are not running the latest version. Please update by restarting with the Launcher!");
				return false;
			}
		}
		return true;
	}

}
