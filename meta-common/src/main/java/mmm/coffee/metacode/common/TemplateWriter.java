/*
 * Copyright 2022 Jon Caulfield
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package mmm.coffee.metacode.common;

import java.io.File;

/**
 * Defines the TemplateWriter trait
 */
public interface TemplateWriter {

    void writeStringToFile(String content, File destination);
}
