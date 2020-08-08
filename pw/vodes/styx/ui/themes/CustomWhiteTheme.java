package pw.vodes.styx.ui.themes;

import mdlaf.themes.JMarsDarkTheme;
import mdlaf.themes.MaterialLiteTheme;
import mdlaf.themes.MaterialOceanicTheme;

public class CustomWhiteTheme extends MaterialLiteTheme {
	
	private int customTabHeight;

	public CustomWhiteTheme(int tabheight) {
		this.customTabHeight = tabheight;
	}

	@Override
	public int getHeightTabTabbedPane() {
		return customTabHeight;
	}

}
