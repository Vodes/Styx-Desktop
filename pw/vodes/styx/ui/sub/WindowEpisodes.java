package pw.vodes.styx.ui.sub;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.text.JTextComponent;

import org.apache.commons.lang3.StringUtils;

import mdlaf.shadows.DropShadowBorder;
import pw.vodes.styx.Styx;
import pw.vodes.styx.core.base.anime.Anime;
import pw.vodes.styx.core.base.anime.AnimeEP;
import pw.vodes.styx.core.base.filemanagement.Watched;
import pw.vodes.styx.ui.listener.WatchAbleActionListener;
import pw.vodes.styx.ui.listener.WatchableMouseListener;

public class WindowEpisodes extends JInternalFrame {
	private final JScrollPane scrollPane = new JScrollPane();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WindowEpisodes frame = new WindowEpisodes(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public WindowEpisodes(Anime anime) {
		setBounds(100, 100, 300, 283);
		getInputMap(JTextComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "ESC");
		getActionMap().put("ESC", new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				Styx.getInstance().window.episodes = null;
			}
		});
		
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setRootPaneCheckingEnabled(false);
		((BasicInternalFrameUI)this.getUI()).setNorthPane(null);
//		this.setBorder(null);
		getContentPane().setLayout(null);
		scrollPane.setBounds(-1, 67, 300, 210);
		this.setBorder(new DropShadowBorder(Color.black, 1, 2, 0.5F, 1, true, true, true, true));
		getContentPane().add(scrollPane);
		
		JPanel panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setLayout(null);
		
		int height = 0;
		for(AnimeEP ep : anime.getEPs()) {
			JButton button = new JButton("EP: " + ep.getEP() + " (" + ep.getDate() + (ep.hasBeenWatched() ? ", seen)" : ")"));
			button.setBounds(0, height, 285, 25);
			button.setFocusable(false);
			button.addActionListener(new WatchAbleActionListener(ep, button));
			button.addMouseListener(new WatchableMouseListener(ep, button));
			panel.add(button);
			height += 28;
		}
		panel.setPreferredSize(new Dimension(scrollPane.getWidth(), height));
		scrollPane.getVerticalScrollBar().setUnitIncrement(12);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(3, 0));
		
		JLabel lblNewLabel = new JLabel(anime.getName());
		lblNewLabel.setBounds(-4, 0, 300, 22);
		lblNewLabel.setFont(new Font("Verdana", Font.BOLD, 12));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblNewLabel);
		
		JButton btnX = new JButton("X");
		btnX.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				Styx.getInstance().window.episodes = null;
			}
		});
		btnX.setFont(new Font("Verdana", Font.PLAIN, 9));
		btnX.setBounds(242, 34, 40, 23);
		getContentPane().add(btnX);
		
		JButton btnMal = new JButton("AL");
		btnMal.setFocusable(false);
		btnMal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Desktop.isDesktopSupported()) {
					try {
						Desktop.getDesktop().browse(new URI(anime.getMyAnimeListLink()));
					} catch (IOException | URISyntaxException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnMal.setFont(new Font("Verdana", Font.BOLD, 14));
		btnMal.setBounds(0, 34, 78, 23);
		getContentPane().add(btnMal);
		
		JButton btnSetSeen = new JButton("Set Seen");
		btnSetSeen.setFont(new Font("Verdana", Font.BOLD, 14));
		btnSetSeen.setFocusable(false);
		btnSetSeen.setBounds(79, 34, 106, 23);
		btnSetSeen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for(AnimeEP ep : anime.getEPs()) {
					ep.setWatched(true);
				}
				Watched.save();
			}
		});
		getContentPane().add(btnSetSeen);
		
		JButton btnDl = new JButton("DL");
		btnDl.setFont(new Font("Verdana", Font.BOLD, 14));
		btnDl.setBounds(186, 34, 53, 23);
		btnDl.setFocusable(false);
		btnDl.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for(AnimeEP ep : anime.getEPs()) {
					ep.download();
				}
			}
		});
		getContentPane().add(btnDl);
	}
	
}
