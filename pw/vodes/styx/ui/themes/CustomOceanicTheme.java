package pw.vodes.styx.ui.themes;

import mdlaf.themes.MaterialOceanicTheme;

public class CustomOceanicTheme extends MaterialOceanicTheme {
	
	private int customTabHeight;
	
	public CustomOceanicTheme(int tabheight) {
		this.customTabHeight = tabheight;
	}
	
	@Override
	public int getHeightTabTabbedPane() {
		return customTabHeight;
	}

}
