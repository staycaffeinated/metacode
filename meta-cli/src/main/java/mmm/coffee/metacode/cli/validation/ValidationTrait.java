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
package mmm.coffee.metacode.cli.validation;

/**
 * A stereotype for classes supporting this validation API.
 * Mostly, the various Validators extend this interface.
 */
public interface ValidationTrait {
    boolean isValid();
    boolean isInvalid();

    /**
     * If a validation check finds an invalid value, this method
     * returns the corresponding error message.  If there is no error,
     * this method returns an empty string.
     */
    String errorMessage();
}
