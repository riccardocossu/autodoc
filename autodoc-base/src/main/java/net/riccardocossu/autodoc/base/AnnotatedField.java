/**
 * 
 */
package net.riccardocossu.autodoc.base;

import java.util.ArrayList;
import java.util.List;

/**
 * Model for an annotated field
 * 
 * @author riccardo
 * 
 */
public class AnnotatedField {
	private String name;
	private String type;
	private List<AnnotationModel> annotations;

	public AnnotatedField() {
		annotations = new ArrayList<AnnotationModel>();
	}

	public List<AnnotationModel> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(List<AnnotationModel> annotations) {
		this.annotations = annotations;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
