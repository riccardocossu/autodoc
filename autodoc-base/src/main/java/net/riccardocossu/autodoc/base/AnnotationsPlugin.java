/**
 * 
 */
package net.riccardocossu.autodoc.base;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;

/**
 * Input plugin; it is responsible to parse annotation and produce model
 * instances
 * 
 * @author riccardo
 * 
 */
@SuppressWarnings("rawtypes")
public interface AnnotationsPlugin {

	/**
	 * Gives the list of annotations which are parsed by this plugin
	 * 
	 * @return the list of annotation which this plugin is able to parse
	 */
	Collection<? extends Class> getManagedAnnotations();

	/**
	 * Parse a single annotation
	 * 
	 * @param target
	 *            the annotation to parse
	 * @return the parsed model
	 */
	public abstract AnnotationModel parse(Annotation target);

	/**
	 * Tells the class parser if this plugin is interested in this method; for
	 * example, only getter methods are interesting for JPA annotations
	 * 
	 * @param method
	 *            the method to check
	 * @return <code>true</code> if the method is relevant, <code>false</code>
	 *         otherwise
	 */
	public abstract boolean isMethodUseful(Method method);

	/**
	 * Tells the engine if the given class is useful for this plugin
	 * 
	 * @param clazz
	 *            the class to check for
	 * @return <code>true</code> if the plugin is interested in parsing this
	 *         class
	 */
	public abstract boolean isClassUseful(Class clazz);

	/**
	 * Telss the engine if the given field is interesting for this plugin
	 * 
	 * @param field
	 *            the field to examine
	 * @return <code>true</code> if the plugin is interested in parsing this
	 *         field
	 */
	public abstract boolean isFieldUseful(Field field);

	/**
	 * Gets a short name for the plugin, which can be used to refer the plugin
	 * in configuration
	 * 
	 * @return the short name for the plugin or <code>null</code> if the plugin
	 *         is not interested in this feature
	 */
	public abstract String getShortName();

}
