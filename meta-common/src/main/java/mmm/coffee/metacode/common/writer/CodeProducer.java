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
package mmm.coffee.metacode.common.writer;

import lombok.NonNull;

import java.io.Writer;

/**
 * Handles writing code to (usually) the file system.
 * Hypothetically, the producer could write to, say, a ZipFile
 * The general idea is to abstract the destination
 */
public interface CodeProducer {

    // CodeProduceFactory(IWriterFactory wFactory);
    // CodeProducer (IWriterFactory factory)

    // Writer w = codeProducer.createWriter(template.outputFile());
    // producer.resolve(template, context, writer);
    // Will flow go something like this?
    // CodeProducerFactory.builder().stateModel(model).build();
    // templates.forEach(producer::resolveTemplate);
    // the codeproducer creates either a NullWriter or FileWriter and writes the content
    // do we need WriterFactory.createWriter(template.getOutputPath());
    // where we might get back a NullWriter or FileWriter

    String resolveTemplate(@NonNull String templateContent /*, modelObject */);
    Writer getWriter();
}
