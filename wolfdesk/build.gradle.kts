plugins {
    kotlin("jvm") version "1.9.24"
    kotlin("plugin.spring") version "1.9.24"
    id("org.springframework.boot") version "3.3.2"
    id("io.spring.dependency-management") version "1.1.6"
    kotlin("plugin.jpa") version "1.9.24"
}

group = "wolfdesk"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

val kotestVersion = "5.9.1"
val kotestSpringVersion = "1.3.0"
val jdslVersion = "3.5.2"
val mockkVersion = "1.13.10"
extra["springModulithVersion"] = "1.2.4"

subprojects {
    apply {
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
        plugin("org.jetbrains.kotlin.jvm")
        plugin("org.jetbrains.kotlin.plugin.spring")
        plugin("org.jetbrains.kotlin.plugin.jpa")
    }

    tasks.jar {
        enabled = true
    }

    tasks.bootJar {
        enabled = false
    }

    repositories {
        mavenCentral()
        maven { url = uri("https://repo.spring.io/milestone") }
    }

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-actuator")
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
        implementation("org.springframework.boot:spring-boot-starter-validation")
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("org.springframework.boot:spring-boot-starter-data-redis")
        implementation("org.springframework.modulith:spring-modulith-starter-core")
        implementation("org.springframework.modulith:spring-modulith-starter-jdbc")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("com.linecorp.kotlin-jdsl:jpql-dsl:${jdslVersion}")
        implementation("com.linecorp.kotlin-jdsl:jpql-render:${jdslVersion}")
        implementation("com.linecorp.kotlin-jdsl:spring-data-jpa-support:${jdslVersion}")

        annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
        testImplementation("org.springframework.boot:spring-boot-starter-test") {
            exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
            exclude(group = "org.mockito")
        }
        testImplementation("io.mockk:mockk:${mockkVersion}")
        testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
        testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
        testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
        testImplementation("io.kotest.extensions:kotest-extensions-spring:$kotestSpringVersion")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")
        testImplementation("org.springframework.modulith:spring-modulith-starter-test")
    }

    dependencyManagement {
        imports {
            mavenBom("org.springframework.ai:spring-ai-bom:1.0.0-M3")
            mavenBom("org.springframework.modulith:spring-modulith-bom:${property("springModulithVersion")}")
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

tasks.bootJar {
    archiveFileName = "application.jar"
    enabled = true
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
}

dependencies {
    implementation("org.springframework.modulith:spring-modulith-starter-core")
    implementation("org.springframework.modulith:spring-modulith-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    developmentOnly("org.springframework.boot:spring-boot-docker-compose")
    runtimeOnly("org.postgresql:postgresql")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    testImplementation("io.mockk:mockk:${mockkVersion}")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
        exclude(group = "org.mockito")
    }
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest.extensions:kotest-extensions-spring:$kotestSpringVersion")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testRuntimeOnly("com.h2database:h2")
    testImplementation("io.rest-assured:rest-assured")
    testImplementation("io.rest-assured:kotlin-extensions")
    testImplementation("org.springframework.modulith:spring-modulith-starter-test")

    implementation(project(":modules:base"))
    implementation(project(":modules:member"))
    implementation(project(":modules:tenant"))
    implementation(project(":modules:ticket"))
    implementation(project(":modules:coupon"))
    implementation(project(":modules:rbac"))
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.ai:spring-ai-bom:1.0.0-M3")
        mavenBom("org.springframework.modulith:spring-modulith-bom:${property("springModulithVersion")}")
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
