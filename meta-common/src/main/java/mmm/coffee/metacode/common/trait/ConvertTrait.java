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
 * Functional interface for a type converter.
 * We'll use this to convert, say, a ProjectDescriptor into a SpringProjectContext,
 * or an EndpointDescriptor into a SpringEndpointContext.
 */
public interface ConvertTrait<F,T> {
    /**
     * Converts an instance of class {@code FROM} into an instance of class {@code TO}.
     * We let the implementer decide how to handle {@code nulls}
     * @param fromType some instance to convert
     * @return the transformed object
     */
    T convert(F fromType);
}

