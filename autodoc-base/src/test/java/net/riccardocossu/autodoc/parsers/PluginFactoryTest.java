package net.riccardocossu.autodoc.parsers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import net.riccardocossu.autodoc.base.OutputPlugin;
import net.riccardocossu.autodoc.html.HtmlOutputPlugin;

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

	@Test(expected = IllegalArgumentException.class)
	public void testHTMLNoConfig() {
		// tests that the JPA plugin is configurable through its short name
		PluginFactory factory = new PluginFactory();
		OutputPlugin op = factory.initOutputPlugin("HTML",
				"nonExistingConfig.xml");
		assertNotNull(op);
	}

	@Test
	public void testHTMLWithConfig() {
		// tests that the HTML plugin is configurable through its short name,
		// using the provided configuration
		PluginFactory factory = new PluginFactory();
		OutputPlugin op = factory.initOutputPlugin("HTML",
				"htmlOutputPlugin.properties");
		assertNotNull(op);
		assertTrue(op instanceof HtmlOutputPlugin);
		HtmlOutputPlugin html = (HtmlOutputPlugin) op;
		assertEquals("/html/templates", html.getBaseTemplatePath());
		assertEquals("/customCssFile.css", html.getCssFile());
		assertEquals("UTF-16", html.getOutputEncoding());
		assertEquals("customPackageTemplate.html",
				html.getPackageTemplateName());
		assertEquals("/custom/Css/Path", html.getCssPath());
	}
}
