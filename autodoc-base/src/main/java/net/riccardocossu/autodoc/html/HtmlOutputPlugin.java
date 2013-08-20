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
	private static final Logger log = LoggerFactory
			.getLogger(HtmlOutputPlugin.class);
	private Configuration cfg;
	private Template packageTemplate;

	private String cssFile = "default.css";
	private String cssPath = "/html/style/";

	public HtmlOutputPlugin() {
		super();
		cfg = new Configuration();
		cfg.setTemplateLoader(new ClassTemplateLoader(this.getClass(),
				"/html/templates"));
		cfg.setObjectWrapper(new DefaultObjectWrapper());
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
		cfg.setIncompatibleImprovements(new Version(2, 3, 20));

		try {
			packageTemplate = cfg.getTemplate("package-template.html");
		} catch (IOException e) {
			log.error("Error parsing base templates", e);
		}
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

}
