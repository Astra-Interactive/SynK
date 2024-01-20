plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
}

dependencies {
    // Coroutines
    implementation(libs.kotlin.coroutines.core)
    // Serialization
    implementation(libs.kotlin.serialization)
    implementation(libs.kotlin.serializationJson)
    implementation(libs.kotlin.serializationKaml)
    // AstraLibs
    implementation(libs.minecraft.astralibs.core)
}
