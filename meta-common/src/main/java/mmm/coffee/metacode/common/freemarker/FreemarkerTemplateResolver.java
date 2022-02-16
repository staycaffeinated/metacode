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
package mmm.coffee.metacode.common.freemarker;

import com.google.inject.Inject;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import lombok.NonNull;
import mmm.coffee.metacode.common.trait.TemplateRendererTrait;

import java.io.IOException;
import java.io.StringWriter;

/**
 * Handles rendering templates
 */
public class FreemarkerTemplateResolver implements TemplateRendererTrait<Object> {

    private final Configuration configuration;

    /**
     * Constructor
     * @param configuration the freemarker configuration
     */
    @Inject
    public FreemarkerTemplateResolver(@NonNull Configuration configuration) {
        this.configuration = configuration;
    }

    /**
     * Render the template
     * @param templateClassPath the path to the template file (a freemarker FTL file, for example)
     * @param dataModel the data model used to resolve template variables
     * @return the rendered content of the template, as a String
     */
    @Override
    public String render(String templateClassPath, Object dataModel) {
        try {
            // materialize the freemarker Template instance
            var template = configuration.getTemplate(templateClassPath, "UTF-8");

            // Parse and render the template
            var writer = new StringWriter();
            template.process(dataModel, writer);
            return writer.toString();
        }
        catch (TemplateException | IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static Configuration createDefaultConfiguration() {
        // return ConfigurationFactory.defaultConfiguration();
        throw new UnsupportedOperationException("Need to implement this method");
    }
}
