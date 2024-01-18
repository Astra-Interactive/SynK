package com.astrainteractive.synk.shared

import com.astrainteractive.synk.api.local.MockLocalInventoryApi
import com.astrainteractive.synk.api.remote.RemoteApi
import com.astrainteractive.synk.api.remote.di.MockApiRemoteModule
import com.astrainteractive.synk.locker.HashSetLocker
import kotlinx.coroutines.runBlocking
import ru.astrainteractive.astralibs.encoding.model.EncodedObject
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
            items = EncodedObject.Base64(""),
            enderChestItems = EncodedObject.Base64(""),
            effects = EncodedObject.Base64(""),
        )

    @BeforeTest
    fun setup(): Unit = runBlocking {
        val api = MockApiRemoteModule().also { it.lifecycle.onEnable() }.remoteApi
        eventController = EventController(
            locker = HashSetLocker(),
            sqlDataSource = api,
            localDataSource = MockLocalInventoryApi,
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
