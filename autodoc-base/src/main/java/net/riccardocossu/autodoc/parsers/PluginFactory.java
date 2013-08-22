/**
 * 
 */
package net.riccardocossu.autodoc.parsers;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;

import net.riccardocossu.autodoc.base.AnnotationsPlugin;
import net.riccardocossu.autodoc.base.OutputPlugin;

/**
 * Plugin container; it's duty is to give the right plugin at the right time to
 * its users.
 * 
 * @author riccardo
 * 
 */
@SuppressWarnings("rawtypes")
public class PluginFactory {

	private Map<String, AnnotationsPlugin> pluginsByAnnotation = new HashMap<String, AnnotationsPlugin>();
	private Set<AnnotationsPlugin> registeredPlugins = new HashSet<AnnotationsPlugin>();
	private static Map<String, AnnotationsPlugin> inputPluginsByShortName = new HashMap<String, AnnotationsPlugin>();
	private static Map<String, OutputPlugin> outputPluginsByShortName = new HashMap<String, OutputPlugin>();
	static {
		ServiceLoader<AnnotationsPlugin> loader = ServiceLoader
				.load(AnnotationsPlugin.class);
		Iterator<AnnotationsPlugin> it = loader.iterator();
		while (it.hasNext()) {
			AnnotationsPlugin pl = it.next();
			String shortName = pl.getShortName();
			// if plugins doesn't want or need to be called by shortname, it
			// should set this method to return null
			if (shortName != null) {
				inputPluginsByShortName.put(shortName, pl);
			}
		}
	}

	/**
	 * Register a plugin for its annotation; the order is relevant, because only
	 * the first plugin is registered for a given annotation.
	 * 
	 * @param plugin
	 *            the plugin to configure for its annotations.
	 */
	public void registerPlugin(AnnotationsPlugin plugin) {
		registeredPlugins.add(plugin);
		Collection<? extends Class> managedAnnotations = plugin
				.getManagedAnnotations();
		for (Class c : managedAnnotations) {
			// first registered plugin only for every annotation
			if (!pluginsByAnnotation.containsKey(c)) {
				pluginsByAnnotation.put(c.getName(), plugin);
			}
		}
	}

	public void registerPlugin(String identifier) {
		AnnotationsPlugin pl = inputPluginsByShortName.get(identifier);
		if (pl == null) {
			try {
				Class<?> clazz = Class.forName(identifier);
				pl = (AnnotationsPlugin) clazz.newInstance();
			} catch (Exception e) {
				throw new IllegalArgumentException("Unable to locate plugin "
						+ identifier, e);
			}
		}
		registerPlugin(pl);
	}

	/**
	 * Returns the registered plugin for the given annotation.
	 * 
	 * @param annotation
	 *            the annotation to look for
	 * @return the registered plugin or <code>null</code> if none
	 */
	public AnnotationsPlugin getPluginForAnnotation(Class annotation) {
		return pluginsByAnnotation.get(annotation.getName());
	}

	/**
	 * Tells if a class is relevant for at least one plugin
	 * 
	 * @param clazz
	 *            the class to check
	 * @param classLevelAnnotations
	 *            the array of annotation, to avoid repeating the call for every
	 *            plugin
	 * @return <code>true</code> if the class is interesting to at least one
	 *         plugin, <code>false</code> otherwise
	 */
	public boolean isClassUseful(Class clazz, Annotation[] classLevelAnnotations) {
		boolean isUseful = false;
		for (Iterator<AnnotationsPlugin> it = registeredPlugins.iterator(); !isUseful
				&& it.hasNext();) {
			isUseful = it.next().isClassUseful(clazz, classLevelAnnotations);
		}
		return isUseful;
	}
}
