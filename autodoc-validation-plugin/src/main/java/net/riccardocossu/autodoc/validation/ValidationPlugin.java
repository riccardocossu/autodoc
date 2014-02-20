/**
 * 
 */
package net.riccardocossu.autodoc.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.validation.Constraint;
import javax.validation.GroupSequence;
import javax.validation.OverridesAttribute;
import javax.validation.ReportAsSingleViolation;
import javax.validation.Valid;
import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import net.riccardocossu.autodoc.base.AnnotationModel;
import net.riccardocossu.autodoc.base.AnnotationsPlugin;
import net.riccardocossu.autodoc.base.BaseAbstractPlugin;

/**
 * @author riccardo
 * 
 */
@SuppressWarnings("rawtypes")
public class ValidationPlugin extends BaseAbstractPlugin implements
		AnnotationsPlugin {

	@SuppressWarnings("unchecked")
	private static final List<? extends Class> MANAGED = Arrays.asList(
			Constraint.class, GroupSequence.class, OverridesAttribute.class,
			OverridesAttribute.List.class, ReportAsSingleViolation.class,
			Valid.class, AssertFalse.class, AssertFalse.List.class,
			AssertTrue.class, AssertTrue.List.class, DecimalMax.class,
			DecimalMax.List.class, DecimalMin.class, DecimalMin.List.class,
			Digits.class, Digits.List.class, Future.class, Future.List.class,
			Max.class, Max.List.class, Min.class, Min.List.class, Null.class,
			Null.List.class, NotNull.class, NotNull.List.class, Past.class,
			Past.List.class, Pattern.class, Pattern.List.class, Size.class,
			Size.List.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.riccardocossu.autodoc.base.AnnotationsPlugin#getManagedAnnotations()
	 */
	public Collection<? extends Class> getManagedAnnotations() {
		return MANAGED;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.riccardocossu.autodoc.base.AnnotationsPlugin#isMethodUseful(java.
	 * lang.reflect.Method, java.lang.annotation.Annotation[])
	 */
	public boolean isMethodUseful(Method method,
			Annotation[] methodLevelAnnotations) {
		boolean res = false;
		boolean isStatic = Modifier.isStatic(method.getModifiers());
		if (!isStatic) {
			res = checkAnnotations(methodLevelAnnotations);
		}
		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.riccardocossu.autodoc.base.AnnotationsPlugin#isClassUseful(java.lang
	 * .Class, java.lang.annotation.Annotation[])
	 */
	public boolean isClassUseful(Class clazz, Annotation[] classLevelAnnotations) {
		boolean res = false;
		if (classLevelAnnotations != null) {
			res = checkAnnotations(classLevelAnnotations);
		}
		if (!res) {
			Method[] declaredMethods = clazz.getDeclaredMethods();
			for (int i = 0; !res && i < declaredMethods.length; i++) {
				res = isMethodUseful(declaredMethods[i],
						declaredMethods[i].getDeclaredAnnotations());
			}
		}
		if (!res) {
			Field[] declaredFields = clazz.getDeclaredFields();
			for (int i = 0; !res && i < declaredFields.length; i++) {
				res = isFieldUseful(declaredFields[i],
						declaredFields[i].getDeclaredAnnotations());
			}
		}
		return res;
	}

	private boolean checkAnnotations(Annotation[] classLevelAnnotations) {
		boolean res = false;
		for (int i = 0; !res && i < classLevelAnnotations.length; i++) {
			Iterator<? extends Class> it = MANAGED.iterator();
			for (; !res && it.hasNext();) {
				Class c = it.next();
				res = c.isInstance(classLevelAnnotations[i]);
			}
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
	public boolean isFieldUseful(Field field, Annotation[] fieldLevelAnnotations) {
		boolean res = false;
		boolean isStatic = Modifier.isStatic(field.getModifiers());
		if (!isStatic) {
			res = checkAnnotations(fieldLevelAnnotations);
		}
		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.riccardocossu.autodoc.base.AnnotationsPlugin#getShortName()
	 */
	public String getShortName() {
		return "VALIDATION";
	}

	public AnnotationModel parse(Constraint target) {
		return getAnnotationValues(target, "validatedBy");
	}

	public AnnotationModel parse(GroupSequence target) {
		return getAnnotationValues(target, "value");
	}

	public AnnotationModel parse(OverridesAttribute target) {
		return getAnnotationValues(target, "constraint", "name");
	}

	public AnnotationModel parse(OverridesAttribute.List target) {
		return getAnnotationValues(target, "value");
	}

	public AnnotationModel parse(ReportAsSingleViolation target) {
		return getAnnotationValues(target, new String[0]);
	}

	public AnnotationModel parse(Valid target) {
		return getAnnotationValues(target, new String[0]);
	}

	public AnnotationModel parse(AssertFalse target) {
		return getAnnotationValues(target, "groups", "message", "payload");
	}

	public AnnotationModel parse(AssertFalse.List target) {
		return getAnnotationValues(target, "value");
	}

	public AnnotationModel parse(AssertTrue target) {
		return getAnnotationValues(target, "groups", "message", "payload");
	}

	public AnnotationModel parse(AssertTrue.List target) {
		return getAnnotationValues(target, "value");
	}

	public AnnotationModel parse(DecimalMax target) {
		return getAnnotationValues(target, "groups", "message", "payload",
				"value");
	}

	public AnnotationModel parse(DecimalMax.List target) {
		return getAnnotationValues(target, "value");
	}

	public AnnotationModel parse(DecimalMin target) {
		return getAnnotationValues(target, "groups", "message", "payload",
				"value");
	}

	public AnnotationModel parse(DecimalMin.List target) {
		return getAnnotationValues(target, "value");
	}

	public AnnotationModel parse(Digits target) {
		return getAnnotationValues(target, "groups", "message", "payload",
				"fraction", "integer");
	}

	public AnnotationModel parse(Digits.List target) {
		return getAnnotationValues(target, "value");
	}

	public AnnotationModel parse(Future target) {
		return getAnnotationValues(target, "groups", "message", "payload");
	}

	public AnnotationModel parse(Future.List target) {
		return getAnnotationValues(target, "value");
	}

	public AnnotationModel parse(Max target) {
		return getAnnotationValues(target, "groups", "message", "payload",
				"value");
	}

	public AnnotationModel parse(Max.List target) {
		return getAnnotationValues(target, "value");
	}

	public AnnotationModel parse(Min target) {
		return getAnnotationValues(target, "groups", "message", "payload",
				"value");
	}

	public AnnotationModel parse(Min.List target) {
		return getAnnotationValues(target, "value");
	}

	public AnnotationModel parse(NotNull target) {
		return getAnnotationValues(target, "groups", "message", "payload");
	}

	public AnnotationModel parse(NotNull.List target) {
		return getAnnotationValues(target, "value");
	}

	public AnnotationModel parse(Null target) {
		return getAnnotationValues(target, "groups", "message", "payload");
	}

	public AnnotationModel parse(Null.List target) {
		return getAnnotationValues(target, "value");
	}

	public AnnotationModel parse(Past target) {
		return getAnnotationValues(target, "groups", "message", "payload");
	}

	public AnnotationModel parse(Past.List target) {
		return getAnnotationValues(target, "value");
	}

	public AnnotationModel parse(Pattern target) {
		return getAnnotationValues(target, "groups", "message", "payload",
				"regexp");
	}

	public AnnotationModel parse(Pattern.List target) {
		return getAnnotationValues(target, "value");
	}

	public AnnotationModel parse(Size target) {
		return getAnnotationValues(target, "groups", "message", "payload",
				"min", "max");
	}

	public AnnotationModel parse(Size.List target) {
		return getAnnotationValues(target, "value");
	}
}
