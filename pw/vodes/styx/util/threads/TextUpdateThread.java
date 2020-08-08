package pw.vodes.styx.util.threads;

import pw.vodes.styx.Styx;

public class TextUpdateThread extends Thread{
	
	
	@Override
	public void run() {
		while(true) {
			try {
				if(Styx.getInstance().window.newspanel.getText().isEmpty() && !Styx.getInstance().news.isEmpty()) {
					Styx.getInstance().window.newspanel.setText(Styx.getInstance().news);
				}
				if(Styx.getInstance().window.changelogpanel.getText().isEmpty() && !Styx.getInstance().changelog.isEmpty()) {
					Styx.getInstance().window.changelogpanel.setText(Styx.getInstance().changelog);
				}

				if(!Styx.getInstance().window.newspanel.getText().isEmpty() && !Styx.getInstance().window.changelogpanel.getText().isEmpty()) {
					break;
				}
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
