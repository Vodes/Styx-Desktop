package pw.vodes.styx.ui.listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import pw.vodes.styx.Styx;
import pw.vodes.styx.core.Core;
import pw.vodes.styx.core.base.anime.Anime;
import pw.vodes.styx.ui.sub.WindowEpisodes;

public class AnimeListMouseListener implements MouseListener{
	
	Anime anime;
	
	public AnimeListMouseListener(Anime anime) {
		this.anime = anime;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			if(Styx.getInstance().window.episodes != null) {
				Styx.getInstance().window.episodes.dispose();
				Styx.getInstance().window.episodes = null;
			}
			Styx.getInstance().window.episodes = new WindowEpisodes(anime);
			Styx.getInstance().window.watchpanel.add(Styx.getInstance().window.episodes);
			Styx.getInstance().window.episodes.show();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {		
	}

	@Override
	public void mouseExited(MouseEvent e) {		
	}

	@Override
	public void mousePressed(MouseEvent e) {		
	}

	@Override
	public void mouseReleased(MouseEvent e) {		
	}

}
