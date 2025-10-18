plugins {
    id("java")
}

group = "ru.kotenokdev"
version = "1.0-SNAPSHOT"


java.toolchain {
    languageVersion = JavaLanguageVersion.of(17)
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    compileOnly("io.papermc.paper:paper-api:1.17.1-R0.1-SNAPSHOT")
}

tasks.test {
    useJUnitPlatform()
}