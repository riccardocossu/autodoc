package net.riccardocossu.autodoc.base;

import java.util.List;

/**
 * @author riccardo
 *
 */
public class AnnotatedMethod {
	private String name;
	
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

	private List<AnnotationModel> annotations;

}
