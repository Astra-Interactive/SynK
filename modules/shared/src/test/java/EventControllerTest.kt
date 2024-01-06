import com.astrainteractive.synk.api.remote.RemoteApi
import com.astrainteractive.synk.locker.HashSetLocker
import com.astrainteractive.synk.shared.EventController
import kotlinx.coroutines.runBlocking
import ru.astrainteractive.astralibs.encoding.IO
import ru.astrainteractive.klibs.mikro.core.dispatchers.DefaultKotlinDispatchers
import ru.astrainteractive.synk.core.model.PlayerModel
import java.util.UUID
import kotlin.random.Random
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class EventControllerTest {
    private lateinit var api: RemoteApi
    private lateinit var eventController: EventController

    private val randomPlayerModel: PlayerModel
        get() = PlayerModel(
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
        val playerModel = randomPlayerModel
        eventController.changeServer(playerModel) {
            eventController.loadPlayer(playerModel) {
                assertEquals(playerModel, it)
            }
        }
    }

    @Test
    fun `Save and load player`(): Unit = runBlocking {
        val playerModel = randomPlayerModel
        eventController.savePlayer(playerModel)
        eventController.loadPlayer(playerModel) {
            assertEquals(playerModel, it)
        }
    }
}
