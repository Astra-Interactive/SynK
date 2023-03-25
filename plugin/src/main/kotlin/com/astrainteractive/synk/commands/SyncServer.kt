package com.astrainteractive.synk.commands

import CommandManager
import com.astrainteractive.synk.bungee.BungeeController
import com.astrainteractive.synk.di.ServiceLocator
import org.bukkit.entity.Player
import ru.astrainteractive.astralibs.AstraLibs
import ru.astrainteractive.astralibs.commands.registerCommand
import ru.astrainteractive.astralibs.commands.registerTabCompleter
import ru.astrainteractive.astralibs.di.getValue
import ru.astrainteractive.astralibs.utils.withEntry

fun CommandManager.tabCompleter() = AstraLibs.instance.registerTabCompleter("syncserver") {
    val config by ServiceLocator.configModule
    if (args.isEmpty()) {
        return@registerTabCompleter config.serverIDList.withEntry(args.last())
    }
    if (args.size == 1) {
        return@registerTabCompleter config.serverIDList.withEntry(args.last())
    }
    return@registerTabCompleter listOf<String>()
}

fun CommandManager.syncServer() = AstraLibs.instance.registerCommand("syncserver") {
    val translation by ServiceLocator.translationModule
    val eventController by ServiceLocator.eventController
    val bukkitPlayerMapper by ServiceLocator.bukkitPlayerMapper

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
            BungeeController.connectPlayerToServer(server, player)
        }
    )
}
