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
public interface AnnotationsPlugin {
	
	Collection<? extends Class> getManagedAnnotations();

}
