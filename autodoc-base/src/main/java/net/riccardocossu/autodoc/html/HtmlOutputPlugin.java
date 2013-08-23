/**
 * 
 */
package net.riccardocossu.autodoc.html;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import net.riccardocossu.autodoc.base.AnnotatedClass;
import net.riccardocossu.autodoc.base.OutputPlugin;
import net.riccardocossu.autodoc.base.PackageContainer;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;

/**
 * Html output plugin; it's based on Freemarker
 * 
 * @author riccardo
 * 
 */
public class HtmlOutputPlugin implements OutputPlugin {
	private static final String SHORT_NAME = "HTML";
	private static final Logger log = LoggerFactory
			.getLogger(HtmlOutputPlugin.class);
	private Configuration cfg;
	private Template packageTemplate;

	private String cssFile = "default.css";
	private String cssPath = "/html/style/";
	private String baseTemplatePath = "/html/templates";
	private String outputEncoding = "UTF-8";
	private String packageTemplateName = "package-template.html";
	private boolean initialized = false;

	public HtmlOutputPlugin() {
		super();
		cfg = new Configuration();
		cfg.setObjectWrapper(new DefaultObjectWrapper());
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
		cfg.setIncompatibleImprovements(new Version(2, 3, 20));
	}

	private void initialize() {
		cfg.setDefaultEncoding(outputEncoding);
		cfg.setTemplateLoader(new ClassTemplateLoader(this.getClass(),
				baseTemplatePath));
		try {
			packageTemplate = cfg.getTemplate(packageTemplateName);
		} catch (IOException e) {
			log.error("Error parsing base templates: " + packageTemplateName, e);
		}
		initialized = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.riccardocossu.autodoc.base.OutputPlugin#process(java.util.List,
	 * java.io.File)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void process(List<PackageContainer> packages,
			File baseOutputDirectory) {
		if (!initialized) {
			initialize();
		}
		File baseDirectory = new File(baseOutputDirectory.getAbsolutePath()
				+ "/html");
		if (!baseDirectory.exists()) {
			baseDirectory.mkdirs();
		}
		File css = new File(baseDirectory.getAbsolutePath() + "/" + cssFile);
		URL cssRes = this.getClass().getResource(cssPath + cssFile);
		try {
			FileUtils.copyURLToFile(cssRes, css);
		} catch (IOException e) {
			log.error("Error copying css file " + cssPath + cssFile, e);
		}
		for (PackageContainer pc : packages) {
			TreeSet<AnnotatedClass> buffer = new TreeSet<AnnotatedClass>(
					new Comparator<AnnotatedClass>() {

						@Override
						public int compare(AnnotatedClass o1, AnnotatedClass o2) {
							String simpleName1 = o1.getQualifiedName()
									.substring(
											o1.getQualifiedName().lastIndexOf(
													'.') + 1);
							String simpleName2 = o2.getQualifiedName()
									.substring(
											o2.getQualifiedName().lastIndexOf(
													'.') + 1);
							return simpleName1.compareTo(simpleName2);
						}
					});
			buffer.addAll(pc.getClasses());
			List<AnnotatedClass> orderedClasses = new ArrayList<AnnotatedClass>();
			for (Iterator<AnnotatedClass> it = buffer.iterator(); it.hasNext();) {
				orderedClasses.add(it.next());
			}
			File report = new File(baseDirectory.getAbsolutePath() + "/"
					+ pc.getName().replaceAll("\\.", "_") + ".html");
			FileWriter out = null;
			try {
				out = new FileWriter(report);
				Map root = new HashMap();
				root.put("pkg", pc);
				root.put("orderedClasses", orderedClasses);
				root.put("cssFile", cssFile);
				packageTemplate.process(root, out);
			} catch (Exception e) {
				log.error("Error parsing package " + pc.getName(), e);
			} finally {
				if (out != null) {
					try {
						out.close();
					} catch (Exception e) {
						log.error("Can't close file", e);
					}
				}
			}
		}

	}

	public String getCssFile() {
		return cssFile;
	}

	public void setCssFile(String cssFile) {
		this.cssFile = cssFile;
	}

	public String getCssPath() {
		return cssPath;
	}

	public void setCssPath(String cssPath) {
		this.cssPath = cssPath;
	}

	@Override
	public String getShortName() {
		return SHORT_NAME;
	}

	@Override
	public void configure(String configResource) {
		if (configResource != null) {
			try {
				org.apache.commons.configuration.Configuration conf = new PropertiesConfiguration(
						configResource);
				log.debug("Reading configuration file: {}", configResource);
				String prop = (String) conf.getProperty("outputEncoding");
				if (prop != null) {
					outputEncoding = prop;
					log.debug("config: outputEncoding = {}", outputEncoding);
				}
				prop = (String) conf.getProperty("baseTemplatePath");
				if (prop != null) {
					baseTemplatePath = prop;
					log.debug("config: baseTemplatePath = {}", baseTemplatePath);
				}
				prop = (String) conf.getProperty("packageTemplateName");
				if (prop != null) {
					packageTemplateName = prop;
					log.debug("config: packageTemplateName = {}",
							packageTemplateName);
				}
				prop = (String) conf.getProperty("cssFile");
				if (prop != null) {
					cssFile = prop;
					log.debug("config: cssFile = {}", cssFile);
				}
				initialize();
				log.debug("Configuration done");
			} catch (ConfigurationException e) {
				throw new IllegalArgumentException(
						"Error reading config file: " + configResource, e);
			}
		}

	}

}
