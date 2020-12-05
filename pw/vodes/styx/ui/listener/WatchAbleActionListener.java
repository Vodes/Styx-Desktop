package pw.vodes.styx.ui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import pw.vodes.styx.core.Core;
import pw.vodes.styx.core.base.Watchable;
import pw.vodes.styx.core.base.filemanagement.Watched;
import pw.vodes.styx.core.sync.Sync;

public class WatchAbleActionListener implements ActionListener {
	
	private Watchable w;
	private JButton button;

	public WatchAbleActionListener(Watchable w, JButton button) {
		this.w = w;
		this.button = button;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		w.play();
		if (Core.getInstance().getOptionmanager().getBoolean("Auto-Seen")) {
			w.setWatched(true);
			if (!button.getText().contains("seen")) {
				button.setText(button.getText().replace(")", ", seen)"));
				if(button.getText().length() > 42) {
					button.setToolTipText(button.getText());
				}
			}
			Sync.setWatched(w);
		}
	}

}
