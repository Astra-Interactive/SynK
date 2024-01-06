package com.astrainteractive.synk.api.remote
import com.astrainteractive.synk.api.remote.di.MockApiRemoteModule
import kotlinx.coroutines.runBlocking
import ru.astrainteractive.astralibs.encoding.IO
import ru.astrainteractive.synk.core.model.PlayerModel
import java.util.UUID
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class RemoteApiTest {
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

    @Test
    fun `Insert - select - delete`(): Unit = runBlocking {
        val api = MockApiRemoteModule().also { it.lifecycle.onEnable() }.remoteApi
        val playerModel = randomPlayerModel
        api.insert(playerModel).also {
            assertEquals(it, playerModel)
        }
        api.select(playerModel.minecraftUUID).also {
            assertEquals(it, playerModel)
        }
        api.delete(playerModel.minecraftUUID).also {
            val selected = api.select(playerModel.minecraftUUID)
            assertNull(selected)
        }
    }

    @Test
    fun `Insert and update`(): Unit = runBlocking {
        val api = MockApiRemoteModule().also { it.lifecycle.onEnable() }.remoteApi
        val playerModel = api.insert(randomPlayerModel)
        val copiedPlayerModel = playerModel.copy(foodLevel = -10)
        api.insertOrUpdate(copiedPlayerModel).also {
            assertEquals(it, copiedPlayerModel)
        }
        api.select(playerModel.minecraftUUID).also {
            assertEquals(it, copiedPlayerModel)
        }
    }
}
