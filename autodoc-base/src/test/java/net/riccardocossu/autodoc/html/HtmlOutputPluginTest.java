package net.riccardocossu.autodoc.html;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.riccardocossu.autodoc.base.PackageContainer;
import net.riccardocossu.autodoc.jpa.JPAPlugin;
import net.riccardocossu.autodoc.parsers.PackageParser;
import net.riccardocossu.autodoc.parsers.PluginFactory;

import org.junit.Test;

public class HtmlOutputPluginTest {

	@Test
	public void testOutput() {
		PackageParser parser = new PackageParser();
		PluginFactory factory = new PluginFactory();
		factory.registerPlugin(new JPAPlugin());
		PackageContainer pack = parser.parse(
				"net.riccardocossu.autodoc.test.model", factory);
		List<PackageContainer> packages = new ArrayList<PackageContainer>();
		packages.add(pack);
		HtmlOutputPlugin outPlugin = new HtmlOutputPlugin();
		outPlugin.process(packages, new File("/tmp"));

	}

}
