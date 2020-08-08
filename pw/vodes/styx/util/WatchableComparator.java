package pw.vodes.styx.util;

import java.util.Comparator;

import pw.vodes.styx.core.base.Watchable;
import pw.vodes.styx.core.base.anime.Anime;

public class WatchableComparator implements Comparator<Watchable>{

	@Override
	public int compare(Watchable o1, Watchable o2) {
		return o1.getName().compareToIgnoreCase(o2.getName());
	}
	
	public static class AnimeComparator implements Comparator<Anime>{
		@Override
		public int compare(Anime o1, Anime o2) {
			return o1.getName().compareToIgnoreCase(o2.getName());
		}
	}

}
