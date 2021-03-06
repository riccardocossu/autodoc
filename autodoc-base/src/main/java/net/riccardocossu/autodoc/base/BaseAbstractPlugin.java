/**
 * 
 */
package net.riccardocossu.autodoc.base;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class which collects all common logic
 * 
 * @author riccardo
 * 
 */
public abstract class BaseAbstractPlugin implements AnnotationsPlugin {
	private static final Logger log = LoggerFactory
			.getLogger(BaseAbstractPlugin.class);

	protected AnnotationModel getAnnotationValues(Annotation target,
			String... attributeNames) {
		AnnotationModel res = new AnnotationModel();
		res.setQualifier(getShortName());
		Class<? extends Annotation> clazz = target.annotationType();
		res.setQualifiedName(clazz.getName());
		List<AttributeModel> attributes = res.getAttributes();
		for (String name : attributeNames) {
			try {
				Method m = clazz.getDeclaredMethod(name, (Class[]) null);
				Object annValue = m.invoke(target, (Object[]) null);
				if (annValue == null) {
					AttributeModel atMd = new AttributeModel(name, "null");
					attributes.add(atMd);
				} else if (annValue instanceof Annotation) {
					AttributeModel atMd = new AttributeModel(name,
							parse((Annotation) annValue));
					atMd.setValueIsAnnotationModel(true);
					attributes.add(atMd);
				} else if (annValue instanceof Annotation[]) {
					AnnotationModel md = new AnnotationModel();
					AttributeModel att = new AttributeModel(name, md);
					att.setValueIsAnnotationModel(true);
					attributes.add(att);
					ArrayList<AnnotationModel> children = new ArrayList<AnnotationModel>();
					md.setChildren(children);
					Annotation[] annotations = (Annotation[]) annValue;
					md.setQualifiedName(annotations.getClass()
							.getComponentType().getName()
							+ "[]");
					for (Annotation a : annotations) {
						children.add(parse(a));
					}
				} else if (annValue.getClass().isArray()) {
					attributes.add(new AttributeModel(name,
							toArrayString(annValue)));
				} else {
					attributes.add(new AttributeModel(name, annValue));
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.riccardocossu.autodoc.base.AnnotationsPlugin#parse(java.lang.annotation
	 * .Annotation)
	 */
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

	private String toArrayString(Object arr) {
		if (arr instanceof boolean[]) {
			return Arrays.toString((boolean[]) arr);
		}
		if (arr instanceof byte[]) {
			return Arrays.toString((byte[]) arr);
		}
		if (arr instanceof short[]) {
			return Arrays.toString((short[]) arr);
		}
		if (arr instanceof char[]) {
			return Arrays.toString((char[]) arr);
		}
		if (arr instanceof int[]) {
			return Arrays.toString((int[]) arr);
		}
		if (arr instanceof long[]) {
			return Arrays.toString((long[]) arr);
		}
		if (arr instanceof float[]) {
			return Arrays.toString((float[]) arr);
		}
		if (arr instanceof double[]) {
			return Arrays.toString((double[]) arr);
		}
		if (arr instanceof Object[]) {
			return Arrays.toString((Object[]) arr);
		}
		return "unknown";
	}

}
