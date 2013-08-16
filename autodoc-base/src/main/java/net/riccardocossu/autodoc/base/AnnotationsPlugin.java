/**
 * 
 */
package net.riccardocossu.autodoc.base;

import java.lang.annotation.Annotation;
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

}
