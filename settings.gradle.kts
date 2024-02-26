pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Music Player"
include(":app")
include(":core")
include(":features")
include(":core:designsystem")
include(":core:navigation")
include(":core:widgets")
include(":core:common")
include(":features:home")
include(":core:presentation")
include(":features:auth")
include(":core:common-impl")
include(":core:network")
include(":core:database")
include(":features:player")
