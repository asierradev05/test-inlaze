plugins {
    java
}

group = "com.ejemplo.qa"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation ("com.github.javafaker:javafaker:1.0.2")
    implementation("org.seleniumhq.selenium:selenium-java:4.12.0")
    implementation("io.cucumber:cucumber-java8:7.14.0")
    implementation("io.cucumber:cucumber-junit:7.14.0")
    implementation("io.github.bonigarcia:webdrivermanager:5.5.3")
    testImplementation("junit:junit:4.13.2")
}

tasks.test {
    useJUnit()
}
