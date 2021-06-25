import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.10"
}

group = "net.adriantodt"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.grack:nanojson:1.7")
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}
