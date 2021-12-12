import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kotlinLanguageVersion: String by project
val kotestVersion: String by project
val kotlinTest: String by project

application {
    mainClass.set("code.challenge.Applicationkt")
}

plugins {
    application
    kotlin("jvm") version "1.6.0"
}


repositories {
    mavenCentral()
    google()
}

dependencies {
    //Aplications dependencies
    implementation(kotlin("stdlib",kotlinLanguageVersion))
    implementation(kotlin("stdlib-common",kotlinLanguageVersion))
    implementation(kotlin("reflect", kotlinLanguageVersion))

    //test dependencies
    testImplementation(kotlin("test", kotlinTest))
    testImplementation(kotlin("test-common", kotlinTest))
    testImplementation(kotlin("test-annotations-common", kotlinTest))
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "11"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "11"
}