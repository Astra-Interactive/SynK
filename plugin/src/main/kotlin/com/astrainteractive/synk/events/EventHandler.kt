package com.astrainteractive.synk.events

import com.astrainteractive.synk.api.local.LocalInventoryApi
import com.astrainteractive.synk.events.di.EventDependencies
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
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.event.world.WorldSaveEvent
import ru.astrainteractive.astralibs.event.DSLEvent

class EventHandler(dependencies: EventDependencies) : EventDependencies by dependencies {

    val onPlayerJoin = DSLEvent<PlayerJoinEvent>(eventListener, plugin) { e ->
        val playerModel = e.player.let(playerMapper::toDTO)
        controller.loadPlayer(
            player = playerModel,
            onLoaded = {
                withContext(dispatch.Main) {
                    playerMapper.fromDTO(it)
                }
            }
        )
    }

    val worldSaveEvent = DSLEvent<WorldSaveEvent>(eventListener, plugin) { e ->
        val players = Bukkit.getOnlinePlayers().map(playerMapper::toDTO)
        controller.saveAllPlayers(players)
    }

    val onPlayerLeave = DSLEvent<PlayerQuitEvent>(eventListener, plugin) { e ->
        val playerModel = e.player.let(playerMapper::toDTO)
        controller.savePlayer(playerModel)
    }

    val onMove = DSLEvent<PlayerMoveEvent>(eventListener, plugin) { e ->
        val playerModel = e.player.let(playerMapper::toDTO)
        if (controller.isPlayerLocked(playerModel)) {
            e.isCancelled = true
        }
    }

    val onDamage = DSLEvent<EntityDamageEvent>(eventListener, plugin) { e ->
        val player = e.entity as? Player ?: return@DSLEvent
        val playerModel = player.let(playerMapper::toDTO)
        if (controller.isPlayerLocked(playerModel)) {
            e.isCancelled = true
        }
    }

    val inventoryOpenEvent = DSLEvent<InventoryOpenEvent>(eventListener, plugin) { e ->
        val player = e.player as? Player ?: return@DSLEvent
        if (controller.isPlayerLocked(player.let(playerMapper::toDTO))) {
            e.isCancelled = true
        }
    }

    val dropItemEvent = DSLEvent<PlayerDropItemEvent>(eventListener, plugin) { e ->
        val playerModel = e.player.let(playerMapper::toDTO)
        if (controller.isPlayerLocked(playerModel)) {
            e.isCancelled = true
        }
    }

    val pickUpItemEvent = DSLEvent<EntityPickupItemEvent>(eventListener, plugin) { e ->
        val player = e.entity as? Player ?: return@DSLEvent
        val playerModel = player.let(playerMapper::toDTO)
        if (controller.isPlayerLocked(playerModel)) {
            e.isCancelled = true
        }
    }

    val onPlayerInteract = DSLEvent<PlayerInteractEvent>(eventListener, plugin) { e ->
        val playerModel = e.player.let(playerMapper::toDTO)
        if (controller.isPlayerLocked(playerModel)) {
            e.isCancelled = true
        }
    }

    val onBlockPlace = DSLEvent<BlockPlaceEvent>(eventListener, plugin) { e ->
        val playerModel = e.player.let(playerMapper::toDTO)
        if (controller.isPlayerLocked(playerModel)) {
            e.isCancelled = true
        }
    }

    val onBlockBreak = DSLEvent<BlockBreakEvent>(eventListener, plugin) { e ->
        val playerModel = e.player.let(playerMapper::toDTO)
        if (controller.isPlayerLocked(playerModel)) {
            e.isCancelled = true
        }
    }

    val onInventoryClick = DSLEvent<InventoryClickEvent>(eventListener, plugin) { e ->
        val player = e.whoClicked as? Player ?: return@DSLEvent
        val playerModel = player.let(playerMapper::toDTO)
        if (controller.isPlayerLocked(playerModel)) {
            e.isCancelled = true
        }
    }

    val onPlayerDeath = DSLEvent<PlayerDeathEvent>(eventListener, plugin) { e ->
        val playerModel = e.player.let(playerMapper::toDTO)
        if (controller.isPlayerLocked(playerModel)) {
            e.isCancelled = true
            e.drops.clear()
        } else {
            controller.savePlayer(playerModel, LocalInventoryApi.TYPE.DEATH)
        }
    }
}
