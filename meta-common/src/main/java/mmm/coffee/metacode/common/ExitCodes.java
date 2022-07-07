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
package mmm.coffee.metacode.common;

import mmm.coffee.metacode.annotations.jacoco.Generated;

/**
 * Exit codes
 */
@Generated // jacoco should ignore this class in code coverage
public class ExitCodes {

    public static final int OK = 0;
    public static final int INVALID_INPUT = 2;

    public static final int ENDPOINTS_ARE_NOT_SUPPORTED_BY_THE_FRAMEWORK = 3;

    /* Hidden constructor */
    private ExitCodes() {}
}
