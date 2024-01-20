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
        implementation(libs.minecraft.astralibs.core)
        // Bukkit
        "bukkitMainCompileOnly"(libs.minecraft.astralibs.core.bukkit)
        "bukkitMainCompileOnly"(libs.minecraft.paper.api)
        // Local
        implementation(projects.modules.core)
    }
}
