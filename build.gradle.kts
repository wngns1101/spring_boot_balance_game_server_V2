import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.2.3" apply false
    id("io.spring.dependency-management") version "1.1.4" apply false
    kotlin("jvm") version "1.9.23"
    id("org.jmailen.kotlinter") version "3.2.0"
    kotlin("plugin.spring") version "1.9.22" apply false
    kotlin("plugin.jpa") version "1.9.22" apply false
}

allprojects {
    group = "org.example"
    version = "0.0.1-SNAPSHOT"

    apply(plugin = "org.jmailen.kotlinter")

    repositories {
        mavenCentral()
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs += "-Xjsr305=strict"
            jvmTarget = "17"
        }
    }

    tasks.withType<JavaCompile> {
        sourceCompatibility = JavaVersion.VERSION_17.toString()
        targetCompatibility = JavaVersion.VERSION_17.toString()
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

subprojects {
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "org.jetbrains.kotlin.plugin.jpa")
    apply(plugin = "kotlin")
    apply(plugin = "kotlin-kapt")

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
        runtimeOnly("com.mysql:mysql-connector-j")
        implementation("org.springframework.boot:spring-boot-starter-jdbc")
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
        implementation("org.springframework.security:spring-security-crypto:5.5.0")
        implementation("org.springframework.boot:spring-boot-starter-data-redis")
    }
}