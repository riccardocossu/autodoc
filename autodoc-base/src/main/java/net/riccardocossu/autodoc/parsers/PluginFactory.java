/**
 * 
 */
package net.riccardocossu.autodoc.parsers;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.riccardocossu.autodoc.base.AnnotationsPlugin;

/**
 * @author riccardo
 * 
 */
@SuppressWarnings("rawtypes")
public class PluginFactory {

	private Map<String, AnnotationsPlugin> registeredPlugins = new HashMap<String, AnnotationsPlugin>();

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

	public AnnotationsPlugin getPluginForAnnotation(Class annotation) {
		return registeredPlugins.get(annotation.getName());
	}
}
