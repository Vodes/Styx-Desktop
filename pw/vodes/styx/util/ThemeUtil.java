package pw.vodes.styx.util;

import java.awt.Color;
import java.awt.Font;
import java.util.Enumeration;

import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import mdlaf.MaterialLookAndFeel;
import mdlaf.themes.JMarsDarkTheme;
import mdlaf.themes.MaterialOceanicTheme;
import pw.vodes.styx.ui.themes.CustomDarkTheme;
import pw.vodes.styx.ui.themes.CustomOceanicTheme;
import pw.vodes.styx.ui.themes.CustomWhiteTheme;

public class ThemeUtil {

	public static Color color = new Color(45, 48, 56);

	public static void setTheme(String theme) {
		try {
			if (theme.contains("Theme")) {
				theme = theme.replace("Theme", "");
			}
			if (theme.equalsIgnoreCase("White")) {
				UIManager.setLookAndFeel(new MaterialLookAndFeel());
				MaterialLookAndFeel.changeTheme(new CustomWhiteTheme(10));
				color = new Color(255, 255, 255);
			} else if (theme.equalsIgnoreCase("Dark")) {
				UIManager.setLookAndFeel(new MaterialLookAndFeel());
				MaterialLookAndFeel.changeTheme(new CustomDarkTheme(10));
				color = new Color(45, 48, 56);
			} else {
				UIManager.setLookAndFeel(new MaterialLookAndFeel());
				MaterialLookAndFeel.changeTheme(new CustomOceanicTheme(10));
				color = new Color(40, 65, 91);
			}
			editThemes();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static void editThemes() {
		ToolTipManager.sharedInstance().setInitialDelay(100);
		setUIFont(new FontUIResource("Verdana", Font.PLAIN, 13));
		UIManager.put("ScrollBar.width", (int) ((int) UIManager.get("ScrollBar.width") * 0.7));
	}

	@SuppressWarnings("rawtypes")
	public static void setUIFont(FontUIResource f) {
		Enumeration keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof FontUIResource)
				UIManager.put(key, f);
		}
	}

}
