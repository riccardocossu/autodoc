/**
 * 
 */
package net.riccardocossu.autodoc.main;

import java.util.ArrayList;
import java.util.List;

import net.riccardocossu.autodoc.base.AnnotationsPlugin;
import net.riccardocossu.autodoc.base.PackageContainer;
import net.riccardocossu.autodoc.parsers.PackageParser;
import net.riccardocossu.autodoc.parsers.PluginFactory;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author riccardo
 * 
 */
public class Engine {
	private static final Logger log = LoggerFactory.getLogger(Engine.class);

	private Configuration configuration;

	public Engine(Configuration configuration) {
		super();
		this.configuration = configuration;
	}

	public List<PackageContainer> execute() {
		String[] plugins = configuration
				.getStringArray("net.riccardocossu.autodoc.plugins");
		log.debug("Using plugins: {}", (Object) plugins);
		PluginFactory factory = new PluginFactory();
		for (String p : plugins) {
			try {
				Class<?> clazz = Class.forName(p);
				AnnotationsPlugin pl = (AnnotationsPlugin) clazz.newInstance();
				factory.registerPlugin(pl);
			} catch (Exception e) {
				log.error("Error including plugin " + p, e);
			}
		}
		String[] packages = configuration
				.getStringArray("net.riccardocossu.autodoc.packages");
		log.debug("Scanning packages: {}", (Object) packages);
		PackageParser parser = new PackageParser();
		List<PackageContainer> parsedPackages = new ArrayList<PackageContainer>();
		for (String p : packages) {
			PackageContainer pk = parser.parse(p, factory);
			parsedPackages.add(pk);
		}
		return parsedPackages;

	}

}
