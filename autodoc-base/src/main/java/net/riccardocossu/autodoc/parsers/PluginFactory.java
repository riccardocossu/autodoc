/**
 * 
 */
package net.riccardocossu.autodoc.parsers;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
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
		ServiceLoader<OutputPlugin> outPutLoader = ServiceLoader
				.load(OutputPlugin.class);
		Iterator<OutputPlugin> outputIt = outPutLoader.iterator();
		while (outputIt.hasNext()) {
			OutputPlugin pl = outputIt.next();
			String shortName = pl.getShortName();
			// if plugins doesn't want or need to be called by shortname, it
			// should set this method to return null
			if (shortName != null) {
				outputPluginsByShortName.put(shortName, pl);
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

	/**
	 * Register input plugin; can be referred by FQCN or short name
	 * 
	 * @param identifier
	 *            identifier for the plugin
	 */
	public void registerInputPlugin(String identifier) {
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
	 * Gets the plugin for the given identifier
	 * 
	 * @param identifier
	 *            is a FQCN or a short identifier
	 * @param configResource
	 *            is a resource path from where the plugin should be able to
	 *            configure itself (can be <code>null</code>; in that case the
	 *            plugin is left to its default configuration
	 * @return the configured plugin instance if everything is ok
	 */
	public OutputPlugin initOutputPlugin(String identifier,
			String configResource) {
		OutputPlugin pl = outputPluginsByShortName.get(identifier);
		if (pl == null) {
			try {
				Class<?> clazz = Class.forName(identifier);
				pl = (OutputPlugin) clazz.newInstance();
			} catch (Exception e) {
				throw new IllegalArgumentException("Unable to locate plugin "
						+ identifier, e);
			}
		}
		if (pl != null && configResource != null) {
			pl.configure(configResource);
		}
		return pl;
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

	/**
	 * Tells if a method is relevant for at least one plugin
	 * 
	 * @param meth
	 *            the method to check
	 * @param methodLevelAnnotations
	 *            the array of annotation, to avoid repeating the call for every
	 *            plugin
	 * @return <code>true</code> if the method is interesting to at least one
	 *         plugin, <code>false</code> otherwise
	 */
	public boolean isMethodUseful(Method meth,
			Annotation[] methodLevelAnnotations) {
		boolean isUseful = false;
		for (Iterator<AnnotationsPlugin> it = registeredPlugins.iterator(); !isUseful
				&& it.hasNext();) {
			isUseful = it.next().isMethodUseful(meth, methodLevelAnnotations);
		}
		return isUseful;
	}

	/**
	 * Tells if a field is relevant for at least one plugin
	 * 
	 * @param field
	 *            the field to check
	 * @param fieldLevelAnnotations
	 *            the array of annotation, to avoid repeating the call for every
	 *            plugin
	 * @return <code>true</code> if the field is interesting to at least one
	 *         plugin, <code>false</code> otherwise
	 */
	public boolean isFieldUseful(Field field, Annotation[] fieldLevelAnnotations) {
		boolean isUseful = false;
		for (Iterator<AnnotationsPlugin> it = registeredPlugins.iterator(); !isUseful
				&& it.hasNext();) {
			isUseful = it.next().isFieldUseful(field, fieldLevelAnnotations);
		}
		return isUseful;
	}

	public Set<AnnotationsPlugin> getRegisteredPlugins() {
		return registeredPlugins;
	}

}
