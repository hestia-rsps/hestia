plugins {
    kotlin("jvm") version "1.3.61"
    kotlin("kapt") version "1.3.61"
    idea
}

buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath("net.onedaybeard.artemis:artemis-odb-gradle-plugin:2.3.0")
    }
}

group = "world.gregs.hestia"
version = "0.3.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(project(":content"))
    implementation(project(":core"))
    implementation(project(":engine"))
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

allprojects {
    apply(plugin = "kotlin")
    apply(plugin = "kotlin-kapt")
    apply(plugin = "artemis")

    repositories {
        mavenCentral()
        maven(url = "https://dl.bintray.com/hestia-rsps/Hestia")
    }

    dependencies {
        //Systems
        implementation("world.gregs.hestia:hestia-server-core:0.4.4")
        implementation(kotlin("scripting-jvm"))
        implementation(kotlin("scripting-common"))
        implementation(kotlin("script-runtime"))
        implementation(kotlin("scripting-compiler-embeddable"))
        implementation(kotlin("compiler-embeddable"))
        implementation("net.onedaybeard.artemis:artemis-odb-serializer:2.3.0")
        implementation("net.mostlyoriginal.artemis-odb:contrib-eventbus:2.4.0")
        implementation("net.mostlyoriginal.artemis-odb:contrib-core:2.4.0")
        implementation("net.fbridault.eeel:artemis-odb-eeel:1.2.1")
        implementation("io.arrow-kt:arrow-fx:0.10.4")
        implementation("io.arrow-kt:arrow-optics:0.10.4")
        implementation("io.arrow-kt:arrow-syntax:0.10.4")
        implementation("io.arrow-kt:arrow-mtl:0.10.4")
        kapt("io.arrow-kt:arrow-meta:0.10.4")

        //Main

        implementation(kotlin("stdlib-jdk8"))
        implementation(kotlin("reflect"))
        implementation("io.netty:netty-all:4.1.44.Final")
        implementation("org.yaml:snakeyaml:1.25")

        //Logging
        implementation("org.slf4j:slf4j-api:1.7.30")
        implementation("ch.qos.logback:logback-classic:1.2.3")

        //Utilities
        implementation( "com.google.guava:guava:28.2-jre")
        implementation( "com.google.code.gson:gson:2.8.6")
        implementation( "commons-io:commons-io:2.6")
        implementation( "org.apache.commons:commons-text:1.8")
        implementation( "org.apache.commons:commons-lang3:3.9")
        implementation( "org.apache.commons:commons-collections4:4.4")

        //Testing
        testImplementation("org.junit.jupiter:junit-jupiter-api:5.5.2")
        testImplementation("org.assertj:assertj-core:3.14.0")
        testImplementation("io.mockk:mockk:1.9.3")
        testImplementation("org.mockito:mockito-all:2.0.2-beta")
        testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")
    }

    tasks {
        getByName<net.onedaybeard.gradle.ArtemisWeavingTask>("weave") {
            isEnableArtemisPlugin = true
            isEnablePooledWeaving = true
            isGenerateLinkMutators = true
            isOptimizeEntitySystems = true
            dependsOn(build)
            classesDir = sourceSets.getByName("main").output.classesDirs.first()
        }
        compileKotlin {
            kotlinOptions.jvmTarget = "1.8"
        }
        compileTestKotlin {
            kotlinOptions.jvmTarget = "1.8"
        }
    }

}