package pw.vodes.styx.ui.loading;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import pw.vodes.styx.Styx;

public class PictureLoadingQueue extends Thread {
	
	public CopyOnWriteArrayList<ButtonPictureCombo> queue = new CopyOnWriteArrayList<>();
	public CopyOnWriteArrayList<ButtonPictureCombo> loading = new CopyOnWriteArrayList<>();
	
	public PictureLoadingQueue() {
		this.start();
	}
	
	@Override
	public void run() {
		while(true) {
			for(ButtonPictureCombo bpc : queue) {
				if(loading.size() < 4) {
					loading.add(bpc);
					queue.remove(bpc);
				}
			}
			for(ButtonPictureCombo bpc : loading) {
				new PictureLoadingThread(bpc).start();
			}
			try {
				this.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public class PictureLoadingThread extends Thread{
		
		private ButtonPictureCombo bpc;
		
		public PictureLoadingThread(ButtonPictureCombo bpc) {
			this.bpc = bpc;
		}
		
		@Override
		public void run() {
			try {
				BufferedImage pic = ImageIO.read(new URL(bpc.getPicURL()));
				Image img = pic.getScaledInstance(bpc.getButton().getWidth() + 10, bpc.getButton().getHeight(), BufferedImage.SCALE_SMOOTH);
				bpc.getButton().setIcon(new ImageIcon(img));
			} catch(Exception e) {}
			Styx.getInstance().picloadqueue.loading.remove(bpc);
			super.run();
		}
		
	}

}
