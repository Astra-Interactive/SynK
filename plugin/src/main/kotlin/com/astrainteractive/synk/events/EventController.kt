package com.astrainteractive.synk.events

import com.astrainteractive.synk.api.local.LocalInventoryApi
import com.astrainteractive.synk.api.mapping.BukkitPlayerMapperImpl
import com.astrainteractive.synk.api.messaging.BungeeController
import com.astrainteractive.synk.api.remote.exception.RemoteApiException
import com.astrainteractive.synk.di.ServiceLocator
import kotlinx.coroutines.*
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import ru.astrainteractive.astralibs.async.BukkitMain
import ru.astrainteractive.astralibs.async.PluginScope
import ru.astrainteractive.astralibs.di.getValue
import ru.astrainteractive.astralibs.utils.uuid
import java.util.*


object EventController {
    private val locker by ServiceLocator.uuidLockerModule
    private val sqlDataSource by ServiceLocator.RemoteDataSourceModule
    private val localDataSource by ServiceLocator.LocalDataSourceModule
    fun isPlayerLocked(player: Player?) = runBlocking {
        locker.isLocked(player?.uniqueId)
    }

    private inline fun <reified T> withLock(uuid: UUID, crossinline block: suspend CoroutineScope.() -> T) =
        PluginScope.launch(Dispatchers.IO) {
            if (locker.isLocked(uuid)) throw RemoteApiException.PlayerLockedException
            locker.lock(uuid)
            val result = block.invoke(this)
            locker.unlock(uuid)
            result
        }

    fun loadPlayer(player: Player) = withLock(player.uniqueId) {
        withContext(Dispatchers.IO) { localDataSource.savePlayer(player, LocalInventoryApi.TYPE.ENTER) }
        val playerDTO = sqlDataSource.select(player.uuid) ?: return@withLock
        withContext(Dispatchers.BukkitMain) { BukkitPlayerMapperImpl.fromDTO(playerDTO) }
    }

    fun savePlayer(player: Player, type: LocalInventoryApi.TYPE = LocalInventoryApi.TYPE.EXIT) =
        withLock(player.uniqueId) {
            localDataSource.savePlayer(player, type)
            val playerDTO = BukkitPlayerMapperImpl.toDTO(player)
            sqlDataSource.update(playerDTO)
        }

    fun saveAllPlayers() = PluginScope.launch(Dispatchers.IO) {
        Bukkit.getOnlinePlayers().map {
            async {
                savePlayer(it, LocalInventoryApi.TYPE.SAVE_ALL)
            }
        }.awaitAll()
    }

    fun changeServer(player: Player, server: String) =
        withLock(player.uniqueId) {
            localDataSource.savePlayer(player, LocalInventoryApi.TYPE.EXIT)
            val playerDTO = BukkitPlayerMapperImpl.toDTO(player)
            sqlDataSource.update(playerDTO)
            BungeeController.connectPlayerToServer(server, player)
        }
}