package pw.vodes.styx.ui.listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import pw.vodes.styx.Main;
import pw.vodes.styx.Styx;
import pw.vodes.styx.ui.sub.WindowUserDialog;

public class UserListener implements MouseListener{
	
	String name;
	
	public UserListener(String name) {
		this.name = name;
	}

	@Override
	public void mouseClicked(MouseEvent e) {		
	}

	@Override
	public void mouseEntered(MouseEvent e) {		
	}

	@Override
	public void mouseExited(MouseEvent e) {		
	}

	@Override
	public void mousePressed(MouseEvent e) {	
		Styx.getInstance().window.userdialog = new WindowUserDialog(name);
		Styx.getInstance().irc.bot.send().message("#Styx", "!" + name + " request watching");
		Styx.getInstance().window.userdialog.frmAwcpUserdialogwindow.setBounds(e.getXOnScreen() - 200, e.getYOnScreen() - 50, Styx.getInstance().window.userdialog.frmAwcpUserdialogwindow.getWidth(), Styx.getInstance().window.userdialog.frmAwcpUserdialogwindow.getHeight());
		Styx.getInstance().window.userdialog.frmAwcpUserdialogwindow.setVisible(true);
	}

	@Override
	public void mouseReleased(MouseEvent e) {		
	}

}
