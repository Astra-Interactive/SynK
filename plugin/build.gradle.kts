import ru.astrainteractive.gradleplugin.setupSpigotProcessor
import ru.astrainteractive.gradleplugin.setupSpigotShadow
import ru.astrainteractive.gradleplugin.util.ProjectProperties.projectInfo

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("ru.astrainteractive.gradleplugin.minecraft.multiplatform")
}

minecraftMultiplatform {
    dependencies {
        // Coroutines
        implementation(libs.kotlin.coroutines.coreJvm)
        implementation(libs.kotlin.coroutines.core)
        // Serialization
        implementation(libs.kotlin.serialization)
        implementation(libs.kotlin.serializationJson)
        implementation(libs.kotlin.serializationKaml)
        // AstraLibs
        implementation(libs.minecraft.astralibs.core)
        implementation(libs.minecraft.astralibs.core.bukkit)
        implementation(libs.minecraft.bstats)
        // SQL
        implementation(libs.driver.jdbc)
        implementation(libs.exposed.java.time)
        implementation(libs.exposed.jdbc)
        implementation(libs.exposed.core)
        implementation(libs.exposed.dao)
        // Spigot dependencies
        compileOnly(libs.minecraft.paper.api)
        // Local
        implementation(projects.modules.apiRemote)
        implementation(projects.modules.apiLocal.bukkitMain)
        implementation(projects.modules.apiLocal)
        implementation(projects.modules.spigotBungee)
        implementation(projects.modules.shared)
        implementation(projects.modules.core)
    }
}

val destination = File("D:\\Minecraft Servers\\Servers\\esmp-configuration\\smp\\plugins")
    .takeIf(File::exists)
    ?: File(rootDir, "jars")

setupSpigotShadow(destination) {
    archiveBaseName.set("${projectInfo.name}-bukkit")
}

setupSpigotProcessor()
