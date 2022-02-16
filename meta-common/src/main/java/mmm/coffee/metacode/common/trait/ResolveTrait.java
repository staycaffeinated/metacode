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
package mmm.coffee.metacode.common.trait;

/**
 * Functional interface for rendering content
 */
public interface ResolveTrait<A,B,R> {
    /**
     * Transforms an input of type {@code A} into an output of type {code R},
     * using {@code B} for context information
     * @param theSource the template
     * @param theContext
     * @return the transformed content, as an instance of {@code R}.
     */
    R resolve (A theSource, B theContext);
}