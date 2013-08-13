/**
 * 
 */
package net.riccardocossu.autodoc.html;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.riccardocossu.autodoc.base.OutputPlugin;
import net.riccardocossu.autodoc.base.PackageContainer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;

/**
 * @author riccardo
 * 
 */
public class HtmlOutputPlugin implements OutputPlugin {
	private static final Logger log = LoggerFactory
			.getLogger(HtmlOutputPlugin.class);
	private Configuration cfg;
	private Template packageTemplate;

	public HtmlOutputPlugin() {
		super();
		cfg = new Configuration();
		cfg.setTemplateLoader(new ClassTemplateLoader(this.getClass(),
				"/html/templates"));
		cfg.setObjectWrapper(new DefaultObjectWrapper());
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
		cfg.setIncompatibleImprovements(new Version(2, 3, 20));
		cfg.setBooleanFormat("true,false");

		try {
			packageTemplate = cfg.getTemplate("package.html");
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
		for (PackageContainer pc : packages) {
			File report = new File(baseDirectory.getAbsolutePath() + "/"
					+ pc.getName().replaceAll("\\.", "_") + ".html");
			FileWriter out = null;
			try {
				out = new FileWriter(report);
				Map root = new HashMap();
				root.put("pkg", pc);
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
}
