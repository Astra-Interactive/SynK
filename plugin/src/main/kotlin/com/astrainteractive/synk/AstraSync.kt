package com.astrainteractive.synk

import CommandManager
import com.astrainteractive.synk.api.messaging.BungeeController
import com.astrainteractive.synk.api.messaging.models.BungeeMessage
import com.astrainteractive.synk.events.EventController
import com.astrainteractive.synk.events.EventHandler
import com.astrainteractive.synk.di.Files
import com.astrainteractive.synk.di.ServiceLocator
import kotlinx.coroutines.runBlocking
import org.bukkit.event.HandlerList
import org.bukkit.plugin.java.JavaPlugin
import ru.astrainteractive.astralibs.AstraLibs
import ru.astrainteractive.astralibs.Logger
import ru.astrainteractive.astralibs.events.GlobalEventListener

/**
 * Initial class for your plugin
 */
class AstraSync : JavaPlugin() {
    companion object {
        lateinit var instance: AstraSync
    }

    init {
        instance = this
    }


    /**
     * This method called when server starts or PlugMan load plugin.
     */
    override fun onEnable() {
        AstraLibs.rememberPlugin(this)
        Logger.prefix = "AstraSync"
        reloadPlugin()
        ServiceLocator.databaseModule.value
        EventHandler()
        CommandManager()
        BungeeController.registerChannel(BungeeMessage.BUNGEE_CHANNEL)
    }

    /**
     * This method called when server is shutting down or when PlugMan disable plugin.
     */
    override fun onDisable() {
        runBlocking { EventController.saveAllPlayers() }
        HandlerList.unregisterAll(this)
        GlobalEventListener.onDisable()

    }

    /**
     * As it says, function for plugin reload
     */
    fun reloadPlugin() {
        Files.configFile.reload()
        ServiceLocator.translationModule.reload()
        ServiceLocator.configModule.reload()
    }

}


