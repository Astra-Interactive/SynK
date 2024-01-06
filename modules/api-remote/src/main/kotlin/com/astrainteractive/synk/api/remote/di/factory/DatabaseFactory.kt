package com.astrainteractive.synk.api.remote.di.factory

import com.astrainteractive.synk.api.remote.entities.PlayerTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.vendors.H2Dialect
import org.jetbrains.exposed.sql.vendors.MariaDBDialect
import org.jetbrains.exposed.sql.vendors.MysqlDialect
import ru.astrainteractive.klibs.kdi.Factory
import ru.astrainteractive.synk.core.PluginConfig

internal class DatabaseFactory(
    private val sqlConfig: PluginConfig.SqlConfig
) : Factory<Database> {

    override fun create(): Database {
        val db = when (sqlConfig) {
            is PluginConfig.SqlConfig.SQLite -> {
                Database.registerDialect(H2Dialect.dialectName) { H2Dialect() }
                Database.connect(
                    url = "jdbc:sqlite:${sqlConfig.path}",
                    driver = "org.sqlite.JDBC",
                )
            }

            is PluginConfig.SqlConfig.MySql -> {
                Database.registerDialect("mariadb") { MariaDBDialect() }
                Database.registerDialect("mysql") { MysqlDialect() }
                Database.connect(
                    url = "jdbc:mysql://${sqlConfig.host}:${sqlConfig.port}/${sqlConfig.name}",
                    driver = "com.mysql.cj.jdbc.Driver",
                    user = sqlConfig.login,
                    password = sqlConfig.password
                )
            }
        }

        Database.registerDialect(MariaDBDialect.dialectName) { MariaDBDialect() }
        Database.registerDialect(MysqlDialect.dialectName) { MysqlDialect() }

        transaction(db) {
            val tables = buildList {
                add(PlayerTable)
            }
            tables.forEach(SchemaUtils::create)
            tables.forEach(SchemaUtils::createMissingTablesAndColumns)
        }
        return db
    }
}
