/**
 * 
 */
package net.riccardocossu.autodoc.parsers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.riccardocossu.autodoc.base.AnnotatedClass;
import net.riccardocossu.autodoc.base.PackageContainer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.reflect.ClassPath;

/**
 * This class is responsible for listing classes and passing them to the class
 * parser.
 * 
 * @author riccardo
 * 
 */
public class PackageParser {
	private static final Logger log = LoggerFactory
			.getLogger(PackageParser.class);

	private ClassParser classParser = new ClassParser();

	public PackageContainer parse(String packagetoParse, PluginFactory factory) {
		log.debug("Parsing package {}", packagetoParse);
		PackageContainer res = new PackageContainer();
		res.setName(packagetoParse);
		List<AnnotatedClass> classes = res.getClasses();
		Set<Class<?>> packClasses = new HashSet<Class<?>>();
		try {
			ClassPath classpath = ClassPath.from(Thread.currentThread()
					.getContextClassLoader()); // scans the class path used by
												// classloader
			for (ClassPath.ClassInfo classInfo : classpath
					.getTopLevelClassesRecursive(packagetoParse)) {
				packClasses.add(classInfo.load());
			}
			for (Class<?> c : packClasses) {
				log.debug("Parsing class {}", c.getName());
				AnnotatedClass ac = classParser.parse(c, factory);
				if (ac != null) {
					classes.add(ac);
				}
			}
		} catch (Exception e) {
			log.error("Error parsing package " + packagetoParse, e);
		}
		return res;
	}

}
