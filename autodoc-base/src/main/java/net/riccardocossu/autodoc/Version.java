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
	public static String URL;

	static {
		Properties p = new Properties();
		URL url = Version.class.getClassLoader().getResource(
				"autodoc-version.properties");
		InputStream st = null;
		try {
			st = url.openStream();
			p.load(st);
		} catch (Exception e) {
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (IOException e) {
					// nothing to do here
				}
			}
		}
		VERSION = p.getProperty("autodoc.version", "N/A");
		URL = p.getProperty("autodoc.url",
				"https://bitbucket.org/riccardocossu/autodoc");

	}

}
