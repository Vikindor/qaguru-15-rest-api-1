plugins {
    id("java")
}

group = "io.github.vikindor"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:6.0.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("io.rest-assured:rest-assured:5.5.6")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testRuntimeOnly("org.slf4j:slf4j-simple:2.0.17")
}

tasks.test {
    useJUnitPlatform()

    jvmArgs("-Dfile.encoding=UTF-8","-Dorg.slf4j.simpleLogger.logFile=System.out")
    environment("SE_AVOID_STATS", "true")
}
