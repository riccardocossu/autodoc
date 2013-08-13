package net.riccardocossu.autodoc.autodoc_maven_plugin;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import net.riccardocossu.autodoc.base.PackageContainer;
import net.riccardocossu.autodoc.main.Engine;

import org.apache.commons.configuration.BaseConfiguration;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

/**
 * Goal which touches a timestamp file.
 * 
 * @goal parse
 * 
 * @phase process-classes
 */
public class AutodocMojo extends AbstractMojo {
	/**
	 * Location of the file.
	 * 
	 * @parameter expression="${project.build.directory}"
	 * @required
	 */
	private File outputDirectory;

	/**
	 * List of packages to parse for annotated classes
	 * 
	 * @parameter
	 */
	private String[] packages;

	/**
	 * List of plugins to be used for annotation parsing
	 * 
	 * @parameter
	 */
	private String[] plugins;

	public void execute() throws MojoExecutionException {
		File f = new File(outputDirectory.getAbsolutePath() + "/autodoc");

		if (!f.exists()) {
			f.mkdirs();
		}

		BaseConfiguration conf = new BaseConfiguration();
		conf.addProperty("net.riccardocossu.autodoc.packages", packages);
		conf.addProperty("net.riccardocossu.autodoc.plugins", plugins);
		Engine eng = new Engine(conf);
		List<PackageContainer> parsedPackages = eng.execute();
		File touch = new File(f, "log.txt");

		FileWriter w = null;
		try {
			w = new FileWriter(touch);
			for (PackageContainer p : parsedPackages) {
				w.write(p.getName() + "\n");
			}

		} catch (IOException e) {
			throw new MojoExecutionException("Error creating file " + touch, e);
		} finally {
			if (w != null) {
				try {
					w.close();
				} catch (IOException e) {
					// ignore
				}
			}
		}
	}
}
