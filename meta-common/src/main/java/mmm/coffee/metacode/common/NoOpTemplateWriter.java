/*
 * Copyright 2022 Jon Caulfield
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package mmm.coffee.metacode.common;

import lombok.NonNull;

import java.io.File;

/**
 * A TemplateWriter that does not write any output. This is useful for testing
 * when a Writer is needed but you do not want content (e.g., generated code)
 * written to the file system.
 */
public class NoOpTemplateWriter implements TemplateWriter {

    @Override
    public void writeStringToFile(String content, @NonNull File file) {
        // Empty
    }
}