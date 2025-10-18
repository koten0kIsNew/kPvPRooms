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
    maven("https://maven.enginehub.org/repo/")
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    compileOnly("io.papermc.paper:paper-api:1.17.1-R0.1-SNAPSHOT")
    compileOnly("com.sk89q.worldedit:worldedit-bukkit:7.0.0-SNAPSHOT"){
        exclude("org.bukkit")
    }
    implementation(project(":api"))
    implementation("org.projectlombok:lombok:1.18.42")
    annotationProcessor("org.projectlombok:lombok:1.18.42")
}

tasks.test {
    useJUnitPlatform()
}