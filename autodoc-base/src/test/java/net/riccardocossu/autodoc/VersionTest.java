package net.riccardocossu.autodoc;

import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class VersionTest {

	@Test
	public void testVersion() {
		String v = Version.VERSION;
		System.out.println("Version: " + v);
		assertNotEquals("N/A", v);
	}

}
