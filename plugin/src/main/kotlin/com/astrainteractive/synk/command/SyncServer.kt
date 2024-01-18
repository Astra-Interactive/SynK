package com.astrainteractive.synk.command

import CommandManager
import org.bukkit.entity.Player
import ru.astrainteractive.astralibs.util.StringListExt.withEntry

fun CommandManager.tabCompleter() = plugin.getCommand("syncserver")?.setTabCompleter { sender, command, label, args ->
    if (args.isEmpty()) {
        return@setTabCompleter config.serverIDList.withEntry(args.last())
    }
    if (args.size == 1) {
        return@setTabCompleter config.serverIDList.withEntry(args.last())
    }
    return@setTabCompleter listOf<String>()
}

fun CommandManager.syncServer() = plugin.getCommand("syncserver")?.setExecutor { sender, command, label, args ->
    translation.pleaseWait.let(kyoriComponentSerializer::toComponent).run(sender::sendMessage)
    translation.inventoryLossWarning.let(kyoriComponentSerializer::toComponent).run(sender::sendMessage)
    val player = sender as? Player
    player ?: run {
        translation.onlyPlayerCommand.let(kyoriComponentSerializer::toComponent).run(sender::sendMessage)
        return@setExecutor true
    }
    val server = args.getOrNull(0) ?: run {
        translation.inputServerName.let(kyoriComponentSerializer::toComponent).run(sender::sendMessage)
        return@setExecutor true
    }
    eventController.changeServer(
        player = bukkitPlayerMapper.toDTO(player),
        onUpdated = {
            bungeeController.connectPlayerToServer(server, player)
        }
    )
    true
}
