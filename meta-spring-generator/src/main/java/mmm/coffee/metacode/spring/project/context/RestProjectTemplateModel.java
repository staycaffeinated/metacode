/*
 * Copyright 2020 Jon Caulfield
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package mmm.coffee.metacode.spring.project.context;

import lombok.Data;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import mmm.coffee.metacode.annotations.jacoco.Generated;
import mmm.coffee.metacode.common.dependency.DependencyCatalog;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;

/**
 * This is the 'Model' object used by Freemarker to resolve template variables.
 * Any variable that might be used in a project-related template must have a
 * corresponding field within this POJO.
 */
@Data
@SuperBuilder
@Generated // Ignore code coverage for this class
public class RestProjectTemplateModel {
    // Basic properties
    private String applicationName;
    private String basePath;
    private String basePackage;
    private String javaVersion;
    private String framework;
    private String groupId;

    // In Mojo2, integrations work by having
    // a field=true|false. eg:
    // features.postgres=true|false
    // features.testcontainers=true|false
    // features.liquibase=true|false
    // I don't think Freemarker understands booleans --
    // a field exists or doesn't exist, so we init all these
    // features to 'false' and set to 'true' as appropriate

    private boolean isWebFlux;
    private boolean isWebMvc;

    // Library versions
    // These are set by reading the dependencies.yml file;
    // we can't get these values from the ProjectDescriptor.
    // We'll need a method load Dependency info and init these fields,
    // say, TemplateModel.addVersions(DependencyYaml) -> TemplateHashModel
    private String springBootVersion;
    private String springCloudVersion;
    private String springDependencyManagementVersion;
    private String problemSpringWebVersion;
    private String assertJVersion;
    private String benManesPluginVersion;
    private String junitSystemRulesVersion;
    private String junitVersion;
    private String liquibaseVersion;
    private String lombokVersion;
    private String log4JVersion;
    private String testContainersVersion;

    public void apply(@NonNull DependencyCatalog dependencyCatalog) {
        dependencyCatalog.entries().forEach(it -> {
            setField(it.getName(), it.getVersion());
        });

        // entries.forEach( library -> templateKeys.put(library.getName()+"Version", library.getVersion()));
    }

    // visible for testing
    void setField(String prefix, String value) {
        try {
            String methodName = conjureSetterMethod(prefix);
            Method method = this.getClass().getDeclaredMethod(methodName, String.class);
            method.invoke(this, value);
        }
        catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            System.err.printf("ERROR: %s%n", e.getMessage());
            // log.error(e.getMessage(), e);
        }
    }
    // Visible for testing
    // Builds the string "set{FieldName}Version}",
    // with first letter of fieldName capitalized
    String conjureSetterMethod(String fieldName) {
        String firstLetter = fieldName.substring(0,1);
        String remainder = fieldName.substring(1, fieldName.length());
        firstLetter = firstLetter.toUpperCase(Locale.ROOT);
        return "set" + firstLetter + remainder + "Version";
    }
}
