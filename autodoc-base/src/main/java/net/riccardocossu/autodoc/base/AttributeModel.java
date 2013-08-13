/**
 * 
 */
package net.riccardocossu.autodoc.base;

/**
 * @author riccardo
 * 
 */
public class AttributeModel {
	private String name;
	private Object value;
	private boolean valueIsAnnotationModel = false;

	public AttributeModel(String name, Object value) {
		super();
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public boolean isValueIsAnnotationModel() {
		return valueIsAnnotationModel;
	}

	public void setValueIsAnnotationModel(boolean valueIsAnnotationModel) {
		this.valueIsAnnotationModel = valueIsAnnotationModel;
	}

}
