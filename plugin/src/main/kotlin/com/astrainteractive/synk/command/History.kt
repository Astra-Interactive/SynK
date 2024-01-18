package com.astrainteractive.synk.command

import CommandManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import ru.astrainteractive.astralibs.command.type.OnlinePlayerArgument
import ru.astrainteractive.astralibs.permission.BukkitPermissibleExt.toPermissible
import ru.astrainteractive.synk.core.SynkPermission

@Suppress("UnusedPrivateMember")
fun CommandManager.history() = plugin.getCommand("history")?.setExecutor { sender, command, label, args ->
    if (!sender.toPermissible().hasPermission(SynkPermission.History)) {
        sender.sendMessage("No permission")
        return@setExecutor true
    }
    val playerSender = sender as? Player ?: run {
        sender.sendMessage("Player only command")
        return@setExecutor true
    }
    val player = args.getOrNull(0).let(OnlinePlayerArgument::transform) ?: run {
        sender.sendMessage("${ChatColor.RED}Wrong usage! /history <player>")
        return@setExecutor true
    }

    scope.launch(Dispatchers.IO) {
        TODO()
    }
    true
}
