import org.springframework.boot.gradle.tasks.bundling.BootJar

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = false
jar.enabled = true

plugins {
    idea
}

dependencies {
    implementation ("com.querydsl:querydsl-jpa:5.0.0:jakarta")
    kapt ("com.querydsl:querydsl-apt:5.0.0:jakarta")
    kapt ("jakarta.annotation:jakarta.annotation-api")
    kapt ("jakarta.persistence:jakarta.persistence-api")
    implementation("com.google.firebase:firebase-admin:9.2.0")
    implementation("org.springframework.boot:spring-boot-starter-quartz")
    implementation("com.amazonaws:aws-java-sdk-s3:1.12.174")
}

idea {
    module {
        val kaptMain = file("build/generated/source/kapt/main")
        sourceDirs.add(kaptMain)
        generatedSourceDirs.add(kaptMain)
    }
}