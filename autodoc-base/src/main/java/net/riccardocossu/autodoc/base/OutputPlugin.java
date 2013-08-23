/**
 * 
 */
package net.riccardocossu.autodoc.base;

import java.io.File;
import java.util.List;

/**
 * @author riccardo
 * 
 */
public interface OutputPlugin {

	/**
	 * Process a list of packages
	 * 
	 * @param packages
	 *            list of packages to be processed
	 * @param baseOutputDirectory
	 *            directory to write output files to
	 */
	abstract void process(List<PackageContainer> packages,
			File baseOutputDirectory);

	/**
	 * Gets a short name for the plugin, which can be used to refer the plugin
	 * in configuration
	 * 
	 * @return the short name for the plugin or <code>null</code> if the plugin
	 *         is not interested in this feature
	 */
	public abstract String getShortName();

	/**
	 * Configures the plugin with the given resource; the implementation is
	 * plugin specific
	 * 
	 * @param configResource
	 *            Resource path where to look for the configuration
	 */
	public abstract void configure(String configResource);

}
