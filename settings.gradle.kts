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
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "LibriaNow"
include(":app")
include("core")
include("feature")

include(":core:network")
include(":core:data")
include(":core:design-system")
include(":core:common")
include(":core:local")
include(":feature:navbar-screens")
include(":feature:common")
include(":feature:anime-screen")
include(":feature:onboarding-screen")
