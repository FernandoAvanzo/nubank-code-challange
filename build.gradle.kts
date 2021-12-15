import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kotlinLanguageVersion: String by project
val kotestVersion: String by project
val kotlinTest: String by project
val kotlinxSerialization: String by project

application {
    mainClass.set("Applicationkt")
}

plugins {
    application
    kotlin("jvm") version "1.6.0"
    kotlin("plugin.serialization") version "1.6.10"
}

repositories {
    mavenCentral()
    google()
}

dependencies {
    //Aplications dependencies
    implementation(kotlin("stdlib",kotlinLanguageVersion))
    implementation(kotlin("stdlib-common",kotlinLanguageVersion))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinxSerialization")

    //test dependencies
    testImplementation(kotlin("test", kotlinTest))
    testImplementation(kotlin("test-common", kotlinTest))
    testImplementation(kotlin("test-annotations-common", kotlinTest))
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "16"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "16"
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "MainKt"
    }
    configurations["compileClasspath"].forEach { file: File ->
        from(zipTree(file.absoluteFile))
    }
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}