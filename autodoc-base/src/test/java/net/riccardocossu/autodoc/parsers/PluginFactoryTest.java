package net.riccardocossu.autodoc.parsers;

import static org.junit.Assert.assertNotNull;
import net.riccardocossu.autodoc.base.OutputPlugin;

import org.junit.Test;

public class PluginFactoryTest {

	@Test
	public void testJPAShortName() {
		// tests that the JPA plugin is configurable through its short name
		PluginFactory factory = new PluginFactory();
		factory.registerInputPlugin("JPA2");
	}

	@Test
	public void testHTMLShortName() {
		// tests that the JPA plugin is configurable through its short name
		PluginFactory factory = new PluginFactory();
		OutputPlugin op = factory.initOutputPlugin("HTML", null);
		assertNotNull(op);
	}
}
