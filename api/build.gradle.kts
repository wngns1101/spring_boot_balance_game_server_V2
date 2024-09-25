import org.springframework.boot.gradle.tasks.bundling.BootJar

val bootJar: BootJar by tasks
bootJar.enabled = true

dependencies {
    implementation(project(":domain"))
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.2")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.3")
//    implementation("org.springframework.security:spring-security-crypto:5.5.0")
//    implementation ("com.google.code.gson:gson:2.10.1")
//    implementation ("com.squareup.okhttp3:okhttp:4.10.0")
    implementation ("com.slack.api:slack-api-client:1.28.0")
    implementation ("com.slack.api:slack-app-backend:1.28.0")
    implementation ("com.slack.api:slack-api-model:1.28.0")
    implementation("io.jsonwebtoken:jjwt-api:0.11.2")
    runtimeOnly ("io.jsonwebtoken:jjwt-impl:0.11.2")
    runtimeOnly ("io.jsonwebtoken:jjwt-jackson:0.11.2")
    implementation("com.amazonaws:aws-java-sdk-s3:1.12.174")
    implementation("org.springframework.boot:spring-boot-starter-mail")
}