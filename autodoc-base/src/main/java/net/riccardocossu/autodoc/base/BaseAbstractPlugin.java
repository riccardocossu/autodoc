/**
 * 
 */
package net.riccardocossu.autodoc.base;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author riccardo
 * 
 */
public abstract class BaseAbstractPlugin implements AnnotationsPlugin {
	private static final Logger log = LoggerFactory
			.getLogger(BaseAbstractPlugin.class);

	protected AnnotationModel getAnnotationValues(Annotation target,
			String... attributeNames) {
		AnnotationModel res = new AnnotationModel();
		Class<? extends Annotation> clazz = target.annotationType();
		res.setQualifiedName(clazz.getName());
		List<AttributeModel> attributes = res.getAttributes();
		for (String name : attributeNames) {
			try {
				Method m = clazz.getDeclaredMethod(name, (Class[]) null);
				Object annValue = m.invoke(target, (Object[]) null);
				if (annValue instanceof Annotation) {
					attributes.add(new AttributeModel(name,
							parse((Annotation) annValue)));
				} else if (annValue instanceof Annotation[]) {
					AnnotationModel md = new AnnotationModel();
					attributes.add(new AttributeModel(name, md));
					ArrayList<AnnotationModel> children = new ArrayList<AnnotationModel>();
					md.setChildren(children);
					Annotation[] annotations = (Annotation[]) annValue;
					md.setQualifiedName(annotations.getClass().getName());
					for (Annotation a : annotations) {
						children.add(parse(a));
					}
				} else {
					attributes.add(new AttributeModel(name, annValue));
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		return res;
	}

	@Override
	public AnnotationModel parse(Annotation target) {
		Class<? extends Annotation> clazz = target.annotationType();
		Method[] declaredMethods = clazz.getDeclaredMethods();
		List<String> names = new ArrayList<String>();
		for (Method m : declaredMethods) {
			names.add(m.getName());
		}
		return getAnnotationValues(target,
				names.toArray(new String[names.size()]));

	}

	protected String toString(Object target) {
		if (target == null) {
			return null;
		} else {
			return target.toString();
		}
	}

}
