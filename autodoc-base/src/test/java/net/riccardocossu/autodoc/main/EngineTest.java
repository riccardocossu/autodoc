package net.riccardocossu.autodoc.main;

import org.apache.commons.configuration.DefaultConfigurationBuilder;
import org.junit.Test;

public class EngineTest {

	@Test
	public void testEngine() throws Exception {
		DefaultConfigurationBuilder factory = new DefaultConfigurationBuilder(
				"config.xml");
		Engine eng = new Engine(factory.getConfiguration());
		eng.execute();
	}

}
