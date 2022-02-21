// --------------------------------------------------------------------------------
// Lint configuration
// --------------------------------------------------------------------------------
tasks.withType(JavaCompile).all {
    options.compilerArgs << "-Xlint:unchecked" << "-Xlint:deprecation" << "-Xlint:rawtypes"
}

// --------------------------------------------------------------------------------
// Lint options (defaulting to flag everything; turn off what you don't want)
// --------------------------------------------------------------------------------
<#noparse>
compileJava.options*.compilerArgs = [
    "-Xlint:serial",    "-Xlint:varargs",     "-Xlint:cast",        "-Xlint:classfile",
    "-Xlint:dep-ann",   "-Xlint:divzero",     "-Xlint:empty",       "-Xlint:finally",
    "-Xlint:overrides", "-Xlint:path",        "-Xlint:-processing", "-Xlint:static",
    "-Xlint:try",       "-Xlint:fallthrough", "-Xlint:rawtypes",    "-Xlint:deprecation",
    "-Xlint:unchecked", "-Xlint:-options"
]

compileTestJava.options*.compilerArgs = [
    "-Xlint:serial",     "-Xlint:varargs",      "-Xlint:cast",        "-Xlint:classfile",
    "-Xlint:dep-ann",    "-Xlint:divzero",      "-Xlint:empty",       "-Xlint:finally",
    "-Xlint:overrides",  "-Xlint:path",         "-Xlint:-processing", "-Xlint:static",
    "-Xlint:try",        "-Xlint:-fallthrough", "-Xlint:-rawtypes",   "-Xlint:-deprecation",
    "-Xlint:-unchecked", "-Xlint:-options"
]
</#noparse>
