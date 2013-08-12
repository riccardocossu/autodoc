package net.riccardocossu.autodoc.test.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "prova")
public class AnnotatedTestEntity {

	@Id
	private Long annotatedField;

	@Column(name = "prova")
	private String getAnnotatedMethod() {
		return "";
	}
}
