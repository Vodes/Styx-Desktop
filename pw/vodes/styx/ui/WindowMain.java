package pw.vodes.styx.ui;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultEditorKit;

import org.apache.commons.lang3.StringUtils;

import priv.globals.Globals;
import pw.vodes.styx.Styx;
import pw.vodes.styx.connection.chat.ChatCommands;
import pw.vodes.styx.core.Core;
import pw.vodes.styx.core.base.Watchable;
import pw.vodes.styx.core.base.anime.Anime;
import pw.vodes.styx.core.base.anime.AnimeEPLocal;
import pw.vodes.styx.core.base.category.Year;
import pw.vodes.styx.core.base.category.season.Season;
import pw.vodes.styx.core.base.movie.Movie;
import pw.vodes.styx.core.base.movie.MovieLocal;
import pw.vodes.styx.ui.listener.UserListener;
import pw.vodes.styx.ui.listener.WatchAbleActionListener;
import pw.vodes.styx.ui.listener.WatchableMouseListener;
import pw.vodes.styx.ui.sub.CharDeletionOverride;
import pw.vodes.styx.ui.sub.WindowEpisodes;
import pw.vodes.styx.ui.sub.WindowUserDialog;
import pw.vodes.styx.util.LogUtil;
import pw.vodes.styx.util.Sys;
import pw.vodes.styx.util.ThemeUtil;
import pw.vodes.styx.util.WatchableComparator;

public class WindowMain {

	public JFrame frame;
	public WindowUserDialog userdialog;
	
	public JScrollPane liveScheduleScroll = new JScrollPane();
	public JTextPane liveSchedulePanel = new JTextPane();
	
	// Home Panels
	public JTextPane newspanel = new JTextPane();
	public JTextPane changelogpanel = new JTextPane();
	
	// Locals Panel
	public JPanel locals_panel = new JPanel();
	public JTextPane downloading_panel = new JTextPane();
	private JTextField textField;
	
	//Chat related
	public JTextPane chatpanel = new JTextPane();
	public JPanel onlineuserspanel = new JPanel();
	public JScrollPane scrollPane_5 = new JScrollPane();
	
	//Watch related
	public JDesktopPane watchpanel = new JDesktopPane();
	public JTabbedPane season_tabbed = new JTabbedPane(JTabbedPane.RIGHT);
	public JScrollPane recents_panel = new JScrollPane();
	public JTabbedPane movie_tabbed = new JTabbedPane(JTabbedPane.RIGHT);
	public WindowEpisodes episodes;
	private JTextField textField_1;
	private JTextField textField_2;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WindowMain window = new WindowMain();
					window.initialize();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 650, 490);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/res/Styx.png")));
		frame.setTitle(Globals.name + " v" + Globals.version);
		
		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBackground(ThemeUtil.color);
		desktopPane.setBounds(0, 0, 644, 471);
		frame.getContentPane().add(desktopPane);
		desktopPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("Verdana", Font.PLAIN, 14));
		tabbedPane.setBounds(0, -5, 444, 487);
		desktopPane.add(tabbedPane);
		//TODO: Home
		JDesktopPane desktopPane_1 = new JDesktopPane();
		desktopPane_1.setBackground(ThemeUtil.color);
		tabbedPane.addTab("Home", null, desktopPane_1, null);
		desktopPane_1.setLayout(null);
		
		JTabbedPane tabbedPane_2 = new JTabbedPane(JTabbedPane.BOTTOM);
		tabbedPane_2.setFont(new Font("Verdana", Font.PLAIN, 14));
		tabbedPane_2.setBounds(0, 0, 439, 431);
		desktopPane_1.add(tabbedPane_2);
		
		JDesktopPane desktopPane_6 = new JDesktopPane();
		desktopPane_6.setBackground(ThemeUtil.color);
		tabbedPane_2.addTab("News", null, desktopPane_6, null);
		desktopPane_6.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 434, 390);
		desktopPane_6.add(scrollPane);

		newspanel.setFont(new Font("Verdana", Font.PLAIN, 13));
		newspanel.setEditable(false);
		newspanel.setText(Styx.getInstance().news);
		scrollPane.setViewportView(newspanel);
		
		JDesktopPane desktopPane_7 = new JDesktopPane();
		desktopPane_7.setBackground(ThemeUtil.color);
		tabbedPane_2.addTab("Changelog", null, desktopPane_7, null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(0, 0, 434, 390);
		desktopPane_7.add(scrollPane_1);
		
		changelogpanel.setFont(new Font("Verdana", Font.PLAIN, 13));
		changelogpanel.setEditable(false);
		changelogpanel.setText(Styx.getInstance().changelog);
		scrollPane_1.setViewportView(changelogpanel);
		
		JDesktopPane desktopPane_9 = new JDesktopPane();
		desktopPane_9.setBackground(ThemeUtil.color);
		tabbedPane_2.addTab("Options", null, desktopPane_9, null);
		desktopPane_9.setLayout(null);
		
		JComboBox comboBox = new JComboBox(new String[] {"White", "Oceanic", "Dark"});
		comboBox.setBounds(12, 12, 180, 31);
		
		comboBox.setSelectedIndex(parseIndex(Core.getInstance().getOptionmanager().getCurrent("Theme")));
		comboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Core.getInstance().getOptionmanager().setOptionValue("Theme", parseIndex2(comboBox.getSelectedIndex()));
			}
		});
		desktopPane_9.add(comboBox);
		
		JToggleButton tglbtnDiscordrpc = new JToggleButton("DiscordRPC");
		tglbtnDiscordrpc.setToolTipText("Show Styx in your Discord Status");
		tglbtnDiscordrpc.setHorizontalAlignment(SwingConstants.LEFT);
		tglbtnDiscordrpc.setBounds(12, 50, 267, 31);
		tglbtnDiscordrpc.setSelected(Core.getInstance().getOptionmanager().getBoolean("DiscordRPC"));
		tglbtnDiscordrpc.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				Core.getInstance().getOptionmanager().setOptionValue("DiscordRPC", tglbtnDiscordrpc.isSelected());
			}
		});
		desktopPane_9.add(tglbtnDiscordrpc);
		
		JToggleButton tglbtnNewToggleButton = new JToggleButton("Auto-Seen");
		tglbtnNewToggleButton.setToolTipText("Set episodes seen when starting to watch them");
		tglbtnNewToggleButton.setHorizontalAlignment(SwingConstants.LEFT);
		tglbtnNewToggleButton.setBounds(12, 93, 267, 31);
		tglbtnNewToggleButton.setSelected(Core.getInstance().getOptionmanager().getBoolean("Auto-Seen"));
		tglbtnNewToggleButton.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				Core.getInstance().getOptionmanager().setOptionValue("Auto-Seen", tglbtnNewToggleButton.isSelected());
			}
		});
		desktopPane_9.add(tglbtnNewToggleButton);
		
		JToggleButton tglbtnNewToggleButton_1 = new JToggleButton("Notification Sound");
		tglbtnNewToggleButton_1.setToolTipText("Get notified when someone sends a message");
		tglbtnNewToggleButton_1.setHorizontalAlignment(SwingConstants.LEFT);
		tglbtnNewToggleButton_1.setBounds(12, 137, 267, 31);
		tglbtnNewToggleButton_1.setSelected(Core.getInstance().getOptionmanager().getBoolean("Notification-Sound"));
		tglbtnNewToggleButton_1.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				Core.getInstance().getOptionmanager().setOptionValue("Notification-Sound", tglbtnNewToggleButton_1.isSelected());
			}
		});
		desktopPane_9.add(tglbtnNewToggleButton_1);
		
		JToggleButton tglbtnJoinNotification = new JToggleButton("Join Notification");
		tglbtnJoinNotification.setToolTipText("Get notified when someone joins");
		tglbtnJoinNotification.setHorizontalAlignment(SwingConstants.LEFT);
		tglbtnJoinNotification.setBounds(12, 179, 267, 31);
		tglbtnJoinNotification.setSelected(Core.getInstance().getOptionmanager().getBoolean("Notification-Sound-Action"));
		tglbtnJoinNotification.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				Core.getInstance().getOptionmanager().setOptionValue("Notification-Sound-Action", tglbtnJoinNotification.isSelected());
			}
		});
		desktopPane_9.add(tglbtnJoinNotification);
		
		textField_1 = new JTextField();
		textField_1.setEditable(false);
		textField_1.setText(Core.getInstance().getOptionmanager().getString("Download-Path"));
		textField_1.setBounds(12, 330, 325, 26);
		desktopPane_9.add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnSelect = new JButton("Select");
		btnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser f = new JFileChooser();
				f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				f.showSaveDialog(Styx.getInstance().window.frame);
				
				textField_1.setText(f.getSelectedFile().getAbsolutePath());
				Core.getInstance().getOptionmanager().setOptionValue("Download-Path", f.getSelectedFile().getAbsolutePath());
				Core.getInstance().updateDownloadPath(new File(Core.getInstance().getOptionmanager().getString("Download-Path")));
			}
		});
		btnSelect.setBounds(349, 325, 73, 37);
		desktopPane_9.add(btnSelect);
		
		JLabel lblDownloadPath = new JLabel("Download Path");
		lblDownloadPath.setBounds(12, 297, 325, 21);
		desktopPane_9.add(lblDownloadPath);
		
		JButton btnOpenLogs = new JButton("Open Logs");
		btnOpenLogs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Desktop.isDesktopSupported()) {
					try {
						Desktop.getDesktop().open(LogUtil.logdirectory);
					} catch (IOException ev) {
						ev.printStackTrace();
					}
				}
			}
		});
		btnOpenLogs.setBounds(307, 10, 115, 37);
		desktopPane_9.add(btnOpenLogs);
		
		JButton btnOpen = new JButton("Open");
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(Desktop.isDesktopSupported()) {
					try {
						Desktop.getDesktop().open(Core.getInstance().getFilemanager().downloadDirectory);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		btnOpen.setBounds(349, 287, 73, 37);
		desktopPane_9.add(btnOpen);
		
		JToggleButton tglbtnPreferGermanSubs = new JToggleButton("Prefer German Subs (if available)");
		tglbtnPreferGermanSubs.setHorizontalAlignment(SwingConstants.LEFT);
		tglbtnPreferGermanSubs.setFont(new Font("Verdana", Font.PLAIN, 13));
		tglbtnPreferGermanSubs.setBounds(12, 221, 267, 31);
		tglbtnPreferGermanSubs.setSelected(Core.getInstance().getOptionmanager().getBoolean("Prefer-German"));
		tglbtnPreferGermanSubs.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				Core.getInstance().getOptionmanager().setOptionValue("Prefer-German", tglbtnPreferGermanSubs.isSelected());
			}
		});
		desktopPane_9.add(tglbtnPreferGermanSubs);
		
		JToggleButton tglbtnPreferEnglishDub = new JToggleButton("Prefer English Dub (if available)");
		tglbtnPreferEnglishDub.setHorizontalAlignment(SwingConstants.LEFT);
		tglbtnPreferEnglishDub.setFont(new Font("Verdana", Font.PLAIN, 13));
		tglbtnPreferEnglishDub.setBounds(12, 263, 267, 31);
		tglbtnPreferEnglishDub.setSelected(Core.getInstance().getOptionmanager().getBoolean("Prefer-Dub"));
		tglbtnPreferEnglishDub.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				Core.getInstance().getOptionmanager().setOptionValue("Prefer-Dub", tglbtnPreferEnglishDub.isSelected());
			}
		});
		desktopPane_9.add(tglbtnPreferEnglishDub);
		//TODO: End
		
		JDesktopPane desktopPane_2 = new JDesktopPane();
		desktopPane_2.setBackground(ThemeUtil.color);
		tabbedPane.addTab("Schedule", null, desktopPane_2, null);
		desktopPane_2.setLayout(null);
		
		if(!Styx.getInstance().isOffline) {
			liveScheduleScroll.setBounds(0, 0, 439, 427);
			liveScheduleScroll.setBackground(ThemeUtil.color);
			desktopPane_2.add(liveScheduleScroll);
			
			liveSchedulePanel.setEditable(false);
			liveSchedulePanel.setBackground(ThemeUtil.color);
			liveScheduleScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
			liveScheduleScroll.setViewportView(liveSchedulePanel);
		}

		watchpanel.setBackground(ThemeUtil.color);
		if(!Styx.getInstance().isOffline) {
			tabbedPane.addTab("Watch", null, watchpanel, null);
			this.setupWatchPanel();
		}
		
		JDesktopPane desktopPane_3 = new JDesktopPane();
		desktopPane_3.setBackground(ThemeUtil.color);
		tabbedPane.addTab("Local", null, desktopPane_3, null);
		desktopPane_3.setLayout(null);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(10, 25, 417, 73);
		desktopPane_3.add(scrollPane_3);
		downloading_panel.setFont(new Font("Verdana", Font.PLAIN, 13));
		
		downloading_panel.setEditable(false);
		scrollPane_3.setViewportView(downloading_panel);
		
		JLabel lblCurrentlyDownloading = new JLabel("Currently Downloading: ");
		lblCurrentlyDownloading.setBounds(0, 0, 206, 21);
		lblCurrentlyDownloading.setFont(new Font("Verdana", Font.BOLD, 14));
		desktopPane_3.add(lblCurrentlyDownloading);
		
		JScrollPane scrollPane_4 = new JScrollPane();
		scrollPane_4.setBounds(10, 110, 417, 306);
		desktopPane_3.add(scrollPane_4);
		
		scrollPane_4.setViewportView(locals_panel);
		locals_panel.setLayout(null);
		
		JButton btnAbort = new JButton("Abort");
		btnAbort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Core.getInstance().getDownloadQueue().files.clear();
				for(Watchable w : Core.getInstance().getDownloadQueue().downloading) {
					w.dlThread.abort();
					Core.getInstance().getDownloadQueue().downloading.remove(w);
				}
			}
		});
		btnAbort.setFont(new Font("Verdana", Font.PLAIN, 12));
		btnAbort.setBounds(312, 0, 115, 24);
		desktopPane_3.add(btnAbort);
		this.setupLocals();
		
		JLabel lblNewLabel = new JLabel("Online Users:");
		lblNewLabel.setFont(new Font("Verdana", Font.PLAIN, 11));
		lblNewLabel.setBounds(445, 0, 160, 15);
		desktopPane.add(lblNewLabel);
		
		scrollPane_5.setBounds(445, 15, 195, 35);
		desktopPane.add(scrollPane_5);
		
		scrollPane_5.setViewportView(onlineuserspanel);	
		scrollPane_5.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
		scrollPane_5.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 5));
		scrollPane_5.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

		JScrollPane scrollPane_6 = new JScrollPane();
		scrollPane_6.setBounds(445, 57, 195, 362);
		desktopPane.add(scrollPane_6);
		chatpanel.setEditable(false);
				
		scrollPane_6.setViewportView(chatpanel);
		scrollPane_6.setMaximumSize(new Dimension(scrollPane_6.getWidth(), scrollPane_6.getHeight()));
		scrollPane_6.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		textField = new JTextField();
		textField.setBounds(445, 433, 195, 26);
		desktopPane.add(textField);
		textField.setColumns(10);
		textField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (Styx.getInstance().isOffline || Styx.getInstance().irc == null || textField.getText().isEmpty() || textField.getText() == ""
						|| !Styx.getInstance().irc.bot.isConnected()) {
					return;
				}
				Styx.getInstance().irc.bot.send().message("#Styx", textField.getText());
				if (ChatCommands.lastuserwasthisone && !((System.currentTimeMillis() - Styx.getInstance().irc.lastmessage) > (2 * 60000))) {
					Styx.getInstance().window.addtoChat(textField.getText());
				} else {	
					Styx.getInstance().window.addtoChat("[" + Styx.getInstance().irc.getTimeStamp() + "] " + Styx.getInstance().user + ": ");
					Styx.getInstance().window.addtoChat(textField.getText());
				}
				ChatCommands.previousSendername = Styx.getInstance().user;
				ChatCommands.lastuserwasthisone = true;
				Styx.getInstance().irc.lastmessage = System.currentTimeMillis();
				textField.setText("");
			}
		});
	}
	
	public static int parseIndex(String s) {
		if(s.equalsIgnoreCase("White")) {
			return 0;
		} else if(s.equalsIgnoreCase("Oceanic")) {
			return 1;
		} else if(s.equalsIgnoreCase("Dark")) {
			return 2;
		}
		return 1;
	}
	public static String parseIndex2(int i) {
		if(i == 0) {
			return "White";
		} else if(i == 1) {
			return "Oceanic";
		} else if(i == 2) {
			return "Dark";
		}
		return "Oceanic";
	}
	
	public void addtoChat(String s) {
		chatpanel.setText(chatpanel.getText() + s + "\n");
		chatpanel.setCaretPosition(chatpanel.getDocument().getLength());
		chatpanel.setMaximumSize(new Dimension(193, 99999));
	}
	
	public void setOnlineUsers(ArrayList<String> alist) {
		onlineuserspanel.removeAll();
		for (String s : alist) {
			JLabel lbl;
			if (s.equalsIgnoreCase("Bot")) {
				if (!Core.getInstance().getOptionmanager().getCurrent("Theme").equalsIgnoreCase("White")) {
					lbl = new JLabel("<html><font color=lime>" + s + "</font></html>");
				} else {
					lbl = new JLabel("<html><font color=green>" + s + "</font></html>");
				}
			} else {
				lbl = new JLabel(s);
			}
			lbl.setFont(new Font("Verdana", Font.PLAIN, 13));
			if (!s.equalsIgnoreCase(Styx.getInstance().user)) {
				lbl.addMouseListener(new UserListener(s));
			}
			onlineuserspanel.add(lbl);
		}
		try {
			onlineuserspanel.repaint();
		}catch(Exception ee) {
			//Ignore 
		}
		scrollPane_5.setViewportView(onlineuserspanel);
		
	}
	
	public void setupWatchPanel() {
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.BOTTOM);
		tabbedPane_1.setFont(new Font("Verdana", Font.PLAIN, 14));
		tabbedPane_1.setBounds(0, 0, 439, 431);
		watchpanel.add(tabbedPane_1);
		
		JDesktopPane desktopPane_4 = new JDesktopPane();
		desktopPane_4.setBackground(ThemeUtil.color);
		tabbedPane_1.addTab("Anime", null, desktopPane_4, null);
		desktopPane_4.setLayout(null);
		
		season_tabbed.setFont(new Font("Verdana", Font.PLAIN, 12));
		season_tabbed.setBounds(0, 0, 434, 395);
		desktopPane_4.add(season_tabbed);
	
		WindowAnime.initRecents();
		WindowAnime.initSeasons();
		
		JDesktopPane desktopPane_5 = new JDesktopPane();
		desktopPane_5.setBackground(ThemeUtil.color);
		tabbedPane_1.addTab("Movies", null, desktopPane_5, null);
		
		movie_tabbed.setFont(new Font("Verdana", Font.PLAIN, 12));
		movie_tabbed.setBounds(0, 0, 434, 395);
		desktopPane_5.add(movie_tabbed);
		WindowMovie.initYears();
		
		JDesktopPane desktopPane_2 = new JDesktopPane();
		tabbedPane_1.addTab("Search", null, desktopPane_2, null);
		desktopPane_2.setBackground(ThemeUtil.color);
		desktopPane_2.setLayout(null);
		
		textField_2 = new JTextField();
		textField_2.setFont(new Font("Verdana", Font.BOLD, 13));
		textField_2.setBounds(12, 12, 410, 31);
		
		JScrollPane scrollPane_7 = new JScrollPane();
		scrollPane_7.setBounds(12, 56, 410, 330);
		scrollPane_7.getVerticalScrollBar().setPreferredSize(new Dimension(6, 0));
		scrollPane_7.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_7.getVerticalScrollBar().setUnitIncrement(10);
		desktopPane_2.add(scrollPane_7);
		
		JPanel panel = new JPanel();
		scrollPane_7.setViewportView(panel);
		panel.setLayout(null);
		textField_2.getActionMap().put(DefaultEditorKit.deletePrevCharAction, new CharDeletionOverride());
		textField_2.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {				
				this.kekw();
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {			
				this.kekw();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {

			}

			private void kekw() {
				if (textField_2.getText().isEmpty()) {
					panel.removeAll();
					panel.repaint();
					panel.setPreferredSize(new Dimension(scrollPane_7.getWidth(), scrollPane_7.getHeight() - 10));
				} else {
					int yPos = 7;
					panel.removeAll();
					ArrayList<Anime> sortedAnime = new ArrayList<>();
					ArrayList<Movie> sortedMovies = new ArrayList<>();
					for (Season sea : Core.getInstance().getSeasonManager().getSeasons()) {
						for (Anime a : sea.getAnimeList()) {
							if (a.getName().toLowerCase().contains(textField_2.getText().toLowerCase())) {
								sortedAnime.add(a);
							}
						}
					}
					for (Year year : Core.getInstance().getMovieManager().getYears()) {
						for (Movie movie : year.getMovies()) {
							if (movie.getName().toLowerCase().contains(textField_2.getText().toLowerCase())) {
								sortedMovies.add(movie);
							}
						}
					}
					Collections.sort(sortedAnime, new WatchableComparator.AnimeComparator());
					Collections.sort(sortedMovies, new WatchableComparator());
					for (Anime a : sortedAnime) {
						JButton button = new JButton(a.getName());
						button.setFont(new Font("Verdana", Font.PLAIN, 13));
						button.setBounds(12, yPos, 386, 25);
						button.addMouseListener(new MouseListener() {

							@Override
							public void mouseReleased(MouseEvent e) {
							}

							@Override
							public void mousePressed(MouseEvent e) {
								if (Styx.getInstance().window.episodes != null) {
									Styx.getInstance().window.episodes.dispose();
									Styx.getInstance().window.episodes = null;
								}
								Styx.getInstance().window.episodes = new WindowEpisodes(a);
								Styx.getInstance().window.watchpanel.add(Styx.getInstance().window.episodes);
								Styx.getInstance().window.episodes.show();
							}

							@Override
							public void mouseExited(MouseEvent e) {
							}

							@Override
							public void mouseEntered(MouseEvent e) {
							}

							@Override
							public void mouseClicked(MouseEvent e) {
							}
						});
						panel.add(button);
						yPos += 30;
					}
					for (Movie movie : sortedMovies) {
						JButton button = new JButton(movie.getName());
						button.setFont(new Font("Verdana", Font.PLAIN, 13));
						button.setBounds(12, yPos, 386, 25);
						button.addActionListener(new WatchAbleActionListener(movie, button));
						button.addMouseListener(new WatchableMouseListener(movie, button));
						panel.add(button);
						yPos += 30;
					}

					panel.repaint();
					panel.setPreferredSize(new Dimension(scrollPane_7.getWidth(), yPos));
				}
				scrollPane_7.setViewportView(panel);
				scrollPane_7.repaint();
			}
		});
		desktopPane_2.add(textField_2);
		textField_2.setColumns(10);
	}
	
	public void setupLocals() {
		int height = 0;
		for(AnimeEPLocal ep : Core.getInstance().getLocalManager().getLocalEPs()) {
			JButton btn = new JButton(ep.getName() + " (E" + ep.getEP() + ", " + ep.getDate() + (ep.hasBeenWatched() ? ", seen)" : ")"));
			btn.setFont(new Font("Verdana", Font.PLAIN, 13));
			btn.setBounds(12, height, 393, 32);
			btn.addActionListener(new WatchAbleActionListener(ep, btn));
			btn.addMouseListener(new WatchableMouseListener(ep, btn));
			locals_panel.add(btn);
//			Sys.out("test");
			height += 34;
		}
		for(MovieLocal ep : Core.getInstance().getLocalManager().getLocalMovies()) {
			JButton btn = new JButton(ep.getName() + " (" + ep.getDate() + (ep.hasBeenWatched() ? ", seen)" : ")"));
			btn.setFont(new Font("Verdana", Font.PLAIN, 13));
			btn.setBounds(12, height, 393, 32);
			btn.addActionListener(new WatchAbleActionListener(ep, btn));
			btn.addMouseListener(new WatchableMouseListener(ep, btn));
			locals_panel.add(btn);
			height += 34;
		}
		locals_panel.setPreferredSize(new Dimension(locals_panel.getWidth(), height));
	}
}
