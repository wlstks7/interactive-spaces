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

package interactivespaces.workbench.project.activity.type.android;

import com.google.common.collect.Lists;

import interactivespaces.workbench.project.Project;
import interactivespaces.workbench.project.ProjectTemplate;
import interactivespaces.workbench.project.activity.builder.ProjectBuilder;
import interactivespaces.workbench.project.activity.builder.java.JavaActivityProjectBuilder;
import interactivespaces.workbench.project.activity.ide.EclipseIdeProjectCreatorSpecification;
import interactivespaces.workbench.project.activity.ide.JavaEclipseIdeProjectCreatorSpecification;
import interactivespaces.workbench.project.activity.type.ProjectType;

/**
 * An Android activity project type.
 *
 * @author Keith M. Hughes
 */
public class AndroidActivityProjectType implements ProjectType {

  /**
   * Name for the type.
   */
  public static final String NAME = "android";

  /**
   * The extension for android projects.
   */
  private AndroidJavaprojectExtension extension = new AndroidJavaprojectExtension();

  @Override
  public boolean isProperType(Project project) {
    return "activity".equals(project.getType()) && NAME.equals(project.getBuilderType());
  }

  @Override
  public ProjectBuilder newBuilder() {
    return new JavaActivityProjectBuilder(extension);
  }

  @Override
  public ProjectTemplate newProjectTemplate() {
    return new GenericAndroidActivityProjectTemplate();
  }

  @Override
  public EclipseIdeProjectCreatorSpecification getEclipseIdeProjectCreatorSpecification() {
    return new JavaEclipseIdeProjectCreatorSpecification(Lists.newArrayList("src/main/java",
        "src/main/resources"), extension);
  }
}
