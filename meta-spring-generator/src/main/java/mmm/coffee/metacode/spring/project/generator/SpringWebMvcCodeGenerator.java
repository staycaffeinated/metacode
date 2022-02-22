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
package mmm.coffee.metacode.spring.project.generator;

import com.google.common.base.Predicate;
import lombok.experimental.SuperBuilder;
import mmm.coffee.metacode.common.catalog.CatalogEntry;
import mmm.coffee.metacode.common.descriptor.RestProjectDescriptor;
import mmm.coffee.metacode.common.generator.ICodeGenerator;
import mmm.coffee.metacode.common.stereotype.Collector;
import mmm.coffee.metacode.common.stereotype.TemplateResolver;
import mmm.coffee.metacode.common.trait.ConvertTrait;
import mmm.coffee.metacode.common.trait.WriteOutputTrait;
import mmm.coffee.metacode.spring.project.context.RestProjectTemplateModel;

/**
 * Code generator for SpringWebMvc project
 */
@SuperBuilder
@SuppressWarnings("java:S1068") // S1068: this is a work-in-progress so unused stuff is ok
public class SpringWebMvcCodeGenerator implements ICodeGenerator<RestProjectDescriptor> {
    
    private final Collector collector;
    private final ConvertTrait<RestProjectDescriptor, RestProjectTemplateModel> descriptor2templateModel;
    private final ConvertTrait<RestProjectDescriptor,Predicate<CatalogEntry>> descriptor2Predicate;
    private final TemplateResolver templateRenderer;
    private final WriteOutputTrait outputHandler;

    /*
     * An instance of a RestProjectDescriptor is almost never available
     * when this object's builder methods are called. 
     */
    private RestProjectDescriptor descriptor;
    
    /**
     * Returns the exit code from the generator.
     * 0 = success
     * 1 = general error
     *
     * @return the exit code, with zero indicating success.
     */
    public int generateCode() {

        return 0;
    }
}
