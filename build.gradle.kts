import org.jetbrains.kotlin.gradle.dsl.JvmTarget

val kotlin_version: String by project
val logback_version: String by project

plugins {
    kotlin("jvm") version "2.0.20"
    id("io.ktor.plugin") version "3.0.0-rc-1"
//    id("io.ktor.plugin") version "2.3.12"
}

group = "org.dronda"
version = "0.0.1"

application {
    mainClass.set("org.dronda.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment", "-Xmx500M")
}

kotlin {
    compilerOptions.jvmTarget = JvmTarget.JVM_11
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-client-core")
    implementation("io.ktor:ktor-client-okhttp")

    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-default-headers-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    testImplementation("io.ktor:ktor-server-test-host-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}
