package pw.vodes.styx.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.commons.io.FileUtils;

import pw.vodes.styx.core.Core;
import pw.vodes.styx.core.base.anime.Anime;
import pw.vodes.styx.core.base.anime.AnimeEP;
import pw.vodes.styx.core.base.category.season.Season;
import pw.vodes.styx.core.base.filemanagement.Watched;

public class FileImporting {
	
	public static File oldStyxDir = new File(System.getProperty("user.home"), "Vodes" + File.separator + "Styx");
	
	public static void doImport() {
		if(!oldStyxDir.exists()) {
			return;
		}
		File watchedFile = new File(oldStyxDir, "watched.eps");
		File optionsFile = new File(oldStyxDir, "options.txt");
		
		if(optionsFile.exists()) {
			Sys.out("Old options.txt File detected.");
			try {
				Files.move(Paths.get(optionsFile.getAbsolutePath()), Paths.get(new File(Core.getInstance().getWorkDirectory(), "options.txt").getAbsolutePath()), StandardCopyOption.REPLACE_EXISTING);
				Sys.out("Options.txt moved.");
			} catch (IOException e) {
				e.printStackTrace();
			}
			Core.getInstance().getOptionmanager().loadOptions();
		}
		if(watchedFile.exists()) {
			for (String s : Core.getInstance().getFilemanager().readFileToLines(watchedFile)) {
				String[] parts = s.split(";");
				for(Season sea : Core.getInstance().getSeasonManager().getSeasons()) {
					for(Anime a : sea.getAnimeList()) {
						for(AnimeEP ep : a.getEPs()) {
							if(parts[0].equalsIgnoreCase(ep.getName()) && parts[1].equalsIgnoreCase(ep.getEP())) {
								ep.setWatched(true);
							}
						}
					}
				}
			}
			Watched.save();
		}
		try {
			FileUtils.deleteDirectory(oldStyxDir);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
