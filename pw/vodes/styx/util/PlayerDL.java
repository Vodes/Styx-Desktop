package pw.vodes.styx.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;

import priv.globals.Globals;
import pw.vodes.styx.Styx;
import pw.vodes.styx.core.Core;
import pw.vodes.styx.util.threads.PlayerDownloadThread;

public class PlayerDL {
	
	public static File directory;
	public static File previousDirectory;
	public static File latestDirectory;
	public static String link;
	
	public static void checkDownloadedPlayers() {
		directory = Core.getInstance().getWorkDirectory();
		boolean mpv = Core.getInstance().getOptionmanager().getBoolean("MPV");
		if(Styx.getInstance().isOffline) {
			getLocalDownloaded(mpv);
			return;
		}
		try {
			getLibDirectories(mpv);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!directory.exists()) {
			directory.mkdirs();
		}
		if(!latestDirectory.exists()) {
			latestDirectory.mkdirs();
		}
		if (new File(latestDirectory.getAbsolutePath(), (mpv ? "mpv.exe" : "vlc.exe")).exists()) {
			return;
		}
		PlayerDownloadThread pdt = new PlayerDownloadThread(link);
		pdt.start();
	}
	
	private static void getLibDirectories(boolean mpv) throws Exception {
		String temp = "";
		URL url;
		if(mpv) {
			url = new URL(Globals.mpvURL);
		} else {
			url = new URL(Globals.vlcURL);
		}
		BufferedReader connection = new BufferedReader(new InputStreamReader(url.openStream()));
		temp = connection.readLine();
		
		latestDirectory = new File(directory, temp.split(";")[1]);
		previousDirectory = new File(directory, temp.split(";")[0]);
		link = temp.split(";")[3];
	}
	
	private static void getLocalDownloaded(boolean mpv) {
		for(File f : Core.getInstance().getFilemanager().workDirectory.listFiles()) {
			if(f.isDirectory() && f.getName().startsWith(mpv ? "mpv" : "vlc")) {
				latestDirectory = f;
			}
		}
	}

}
