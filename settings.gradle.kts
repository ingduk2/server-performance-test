pluginManagement {
    includeBuild("build-logic")
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "server-performance-test"

include(
    "application:common",
    "application:external",
    "application:spring-mvc-webclient",
    "application:spring-webflux",
)