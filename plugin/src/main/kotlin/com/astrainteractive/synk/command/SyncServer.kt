package com.astrainteractive.synk.command

import CommandManager
import org.bukkit.entity.Player
import ru.astrainteractive.astralibs.command.registerCommand
import ru.astrainteractive.astralibs.command.registerTabCompleter
import ru.astrainteractive.astralibs.util.withEntry

fun CommandManager.tabCompleter() = plugin.registerTabCompleter("syncserver") {
    if (args.isEmpty()) {
        return@registerTabCompleter config.serverIDList.withEntry(args.last())
    }
    if (args.size == 1) {
        return@registerTabCompleter config.serverIDList.withEntry(args.last())
    }
    return@registerTabCompleter listOf<String>()
}

fun CommandManager.syncServer() = plugin.registerCommand("syncserver") {
    sender.sendMessage(translation.pleaseWait)
    sender.sendMessage(translation.inventoryLossWarning)
    val player = sender as? Player
    player ?: run {
        sender.sendMessage(translation.onlyPlayerCommand)
        return@registerCommand
    }
    val server = args.getOrNull(0) ?: run {
        sender.sendMessage(translation.inputServerName)
        return@registerCommand
    }
    eventController.changeServer(
        player = bukkitPlayerMapper.toDTO(player),
        onUpdated = {
            bungeeController.connectPlayerToServer(server, player)
        }
    )
}
