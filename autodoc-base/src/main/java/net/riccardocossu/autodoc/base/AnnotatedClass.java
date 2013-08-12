/**
 * 
 */
package net.riccardocossu.autodoc.base;

import java.util.ArrayList;
import java.util.List;

/**
 * @author riccardo
 * 
 */
public class AnnotatedClass {

	private String qualifiedName;

	private List<AnnotationModel> annotations;
	private List<AnnotatedField> fields;
	private List<AnnotatedMethod> methods;

	public AnnotatedClass() {
		annotations = new ArrayList<AnnotationModel>();
		fields = new ArrayList<AnnotatedField>();
		methods = new ArrayList<AnnotatedMethod>();
	}

	public List<AnnotatedField> getFields() {
		return fields;
	}

	public void setFields(List<AnnotatedField> fields) {
		this.fields = fields;
	}

	public List<AnnotatedMethod> getMethods() {
		return methods;
	}

	public void setMethods(List<AnnotatedMethod> methods) {
		this.methods = methods;
	}

	public List<AnnotationModel> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(List<AnnotationModel> annotations) {
		this.annotations = annotations;
	}

	public String getQualifiedName() {
		return qualifiedName;
	}

	public void setQualifiedName(String qualifiedName) {
		this.qualifiedName = qualifiedName;
	}

}
