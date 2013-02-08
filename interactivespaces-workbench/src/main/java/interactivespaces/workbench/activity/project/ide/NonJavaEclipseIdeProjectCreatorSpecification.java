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

import java.io.IOException;
import java.util.Map;

import com.google.common.collect.Lists;

/**
 * Specification for non-java projects.
 * 
 * @author Keith M. Hughes
 */
public class NonJavaEclipseIdeProjectCreatorSpecification implements
		EclipseIdeProjectCreatorSpecification {

	private static final String ECLIPSE_BUILDER_NON_JAVA = "org.eclipse.wst.common.project.facet.core.builder";
	private static final String ECLIPSE_NATURE_NON_JAVA = "org.eclipse.wst.common.project.facet.core.nature";

	@Override
	public void addSpecificationData(ActivityProject project,
			Map<String, Object> freemarkerContext) {
		freemarkerContext.put(ECLIPSE_PROJECT_FIELD_NATURES,
				Lists.newArrayList(ECLIPSE_NATURE_NON_JAVA));
		freemarkerContext.put(ECLIPSE_PROJECT_FIELD_BUILDER,
				ECLIPSE_BUILDER_NON_JAVA);
	}

	@Override
	public void writeAdditionalFiles(ActivityProject project,
			Map<String, Object> freemarkerContext,
			FreemarkerTemplater templater, InteractiveSpacesWorkbench workbench)
			throws IOException, TemplateException {
		// Nothing to do
	}
}
