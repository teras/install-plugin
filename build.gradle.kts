plugins {
    `java-gradle-plugin`
    `maven-publish`
    signing
    id("io.github.gradle-nexus.publish-plugin") version "1.3.0"
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

nexusPublishing {
    repositories.sonatype()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])

            pom {
                name.set("Gradle native installer")
                description.set("Gradle native installer of binary files")
                url.set("https://github.com/teras/")
                licenses {
                    license {
                        name.set("GNU Lesser General Public License, Version 2")
                        url.set("https://www.gnu.org/licenses/lgpl-2.0.html")
                    }
                }
                developers {
                    developer {
                        name.set("Panayotis Katsaloulis")
                        email.set("panayotis@panayotis.com")
                        organizationUrl.set("https://www.panayotis.com")
                    }
                }
//                scm {
//                    connection.set("<<SCM Connection URL>>")
//                    developerConnection.set("<<SCM Dev Connection URL>>")
//                    url.set("<<Source URL>>")
//                }
            }
        }
    }
}

signing {
    sign(publishing.publications["mavenJava"])
}