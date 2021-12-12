import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kotlinLanguageVersion: String by project
val kotestVersion: String by project
val mockkVersion: String by project

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
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect", kotlinLanguageVersion))
    //todo
    //testImplementation("io.kotest:kotest-runner-junit5-jvm:$kotestVersion")
    //testImplementation("io.kotest:kotest-assertions-core-jvm:$kotestVersion")
    //testImplementation("io.mockk:mockk:$mockkVersion")
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}