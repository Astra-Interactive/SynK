plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
}

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
    // SQL
    implementation(libs.driver.jdbc)
    implementation(libs.exposed.java.time)
    implementation(libs.exposed.jdbc)
    implementation(libs.exposed.core)
    implementation(libs.exposed.dao)
    // Test
    testImplementation(kotlin("test"))
    // Local
    implementation(projects.modules.apiRemote)
    implementation(projects.modules.apiLocal)
    implementation(projects.modules.core)
}
