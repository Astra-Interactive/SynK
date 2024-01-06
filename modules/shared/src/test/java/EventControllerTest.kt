import com.astrainteractive.synk.api.remote.RemoteApi
import com.astrainteractive.synk.shared.EventController
import com.astrainteractive.synk.utils.HashSetLocker
import kotlinx.coroutines.runBlocking
import ru.astrainteractive.astralibs.encoding.IO
import ru.astrainteractive.klibs.mikro.core.dispatchers.DefaultKotlinDispatchers
import ru.astrainteractive.synk.core.model.PlayerDTO
import java.util.UUID
import kotlin.random.Random
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class EventControllerTest {
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

    @BeforeTest
    fun setup(): Unit = runBlocking {
        val api = MockApiRemoteModule().also { it.lifecycle.onEnable() }.remoteApi
        eventController = EventController(
            locker = HashSetLocker(),
            sqlDataSource = api,
            localDataSource = MockInventoryAPI,
            dispatchers = DefaultKotlinDispatchers
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
