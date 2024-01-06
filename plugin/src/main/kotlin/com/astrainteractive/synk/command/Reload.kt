package com.astrainteractive.synk.command

import CommandManager
import ru.astrainteractive.astralibs.command.registerCommand

/**
 * Reload command handler
 */

/**
 * This function called only when atempreload being called
 *
 * Here you should also check for permission
 */
fun CommandManager.reload() = plugin.registerCommand("atempreload") {
    sender.sendMessage(translation.reload)
    plugin.reloadPlugin()
    sender.sendMessage(translation.reloadComplete)
}
