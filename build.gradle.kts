// dependency versions
val vLombok = "1.18.34"
val vGson = "2.10.1"
val vJunit = "5.10.0"
val vDarkLaf = "3.0.2"

plugins {
    id("java")
}

group = "com.aero.servicely"
version = "0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // gson - (de-)serialization
    implementation("com.google.code.gson:gson:$vGson")

    // look and feel
    implementation("com.github.weisj:darklaf-core:$vDarkLaf")

    // junit::jupiter
    testImplementation(platform("org.junit:junit-bom:$vJunit"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    // lombok
    compileOnly("org.projectlombok:lombok:$vLombok")
    testCompileOnly("org.projectlombok:lombok:$vLombok")
    annotationProcessor("org.projectlombok:lombok:$vLombok")
    testAnnotationProcessor("org.projectlombok:lombok:$vLombok")
}

tasks.test {
    useJUnitPlatform()
}