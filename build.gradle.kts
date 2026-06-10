plugins {
    id("java")
    id("io.qameta.allure") version "2.9.6"
}

group = "autotests"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("com.consol.citrus:citrus-base:3.4.0")
    testImplementation("com.consol.citrus:citrus-testng:3.4.0")
    testImplementation("com.consol.citrus:citrus-spring:3.4.0")
    testImplementation("com.consol.citrus:citrus-http:3.4.0")
    testImplementation("com.consol.citrus:citrus-validation-json:3.4.0")
    testImplementation("org.slf4j:slf4j-reload4j:2.0.7")
    testImplementation("org.projectlombok:lombok:1.18.28")
    testImplementation("com.consol.citrus:citrus-sql:3.4.0")
    testImplementation("com.h2database:h2:1.4.196")
    testImplementation("io.qameta.allure:allure-testng:2.9.6")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testCompileOnly("org.projectlombok:lombok:1.18.32")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.32")
}

tasks.test {
    useTestNG()
}

