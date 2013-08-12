package net.riccardocossu.autodoc.jpa;

import static org.junit.Assert.assertNotNull;

import java.lang.annotation.Annotation;

import net.riccardocossu.autodoc.base.AnnotationModel;
import net.riccardocossu.autodoc.test.model.SimpleTestEntity;

import org.junit.Test;

public class JPAPluginTest {

	@Test
	public void test() {
		JPAPlugin pl = new JPAPlugin();
		Annotation[] annotations = SimpleTestEntity.class.getAnnotations();
		for (Annotation a : annotations) {
			AnnotationModel res = pl.parse(a);
			assertNotNull(res);
		}
	}

}
