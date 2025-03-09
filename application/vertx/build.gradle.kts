plugins {
    id("project-conventions")
    id("java-conventions")
    id("spring-conventions")
    id("jib-conventions")
}

dependencies {
    // module
    implementation(project(":application:common"))

    // spring
    implementation("org.springframework.boot:spring-boot-starter")

    // vertx
    implementation("io.vertx:vertx-core:4.5.13")
    implementation("io.vertx:vertx-web:4.5.13")
    implementation("io.vertx:vertx-web-client:4.5.13")

    // monitoring
    implementation("io.vertx:vertx-micrometer-metrics:4.5.13")
    implementation("io.micrometer:micrometer-registry-prometheus:1.1.0")

    // exclude configuration
    configurations.all {
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-actuator")
    }
}