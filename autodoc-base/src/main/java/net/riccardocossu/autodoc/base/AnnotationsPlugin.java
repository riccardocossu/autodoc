/**
 * 
 */
package net.riccardocossu.autodoc.base;

import java.lang.annotation.Annotation;
import java.util.Collection;

/**
 * @author riccardo
 * 
 */
@SuppressWarnings("rawtypes")
public interface AnnotationsPlugin {

	Collection<? extends Class> getManagedAnnotations();

	public abstract AnnotationModel parse(Annotation target);

}
