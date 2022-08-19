/*
 * Copyright (c) 2022 Jon Caulfield.
 */
package mmm.coffee.metacode.spring.endpoint.generator;

import com.google.common.base.Predicate;
import lombok.experimental.SuperBuilder;
import mmm.coffee.metacode.common.ExitCodes;
import mmm.coffee.metacode.common.catalog.CatalogEntry;
import mmm.coffee.metacode.common.descriptor.Framework;
import mmm.coffee.metacode.common.descriptor.RestEndpointDescriptor;
import mmm.coffee.metacode.common.exception.CreateEndpointUnsupportedException;
import mmm.coffee.metacode.common.generator.ICodeGenerator;
import mmm.coffee.metacode.common.io.MetaProperties;
import mmm.coffee.metacode.common.io.MetaPropertiesHandler;
import mmm.coffee.metacode.common.stereotype.Collector;
import mmm.coffee.metacode.common.stereotype.MetaTemplateModel;
import mmm.coffee.metacode.common.stereotype.TemplateResolver;
import mmm.coffee.metacode.common.trait.ConvertTrait;
import mmm.coffee.metacode.common.trait.WriteOutputTrait;
import mmm.coffee.metacode.spring.endpoint.model.RestEndpointTemplateModel;
import mmm.coffee.metacode.spring.endpoint.mustache.MustacheEndpointDecoder;
import org.apache.commons.configuration2.Configuration;

/**
 * SpringEndpointGenerator
 */
@SuperBuilder
@SuppressWarnings({"java:S4738","java:S1602","java:S125"})
// S4738: accepting Google Predicate class for now
// S1602: false positive: comment around line 82 happens to look like a lambda function
// S125: we're OK with comments that look like code
public class SpringEndpointGenerator implements ICodeGenerator<RestEndpointDescriptor> {

    // At this time, we don't know which Collector to use until the doPreprocessing
    // method is called, because we have one collector for WebMvc templates, and a
    // different collector for WebFlux. This choice is worth revisiting.   
    private Collector collector;
    private final ConvertTrait<RestEndpointDescriptor, RestEndpointTemplateModel> descriptor2templateModel;
    private final ConvertTrait<RestEndpointDescriptor, Predicate<CatalogEntry>> descriptor2predicate;
    private final TemplateResolver<MetaTemplateModel> templateRenderer;
    private final WriteOutputTrait outputHandler;
    private final MustacheEndpointDecoder mustacheDecoder;
    final MetaPropertiesHandler<RestEndpointDescriptor> metaPropertiesHandler;


    /**
     * Fills in data missing from the {@code spec} using the {@code metacode.properties}
     * as the source of data for those missing pieces.
     *
     * For example, when creating an endpoint, the end-user does not type in the
     * base package since we can acquire the base package from the {@code metacode.properties}
     * file. The general motivation is to keep the CLI simple,
     *
     * @param spec contains the values entered on the command line -- the resourceName
     *             and resourcePath
     * @return an updated {@code spec} with the basePackage, basePath, and framework.
     */
    @Override
    public ICodeGenerator<RestEndpointDescriptor> doPreprocessing(RestEndpointDescriptor spec) {
        Configuration config = metaPropertiesHandler.readMetaProperties();
        spec.setBasePackage(config.getString(MetaProperties.BASE_PACKAGE));
        spec.setBasePath(config.getString(MetaProperties.BASE_PATH));
        spec.setFramework(config.getString(MetaProperties.FRAMEWORK));
        spec.setWithLiquibase(config.getBoolean(MetaProperties.ADD_LIQUIBASE, false));
        spec.setWithPostgres(config.getBoolean(MetaProperties.ADD_POSTGRESQL, false));
        spec.setWithTestContainers(config.getBoolean(MetaProperties.ADD_TESTCONTAINERS, false));

        return this;
    }

    /**
     * Performs the code generation. Returns:
     * 0 = success
     * 1 = general error
     *
     * @param descriptor the endpoint metadata that tells us the resource and route to create
     * @return the exit code, with zero indicating success.
     */
    @Override
    public int generateCode(RestEndpointDescriptor descriptor) {
        if (!frameworkIsSupported(descriptor.getFramework())) {
            throw new CreateEndpointUnsupportedException(String.format("The `create endpoint` command not supported by the %s project template", descriptor.getFramework()));
        }

        // Build the TemplateModel consumed by Freemarker to resolve template variables
        var templateModel = descriptor2templateModel.convert(descriptor);
        
        // Create a predicate to determine which template's to render
        Predicate<CatalogEntry> keepThese = descriptor2predicate.convert(descriptor);

        // Provide state to the MustacheDecoder so that mustache variables can be resolved.
        mustacheDecoder.configure(templateModel);

        // Render the templates
        collector.collect().stream().filter(keepThese).forEach( it -> {
            // essentially: it -> { writeIt ( renderIt(it) ) }
            outputHandler.writeOutput(
                    // CatalogEntry's use mustache expressions for destinations;
                    // we need to translate that expression that to its canonical-ish path
                    mustacheDecoder.decode(it.getDestination()),
                    templateRenderer.render(it.getTemplate(), templateModel));
        });

        return ExitCodes.OK;
    }

    /**
     * Verify the endpoint is being created within a project template that supports endpoints.
     */
    boolean frameworkIsSupported(final String framework) {
        // It only makes sense to generate endpoints for WebMvc and WebFlux projects.
        // For the SpringBoot template, too many expected classes would be missing for this to work.
        return (framework.equalsIgnoreCase(Framework.SPRING_WEBMVC.frameworkName()) ||
            framework.equalsIgnoreCase(Framework.SPRING_WEBFLUX.frameworkName()));
    }
}
