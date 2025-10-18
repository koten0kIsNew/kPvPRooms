plugins {
    id("java")
}

group = "ru.kotenokdev"
version = "2.0-BETA"

java.toolchain {
    languageVersion = JavaLanguageVersion.of(17)
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation(project(":shared"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.register<Copy>("copyJar"){
    from ("/build/libs/${project.name}-${project.version}.jar")
    into ("C:\\Users\\kotenok_dev\\Desktop\\test server\\plugins")
    dependsOn("compileCrutch")
}

tasks.register<Jar>("compileCrutch"){
    dependsOn(subprojects.map { it.tasks.named("jar") })
    from(subprojects.map { it.the<SourceSetContainer>()["main"].output })
    archiveBaseName.set("${project.name}-${project.version}")
    archiveVersion.set("")
    destinationDirectory.set(layout.buildDirectory.dir("libs"))
}


tasks.build {
    finalizedBy("compileCrutch", "copyJar")
}