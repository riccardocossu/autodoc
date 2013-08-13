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
public class AnnotationModel {
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

}
