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
package mmm.coffee.metacode.common.generator;

import com.google.inject.Inject;
import lombok.NonNull;
import mmm.coffee.metacode.annotations.guice.CatalogProvider;
import mmm.coffee.metacode.common.catalog.ICatalogReader;
import mmm.coffee.metacode.common.descriptor.Descriptor;

/**
 * CodeGeneratorImpl
 */
public class CodeGeneratorImpl implements ICodeGenerator {
    private ICatalogReader catalog;
    private Descriptor descriptor;

    @Override
    public void setDescriptor(@NonNull Descriptor descriptor) {
        this.descriptor = descriptor;
    }

    public void setCatalog(@NonNull ICatalogReader catalog) {
        this.catalog = catalog;
    }

    // private CodeProducerFactory producerFactory;
    // Writer w = producer.buildWriter(template.getOutputPath())
    // String s = producer.resolve(template,scope);
    // writer.write(s);

    @Inject
    public CodeGeneratorImpl(@CatalogProvider ICatalogReader catalog) {
        this.catalog = catalog;
    }

    public int generateCode() {
        /*
         TemplateCollector::collect() --
           -- collectTemplates will have some hard-wired logic to apply
           -- predicates/filtering of the global population of templates to
           -- build a final list of only the templates to render.
           -- from the command line args, we can determine which templates are needed.
         var templateCollector = new TemplateCollector(String[] catalogFiles, Descriptor);
         TemplateCollector::addCatalog(String catalogFile);

         CodeGenerator generator = new SpringCodeGenerator(Descriptor<RestProject>,
                                                        Collector<CatalogEntry>,
                                                        Converter<?,?>,
                                                        Resolver<DataModel>,
                                                        TemplateWriter<?,?>)
         generator.generate();
         writeMetaCodeProperties();

         int generate() {
           dataModel = converter.convert(descriptor);
           templateCollector.collect().stream().forEach( it -> {
             String resolvedText = templateResolver.render(it.template(), dataModel);
             templateWriter.writeOutput(fqp(it.destination(), dataModel), resolvedText);
             }
           }
        */

        return 0;
    }
}
