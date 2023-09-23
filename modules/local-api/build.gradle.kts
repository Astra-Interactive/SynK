plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
}
dependencies {
    implementation(project(":modules:models"))
}
