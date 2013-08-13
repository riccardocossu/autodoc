package net.riccardocossu.autodoc.parsers;

import static org.junit.Assert.assertEquals;
import net.riccardocossu.autodoc.base.PackageContainer;

import org.junit.Test;

public class PackageParserTest {

	@Test
	public void testPackage() {
		PackageParser parser = new PackageParser();
		PluginFactory factory = new PluginFactory();
		PackageContainer pack = parser.parse(
				"net.riccardocossu.autodoc.test.model", factory);
		assertEquals(4, pack.getClasses().size());
	}

}
