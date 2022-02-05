// --------------------------------------------------------------------------------
// This contains the configuration of the Eclipse Spotless code formatter.
// See https://github.com/diffplug/spotless/tree/main/plugin-gradle
// --------------------------------------------------------------------------------

spotless {
    // Optional: limit format enforcement to just the files changed by this feature branch
    // Of course, the code base has to be under Git control for this flag to work.
    // ratchetFrom 'origin/main'

    // Format rules for miscellaneous files
    format 'misc', {
        // define the files to apply `misc` to
        target '*.gradle', '*.md', '.gitignore'

        // define the steps to apply to those files
        trimTrailingWhitespace()
        indentWithSpaces(4)
        endWithNewline()
    }
    // Format rules for Java source
    java {
        // don't need to set target, it is inferred from java

        // apply the desired code formatter
        // googleJavaFormat().aosp()
        eclipse()
    }
}

// With this dependsOn, spotlessApply is auto-applied when the 'build' task runs
build.dependsOn 'spotlessApply'
