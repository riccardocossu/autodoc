package net.riccardocossu.autodoc.jpa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.lang.annotation.Annotation;
import java.util.List;

import javax.persistence.NamedQueries;
import javax.persistence.QueryHint;

import net.riccardocossu.autodoc.base.AnnotationModel;
import net.riccardocossu.autodoc.base.AttributeModel;
import net.riccardocossu.autodoc.test.model.NestedQueryTestEntity;
import net.riccardocossu.autodoc.test.model.SimpleTestEntity;

import org.junit.Test;

public class JPAPluginTest {

	@Test
	public void testSimple() {
		JPAPlugin pl = new JPAPlugin();
		Annotation[] annotations = SimpleTestEntity.class.getAnnotations();
		for (Annotation a : annotations) {
			AnnotationModel res = pl.parse(a);
			assertNotNull(res);
		}
	}

	@Test
	public void testNested() {
		JPAPlugin pl = new JPAPlugin();
		Annotation[] annotations = NestedQueryTestEntity.class.getAnnotations();
		for (Annotation a : annotations) {
			if (a instanceof NamedQueries) {
				AnnotationModel res = pl.parse(a);
				assertNotNull(res);
				assertEquals(1, res.getAttributes().size());
				assertNull(res.getChildren());
				AttributeModel nq = res.getAttributes().get(0);
				assertEquals("value", nq.getName());
				assertTrue(nq.getValue() instanceof AnnotationModel);
				AnnotationModel am = (AnnotationModel) nq.getValue();
				assertEquals(0, am.getAttributes().size());
				List<AnnotationModel> children = am.getChildren();
				assertEquals(1, children.size());
				AnnotationModel firstNq = children.get(0);
				assertEquals(4, firstNq.getAttributes().size());
				AnnotationModel amHA = (AnnotationModel) firstNq
						.getAttributes().get(2).getValue();
				assertEquals(QueryHint.class.getName() + "[]",
						amHA.getQualifiedName());
				List<AnnotationModel> amHACh = amHA.getChildren();
				assertEquals(2, amHACh.get(0).getAttributes().size());
			}

		}
	}
}
