package pw.vodes.styx.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import mdlaf.shadows.DropShadowBorder;
import pw.vodes.styx.Styx;
import pw.vodes.styx.core.Core;
import pw.vodes.styx.core.base.anime.Anime;
import pw.vodes.styx.core.base.anime.AnimeEP;
import pw.vodes.styx.core.base.category.season.Season;
import pw.vodes.styx.ui.listener.WatchAbleActionListener;
import pw.vodes.styx.ui.listener.WatchableMouseListener;
import pw.vodes.styx.ui.loading.ButtonPictureCombo;
import pw.vodes.styx.ui.sub.WindowEpisodes;

public class WindowAnime {
	
	public static void initRecents() {
		int height = 0;
		JPanel panel = new JPanel();
		panel.setLayout(null);
		for(AnimeEP recents : Core.getInstance().getSeasonManager().getRecentEPs()) {
			JButton btn = new JButton(recents.getButtonString(false).replace("EP: ", "E").replace(".2020", ""));
			btn.setBounds(0, height, 286, 30);
			btn.setFont(new Font("Verdana", Font.PLAIN, 11));
			btn.setFocusable(false);
			btn.addActionListener(new WatchAbleActionListener(recents, btn));
			btn.addMouseListener(new WatchableMouseListener(recents, btn));
			height += 32;
			panel.add(btn);
		}
		Styx.getInstance().window.recents_panel = new JScrollPane(panel);
		Styx.getInstance().window.season_tabbed.addTab("Recently Updated", null, Styx.getInstance().window.recents_panel, null);
		panel.setPreferredSize(new Dimension(Styx.getInstance().window.recents_panel.getWidth(), height + 32));
	}
	
	public static void initSeasons() {
		for(Season sea : Core.getInstance().getSeasonManager().getSeasons()) {
			JScrollPane scrollPane = new JScrollPane();
			JPanel panel = new JPanel();
			scrollPane.setViewportView(panel);
			panel.setLayout(null);
			Styx.getInstance().window.season_tabbed.addTab(sea.getName(), null, scrollPane, null);
			int buttoncount = 0, xCord = 1, yCord = 0, allowedButtonsInRow = 2, animecount = 0;
			if(sea.getAnimeList().size() > 4) {
				allowedButtonsInRow = 3;
			}
			for(Anime a : sea.getAnimeList()) {
				JButton button = new JButton("");
				button.setToolTipText(a.getName());
				button.addMouseListener(new MouseListener() {
					
					@Override
					public void mouseReleased(MouseEvent e) {}
					
					@Override
					public void mousePressed(MouseEvent e) {
						if(Styx.getInstance().window.episodes != null) {
							Styx.getInstance().window.episodes.dispose();
							Styx.getInstance().window.episodes = null;
						}
						Styx.getInstance().window.episodes = new WindowEpisodes(a);
						Styx.getInstance().window.watchpanel.add(Styx.getInstance().window.episodes);
						Styx.getInstance().window.episodes.show();
					}
					
					@Override
					public void mouseExited(MouseEvent e) {}
					
					@Override
					public void mouseEntered(MouseEvent e) {}
					
					@Override
					public void mouseClicked(MouseEvent e) {}
				});
				if(allowedButtonsInRow == 3) {
					button.setBounds(xCord, yCord, 92, 121);
				} else {
					button.setBounds(xCord, yCord, 132, 173);
				}
				button.setFocusable(false);
				button.setBorder(new DropShadowBorder(Color.black, 1, 2, 0.5F, 1, true, true, true, true));
				Styx.getInstance().picloadqueue.queue.add(new ButtonPictureCombo(button, a.getCoverURL()));
				buttoncount++;
				xCord += allowedButtonsInRow == 3 ? 95 : 145;
				
				if(buttoncount >= allowedButtonsInRow) {
					xCord = 1;
					yCord += allowedButtonsInRow == 3 ? 127 : 185;
					buttoncount = 0;
				}
				animecount++;
				
				panel.add(button);
			}
			if(allowedButtonsInRow == 3) {
				if(animecount % 3 == 0) {
					panel.setPreferredSize(new Dimension(scrollPane.getWidth(), yCord - 2));
				} else {
					panel.setPreferredSize(new Dimension(scrollPane.getWidth(), yCord + 130));
				}
			} else {
				if(animecount % 2 == 0) {
					panel.setPreferredSize(new Dimension(scrollPane.getWidth(), yCord - 8));
				} else {
					panel.setPreferredSize(new Dimension(scrollPane.getWidth(), yCord + 180));
				}
			}
				
			scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(6, 0));
			scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			scrollPane.getVerticalScrollBar().setUnitIncrement(10);
		}

	}
	
//	JButton btnKek = new JButton("kek1");
//	btnKek.setBounds(4, 0, 135, 184);
//	panel.add(btnKek);
//	
//	JButton btnKek_2 = new JButton("kek2");
//	btnKek_2.setBounds(151, 0, 135, 184);
//	panel.add(btnKek_2);
//	
//	JButton btnKek_1 = new JButton("kek1");
//	btnKek_1.setBounds(4, 192, 135, 184);
//	panel.add(btnKek_1);
//	
//	JButton btnKek_2_1 = new JButton("kek2");
//	btnKek_2_1.setBounds(151, 192, 135, 184);
//	panel.add(btnKek_2_1);

}
