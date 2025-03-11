import io.ktor.plugin.features.*

val ktorVersion: String by project
val kotlinVersion: String by project
val logbackVersion: String by project

plugins {
    kotlin("jvm") version "2.1.10"
    id("io.ktor.plugin") version "3.0.3"
}

group = "dev.syoritohatsuki"
version = "2025.3.1"

application {
    mainClass.set("dev.syoritohatsuki.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

ktor {
    val projectName = project.name
    docker {
        jreVersion.set(JavaVersion.VERSION_21)
        localImageName.set(projectName)
        imageTag.set(version.toString())
        portMappings.set(
            listOf(
                DockerPortMapping(
                    1541,
                    1541,
                    DockerPortMappingProtocol.TCP
                )
            )
        )
        externalRegistry.set(
            DockerImageRegistry.externalRegistry(
                username = provider { System.getProperty("DOCKER_USERNAME") ?: error("DOCKER_USERNAME is undefined") },
                password = provider { System.getProperty("DOCKER_PASSWORD") ?: error("DOCKER_PASSWORD is undefined") },
                project = provider { projectName },
                hostname = provider { System.getProperty("DOCKER_HOSTNAME") ?: error("DOCKER_HOSTNAME is undefined") },
                namespace = provider { "personal" })
        )
    }
}

tasks {
    val loadEnv by registering {
        doLast {
            val envFile = file(".env")
            if (envFile.exists()) {
                envFile.readLines()
                    .filter { it.isNotBlank() && !it.startsWith("#") }
                    .forEach { line ->
                        val (key, value) = line.split("=", limit = 2)
                        System.setProperty(key.trim(), value.trim())
                    }
            } else {
                logger.warn(".env file not found!")
            }
        }
    }

    processResources {
        dependsOn("loadEnv")
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-host-common-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-cio-jvm:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktorVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")
}
