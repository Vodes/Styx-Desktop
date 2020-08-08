package pw.vodes.styx.ui.listener;

import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.JButton;

import pw.vodes.styx.core.Core;
import pw.vodes.styx.core.base.Watchable;
import pw.vodes.styx.core.base.filemanagement.Watched;
import pw.vodes.styx.ui.sub.WatchableContextMenu;

public class WatchableMouseListener implements java.awt.event.MouseListener {

	private Watchable w;
	private JButton button;

	public WatchableMouseListener(Watchable w, JButton button) {
		this.w = w;
		this.button = button;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) {
			WatchableContextMenu pop = new WatchableContextMenu(w, button);
			pop.show(e.getComponent(), e.getX(), e.getY());
		}

		if (e.getButton() == MouseEvent.BUTTON2) {
			w.setWatched(true);
			if (!button.getText().contains("seen")) {
				button.setText(button.getText().replace(")", ", seen)"));
			}
			Watched.save();
			if (Core.getInstance().getOptionmanager().getBoolean("MPV")) {
				try {
					Runtime.getRuntime().exec("taskkill /IM mpv.exe");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			} else {
				try {
					Runtime.getRuntime().exec("taskkill /IM vlc.exe");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
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
