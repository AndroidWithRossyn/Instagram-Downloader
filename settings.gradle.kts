pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
        jcenter() // Warning: this repository is going to shut down soon
        mavenCentral {
            url = uri("https://oss.jfrog.org/artifactory/oss-snapshot-local")
        }
        mavenCentral {
            url = uri("https://jitpack.io")
        }
        maven {
            url = uri("https://android-sdk.is.com/")
        }
        maven {
            url = uri("https://maven.google.com")
        }
        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }
    }
}

rootProject.name = "InInsta"
include(":app")
include(":app-hilt")
include(":bubblenavigation")
