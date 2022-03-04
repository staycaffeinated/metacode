/*
 * Copyright 2022 Jon Caulfield
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

import mmm.coffee.metacode.annotations.jacoco.Generated;

/**
 * CodeGenerator
 */
@SuppressWarnings("java:S2326") // S2326: fixing this is on the roadmap
@Generated // exclude from code coverage reports
public interface ICodeGenerator<T> {

    ICodeGenerator<T> doPreprocessing(T spec);

    /**
     * Performs the code generation. Returns:
     *      0 = success
     *      1 = general error
     * @return the exit code, with zero indicating success. 
     */
    int generateCode(T spec);
}
