plugins {
    `java-library`
    java
    kotlin("jvm") version "2.0.10"
//    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "me.redth"
version = "0.1"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("org.github.paperspigot:paperspigot-api:1.8.8-R0.1-SNAPSHOT")
    compileOnly(files("libs/paper-patched.jar"))
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.build {
//    dependsOn("shadowJar")
}

kotlin {
    jvmToolchain(8)
}