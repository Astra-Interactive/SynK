plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
}
dependencies {
    // AstraLibs
    implementation(libs.minecraft.astralibs.ktxcore)
    // Serialization
    implementation(libs.kotlin.serialization)
    implementation(libs.kotlin.serializationJson)
    implementation(libs.kotlin.serializationKaml)
}
