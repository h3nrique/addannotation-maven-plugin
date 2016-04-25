package br.com.fabricads;

import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.JavaClassSource;

import java.io.File;
import java.util.Collection;
import java.util.Map;

/**
 * Remove Annotation.
 *
 * @author <a href="https://github.com/h3nrique" title="Paulo Henrique Alves">Paulo Henrique Alves</a>
 */
@Mojo( name = "after")
public class AfterCompile extends AbstractMojo {

	/**
	 * Project dir.
	 */
	@Parameter( property = "project.basedir", required = true)
	private File projectBasedir;

	/**
	 * Debug enabled?
	 */
	@Parameter( property = "debug")
	private Boolean debug;

	/**
	 * Class map.
	 */
	@Parameter( property = "clazz", required = true)
	private Map<String, String> clazz;

	public void execute() throws MojoExecutionException {
		Collection<File> listFiles = FileUtils.listFiles(projectBasedir, new String[]{"java"}, true);
		for (File file : listFiles) {
			String className = file.getName().replace(".java", "");
			if(clazz.containsKey(className)) {
				try {
					getLog().debug("Reading class :: ".concat(className));
					JavaClassSource javaClass = Roaster.parse(JavaClassSource.class, file);
					getLog().debug("Removing import...");
					javaClass.removeImport("com.example.annotations.AnnotationExample");
					getLog().debug("Removing annotation...");
					javaClass.removeAnnotation(javaClass.getAnnotation("AnnotationExample"));
					getLog().debug("Writing file...");
					FileUtils.writeStringToFile(file, javaClass.toString(), "UTF-8"); // Write class using UTF-8.
				} catch (Exception err) {
					getLog().debug(err);
					throw new MojoExecutionException("Error while change class.", err);
				}
			}
		}
	}
}