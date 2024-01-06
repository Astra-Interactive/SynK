package com.astrainteractive.synk.command

import CommandManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import ru.astrainteractive.astralibs.command.registerCommand
import ru.astrainteractive.astralibs.permission.BukkitPermissibleExt.toPermissible
import ru.astrainteractive.synk.core.SynkPermission

@Suppress("UnusedPrivateMember")
fun CommandManager.history() = plugin.registerCommand("history") {
    if (!sender.toPermissible().hasPermission(SynkPermission.History)) {
        sender.sendMessage("No permission")
        return@registerCommand
    }
    val playerSender = sender as? Player ?: run {
        sender.sendMessage("Player only command")
        return@registerCommand
    }
    val player = argument(0) { it?.let(Bukkit::getPlayer) }.onFailure {
        sender.sendMessage("${ChatColor.RED}Wrong usage! /history <player>")
    }.successOrNull()?.value ?: return@registerCommand

    scope.launch(Dispatchers.IO) {
        TODO()
    }
}
