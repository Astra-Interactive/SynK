package base

import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.Database
import ru.astrainteractive.klibs.kdi.Factory
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

interface ORMTest {
    val dbFactory: Factory<Database>
    var database: Database?
    fun assertConnected(): Database {
        return checkNotNull(database) { "Database not connected" }
    }

    @BeforeTest
    open fun setup(): Unit = runBlocking {
        database = dbFactory.create()
    }

    @AfterTest
    open fun destroy(): Unit = runBlocking {
        database?.connector?.invoke()?.close()
        database = null
    }
}
