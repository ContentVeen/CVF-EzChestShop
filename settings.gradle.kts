rootProject.name = "EzChestShopReborn"

dependencyResolutionManagement {
    // Incubating Gradle features
    @Suppress("UnstableApiUsage")
    repositories {
        mavenCentral()

        maven("https://repo.papermc.io/repository/maven-public/") {
            content {
                includeGroup("io.papermc.paper")
                includeGroup("net.kyori")
                includeGroup("net.md-5")
            }
        }

        maven("https://maven.enginehub.org/repo/") {
            content {
                includeGroupAndSubgroups("com.sk89q.worldguard")
                includeGroupAndSubgroups("com.sk89q.worldedit")
                includeGroupAndSubgroups("org.enginehub.lin-bus")
            }
        }

        maven("https://maven.playpro.com") {
            content {
                includeGroup("net.coreprotect")
            }
        }

        maven("https://repo.minebench.de/") {
            content {
                includeGroup("de.themoep")
            }
        }

        maven("https://repo.glaremasters.me/repository/towny/") {
            content {
                includeGroup("com.palmergames.bukkit.towny")
            }
        }

        // AdvancedRegionMarket is no longer reliably hosted on a Maven repository
        // (the upstream nexus.alex9849.net is gone and the maven.nouish.no mirror has
        // been returning 5xx errors). Resolve the artifact straight from the GitHub
        // release instead, where it is published as a plain jar.
        exclusiveContent {
            forRepository {
                ivy("https://github.com/alex9849/advanced-region-market/releases/download/") {
                    patternLayout {
                        artifact("[revision]/[module]-[revision].jar")
                    }
                    metadataSources { artifact() }
                }
            }
            filter {
                includeModule("net.alex9849.advancedregionmarket", "advancedregionmarket")
            }
        }

        maven("https://repo.extendedclip.com/content/repositories/placeholderapi/") {
            content {
                includeModule("me.clip", "placeholderapi")
            }
        }

        maven("https://libraries.minecraft.net/") {
            content {
                includeGroup("com.mojang")
            }
        }

        maven("https://jitpack.io") {
            content {
                includeModule("com.github.Anon8281", "UniversalScheduler")
                includeModule("com.github.Slimefun", "Slimefun4")
                includeModule("com.github.MilkBowl", "VaultAPI")
            }
        }
    }
}

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://repo.papermc.io/repository/maven-public/")
    }
}

include("core")
include("internal:v1_21_R1")
include("internal:v1_21_R2")
include("internal:v1_21_R3")
include("internal:v1_21_R6")
include("paper-plugin")
