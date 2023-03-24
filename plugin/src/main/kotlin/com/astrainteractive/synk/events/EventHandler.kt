package com.astrainteractive.synk.events

import com.astrainteractive.synk.api.local.LocalInventoryApi
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
import ru.astrainteractive.astralibs.events.DSLEvent

class EventHandler {
    private val controller = EventController

    val onPlayerJoin = DSLEvent.event<PlayerJoinEvent> { e ->
        controller.loadPlayer(e.player)
    }

    val worldSaveEvent = DSLEvent.event<WorldSaveEvent> { e ->
        controller.saveAllPlayers()
    }

    val onPlayerLeave = DSLEvent.event<PlayerQuitEvent>{ e ->
        controller.savePlayer(e.player)
    }
    val onMove = DSLEvent.event<PlayerMoveEvent> { e ->
        if (controller.isPlayerLocked(e.player))
            e.isCancelled = true
    }

    val onDamage = DSLEvent.event<EntityDamageEvent> { e ->
        val player = e.entity as? Player ?: return@event
        if (controller.isPlayerLocked(player))
            e.isCancelled = true
    }

    val inventoryOpenEvent = DSLEvent.event<InventoryOpenEvent> { e ->
        val player = e.player as? Player ?: return@event
        if (controller.isPlayerLocked(player))
            e.isCancelled = true
    }

    val dropItemEvent = DSLEvent.event<PlayerDropItemEvent> { e ->
        if (controller.isPlayerLocked(e.player))
            e.isCancelled = true
    }

    val pickUpItemEvent = DSLEvent.event<EntityPickupItemEvent> { e ->
        val player = e.entity as? Player ?: return@event
        if (controller.isPlayerLocked(player))
            e.isCancelled = true
    }

    val onPlayerInteract = DSLEvent.event<PlayerInteractEvent> { e ->
        if (controller.isPlayerLocked(e.player))
            e.isCancelled = true
    }

    val onBlockPlace = DSLEvent.event<BlockPlaceEvent> { e ->
        if (controller.isPlayerLocked(e.player))
            e.isCancelled = true
    }

    val onBlockBreak = DSLEvent.event<BlockBreakEvent> { e ->
        if (controller.isPlayerLocked(e.player))
            e.isCancelled = true
    }

    val onInventoryClick = DSLEvent.event<InventoryClickEvent> { e ->
        val player = e.whoClicked as? Player ?: return@event
        if (controller.isPlayerLocked(player))
            e.isCancelled = true
    }

    val onPlayerDeath = DSLEvent.event<PlayerDeathEvent> { e ->
        if (controller.isPlayerLocked(e.player)) {
            e.isCancelled = true
            e.drops.clear()
        } else controller.savePlayer(e.player, LocalInventoryApi.TYPE.DEATH)
    }
}