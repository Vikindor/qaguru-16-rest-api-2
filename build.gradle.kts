plugins {
    id("java")
    id("io.freefair.lombok") version "9.0.0"
    id("io.qameta.allure") version "3.0.0" // allure-framework/allure-gradle
}

group = "io.github.vikindor"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:6.0.0-M2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("io.rest-assured:rest-assured:5.5.6")
    testImplementation("io.rest-assured:json-schema-validator:5.5.6")
    testImplementation("org.assertj:assertj-core:3.26.0")
    testImplementation("io.qameta.allure:allure-rest-assured:2.30.0")
    allureRawResultElements(files(layout.buildDirectory.dir("allure-results")))
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testRuntimeOnly("org.slf4j:slf4j-simple:2.0.17")
}

allure {
    report {
        version.set("2.35.1") // allure-framework/allure2
    }
    adapter {
        autoconfigure.set(true)
        autoconfigureListeners.set(true)
        frameworks {
            junit5 {
                adapterVersion.set("2.30.0") // Same as allure-framework/allure-java
            }
        }
    }
}

tasks.test {
    useJUnitPlatform()

    jvmArgs("-Dfile.encoding=UTF-8","-Dorg.slf4j.simpleLogger.logFile=System.out")
    environment("SE_AVOID_STATS", "true")
}
