package pw.vodes.styx.ui.updating;

import pw.vodes.styx.Styx;
import pw.vodes.styx.core.Core;
import pw.vodes.styx.core.base.Watchable;
import pw.vodes.styx.core.base.anime.AnimeEP;

public class DownloadingUpdateThread extends Thread{
	
	@Override
	public void run() {
		while(true) {
			try {
				String s = "";
				for(Watchable w : Core.getInstance().getDownloadQueue().downloading) {
					if(w.dlThread != null) {
						if(w instanceof AnimeEP) {
							AnimeEP ep = (AnimeEP) w;
							s += ep.getName() + " (E" + ep.getEP() + ", " + w.dlThread.getProgress() + ")\n";
						} else {
							s += w.getName() + " (" + w.dlThread.getProgress() + ")" + "\n";
						}
					}
				}
				for(Watchable w : Core.getInstance().getDownloadQueue().files) {
					if(w instanceof AnimeEP) {
						AnimeEP ep = (AnimeEP) w;
						s += "Queued: " + ep.getName() + " (E" + ep.getEP() + ")\n";
					} else {
						s += "Queued: " + w.getName() + "\n";
					}
				}
				Styx.getInstance().window.downloading_panel.setText(s == "" ? "Nothing is downloading..." : s);
			} catch(Exception e) {
				e.printStackTrace();
			}
			try {
				this.sleep(1500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
