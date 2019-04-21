import org.gradle.jvm.tasks.Jar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm") version "1.3.30"
    idea
    id("fabric-loom") version "0.2.1-SNAPSHOT"
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

base {
    archivesBaseName = "ingame-data-factory"
}

repositories {
    mavenCentral()
    maven(url = "http://server.bbkr.space:8081/artifactory/libs-release")
    maven(url = "http://server.bbkr.space:8081/artifactory/libs-snapshot")
}

val shortVersion = "0.1.0"
version = "$shortVersion+1.14-pre5"

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
    minecraft("com.mojang:minecraft:1.14 Pre-Release 5")
    mappings("net.fabricmc:yarn:1.14 Pre-Release 5+build.4")
    modCompile("net.fabricmc:fabric-loader:0.4.2+build.131")
    modCompile("net.fabricmc:fabric:0.2.7+build.126")
    modCompile("net.fabricmc:fabric-language-kotlin:1.3.30-SNAPSHOT")
    compileOnly("net.fabricmc:fabric-language-kotlin:1.3.30-SNAPSHOT")
    modCompile("io.github.cottonmc:client-commands:0.2.1+1.14-pre5-SNAPSHOT")
    include("io.github.cottonmc:client-commands:0.2.1+1.14-pre5-SNAPSHOT")
    add("shadow", "io.github.cottonmc:json-factory:0.4.1") {
        exclude(group = "com.google.code.gson")
        exclude(group = "org.jetbrains.kotlin")
    }
}

tasks.withType<Jar> {
    from(configurations["shadow"].asFileTree.files.map { zipTree(it) })
}

tasks.getByName<ProcessResources>("processResources") {
    filesMatching("fabric.mod.json") {
        expand(
            mutableMapOf(
                "version" to shortVersion
            )
        )
    }
}
