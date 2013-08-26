/**
 * 
 */
package net.riccardocossu.autodoc.test.plugin;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.management.MXBean;

import net.riccardocossu.autodoc.base.AnnotationModel;
import net.riccardocossu.autodoc.base.AnnotationsPlugin;

/**
 * @author riccardo
 * 
 */
@SuppressWarnings("rawtypes")
public class TestSimplestPlugin implements AnnotationsPlugin {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.riccardocossu.autodoc.base.AnnotationsPlugin#getManagedAnnotations()
	 */

	@SuppressWarnings("unchecked")
	@Override
	public Collection<? extends Class> getManagedAnnotations() {
		List<Class> res = new ArrayList();
		res.add(MXBean.class);
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
		AnnotationModel am = new AnnotationModel();
		am.setQualifiedName(MXBean.class.getName());
		return am;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.riccardocossu.autodoc.base.AnnotationsPlugin#isMethodUseful(java.
	 * lang.reflect.Method, java.lang.annotation.Annotation[])
	 */
	@Override
	public boolean isMethodUseful(Method method,
			Annotation[] methodLevelAnnotations) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.riccardocossu.autodoc.base.AnnotationsPlugin#isClassUseful(java.lang
	 * .Class, java.lang.annotation.Annotation[])
	 */
	@Override
	public boolean isClassUseful(Class clazz, Annotation[] classLevelAnnotations) {
		boolean res = false;
		for (Annotation a : classLevelAnnotations) {
			res = res || a instanceof MXBean;
		}
		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.riccardocossu.autodoc.base.AnnotationsPlugin#isFieldUseful(java.lang
	 * .reflect.Field, java.lang.annotation.Annotation[])
	 */
	@Override
	public boolean isFieldUseful(Field field, Annotation[] fieldLevelAnnotations) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.riccardocossu.autodoc.base.AnnotationsPlugin#getShortName()
	 */
	@Override
	public String getShortName() {
		return null;
	}
}
