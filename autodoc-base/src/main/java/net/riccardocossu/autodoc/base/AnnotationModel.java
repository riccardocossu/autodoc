/**
 * 
 */
package net.riccardocossu.autodoc.base;

import java.util.ArrayList;
import java.util.List;

/**
 * Model for an annotation; if it contains an array, its elements are in the
 * children property
 * 
 * @author riccardo
 * 
 */
public class AnnotationModel {
	private String qualifier;
	private String qualifiedName;
	private List<AttributeModel> attributes = new ArrayList<AttributeModel>();

	private List<AnnotationModel> children;

	public String getQualifiedName() {
		return qualifiedName;
	}

	public void setQualifiedName(String qualifiedName) {
		this.qualifiedName = qualifiedName;
	}

	public List<AnnotationModel> getChildren() {
		return children;
	}

	public void setChildren(List<AnnotationModel> children) {
		this.children = children;
	}

	public List<AttributeModel> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<AttributeModel> attributes) {
		this.attributes = attributes;
	}

	public String getQualifier() {
		return qualifier;
	}

	public void setQualifier(String qualifier) {
		this.qualifier = qualifier;
	}

}
