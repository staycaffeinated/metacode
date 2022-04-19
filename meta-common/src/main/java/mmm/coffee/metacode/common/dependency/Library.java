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
package mmm.coffee.metacode.common.dependency;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mmm.coffee.metacode.annotations.jacoco.Generated;

import java.util.List;

/**
 * A Dependency, as represented in the dependencies.yml
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Generated // exclude this from code coverage; lombok does all the work
public class Library {
    private List<Dependency> dependencies;
}
