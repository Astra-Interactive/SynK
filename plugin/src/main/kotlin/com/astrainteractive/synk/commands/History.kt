package com.astrainteractive.synk.commands

import CommandManager
import com.astrainteractive.synk.plugin.SynkPermission
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import ru.astrainteractive.astralibs.AstraLibs
import ru.astrainteractive.astralibs.async.PluginScope
import ru.astrainteractive.astralibs.commands.registerCommand

fun CommandManager.history() = AstraLibs.instance.registerCommand("history") {
    if (!SynkPermission.History.hasPermission(sender)) {
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

    PluginScope.launch(Dispatchers.IO) {
        TODO()
    }
}