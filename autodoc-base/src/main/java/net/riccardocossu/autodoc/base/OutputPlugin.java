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

	abstract void process(List<PackageContainer> packages,
			File baseOutputDirectory);

}
