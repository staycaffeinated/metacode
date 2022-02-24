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
package mmm.coffee.metacode.common.writer;

import lombok.NonNull;
import mmm.coffee.metacode.common.exception.RuntimeApplicationError;
import mmm.coffee.metacode.common.trait.WriteOutputTrait;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * Handles writing String content to a destination.
 * Typically, the destination is a File but, for testing,
 * writing can be a no-op. 
 */
public class ContentToFileWriter implements WriteOutputTrait {

    /**
     * Writes {@code content} to the {@code destination}.
     * A File object of {@code destination} is created.
     *
     * If {@code content} is null, nothing happens here.
     * If {@code content} is an empty string, the destination file is created,
     * with the empty content written to it.
     *
     * @param destination the FQP to the output file
     * @param content the content written to the output file
     */
    @Override
    public void writeOutput(@NonNull String destination, String content) {
        if (Objects.isNull(content)) return;
        try {
            File fOutput = new File(destination);
            FileUtils.forceMkdir(fOutput.getParentFile());
            FileUtils.writeStringToFile(fOutput, content, StandardCharsets.UTF_8);
        }
        catch (IOException e) {
            throw new RuntimeApplicationError(e.getMessage(), e);
        }
    }
}