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
import mmm.coffee.metacode.common.catalog.TemplateCatalog;
import mmm.coffee.metacode.common.descriptor.Descriptor;

import javax.annotation.Nullable;

/**
 * CodeGeneratorImpl
 */
public class CodeGeneratorImpl implements ICodeGenerator {
    private TemplateCatalog catalog;
    private Descriptor descriptor;

    @Override
    public void setDescriptor(@NonNull Descriptor descriptor) {
        this.descriptor = descriptor;
    }

    public void setCatalog(@NonNull TemplateCatalog catalog) {
        this.catalog = catalog;
    }

    // private CodeProducerFactory producerFactory;
    // Writer w = producer.buildWriter(template.getOutputPath())
    // String s = producer.resolve(template,scope);
    // writer.write(s);

    @Inject
    public CodeGeneratorImpl(TemplateCatalog catalog) {
        this.catalog = catalog;
    }

    public int generateCode() { return 0; }
}
