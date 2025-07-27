plugins {
    kotlin("jvm") version "1.9.24"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.hazelcast:hazelcast:4.2.6")
    implementation("com.hazelcast.jet:hazelcast-jet:4.5.4")
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
}

tasks.test {
    useJUnitPlatform()
}
