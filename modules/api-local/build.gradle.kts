import ru.astrainteractive.gradleplugin.sourceset.JvmSourceSet.Companion.configureAstraSourceSet

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
}

configureAstraSourceSet("bukkit")

dependencies {
    // AstraLibs
    implementation(libs.minecraft.astralibs.ktxcore)
    "bukkitMainCompileOnly"(libs.minecraft.astralibs.spigot.core)
    // Spigot dependencies
    "bukkitMainCompileOnly"(libs.minecraft.paper.api)
    // Local
    implementation(projects.modules.models)
}

println("APILOCALSETS: ${sourceSets.names}")
