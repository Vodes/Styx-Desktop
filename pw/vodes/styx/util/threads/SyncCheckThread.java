package pw.vodes.styx.util.threads;

import javax.swing.JFrame;

import pw.vodes.styx.Styx;
import pw.vodes.styx.core.sync.Sync;

public class SyncCheckThread extends Thread {
		
	@Override
	public void run() {
		while(true) {
			try {
				Styx.getInstance().canBeClosed = Sync.canBeClosed();
				if(Styx.getInstance().window != null) {
					Styx.getInstance().window.frame.setDefaultCloseOperation(Styx.getInstance().canBeClosed ? JFrame.EXIT_ON_CLOSE : JFrame.DO_NOTHING_ON_CLOSE );
				}
				this.sleep(100);
			} catch(Exception e) {}
		}		
	}

}
