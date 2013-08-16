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

}
