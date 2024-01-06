
import kotlinx.coroutines.runBlocking
import ru.astrainteractive.astralibs.encoding.IO
import ru.astrainteractive.synk.core.model.PlayerDTO
import java.util.UUID
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class RemoteTest {
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

    @Test
    fun `Insert - select - delete`(): Unit = runBlocking {
        val api = MockApiRemoteModule().also { it.lifecycle.onEnable() }.remoteApi
        val playerDTO = randomPlayerDTO
        api.insert(playerDTO).also {
            assertEquals(it, playerDTO)
        }
        api.select(playerDTO.minecraftUUID).also {
            assertEquals(it, playerDTO)
        }
        api.delete(playerDTO.minecraftUUID).also {
            val selected = api.select(playerDTO.minecraftUUID)
            assertNull(selected)
        }
    }

    @Test
    fun `Insert and update`(): Unit = runBlocking {
        val api = MockApiRemoteModule().also { it.lifecycle.onEnable() }.remoteApi
        val playerDTO = api.insert(randomPlayerDTO)
        val copiedPlayerDTO = playerDTO.copy(foodLevel = -10)
        api.insertOrUpdate(copiedPlayerDTO).also {
            assertEquals(it, copiedPlayerDTO)
        }
        api.select(playerDTO.minecraftUUID).also {
            assertEquals(it, copiedPlayerDTO)
        }
    }
}
