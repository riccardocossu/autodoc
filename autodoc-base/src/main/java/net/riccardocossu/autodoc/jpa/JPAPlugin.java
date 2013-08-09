/**
 * 
 */
package net.riccardocossu.autodoc.jpa;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;

import net.riccardocossu.autodoc.base.AnnotationsPlugin;

/**
 * @author riccardo
 *
 */
public class JPAPlugin implements AnnotationsPlugin {
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static final List<? extends Class> MANAGED = Arrays.asList(Entity.class,MappedSuperclass.class,
			Embeddable.class); 

	/* (non-Javadoc)
	 * @see net.riccardocossu.autodoc.base.AnnotationsPlugin#getManagedAnnotations()
	 */
	@Override
	public Collection<? extends Class> getManagedAnnotations() {
		// TODO Auto-generated method stub
		return MANAGED;
	}

}
