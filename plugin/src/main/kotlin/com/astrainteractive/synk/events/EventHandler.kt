package com.astrainteractive.synk.events

import com.astrainteractive.synk.api.local.LocalInventoryApi
import com.astrainteractive.synk.di.ServiceLocator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityPickupItemEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.event.player.*
import org.bukkit.event.world.WorldSaveEvent
import ru.astrainteractive.astralibs.async.BukkitMain
import ru.astrainteractive.astralibs.di.getValue
import ru.astrainteractive.astralibs.events.DSLEvent

class EventHandler {
    private val controller by ServiceLocator.eventController
    private val playerMapper by ServiceLocator.bukkitPlayerMapper

    val onPlayerJoin = DSLEvent.event<PlayerJoinEvent> { e ->
        val playerDTO = e.player.let(playerMapper::toDTO)
        controller.loadPlayer(
            player = playerDTO,
            onLoaded = {
                withContext(Dispatchers.BukkitMain) {
                    playerMapper.fromDTO(it)
                }
            })
    }

    val worldSaveEvent = DSLEvent.event<WorldSaveEvent> { e ->
        val players = Bukkit.getOnlinePlayers().map(playerMapper::toDTO)
        controller.saveAllPlayers(players)
    }

    val onPlayerLeave = DSLEvent.event<PlayerQuitEvent> { e ->
        val playerDTO = e.player.let(playerMapper::toDTO)
        controller.savePlayer(playerDTO)
    }
    val onMove = DSLEvent.event<PlayerMoveEvent> { e ->
        val playerDTO = e.player.let(playerMapper::toDTO)
        if (controller.isPlayerLocked(playerDTO))
            e.isCancelled = true
    }

    val onDamage = DSLEvent.event<EntityDamageEvent> { e ->
        val player = e.entity as? Player ?: return@event
        val playerDTO = player.let(playerMapper::toDTO)
        if (controller.isPlayerLocked(playerDTO))
            e.isCancelled = true
    }

    val inventoryOpenEvent = DSLEvent.event<InventoryOpenEvent> { e ->
        val player = e.player as? Player ?: return@event
        if (controller.isPlayerLocked(player.let(playerMapper::toDTO)))
            e.isCancelled = true
    }

    val dropItemEvent = DSLEvent.event<PlayerDropItemEvent> { e ->
        val playerDTO = e.player.let(playerMapper::toDTO)
        if (controller.isPlayerLocked(playerDTO))
            e.isCancelled = true
    }

    val pickUpItemEvent = DSLEvent.event<EntityPickupItemEvent> { e ->
        val player = e.entity as? Player ?: return@event
        val playerDTO = player.let(playerMapper::toDTO)
        if (controller.isPlayerLocked(playerDTO))
            e.isCancelled = true
    }

    val onPlayerInteract = DSLEvent.event<PlayerInteractEvent> { e ->
        val playerDTO = e.player.let(playerMapper::toDTO)
        if (controller.isPlayerLocked(playerDTO))
            e.isCancelled = true
    }

    val onBlockPlace = DSLEvent.event<BlockPlaceEvent> { e ->
        val playerDTO = e.player.let(playerMapper::toDTO)
        if (controller.isPlayerLocked(playerDTO))
            e.isCancelled = true
    }

    val onBlockBreak = DSLEvent.event<BlockBreakEvent> { e ->
        val playerDTO = e.player.let(playerMapper::toDTO)
        if (controller.isPlayerLocked(playerDTO))
            e.isCancelled = true
    }

    val onInventoryClick = DSLEvent.event<InventoryClickEvent> { e ->
        val player = e.whoClicked as? Player ?: return@event
        val playerDTO = player.let(playerMapper::toDTO)
        if (controller.isPlayerLocked(playerDTO))
            e.isCancelled = true
    }

    val onPlayerDeath = DSLEvent.event<PlayerDeathEvent> { e ->
        val playerDTO = e.player.let(playerMapper::toDTO)
        if (controller.isPlayerLocked(playerDTO)) {
            e.isCancelled = true
            e.drops.clear()
        } else controller.savePlayer(playerDTO, LocalInventoryApi.TYPE.DEATH)
    }
}