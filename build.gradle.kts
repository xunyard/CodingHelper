plugins {
    id("java")
    id("org.jetbrains.intellij") version "1.10.0"
}

group  = "cn.xunyard"
version = "2024.1"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("io.swagger:swagger-annotations:1.6.5")
    compileOnly("org.projectlombok:lombok:1.18.22")
    annotationProcessor("org.projectlombok:lombok:1.18.22")
}

intellij {
    version.set("2024.1")
    type.set("IC") // Target IDE Platform

    plugins.set(listOf("java"))
}


tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }

    patchPluginXml {
        sinceBuild.set("231")
        untilBuild.set("241.*")
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }
}