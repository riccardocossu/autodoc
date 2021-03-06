/**
 * 
 */
package net.riccardocossu.autodoc.main;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.riccardocossu.autodoc.base.AnnotationsPlugin;
import net.riccardocossu.autodoc.base.OutputPlugin;
import net.riccardocossu.autodoc.base.PackageContainer;
import net.riccardocossu.autodoc.parsers.PackageParser;
import net.riccardocossu.autodoc.parsers.PluginFactory;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is responsible for all processing, from class parsing to output.
 * Every operation is delegated to plugins.
 * 
 * @author riccardo
 * 
 */
public class Engine {
	public static final String CONFIG_PACKAGES = "net.riccardocossu.autodoc.packages";

	public static final String CONFIG_INPUT_PLUGINS = "net.riccardocossu.autodoc.inputPlugins";

	public static final String CONFIG_OUTPUT_PLUGINS = "net.riccardocossu.autodoc.outputPlugins";

	public static final String CONFIG_BASE_OUTPUT_DIR = "net.riccardocossu.autodoc.baseOutputDir";

	private static final Logger log = LoggerFactory.getLogger(Engine.class);

	private Configuration configuration;

	public Engine(Configuration configuration) {
		super();
		this.configuration = configuration;
	}

	public List<PackageContainer> execute() {
		String[] plugins = configuration.getStringArray(CONFIG_INPUT_PLUGINS);
		log.info("Using plugins: {}", (Object) plugins);
		PluginFactory factory = new PluginFactory();
		for (String p : plugins) {
			try {
				factory.registerInputPlugin(p);
			} catch (Exception e) {
				log.error("Error including input plugin " + p, e);
			}
		}
		Set<String> activeInputPlugins = new HashSet<String>();
		for (AnnotationsPlugin p : factory.getRegisteredPlugins()) {
			activeInputPlugins.add(p.getShortName());
		}
		String[] packages = configuration.getStringArray(CONFIG_PACKAGES);
		log.info("Scanning packages: {}", (Object) packages);
		PackageParser parser = new PackageParser();
		List<PackageContainer> parsedPackages = new ArrayList<PackageContainer>();
		for (String p : packages) {
			PackageContainer pk = parser.parse(p, factory);
			parsedPackages.add(pk);
		}
		String confOutputDir = configuration.getString(CONFIG_BASE_OUTPUT_DIR);
		if (confOutputDir == null) {
			confOutputDir = System.getProperty("java.io.tmpdir");
		}
		File baseOutputDirectory = new File(confOutputDir);
		String[] outputPlugins = configuration
				.getStringArray(CONFIG_OUTPUT_PLUGINS);
		log.info("Using output plugins: {}", (Object) outputPlugins);
		for (String p : outputPlugins) {
			try {
				String[] splittedPackage = p.split(",");
				// identifier is mandatory and it's always the first part
				String identifier = splittedPackage[0];
				String configResource = splittedPackage.length > 1 ? splittedPackage[1]
						: null;
				OutputPlugin pl = factory.initOutputPlugin(identifier,
						configResource);
				pl.setActiveInputPlugins(activeInputPlugins);
				pl.process(parsedPackages, baseOutputDirectory);
			} catch (Exception e) {
				log.error("Error including or executing output plugin " + p, e);
			}
		}
		return parsedPackages;

	}
}
