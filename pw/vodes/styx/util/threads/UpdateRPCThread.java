package pw.vodes.styx.util.threads;

public class UpdateRPCThread extends Thread {
	
	@Override
	public void run() {
		while(true) {
			try {
				this.sleep(1500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
