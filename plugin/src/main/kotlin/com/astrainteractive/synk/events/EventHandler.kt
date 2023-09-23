package com.astrainteractive.synk.events

import com.astrainteractive.synk.api.local.LocalInventoryApi
import com.astrainteractive.synk.events.di.EventModule
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
import ru.astrainteractive.klibs.kdi.getValue

class EventHandler(module: EventModule) : EventModule by module {

    val onPlayerJoin = DSLEvent<PlayerJoinEvent>(eventListener, plugin) { e ->
        val playerDTO = e.player.let(playerMapper::toDTO)
        controller.loadPlayer(
            player = playerDTO,
            onLoaded = {
                withContext(dispatch.BukkitMain) {
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
        val playerDTO = e.player.let(playerMapper::toDTO)
        controller.savePlayer(playerDTO)
    }
    val onMove = DSLEvent<PlayerMoveEvent>(eventListener, plugin) { e ->
        val playerDTO = e.player.let(playerMapper::toDTO)
        if (controller.isPlayerLocked(playerDTO)) {
            e.isCancelled = true
        }
    }

    val onDamage = DSLEvent<EntityDamageEvent>(eventListener, plugin) { e ->
        val player = e.entity as? Player ?: return@DSLEvent
        val playerDTO = player.let(playerMapper::toDTO)
        if (controller.isPlayerLocked(playerDTO)) {
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
        val playerDTO = e.player.let(playerMapper::toDTO)
        if (controller.isPlayerLocked(playerDTO)) {
            e.isCancelled = true
        }
    }

    val pickUpItemEvent = DSLEvent<EntityPickupItemEvent>(eventListener, plugin) { e ->
        val player = e.entity as? Player ?: return@DSLEvent
        val playerDTO = player.let(playerMapper::toDTO)
        if (controller.isPlayerLocked(playerDTO)) {
            e.isCancelled = true
        }
    }

    val onPlayerInteract = DSLEvent<PlayerInteractEvent>(eventListener, plugin) { e ->
        val playerDTO = e.player.let(playerMapper::toDTO)
        if (controller.isPlayerLocked(playerDTO)) {
            e.isCancelled = true
        }
    }

    val onBlockPlace = DSLEvent<BlockPlaceEvent>(eventListener, plugin) { e ->
        val playerDTO = e.player.let(playerMapper::toDTO)
        if (controller.isPlayerLocked(playerDTO)) {
            e.isCancelled = true
        }
    }

    val onBlockBreak = DSLEvent<BlockBreakEvent>(eventListener, plugin) { e ->
        val playerDTO = e.player.let(playerMapper::toDTO)
        if (controller.isPlayerLocked(playerDTO)) {
            e.isCancelled = true
        }
    }

    val onInventoryClick = DSLEvent<InventoryClickEvent>(eventListener, plugin) { e ->
        val player = e.whoClicked as? Player ?: return@DSLEvent
        val playerDTO = player.let(playerMapper::toDTO)
        if (controller.isPlayerLocked(playerDTO)) {
            e.isCancelled = true
        }
    }

    val onPlayerDeath = DSLEvent<PlayerDeathEvent>(eventListener, plugin) { e ->
        val playerDTO = e.player.let(playerMapper::toDTO)
        if (controller.isPlayerLocked(playerDTO)) {
            e.isCancelled = true
            e.drops.clear()
        } else {
            controller.savePlayer(playerDTO, LocalInventoryApi.TYPE.DEATH)
        }
    }
}
