package pw.vodes.styx.ui.sub;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.apache.commons.io.FileUtils;

import pw.vodes.styx.Styx;
import pw.vodes.styx.core.Core;
import pw.vodes.styx.core.base.Watchable;
import pw.vodes.styx.core.base.anime.AnimeEP;
import pw.vodes.styx.core.base.anime.AnimeEPLocal;
import pw.vodes.styx.core.base.filemanagement.LocalFiles;
import pw.vodes.styx.core.base.filemanagement.Watched;
import pw.vodes.styx.core.base.movie.Movie;
import pw.vodes.styx.core.base.movie.MovieLocal;
import pw.vodes.styx.core.base.util.enums.ReloadType;

public class WatchableContextMenu extends JPopupMenu {
	
	public WatchableContextMenu(Watchable w, JButton button) {
		JMenuItem setSeen = new JMenuItem("Set Seen");
		setSeen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				w.setWatched(true);
				if (!button.getText().contains("seen")) {
					button.setText(button.getText().replace(")", ", seen)"));
				}
				Watched.save();
			}
		});
		add(setSeen);
		if(w instanceof AnimeEP || w instanceof Movie) {
			JMenuItem download = new JMenuItem("Download");
			download.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					w.download();
				}
			});
			add(download);
			JMenuItem mal = new JMenuItem("AniList");
			mal.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if(w instanceof AnimeEP) {
						AnimeEP ep = (AnimeEP) w;
						openBrowser(ep.getAnime().getMyAnimeListLink());
					} else {
						Movie m = (Movie) w;
						openBrowser(m.getMyAnimeListLink());
					}					
				}
			});
			add(mal);
		} else {
			JMenuItem delete = new JMenuItem("Delete");
			delete.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						try {
							FileUtils.forceDelete(w.getFile());
						} catch(Exception ex2) {
							
						}
						if(w instanceof AnimeEPLocal) {
							AnimeEPLocal ep = (AnimeEPLocal) w;
							Core.getInstance().getLocalManager().getLocalEPs().remove(ep);
						} else {
							MovieLocal m = (MovieLocal) w;
							Core.getInstance().getLocalManager().getLocalMovies().remove(m);
						}
						LocalFiles.save();
						Styx.getInstance().reloadEpisodes(ReloadType.Locals);
					}catch(Exception ex) {
						ex.printStackTrace();
					}
				}
			});
			add(delete);
		}
	}
	
	public void openBrowser(String url) {
		try {
			if(Desktop.isDesktopSupported()) {
				Desktop.getDesktop().browse(new URI(url));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
