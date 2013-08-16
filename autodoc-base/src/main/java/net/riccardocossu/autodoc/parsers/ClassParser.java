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

	public AnnotatedClass parse(@SuppressWarnings("rawtypes") Class clazz,
			PluginFactory factory) {
		AnnotatedClass ac = new AnnotatedClass();
		ac.setQualifiedName(clazz.getName());
		Annotation[] annotations = clazz.getDeclaredAnnotations();
		for (Annotation a : annotations) {
			AnnotationsPlugin pl = factory.getPluginForAnnotation(a
					.annotationType());
			if (pl != null) {
				ac.getAnnotations().add(pl.parse(a));
			}
		}
		Field[] fields = clazz.getDeclaredFields();
		for (Field f : fields) {
			AnnotatedField af = new AnnotatedField();
			ac.getFields().add(af);
			af.setName(f.getName());
			Annotation[] declaredAnnotations = f.getDeclaredAnnotations();
			for (Annotation a : declaredAnnotations) {
				AnnotationsPlugin pl = factory.getPluginForAnnotation(a
						.annotationType());
				if (pl != null) {
					af.getAnnotations().add(pl.parse(a));
				}

			}
		}
		Method[] methods = clazz.getDeclaredMethods();
		for (Method m : methods) {
			AnnotatedMethod am = new AnnotatedMethod();
			boolean isInteresting = false;
			am.setName(m.getName());
			Annotation[] declaredAnnotations = m.getDeclaredAnnotations();
			for (Annotation a : declaredAnnotations) {
				AnnotationsPlugin pl = factory.getPluginForAnnotation(a
						.annotationType());
				if (pl != null) {
					am.getAnnotations().add(pl.parse(a));
					isInteresting = isInteresting || pl.isMethodUseful(m);
				}
			}
			if (isInteresting) {
				ac.getMethods().add(am);
			}
		}
		return ac;
	}
}
