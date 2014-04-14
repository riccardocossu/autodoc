# Why Autodoc?

I love documentation, I love Javadoc; often taking 5 minutes to write some code documentation can literally save someone else's hours! And there's more: using a tool like Maven it is trivial to produce HTML docs for everyone to use and distribute them as zipped artefacts and/or publish them on a web site.

Since when annotations were introduced in Java 5 a lot of configuration ended up there: table names, column attributes, validation constraints, you name it! 

But Javadoc **doesn't contain** this useful information, which lies scattered around maybe hundred of different files; also Javadocs are not checked for completeness or corrected during their generation, so they can easily diverge over time from the code, when this changes.

What if you could just generate a **Javadoc-like report for annotations** just as easily as you do now with regular Javadocs?

Well, today is your lucky day, **Autodoc** does that for you!

How much time does it take from you to write the data it needs? **No time** over what you spend to write annotations in the first place! It's just a matter of a few lines of configuration on your pom, it will take just a few minutes.

Ok, but this seems too good to be true, there must be a catch... maybe you will spend days in keeping its data in sync with the code? No, because **autodoc data is the code itself**!


## Use cases ##
You can use Autodoc tools to easily document the information you have in annotated java classes to synthetic documents; through the use of plugins, any compile time annotation can be parsed (future extensions will focus on javax.validation and possibly source level annotation).
Output plugins run one after the other, so multiple output can be produced from the same set of annotated classes.
Autodoc is free software distributed under the Apache Licence 2.0.

## Maven autodoc plugin ##

At present, the easyest way to start using autotools is including the **autodoc-maven-plugin** in your pom:


```
#!xml

<plugins>
	<plugin>
		<groupId>net.riccardocossu.autodoc</groupId>
		<artifactId>autodoc-maven-plugin</artifactId>
		<version>1.0.0</version>
		<configuration>
			<outputDirectory>${project.build.directory}</outputDirectory>
			<packages>
				<!-- list of packages to parse -->
				<param>fully.qualified.package.name</param>
			</packages>
			<inputPlugins>
				<!-- fully classified name of needed plugin; JPA plugin is for JPA2 annotations -->
				<param>net.riccardocossu.autodoc.jpa.JPAPlugin</param>
			</inputPlugins>
			<outputPlugins>
				<!-- output plugin are supposed to write to files; for example, this is the html output plugin -->
				<param>net.riccardocossu.autodoc.html.HtmlOutputPlugin</param>
			</outputPlugins>
		</configuration>
		<executions>
			<execution>
				<phase>process-classes</phase>
				<goals>
					<goal>parse</goal>
				</goals>
			</execution>
		</executions>
	</plugin>
	....
</plugins>
``` 

With this configuration JPA2 annotations in the package will produce a report in target/autodoc/html; currently the packages are always are always parsed recursively.

Starting from version 0.3.0 you can use a short name for plugins (**JPA2** for jpa plugin and **HTML** for html plugin) and use a configuration file with output plugins, by adding a config file name after the plugin identifier, separing with a ",".

You can also easily write [your own plugin](Writing a plugin "Writing a plugin").
```
#!xml
<inputPlugins>
				<!-- short name of needed plugin; JPA plugin is for JPA2 annotations -->
				<param>JPA2</param>
			</inputPlugins>
			<outputPlugins>
				<!-- output plugin are supposed to write to files; for example, this is the html output plugin -->
				<param>HTML,customConfig.properties</param>
			</outputPlugins>

```
For details on how to configure Html plugin see [here](https://bitbucket.org/riccardocossu/autodoc/wiki/Html%20Plugin).
A quick list of the new features in version 0.3.0 is on [my blog](http://codealone.blogspot.com/2013/08/new-version-for-autodoc-big-news.html).

## Other tools ##
Support for different build tools is planned, and will be delivered based on users demand.
This is a list of dependency for the tool, in the case one wants to run it manually (see net.riccardocossu.autodoc.main.EngineTest in the tests for an example):

* jcl-over-slf4j-1.7.5
* slf4j-api-1.7.5
* hibernate-jpa-2.0-api-1.0.1.Final
* guava-14.0.1
* commons-configuration-1.9
* commons-lang-2.6
* freemarker-2.3.20


Javadoc is copyright of Oracle Corporation