package base

import factories.SQLiteDBFactory
import org.jetbrains.exposed.sql.Database
import ru.astrainteractive.astralibs.di.Factory

class SQLiteTest(dbFile: String) : ORMTest {
    override val dbFactory: Factory<Database> = SQLiteDBFactory(dbFile)
    override var database: Database? = null
}
