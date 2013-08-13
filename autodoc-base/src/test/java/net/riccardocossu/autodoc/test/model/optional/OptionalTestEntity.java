/**
 * 
 */
package net.riccardocossu.autodoc.test.model.optional;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author riccardo
 * 
 */
@Entity
public class OptionalTestEntity {
	@Id
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
