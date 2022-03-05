/*
 * Copyright (c) 2022 Jon Caulfield.
 */
package mmm.coffee.metacode.spring.endpoint.generator;

import com.google.common.base.Predicate;
import lombok.experimental.SuperBuilder;
import mmm.coffee.metacode.common.ExitCodes;
import mmm.coffee.metacode.common.catalog.CatalogEntry;
import mmm.coffee.metacode.common.dependency.DependencyCatalog;
import mmm.coffee.metacode.common.descriptor.Framework;
import mmm.coffee.metacode.common.descriptor.RestEndpointDescriptor;
import mmm.coffee.metacode.common.generator.ICodeGenerator;
import mmm.coffee.metacode.common.io.MetaProperties;
import mmm.coffee.metacode.common.io.MetaPropertiesHandler;
import mmm.coffee.metacode.common.stereotype.Collector;
import mmm.coffee.metacode.common.stereotype.MetaTemplateModel;
import mmm.coffee.metacode.common.stereotype.TemplateResolver;
import mmm.coffee.metacode.common.trait.ConvertTrait;
import mmm.coffee.metacode.common.trait.WriteOutputTrait;
import mmm.coffee.metacode.spring.endpoint.context.RestEndpointTemplateModel;
import mmm.coffee.metacode.spring.endpoint.function.MustacheEndpointDecoder;
import mmm.coffee.metacode.spring.project.function.MustacheDecoder;
import org.apache.commons.configuration2.Configuration;

/**
 * SpringEndpointGenerator
 */
@SuperBuilder
public class SpringEndpointGenerator implements ICodeGenerator<RestEndpointDescriptor> {

    private final Collector collector;
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
        
        return this;
    }

    /**
     * Performs the code generation. Returns:
     * 0 = success
     * 1 = general error
     *
     * @param descriptor
     * @return the exit code, with zero indicating success.
     */
    @Override
    public int generateCode(RestEndpointDescriptor descriptor) {
        // Build the TemplateModel consumed by Freemarker to resolve template variables
        var templateModel = descriptor2templateModel.convert(descriptor);

        // Create a predicate to determine which template's to render
        Predicate<CatalogEntry> keepThese = descriptor2predicate.convert(descriptor);

        // Provide state to the MustacheDecoder so that mustache variables can be resolved.
        mustacheDecoder.configure(templateModel);

        // Render the templates
        collector.collect().stream().filter(keepThese).forEach( it -> {
            // essentially: it -> { writeIt ( renderIt(it) ) }
            outputHandler.writeOutput (
                    // CatalogEntry's use mustache expressions for destinations;
                    // we need to translate that expression that to its actual path
                    mustacheDecoder.decode(it.getDestination()),
                    templateRenderer.render (it.getTemplate(), templateModel));
        });

        return ExitCodes.OK;
    }
}
