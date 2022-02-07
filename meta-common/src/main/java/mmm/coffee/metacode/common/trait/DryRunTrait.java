/*
 * Copyright 2022 Jon Caulfield
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package mmm.coffee.metacode.common.trait;

import mmm.coffee.metacode.annotations.jacoco.Generated;

/**
 * Stereotype for a specification. This is the mechanic
 * for identifying a class that's passed from the CLI package
 * to the GENERATOR package.
 */
@Generated //
public interface DryRunTrait {

    default boolean isDryRun() { return false; }
}
