package pw.vodes.styx.ui.themes;

import mdlaf.themes.JMarsDarkTheme;
import mdlaf.themes.MaterialLiteTheme;

public class CustomDarkTheme extends JMarsDarkTheme {
	
	private int customTabHeight;

	public CustomDarkTheme(int tabheight) {
		this.customTabHeight = tabheight;
	}

	@Override
	public int getHeightTabTabbedPane() {
		return customTabHeight;
	}

}
