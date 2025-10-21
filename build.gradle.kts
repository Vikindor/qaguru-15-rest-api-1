plugins {
    id("java")
}

group = "io.github.vikindor"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("com.codeborne:selenide:7.10.1")
    testImplementation(platform("org.junit:junit-bom:6.0.0-M2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("io.rest-assured:rest-assured:5.5.2")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testRuntimeOnly("org.slf4j:slf4j-simple:2.0.17")
}

tasks.test {
    useJUnitPlatform()

    jvmArgs("-Dfile.encoding=UTF-8","-Dorg.slf4j.simpleLogger.logFile=System.out")
    environment("SE_AVOID_STATS", "true")
}
