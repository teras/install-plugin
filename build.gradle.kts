plugins {
    `java-gradle-plugin`
    `maven-publish`
}

group = "com.panayotis.gradle"
version = "1.0.0"

gradlePlugin {
    plugins {
        create("install") {
            id = "com.panayotis.gradle.install"
            implementationClass = "com.panayotis.gradle.InstallPlugin"
        }
    }
}

publishing {
    repositories {
        maven {
            url = uri(layout.buildDirectory.dir("maven-repo"))
        }
    }
}