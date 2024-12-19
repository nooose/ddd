val jjwtVersion = "0.12.6"
val bucket4jVersion = "8.10.1"

dependencies {
    api("org.springframework.boot:spring-boot-starter-security")
    implementation("io.jsonwebtoken:jjwt-api:${jjwtVersion}")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:${jjwtVersion}")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:${jjwtVersion}")
    implementation("org.springframework.kafka:spring-kafka")
    implementation("com.bucket4j:bucket4j-core:$bucket4jVersion")
}
