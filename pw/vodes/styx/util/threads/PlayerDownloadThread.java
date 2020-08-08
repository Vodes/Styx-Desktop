package pw.vodes.styx.util.threads;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.swing.JFrame;

import org.apache.commons.io.FileUtils;

import priv.globals.Globals;
import pw.vodes.styx.Styx;
import pw.vodes.styx.core.Core;
import pw.vodes.styx.util.PlayerDL;
import pw.vodes.styx.util.Sys;
import pw.vodes.styx.util.ZipUtil;

public class PlayerDownloadThread extends Thread {
	
	private String url;
	
	public PlayerDownloadThread(String url) {
		this.url = url;
	}
	
	@Override
	public void run() {
		// Prevent stupid people from instantly closing the thing
		Styx.getInstance().window.frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		Styx.getInstance().isDownloading = true;
		Styx.getInstance().window.frame.setTitle(Globals.name + " v" + Globals.version + " [Downloading Player]");
		boolean mpv = Core.getInstance().getOptionmanager().getBoolean("MPV");
		try {
			Sys.out("Downloading " + (mpv ? "MPV" : "VLC")  + " Player...");
			File tempdirectory = new File(Core.getInstance().getWorkDirectory(), "Temp2");
			tempdirectory.mkdir();
			URL url2 = new URL(url);
			HttpURLConnection conn;
			conn = (HttpURLConnection) url2.openConnection();
			conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.7; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
			InputStream in = conn.getInputStream();
			Files.copy(in, Paths.get(new File(tempdirectory, "player.zip").getAbsolutePath()), StandardCopyOption.REPLACE_EXISTING);
//			FileUtils.copyURLToFile(url2, new File(tempdirectory, "player.zip"));
			ZipUtil zu = new ZipUtil();
			zu.unzip(new File(tempdirectory, "player.zip").getAbsolutePath(), PlayerDL.latestDirectory.getAbsolutePath());
			FileUtils.deleteDirectory(tempdirectory);
			if(!PlayerDL.latestDirectory.getAbsolutePath().equalsIgnoreCase(PlayerDL.previousDirectory.getAbsolutePath())) {
				FileUtils.deleteDirectory(PlayerDL.previousDirectory);
			}
			Sys.out("Finished downloading.");
		} catch (Exception e) {
			e.printStackTrace();
			Sys.out("Downloading failed!", "error");
		}
		// Allow stupid people to close the programm again
		Styx.getInstance().window.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Styx.getInstance().isDownloading = false;
		Styx.getInstance().window.frame.setTitle(Globals.name + " v" + Globals.version);
	}

}
