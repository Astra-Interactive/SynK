package base

import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.Database
import ru.astrainteractive.astralibs.di.Factory
import kotlin.test.*

interface ORMTest {
    abstract val dbFactory: Factory<Database>
    var database: Database?
    fun assertConnected(): Database {
        return database ?: throw IllegalStateException("Database not connected")
    }

    @BeforeTest
    open fun setup(): Unit = runBlocking {
        database = dbFactory.value
    }

    @AfterTest
    open fun destroy(): Unit = runBlocking {
        database?.connector?.invoke()?.close()
        database = null
    }
}
