import org.springframework.boot.gradle.tasks.bundling.BootJar

val bootJar: BootJar by tasks
bootJar.enabled = true

dependencies {
    implementation(project(":domain"))
    testImplementation(kotlin("test"))
    implementation("nz.net.ultraq.thymeleaf:thymeleaf-layout-dialect")
}
