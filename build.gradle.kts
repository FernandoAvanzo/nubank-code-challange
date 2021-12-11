import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

application {
    mainClass.set("code.challenge.Applicationkt")
}

plugins {
    application
    kotlin("jvm") version "1.6.0"
}


repositories {
    mavenCentral()
}

dependencies { implementation(kotlin("stdlib-jdk8")) }

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}