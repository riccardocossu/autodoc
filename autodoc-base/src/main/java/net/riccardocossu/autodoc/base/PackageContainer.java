/**
 * 
 */
package net.riccardocossu.autodoc.base;

import java.util.ArrayList;
import java.util.List;

/**
 * model for an entire package and its subpackages
 * 
 * @author riccardo
 * 
 */
public class PackageContainer {
	private String name;

	private List<AnnotatedClass> classes = new ArrayList<AnnotatedClass>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<AnnotatedClass> getClasses() {
		return classes;
	}

	public void setClasses(List<AnnotatedClass> classes) {
		this.classes = classes;
	}

}
