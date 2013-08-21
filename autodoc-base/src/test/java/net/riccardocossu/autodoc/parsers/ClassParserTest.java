package net.riccardocossu.autodoc.parsers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import net.riccardocossu.autodoc.base.AnnotatedClass;
import net.riccardocossu.autodoc.jpa.JPAPlugin;
import net.riccardocossu.autodoc.test.model.AnnotatedTestEntity;
import net.riccardocossu.autodoc.test.model.optional.EntityWithEmbeddable;

import org.junit.Test;

public class ClassParserTest {

	@Test
	public void test() {
		PluginFactory factory = new PluginFactory();
		factory.registerPlugin(new JPAPlugin());
		ClassParser parser = new ClassParser();
		AnnotatedClass res = parser.parse(AnnotatedTestEntity.class, factory);
		assertNotNull(res);
		assertEquals(1, res.getAnnotations().size());
		assertEquals(1, res.getFields().size());
		assertEquals(1, res.getMethods().size());
	}

	@Test
	public void testEmbedded() {
		PluginFactory factory = new PluginFactory();
		factory.registerPlugin(new JPAPlugin());
		ClassParser parser = new ClassParser();
		AnnotatedClass res = parser.parse(EntityWithEmbeddable.class, factory);
		assertNotNull(res);
		assertEquals(2, res.getAnnotations().size());
		assertEquals(1, res.getFields().size());
		assertEquals(0, res.getMethods().size());
	}

}
