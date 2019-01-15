import org.gradle.jvm.tasks.Jar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm") version "1.3.11"
    idea
    id("fabric-loom") version "0.1.0-SNAPSHOT"
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

base {
    archivesBaseName = "ingame-json-factory"
}

repositories {
    mavenCentral()
    maven(url = "http://server.bbkr.space:8081/artifactory/libs-release")
    maven(url = "http://server.bbkr.space:8081/artifactory/libs-snapshot")
}

version = "0.1.0"

minecraft {
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

configurations {
    create("shadow")
    this["compile"].extendsFrom(this["shadow"])
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    minecraft("com.mojang:minecraft:19w02a")
    mappings("net.fabricmc:yarn:19w02a.13")
    modCompile("net.fabricmc:fabric-loader:0.3.2.92")
    modCompile("net.fabricmc:fabric:0.1.4.71")
    modCompile("net.fabricmc:fabric-language-kotlin:1.3.10-29")
    modCompile("io.github.cottonmc:client-commands:0.1.0+19w02a-SNAPSHOT")

    // Other libraries
//    add("shadow", "io.github.cottonmc:json-factory:0.3.0")
    add("shadow", files("../json-factory/build/libs/json-factory-0.3.1-SNAPSHOT.jar"))
}

tasks.withType<Jar> {
    from(configurations["shadow"].asFileTree.files.map { zipTree(it) })
}
