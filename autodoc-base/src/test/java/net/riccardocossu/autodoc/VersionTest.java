package net.riccardocossu.autodoc;

import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class VersionTest {

	@Test
	public void testVersion() {
		assertNotEquals("N/A", Version.VERSION);
	}

}
