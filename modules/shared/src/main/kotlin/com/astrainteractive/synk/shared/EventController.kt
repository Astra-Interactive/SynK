package com.astrainteractive.synk.shared

import com.astrainteractive.synk.api.local.LocalInventoryApi
import com.astrainteractive.synk.api.remote.RemoteApi
import com.astrainteractive.synk.locker.Locker
import com.astrainteractive.synk.locker.LockerExt.launchWithLock
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import ru.astrainteractive.astralibs.async.AsyncComponent
import ru.astrainteractive.klibs.mikro.core.dispatchers.KotlinDispatchers
import ru.astrainteractive.synk.core.model.PlayerModel
import java.util.UUID

class EventController(
    private val locker: Locker<UUID>,
    private val sqlDataSource: RemoteApi,
    private val localDataSource: LocalInventoryApi<*>,
    private val dispatchers: KotlinDispatchers
) : AsyncComponent() {

    fun isPlayerLocked(player: PlayerModel?): Boolean = runBlocking {
        locker.isLocked(player?.minecraftUUID)
    }

    fun loadPlayer(
        player: PlayerModel,
        onLoaded: suspend CoroutineScope.(PlayerModel) -> Unit
    ) = locker.launchWithLock(player.minecraftUUID, componentScope) {
        withContext(dispatchers.IO) { localDataSource.savePlayer(player, LocalInventoryApi.TYPE.ENTER) }
        val playerModel = sqlDataSource.select(player.minecraftUUID) ?: return@launchWithLock
        onLoaded.invoke(this, playerModel)
    }

    fun savePlayer(
        player: PlayerModel,
        type: LocalInventoryApi.TYPE = LocalInventoryApi.TYPE.EXIT
    ) = locker.launchWithLock(player.minecraftUUID, componentScope) {
        localDataSource.savePlayer(player, type)
        sqlDataSource.insertOrUpdate(player)
    }

    fun saveAllPlayers(players: List<PlayerModel>) = launch(dispatchers.IO) {
        players.map {
            async {
                savePlayer(it, LocalInventoryApi.TYPE.SAVE_ALL)
            }
        }.awaitAll()
    }

    fun changeServer(
        player: PlayerModel,
        onUpdated: () -> Unit
    ) = locker.launchWithLock(player.minecraftUUID, componentScope) {
        localDataSource.savePlayer(player, LocalInventoryApi.TYPE.EXIT)
        sqlDataSource.insertOrUpdate(player)
        onUpdated.invoke()
    }
}
