package pw.vodes.styx.util.threads;

import pw.vodes.styx.Styx;
import pw.vodes.styx.core.Core;

public class LiveScheduleThread extends Thread {
	
	@Override
	public void run() {
		while(true) {
			try {
				String s = Core.getInstance().getFilemanager().readURLToLine("https://vodes.pw/awcp/LiveSchedule.txt");
				if(s != null && !s.isEmpty() && s.length() > 10) {
					Styx.getInstance().window.liveSchedulePanel.setText(s);
				}
			} catch (Exception e) {
				try {
					this.sleep(25000);
				} catch (InterruptedException e2) {
					e2.printStackTrace();
				}
			}
			try {
				this.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
