package pw.vodes.styx.util.mpv;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.swing.JFrame;

import org.apache.commons.io.FileUtils;

import priv.globals.Globals;
import pw.vodes.styx.Styx;
import pw.vodes.styx.core.Core;
import pw.vodes.styx.util.Sys;
import pw.vodes.styx.util.ZipUtil;

public class MpvDownloader {
	
	private static File oldDir;
	private static File latestDir;
	
	public static void check() {
		if(Styx.getInstance().isOffline) {
			for(File f : Core.getInstance().getWorkDirectory().listFiles()) {
				if(f.isDirectory() && f.getName().toLowerCase().startsWith("mpv")) {
					latestDir = f;
					break;
				}
			}
		} else {
			String line = Core.getInstance().getFilemanager().readURLToLine(Globals.mpvURL);
			if(!line.isEmpty()) {
				File oldDir = new File(Core.getInstance().getWorkDirectory(), line.split(";")[0]);
				if(oldDir.exists()) {
					oldDir.delete();
				}
				File directory = new File(Core.getInstance().getWorkDirectory(), line.split(";")[1]);
				if(!directory.exists() || !new File(directory, "mpv.exe").exists()) {
					directory.mkdirs();
					Thread t = new Thread(() -> {
						Styx.getInstance().window.frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
						File tempDir = new File(Core.getInstance().getWorkDirectory(), "temp");
						tempDir.mkdirs();
						URLConnection connection;
						try {
							connection = new URL(line.split(";")[3]).openConnection();
							connection.setRequestProperty("https.protocols", "TLSv1.2,TLSv1.1,SSLv3");
							connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.7; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
							Files.copy(connection.getInputStream(), new File(tempDir, "temp.zip").toPath(), StandardCopyOption.REPLACE_EXISTING);
							ZipUtil zu = new ZipUtil();
							zu.unzip(new File(tempDir, "temp.zip").getAbsolutePath(), latestDir.getAbsolutePath());
							FileUtils.forceDelete(tempDir);
						} catch (IOException e1) { e1.printStackTrace(); }
						Styx.getInstance().window.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					});
					t.start();
				}
				latestDir = directory;
			}
		}
	}
	
	public static File getLatestDir() {
		return latestDir;
	}

}
