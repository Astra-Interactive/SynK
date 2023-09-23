package com.astrainteractive.synk.di.factories

import com.astrainteractive.synk.api.remote.entities.PlayerTable
import com.astrainteractive.synk.models.config.PluginConfig
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.vendors.MariaDBDialect
import org.jetbrains.exposed.sql.vendors.MysqlDialect
import ru.astrainteractive.klibs.kdi.Factory

class DatabaseFactory(
    private val config: PluginConfig
) : Factory<Database> {

    private val host: String
        get() = config.mysql.host
    private val port: String
        get() = config.mysql.port
    private val login: String
        get() = config.mysql.login
    private val password: String
        get() = config.mysql.password
    private val name: String
        get() = config.mysql.name
    private val driver: String
        get() = config.mysql.driver

    override fun create(): Database {
        val db = Database.connect(
            "jdbc:mysql://$host:$port/$name",
            driver = driver,
            user = login,
            password = password
        )

        Database.registerDialect("mariadb") { MariaDBDialect() }
        Database.registerDialect("mysql") { MysqlDialect() }

        transaction {
            val tables = buildList {
                add(PlayerTable)
            }
            tables.forEach(SchemaUtils::create)
            tables.forEach(SchemaUtils::createMissingTablesAndColumns)
        }
        return db
    }
}
