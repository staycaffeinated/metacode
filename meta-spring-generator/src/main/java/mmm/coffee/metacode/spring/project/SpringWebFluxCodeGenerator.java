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

import mmm.coffee.metacode.common.descriptor.Descriptor;
import mmm.coffee.metacode.common.descriptor.RestProjectDescriptor;

/**
 * Code generator for SpringWebMvc project
 */
public class SpringWebFluxCodeGenerator implements ISpringWebMvcProjectGenerator {

    private RestProjectDescriptor descriptor;

    @Override
    public void setDescriptor(Descriptor descriptor) {
        if (!(descriptor instanceof RestProjectDescriptor)) {
            String msg = String.format("Internal error: attempted to create SpringWebFluxCodeGenerator with a %s", descriptor.getClass().getName());
            throw new RuntimeException(msg);
        }
        this.descriptor = (RestProjectDescriptor)  descriptor;
    }

    /**
     * Returns the exit code from the generator.
     * 0 = success
     * 1 = general error
     *
     * @return the exit code, with zero indicating success.
     */
    @Override
    public int generateCode() {
        return 0;
    }
}
