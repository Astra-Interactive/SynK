package com.astrainteractive.synk.shared

import com.astrainteractive.synk.api.local.LocalInventoryApi
import com.astrainteractive.synk.api.remote.RemoteApi
import com.astrainteractive.synk.api.remote.exception.RemoteApiException
import com.astrainteractive.synk.utils.Locker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.jetbrains.kotlin.tooling.core.UnsafeApi
import ru.astrainteractive.astralibs.async.AsyncComponent
import ru.astrainteractive.klibs.mikro.core.dispatchers.KotlinDispatchers
import ru.astrainteractive.synk.core.model.PlayerDTO
import java.util.UUID

class EventController(
    private val locker: Locker<UUID>,
    private val sqlDataSource: RemoteApi,
    private val localDataSource: LocalInventoryApi<*>,
    private val dispatchers: KotlinDispatchers
) : AsyncComponent() {
    fun isPlayerLocked(player: PlayerDTO?): Boolean = runBlocking {
        locker.isLocked(player?.minecraftUUID)
    }

    @OptIn(UnsafeApi::class)
    private inline fun <reified T> withLock(
        uuid: UUID,
        crossinline block: suspend CoroutineScope.() -> T
    ) = launch(dispatchers.IO) {
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
        withContext(dispatchers.IO) { localDataSource.savePlayer(player, LocalInventoryApi.TYPE.ENTER) }
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

    @OptIn(UnsafeApi::class)
    fun saveAllPlayers(players: List<PlayerDTO>) = launch(dispatchers.IO) {
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
    }
}
