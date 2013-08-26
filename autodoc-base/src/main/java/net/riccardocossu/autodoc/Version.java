/**
 * 
 */
package net.riccardocossu.autodoc;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * Placeholder for version information
 * 
 * @author riccardo
 * 
 */
public class Version {
	public static String VERSION;

	static {
		Properties p = new Properties();
		URL url = Version.class.getClassLoader().getResource(
				"autodoc-version.properties");
		InputStream st = null;
		try {
			st = url.openStream();
			p.load(st);
			VERSION = p.getProperty("autodoc.version");
		} catch (Exception e) {
			VERSION = "N/A";
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (IOException e) {
					// nothing to do here
				}
			}
		}

	}

}
