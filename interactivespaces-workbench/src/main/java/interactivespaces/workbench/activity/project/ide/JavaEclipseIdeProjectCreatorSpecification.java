/*
 * Copyright (C) 2012 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package interactivespaces.workbench.activity.project.ide;

import freemarker.template.TemplateException;
import interactivespaces.workbench.FreemarkerTemplater;
import interactivespaces.workbench.InteractiveSpacesWorkbench;
import interactivespaces.workbench.activity.project.ActivityProject;
import interactivespaces.workbench.activity.project.builder.java.JavaActivityExtensions;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

/**
 * Specification for Java projects.
 * 
 * @author Keith M. Hughes
 */
public class JavaEclipseIdeProjectCreatorSpecification implements
		EclipseIdeProjectCreatorSpecification {

	/**
	 * The location of the eclipse classpath template file.
	 */
	private static final String TEMPLATE_FILEPATH_ECLIPSE_CLASSPATH = "ide/eclipse/java-classpath.ftl";

	/**
	 * The name of the Eclipse classpath file.
	 */
	private static final String FILENAME_CLASSPATH_FILE = ".classpath";

	private static final String ECLIPSE_BUILDER_JAVA = "org.eclipse.jdt.core.javabuilder";
	private static final String ECLIPSE_NATURE_JAVA = "org.eclipse.jdt.core.javanature";

	/**
	 * The Java activity extensions.
	 * 
	 * <p>
	 * Can be {@code null}.
	 */
	private JavaActivityExtensions extensions;

	/**
	 * Construct a specification with {@code null} extensions.
	 */
	public JavaEclipseIdeProjectCreatorSpecification() {
		this(null);
	}

	/**
	 * Construct a specification with extensions.
	 * 
	 * @param extensions
	 *            the extensions to use (can be {@code null})
	 */
	public JavaEclipseIdeProjectCreatorSpecification(
			JavaActivityExtensions extensions) {
		this.extensions = extensions;
	}

	@Override
	public void addSpecificationData(ActivityProject project,
			Map<String, Object> freemarkerContext) {
		freemarkerContext.put(ECLIPSE_PROJECT_FIELD_NATURES,
				Lists.newArrayList(ECLIPSE_NATURE_JAVA));
		freemarkerContext.put(ECLIPSE_PROJECT_FIELD_BUILDER,
				ECLIPSE_BUILDER_JAVA);
	}

	@Override
	public void writeAdditionalFiles(ActivityProject project,
			Map<String, Object> freemarkerContext,
			FreemarkerTemplater templater, InteractiveSpacesWorkbench workbench)
			throws IOException, TemplateException {
		List<File> projectLibs = Lists.newArrayList(workbench.getControllerClasspath());
		if (extensions != null) {
			extensions.addToClasspath(projectLibs, workbench);
		}
		
		freemarkerContext.put("libs", projectLibs);

		templater.writeTemplate(freemarkerContext,
				new File(project.getBaseDirectory(), FILENAME_CLASSPATH_FILE),
				TEMPLATE_FILEPATH_ECLIPSE_CLASSPATH);
	}
}
