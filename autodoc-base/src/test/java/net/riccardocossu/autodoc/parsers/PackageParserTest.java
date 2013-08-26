package net.riccardocossu.autodoc.parsers;

import static org.junit.Assert.assertEquals;
import net.riccardocossu.autodoc.base.PackageContainer;
import net.riccardocossu.autodoc.jpa.JPAPlugin;
import net.riccardocossu.autodoc.test.plugin.TestSimplestPlugin;

import org.junit.Test;

public class PackageParserTest {

	@Test
	public void testJPAPlugin() {
		PackageParser parser = new PackageParser();
		PluginFactory factory = new PluginFactory();
		factory.registerPlugin(new JPAPlugin());
		PackageContainer pack = parser.parse(
				"net.riccardocossu.autodoc.test.model", factory);
		assertEquals(6, pack.getClasses().size());
	}

	@Test
	public void testSimplestPlugin() {
		PackageParser parser = new PackageParser();
		PluginFactory factory = new PluginFactory();
		factory.registerPlugin(new TestSimplestPlugin());
		PackageContainer pack = parser.parse(
				"net.riccardocossu.autodoc.test.model", factory);
		assertEquals(1, pack.getClasses().size());
	}

	@Test
	public void testSimplestAndJPAPlugin() {
		PackageParser parser = new PackageParser();
		PluginFactory factory = new PluginFactory();
		factory.registerPlugin(new TestSimplestPlugin());
		factory.registerPlugin(new JPAPlugin());
		PackageContainer pack = parser.parse(
				"net.riccardocossu.autodoc.test.model", factory);
		assertEquals(7, pack.getClasses().size());
	}

}
