import base.ORMTest
import base.SQLiteTest
import com.astrainteractive.synk.api.remote.RemoteApi
import com.astrainteractive.synk.api.remote.RemoteApiImpl
import com.astrainteractive.synk.api.remote.entities.PlayerTable
import com.astrainteractive.synk.models.dto.PlayerDTO
import com.astrainteractive.synk.shared.EventController
import com.astrainteractive.synk.utils.HashSetLocker
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import ru.astrainteractive.astralibs.encoding.IO
import java.io.File
import java.util.UUID
import kotlin.random.Random
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class EventControllerTest : ORMTest by SQLiteTest("data.db") {
    private lateinit var api: RemoteApi
    private lateinit var eventController: EventController
    private val randomPlayerDTO: PlayerDTO
        get() = PlayerDTO(
            minecraftUUID = UUID.randomUUID(),
            totalExperience = Random.nextInt(0, 1024),
            health = Random.nextDouble(0.0, 20.0),
            foodLevel = Random.nextInt(0, 20),
            lastServerName = UUID.randomUUID().toString(),
            items = IO.Base64(""),
            enderChestItems = IO.Base64(""),
            effects = IO.Base64(""),
        )

    @AfterTest
    override fun destroy(): Unit = runBlocking {
        transaction { SchemaUtils.drop(PlayerTable) }
        super.destroy()
        File("data.db").delete()
    }

    @BeforeTest
    override fun setup(): Unit = runBlocking {
        super.setup()
        val database = assertConnected()
        transaction { SchemaUtils.create(PlayerTable) }
        api = RemoteApiImpl(database)
        eventController = EventController(
            locker = HashSetLocker(),
            sqlDataSource = api,
            localDataSource = MockInventoryAPI
        )
    }

    @Test
    fun `Change server`(): Unit = runBlocking {
        val playerDTO = randomPlayerDTO
        eventController.changeServer(playerDTO) {
            eventController.loadPlayer(playerDTO) {
                assertEquals(playerDTO, it)
            }
        }
    }

    @Test
    fun `Save and load player`(): Unit = runBlocking {
        val playerDTO = randomPlayerDTO
        eventController.savePlayer(playerDTO)
        eventController.loadPlayer(playerDTO) {
            assertEquals(playerDTO, it)
        }
    }
}
