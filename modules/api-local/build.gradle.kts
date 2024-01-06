@file:Suppress("UnusedPrivateMember")

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("ru.astrainteractive.gradleplugin.minecraft.multiplatform")
}

minecraftMultiplatform {
    bukkit()
    dependencies {
        // AstraLibs
        implementation(libs.minecraft.astralibs.ktxcore)
        // Bukkit
        "bukkitMainCompileOnly"(libs.minecraft.astralibs.spigot.core)
        "bukkitMainCompileOnly"(libs.minecraft.paper.api)
        // Local
        implementation(projects.modules.core)
    }
}
