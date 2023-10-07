import ru.astrainteractive.gradleplugin.setupSpigotProcessor
import ru.astrainteractive.gradleplugin.setupSpigotShadow
import ru.astrainteractive.gradleplugin.sourceset.JvmSourceSetExt.bukkitMain

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
}

evaluationDependsOn(":modules:api-local")

dependencies {
    // Coroutines
    implementation(libs.kotlin.coroutines.coreJvm)
    implementation(libs.kotlin.coroutines.core)
    // Serialization
    implementation(libs.kotlin.serialization)
    implementation(libs.kotlin.serializationJson)
    implementation(libs.kotlin.serializationKaml)
    // AstraLibs
    implementation(libs.minecraft.astralibs.ktxcore)
    implementation(libs.minecraft.astralibs.spigot.core)
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
    implementation(projects.modules.models)
    implementation(projects.modules.apiRemote)
    implementation(projects.modules.apiLocal.bukkitMain)
    implementation(projects.modules.apiLocal)
    implementation(projects.modules.spigotBungee)
    implementation(projects.modules.shared)
}

File("D:\\Minecraft Servers\\Servers\\esmp-configuration\\anarchy\\plugins").let { file ->
    if (file.exists()) setupSpigotShadow(file) else setupSpigotShadow()
}

setupSpigotProcessor()
