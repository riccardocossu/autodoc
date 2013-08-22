package net.riccardocossu.autodoc.parsers;

import org.junit.Test;

public class PluginFactoryTest {

	@Test
	public void testJPAShortName() {
		// tests that the JPA plugin is configurable through its short name
		PluginFactory factory = new PluginFactory();
		factory.registerPlugin("JPA2");
	}

}
