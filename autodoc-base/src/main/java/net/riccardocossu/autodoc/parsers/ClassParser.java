/**
 * 
 */
package net.riccardocossu.autodoc.parsers;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import net.riccardocossu.autodoc.base.AnnotatedClass;
import net.riccardocossu.autodoc.base.AnnotatedField;
import net.riccardocossu.autodoc.base.AnnotatedMethod;
import net.riccardocossu.autodoc.base.AnnotationsPlugin;

/**
 * Class parser; it gets the right plugin from the factory and istruct it to
 * parse every interesting annotation it finds on classes.
 * 
 * @author riccardo
 * 
 */
public class ClassParser {

	/**
	 * Parse the given class
	 * 
	 * @param clazz
	 *            the class to parse
	 * @param factory
	 *            the configured plugin factory
	 * @return the parsed class model, or <code>null</code> if no plugin is
	 *         interested in it
	 */
	public AnnotatedClass parse(@SuppressWarnings("rawtypes") Class clazz,
			PluginFactory factory) {
		AnnotatedClass ac = null;
		Annotation[] annotations = clazz.getDeclaredAnnotations();
		if (factory.isClassUseful(clazz, annotations)) {
			ac = new AnnotatedClass();
			ac.setQualifiedName(clazz.getName());
			AnnotationsPlugin pl = null;
			Annotation[] declaredAnnotations = null;
			for (Annotation a : annotations) {
				pl = factory.getPluginForAnnotation(a.annotationType());
				if (pl != null) {
					ac.getAnnotations().add(pl.parse(a));
				}
			}
			Field[] fields = clazz.getDeclaredFields();
			for (Field f : fields) {
				declaredAnnotations = f.getDeclaredAnnotations();
				if (factory.isFieldUseful(f, declaredAnnotations)) {
					AnnotatedField af = new AnnotatedField();
					af.setName(f.getName());
					af.setType(f.getType().getName());
					for (Annotation a : declaredAnnotations) {
						pl = factory.getPluginForAnnotation(a.annotationType());
						if (pl != null) {
							af.getAnnotations().add(pl.parse(a));
						}
					}
					ac.getFields().add(af);
				}
			}
			Method[] methods = clazz.getDeclaredMethods();
			for (Method m : methods) {
				declaredAnnotations = m.getDeclaredAnnotations();
				if (factory.isMethodUseful(m, declaredAnnotations)) {
					AnnotatedMethod am = new AnnotatedMethod();
					am.setName(m.getName());
					am.setReturnType(m.getReturnType().getName());
					for (Annotation a : declaredAnnotations) {
						pl = factory.getPluginForAnnotation(a.annotationType());
						if (pl != null) {
							am.getAnnotations().add(pl.parse(a));
						}
					}
					ac.getMethods().add(am);
				}
			}
		}
		return ac;
	}
}
