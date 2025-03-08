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
    implementation("org.springframework.boot:spring-boot-starter-web")

    // httpclient
    implementation("org.apache.httpcomponents.client5:httpclient5")
}