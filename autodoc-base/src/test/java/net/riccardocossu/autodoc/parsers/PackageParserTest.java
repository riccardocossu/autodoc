package net.riccardocossu.autodoc.parsers;

import static org.junit.Assert.assertEquals;
import net.riccardocossu.autodoc.base.PackageContainer;
import net.riccardocossu.autodoc.jpa.JPAPlugin;

import org.junit.Test;

public class PackageParserTest {

	@Test
	public void testPackage() {
		PackageParser parser = new PackageParser();
		PluginFactory factory = new PluginFactory();
		factory.registerPlugin(new JPAPlugin());
		PackageContainer pack = parser.parse(
				"net.riccardocossu.autodoc.test.model", factory);
		assertEquals(4, pack.getClasses().size());
	}

}
