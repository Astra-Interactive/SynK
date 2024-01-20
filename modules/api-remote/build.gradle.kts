plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
}
dependencies {
    // AstraLibs
    implementation(libs.minecraft.astralibs.core)
    // SQL
    implementation(libs.driver.jdbc)
    implementation(libs.driver.h2)
    implementation(libs.exposed.java.time)
    implementation(libs.exposed.jdbc)
    implementation(libs.exposed.core)
    implementation(libs.exposed.dao)
    // Local
    implementation(projects.modules.core)
}
