package pw.vodes.styx.ui.sub;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import pw.vodes.styx.Styx;
import pw.vodes.styx.core.Core;

public class WindowUserDialog {

	public JFrame frmAwcpUserdialogwindow;
	public JLabel lblTest = new JLabel("");
	public JLabel lblNewLabel_1 = new JLabel("Waiting for response...");
	
	public static String name;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WindowUserDialog window = new WindowUserDialog(name);
					window.frmAwcpUserdialogwindow.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public WindowUserDialog(String name) {
		initialize(name);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String name) {
		frmAwcpUserdialogwindow = new JFrame();
		frmAwcpUserdialogwindow.setAlwaysOnTop(true);
		frmAwcpUserdialogwindow.setResizable(false);
		frmAwcpUserdialogwindow.setTitle("AWCP User-Dialog-Window");
		frmAwcpUserdialogwindow.setBounds(100, 100, 390, 130);
		frmAwcpUserdialogwindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAwcpUserdialogwindow.setUndecorated(true);
		frmAwcpUserdialogwindow.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/res/Styx.png")));
		frmAwcpUserdialogwindow.setShape(new RoundRectangle2D.Double(0, 0, frmAwcpUserdialogwindow.getWidth(), frmAwcpUserdialogwindow.getHeight(), 15, 15));
		frmAwcpUserdialogwindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmAwcpUserdialogwindow.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Is Playing: ");
		lblNewLabel.setFont(new Font("Verdana", Font.BOLD, 13));
		lblNewLabel.setBounds(12, 56, 82, 21);
		if(name.equalsIgnoreCase("Bot")) {
			if(!Core.getInstance().getOptionmanager().getCurrent("Theme").equalsIgnoreCase("White")) {
				lblTest = new JLabel("<html><font color=lime>" + name + "</font></html>");
			} else {
				lblTest = new JLabel("<html><font color=green>" + name + "</font></html>");
			}
			lblNewLabel_1.setBounds(9, 56, 367, 21);
			lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		} else {
			lblTest = new JLabel(name);
			frmAwcpUserdialogwindow.getContentPane().add(lblNewLabel);
			lblNewLabel_1.setBounds(99, 56, 280, 21);
		}
		
		JButton btnX = new JButton("X");
		btnX.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Styx.getInstance().window.userdialog.close();
			}
		});
		btnX.setFont(new Font("Verdana", Font.PLAIN, 13));
		btnX.setBounds(334, 97, 45, 21);
		frmAwcpUserdialogwindow.getContentPane().add(btnX);
		
		lblTest.setHorizontalAlignment(SwingConstants.CENTER);
		lblTest.setFont(new Font("Verdana", Font.BOLD, 15));
		lblTest.setBounds(12, 12, 367, 32);
		frmAwcpUserdialogwindow.getContentPane().add(lblTest);
		
		lblNewLabel_1.setFont(new Font("Verdana", Font.PLAIN, 13));
		frmAwcpUserdialogwindow.getContentPane().add(lblNewLabel_1);
	}
	
	public void close() {
		Styx.getInstance().window.userdialog.frmAwcpUserdialogwindow.dispose();
		Styx.getInstance().window.userdialog = null;
	}
	
}
