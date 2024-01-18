package com.astrainteractive.synk.command

import CommandManager

/**
 * Reload command handler
 */

/**
 * This function called only when atempreload being called
 *
 * Here you should also check for permission
 */
fun CommandManager.reload() = plugin.getCommand("atempreload")?.setExecutor { sender, command, label, args ->
    translation.reload.let(kyoriComponentSerializer::toComponent).run(sender::sendMessage)
    plugin.reloadPlugin()
    translation.reloadComplete.let(kyoriComponentSerializer::toComponent).run(sender::sendMessage)
    true
}
