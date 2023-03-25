package com.astrainteractive.synk.commands

import CommandManager
import com.astrainteractive.synk.SynK
import com.astrainteractive.synk.di.ServiceLocator
import ru.astrainteractive.astralibs.AstraLibs
import ru.astrainteractive.astralibs.commands.registerCommand
import ru.astrainteractive.astralibs.di.getValue

/**
 * Reload command handler
 */

/**
 * This function called only when atempreload being called
 *
 * Here you should also check for permission
 */
fun CommandManager.reload() = AstraLibs.instance.registerCommand("atempreload") {
    val translation by ServiceLocator.translationModule
    sender.sendMessage(translation.reload)
    SynK.instance.reloadPlugin()
    sender.sendMessage(translation.reloadComplete)
}
