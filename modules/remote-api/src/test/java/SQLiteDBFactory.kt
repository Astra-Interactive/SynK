import com.astrainteractive.synk.api.remote.entities.PlayerTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.vendors.MariaDBDialect
import org.jetbrains.exposed.sql.vendors.MysqlDialect
import ru.astrainteractive.astralibs.di.Factory

class SQLiteDBFactory(private val file: String): Factory<Database>() {
    override fun initializer(): Database {
        val db = Database.connect("jdbc:sqlite:$file", "org.sqlite.JDBC")

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