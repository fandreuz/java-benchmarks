plugins {
    java
    id("me.champeau.jmh") version "0.7.2"
    id("com.diffplug.spotless") version "7.0.0.BETA1"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.openjdk.jmh:jmh-core:1.37")
    annotationProcessor("org.openjdk.jmh:jmh-generator-annprocess:1.37")
    implementation("org.apache.commons:commons-lang3:3.15.0")
    implementation("org.eclipse.collections:eclipse-collections:11.1.0")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

spotless {
    java {
        importOrder()
        removeUnusedImports()
        cleanthat()
        formatAnnotations()
    }
}

jmh {
    val includePattern: String? by project
    includePattern?.let {
        includes.add(it)
    }
}
