/**
 * 
 */
package net.riccardocossu.autodoc.parsers;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.riccardocossu.autodoc.base.AnnotationsPlugin;

/**
 * Plugin container; it's duty is to give the right plugin at the right time to
 * its users.
 * 
 * @author riccardo
 * 
 */
@SuppressWarnings("rawtypes")
public class PluginFactory {

	private Map<String, AnnotationsPlugin> registeredPlugins = new HashMap<String, AnnotationsPlugin>();

	/**
	 * Register a plugin for its annotation; the order is relevant, because only
	 * the first plugin is registered for a given annotation.
	 * 
	 * @param plugin
	 *            the plugin to configure for its annotations.
	 */
	public void registerPlugin(AnnotationsPlugin plugin) {
		Collection<? extends Class> managedAnnotations = plugin
				.getManagedAnnotations();
		for (Class c : managedAnnotations) {
			// first registered plugin only for every annotation
			if (!registeredPlugins.containsKey(c)) {
				registeredPlugins.put(c.getName(), plugin);
			}
		}
	}

	/**
	 * Returns the registered plugin for the given annotation.
	 * 
	 * @param annotation
	 *            the annotation to look for
	 * @return the registered plugin or <code>null</code> if none
	 */
	public AnnotationsPlugin getPluginForAnnotation(Class annotation) {
		return registeredPlugins.get(annotation.getName());
	}
}
