// dependency versions
val vLombok = "1.18.34"
val vGson = "2.10.1"
val vJunit = "5.10.0"
val vDarkLaf = "3.0.2"
val vCommonsLang = "3.0"
val vLog4J2 = "2.20.0"
val vSlf4j = "2.0.9"

plugins {
    id("java")
    kotlin("jvm") version "2.1.10"
}

group = "com.aero.servicely"
version = "0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.apache.commons:commons-lang3:$vCommonsLang")
    implementation("com.google.code.gson:gson:$vGson")
    implementation("com.github.weisj:darklaf-core:$vDarkLaf")
    implementation("org.slf4j:slf4j-api:$vSlf4j")
    implementation("org.apache.logging.log4j:log4j-core:$vLog4J2")
    implementation("org.apache.logging.log4j:log4j-slf4j2-impl:$vLog4J2")
    implementation("org.apache.logging.log4j:log4j-api:$vLog4J2")

    compileOnly("org.projectlombok:lombok:$vLombok")

    annotationProcessor("org.projectlombok:lombok:$vLombok")

    testImplementation(platform("org.junit:junit-bom:$vJunit"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    testCompileOnly("org.projectlombok:lombok:$vLombok")

    testAnnotationProcessor("org.projectlombok:lombok:$vLombok")
}


tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}
