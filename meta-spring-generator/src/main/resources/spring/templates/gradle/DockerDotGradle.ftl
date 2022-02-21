// --------------------------------------------------------------------------------
// The Jib plugin for Docker
// --------------------------------------------------------------------------------
jib {
    from {
        image = 'openjdk:17-jdk-alpine'
    }
    container {
        format = 'OCI'
    }
}
// If you want successful builds to automatically create a docker image, uncomment the next line
// tasks.build.dependsOn tasks.jibDockerBuild