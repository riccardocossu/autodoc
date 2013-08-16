package net.riccardocossu.autodoc.base;

import java.util.ArrayList;
import java.util.List;

/**
 * Model for an annotated method
 * 
 * @author riccardo
 * 
 */
public class AnnotatedMethod {
	private String name;
	private List<AnnotationModel> annotations;

	public AnnotatedMethod() {
		annotations = new ArrayList<AnnotationModel>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<AnnotationModel> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(List<AnnotationModel> annotations) {
		this.annotations = annotations;
	}

}
