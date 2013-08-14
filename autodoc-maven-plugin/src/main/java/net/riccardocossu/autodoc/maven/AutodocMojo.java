package net.riccardocossu.autodoc.maven;

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
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.riccardocossu.autodoc.base.PackageContainer;
import net.riccardocossu.autodoc.main.Engine;

import org.apache.commons.configuration.BaseConfiguration;
import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;

/**
 * Goal which touches a timestamp file.
 * 
 * @goal parse
 * 
 * @phase process-classes
 * @requiresDependencyResolution compile+runtime
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
	private String[] inputPlugins;
	/**
	 * List of output plugins to be used for report generation
	 * 
	 * @parameter
	 */
	private String[] outputPlugins;

	/**
	 * Maven project
	 * 
	 * @parameter expression="${project}"
	 */
	private MavenProject project;

	@SuppressWarnings("unchecked")
	public void execute() throws MojoExecutionException {
		try {
			Set<URL> urls = new HashSet<URL>();
			List<String> elements = project.getCompileClasspathElements();
			for (String element : elements) {
				urls.add(new File(element).toURI().toURL());
			}

			ClassLoader contextClassLoader = URLClassLoader.newInstance(urls
					.toArray(new URL[0]), Thread.currentThread()
					.getContextClassLoader());

			Thread.currentThread().setContextClassLoader(contextClassLoader);

		} catch (DependencyResolutionRequiredException e) {
			throw new MojoExecutionException("Error expanding classloader", e);
		} catch (MalformedURLException e) {
			throw new MojoExecutionException("Error expanding classloader", e);
		}
		File f = new File(outputDirectory.getAbsolutePath() + "/autodoc");

		if (!f.exists()) {
			f.mkdirs();
		}

		BaseConfiguration conf = new BaseConfiguration();
		conf.addProperty(Engine.CONFIG_PACKAGES, packages);
		conf.addProperty(Engine.CONFIG_INPUT_PLUGINS, inputPlugins);
		conf.addProperty(Engine.CONFIG_OUTPUT_PLUGINS, outputPlugins);
		conf.addProperty(Engine.CONFIG_BASE_OUTPUT_DIR, f.getAbsolutePath());
		Engine eng = new Engine(conf);
		List<PackageContainer> parsedPackages = eng.execute();
		getLog().info(
				String.format("Parsed %d packages", parsedPackages.size()));
	}
}
