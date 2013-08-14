/**
 * 
 */
package net.riccardocossu.autodoc.parsers;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import net.riccardocossu.autodoc.base.AnnotatedClass;
import net.riccardocossu.autodoc.base.PackageContainer;

import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author riccardo
 * 
 */
public class PackageParser {
	private static final Logger log = LoggerFactory
			.getLogger(PackageParser.class);

	private ClassParser classParser = new ClassParser();

	public PackageContainer parse(String packagetoParse, PluginFactory factory) {
		PackageContainer res = new PackageContainer();
		res.setName(packagetoParse);
		List<AnnotatedClass> classes = res.getClasses();
		List<ClassLoader> classLoadersList = new LinkedList<ClassLoader>();
		classLoadersList.add(ClasspathHelper.contextClassLoader());
		classLoadersList.add(ClasspathHelper.staticClassLoader());

		Reflections ref = new Reflections(
				new ConfigurationBuilder()
						.setScanners(new SubTypesScanner(false),
								new ResourcesScanner())
						.setUrls(
								ClasspathHelper.forClassLoader(classLoadersList
										.toArray(new ClassLoader[0])))
						.filterInputsBy(
								new FilterBuilder().include(FilterBuilder
										.prefix(packagetoParse))));
		Set<Class<?>> packClasses = ref.getSubTypesOf(Object.class);
		for (Class<?> c : packClasses) {
			log.debug("Parsing class {}", c.getName());
			AnnotatedClass ac = classParser.parse(c, factory);
			classes.add(ac);
		}
		return res;
	}

}
