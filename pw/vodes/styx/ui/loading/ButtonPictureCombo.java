package pw.vodes.styx.ui.loading;

import javax.swing.JButton;

public class ButtonPictureCombo {
	
	private JButton button;
	private String picurl;
	
	public ButtonPictureCombo (JButton button, String picurl) {
		this.button = button;
		this.picurl = picurl;
	}
	
	public JButton getButton() {
		return button;
	}
	
	public String getPicURL() {
		return picurl;
	}

}
