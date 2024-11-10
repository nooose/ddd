
dependencies {
    implementation(project(":modules:base"))
    implementation(project(":modules:tenant"))

    implementation(platform("org.springframework.ai:spring-ai-bom:1.0.0-M3"))
    implementation("org.springframework.ai:spring-ai-openai-spring-boot-starter")
}
