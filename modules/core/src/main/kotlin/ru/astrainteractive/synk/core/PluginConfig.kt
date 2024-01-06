package ru.astrainteractive.synk.core

/**
 * Example config file with 3 types of initialization
 */
@kotlinx.serialization.Serializable
data class PluginConfig(
    val mysql: SqlConfig,
    val serverIDList: List<String> = emptyList(),
    val serverID: String,
) {
    sealed interface SqlConfig {
        @kotlinx.serialization.Serializable
        data class SQLite(val path: String) : SqlConfig

        @kotlinx.serialization.Serializable
        class MySql(
            val host: String,
            val port: String,
            val login: String,
            val password: String,
            val name: String,
        ) : SqlConfig
    }
}
