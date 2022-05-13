/*
 * Copyright (c) 2022 Jon Caulfield.
 */
package mmm.coffee.metacode.cli.create.project;

import mmm.coffee.metacode.common.descriptor.Framework;
import mmm.coffee.metacode.common.descriptor.RestProjectDescriptor;
import mmm.coffee.metacode.spring.constant.SpringIntegrations;
import picocli.CommandLine;

/**
 * AbstractCreateSpringProject holds command line options that are common
 * to spring-webflux and spring-webmvc.
 */
public class AbstractCreateSpringProject extends AbstractCreateRestProject {
    //
    // The Support option. These are the added capabilities supported by the generated application.
    //
    // I've been back and forth with what to name this flag.  Spring Initialzr has the notion of
    // 'dependencies', but all Spring Initializr does is add those dependencies to the gradle/maven build file.
    // Micronaut uses the term 'feature', but added libraries aren't exactly 'features' of the
    // generated application. The term 'trait' seems in line: for example, a 'postgres trait' indicates
    // the traits of a Postgres database are enabled in the application. Along that line, 'support'
    // also seems like a good term: the generated application will 'support' postgres or liquibase or CORS
    // or heath checks or whatever
    // First, we have to provide an Iterable<String> that provides the list of valid candidates
    //
    // The declaration the additional library options
    // For example, ```--add postgres liquibase```
    // What about -i --integrations? so, for example, `-i postgres testcontainers`
    @CommandLine.Option(names={"-a", "--add"},
            arity="0..*",
            paramLabel = "LIBRARY",
            description = { "Add the support of additional libraries to the project. Currently supported libraries are: ${COMPLETION-CANDIDATES}." }
    )
    protected SpringIntegrations[] features;

    /**
     * Creates a project descriptor from the command-line arguments
     * @return a POJO that encapsulates the command-line arguments
     */
    protected RestProjectDescriptor buildProjectDescriptor(Framework framework) {
        // Get the basic information
        var descriptor = RestProjectDescriptor
                .builder()
                .applicationName(applicationName)
                .basePackage(packageName)
                .basePath(basePath)
                .groupId(groupId)
                .framework(framework)
                .build();

        // take note of any features/integrations
        if (features != null) {
            for (SpringIntegrations f : features) {
                descriptor.getIntegrations().add(f.name());
            }
        }
        return descriptor;
    }
}
