import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.10"
    id("maven-publish")
}

group = "net.adriantodt.notionapi"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.grack:nanojson:1.7")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.0")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.7.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.0")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

publishing {
    publications.create<MavenPublication>("release") {
        from(components["kotlin"])
    }
    repositories {
        maven {
            url = uri("https://maven.cafeteria.dev/releases")
            credentials {
                username = project.property("mcdUsername").toString()
                password = project.property("mcdPassword").toString()
            }
            authentication.create<BasicAuthentication>("basic")
        }
        mavenLocal()
    }
}
