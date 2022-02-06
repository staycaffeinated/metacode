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
package mmm.coffee.metacode.spring.project;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import mmm.coffee.metacode.annotations.SpringWebFlux;
import mmm.coffee.metacode.annotations.SpringWebMvc;
import mmm.coffee.metacode.common.generator.ICodeGenerator;

/**
 * Module for SpringWebMvc Project generator
 */
public final class SpringCodeGeneratorModule extends AbstractModule {
    
    @Provides
    @SpringWebMvc
    ICodeGenerator provideSpringWebMvcGenerator() {
        return new SpringWebMvcCodeGenerator();
    }

    @Provides
    @SpringWebFlux
    ICodeGenerator providesSpringWebFluxGenerator() {
        return new SpringWebFluxCodeGenerator();
    }
}
