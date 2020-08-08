package pw.vodes.styx.ui;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import pw.vodes.styx.Styx;
import pw.vodes.styx.core.Core;
import pw.vodes.styx.core.base.anime.Anime;
import pw.vodes.styx.core.base.category.Year;
import pw.vodes.styx.core.base.category.season.Season;
import pw.vodes.styx.core.base.movie.Movie;
import pw.vodes.styx.ui.listener.WatchAbleActionListener;
import pw.vodes.styx.ui.listener.WatchableMouseListener;
import pw.vodes.styx.ui.loading.ButtonPictureCombo;

public class WindowMovie {
	
	public static void initYears() {
		for(Year year : Core.getInstance().getMovieManager().getYears()) {
			JScrollPane scrollPane = new JScrollPane();
			JPanel panel = new JPanel();
			scrollPane.setViewportView(panel);
			panel.setLayout(null);
			Styx.getInstance().window.movie_tabbed.addTab(year.getYear(), null, scrollPane, null);
			int buttoncount = 0, xCord = 5, yCord = 0, allowedButtonsInRow = 2, animecount = 0;
			for(Movie m : year.getMovies()) {
				JButton button = new JButton("");
				button.setToolTipText(m.getName());
				button.addActionListener(new WatchAbleActionListener(m, button));
				button.addMouseListener(new WatchableMouseListener(m, button));
				button.setBounds(xCord, yCord, 132, 173);
				button.setFocusable(false);
				Styx.getInstance().picloadqueue.queue.add(new ButtonPictureCombo(button, m.getCoverURL()));
//				try {
//					BufferedImage pic = ImageIO.read(new URL(m.getCoverURL()));
//					Image img = pic.getScaledInstance(button.getWidth() + 10, button.getHeight(), BufferedImage.SCALE_SMOOTH);
//					button.setIcon(new ImageIcon(img));
//				} catch (Exception e1) {
//					e1.printStackTrace();
//				}
				
				buttoncount++;
				xCord += 145;
				
				if(buttoncount >= allowedButtonsInRow) {
					xCord = 5;
					yCord += 185;
					buttoncount = 0;
				}
				animecount++;
				
				panel.add(button);
			}
			if(animecount%2 == 0) {
				panel.setPreferredSize(new Dimension(scrollPane.getWidth(), yCord));
			} else {
				panel.setPreferredSize(new Dimension(scrollPane.getWidth(), yCord + 176));
			}
				
			scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(6, 0));
			scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			scrollPane.getVerticalScrollBar().setUnitIncrement(10);
		}

	}

}
