package pw.vodes.styx.util.threads;

import pw.vodes.styx.Styx;

public class ChatCheckThread extends Thread {
	
	@Override
	public void run() {
		while(true) {
			if(!Styx.getInstance().isOffline) {
			}
		}

	}

}
