package com.astrainteractive.synk.shared

import com.astrainteractive.synk.api.local.LocalInventoryApi
import com.astrainteractive.synk.api.remote.RemoteApi
import com.astrainteractive.synk.api.remote.exception.RemoteApiException
import com.astrainteractive.synk.models.dto.PlayerDTO
import com.astrainteractive.synk.utils.Locker
import kotlinx.coroutines.*
import ru.astrainteractive.astralibs.async.PluginScope
import java.util.*

class EventController(
    private val locker: Locker<UUID>,
    private val sqlDataSource: RemoteApi,
    private val localDataSource: LocalInventoryApi<*>
) {
    fun isPlayerLocked(player: PlayerDTO?) = runBlocking {
        locker.isLocked(player?.minecraftUUID)
    }

    private inline fun <reified T> withLock(
        uuid: UUID,
        crossinline block: suspend CoroutineScope.() -> T
    ) = PluginScope.launch(Dispatchers.IO) {
        if (locker.isLocked(uuid)) throw RemoteApiException.PlayerLockedException
        locker.lock(uuid)
        val result = block.invoke(this)
        locker.unlock(uuid)
        result
    }

    fun loadPlayer(
        player: PlayerDTO,
        onLoaded: suspend CoroutineScope.(PlayerDTO) -> Unit
    ) = withLock(player.minecraftUUID) {
        withContext(Dispatchers.IO) { localDataSource.savePlayer(player, LocalInventoryApi.TYPE.ENTER) }
        val playerDTO = sqlDataSource.select(player.minecraftUUID) ?: return@withLock
        onLoaded.invoke(this, playerDTO)
    }

    fun savePlayer(
        player: PlayerDTO,
        type: LocalInventoryApi.TYPE = LocalInventoryApi.TYPE.EXIT
    ) = withLock(player.minecraftUUID) {
        localDataSource.savePlayer(player, type)
        sqlDataSource.insertOrUpdate(player)
    }

    fun saveAllPlayers(players: List<PlayerDTO>) = PluginScope.launch(Dispatchers.IO) {
        players.map {
            async {
                savePlayer(it, LocalInventoryApi.TYPE.SAVE_ALL)
            }
        }.awaitAll()
    }

    fun changeServer(
        player: PlayerDTO,
        onUpdated: () -> Unit
    ) = withLock(player.minecraftUUID) {
        localDataSource.savePlayer(player, LocalInventoryApi.TYPE.EXIT)
        sqlDataSource.insertOrUpdate(player)
        onUpdated.invoke()
//            BungeeController.connectPlayerToServer(server, player)
    }
}
