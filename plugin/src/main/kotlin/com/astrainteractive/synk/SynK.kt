package com.astrainteractive.synk

import CommandManager
import com.astrainteractive.synk.bungee.BungeeController
import com.astrainteractive.synk.bungee.models.BungeeMessage
import com.astrainteractive.synk.di.Files
import com.astrainteractive.synk.di.ServiceLocator
import com.astrainteractive.synk.events.EventHandler
import kotlinx.coroutines.runBlocking
import org.bukkit.Bukkit
import org.bukkit.event.HandlerList
import org.bukkit.plugin.java.JavaPlugin
import ru.astrainteractive.astralibs.AstraLibs
import ru.astrainteractive.astralibs.Logger
import ru.astrainteractive.astralibs.di.getValue
import ru.astrainteractive.astralibs.events.GlobalEventListener

/**
 * Initial class for your plugin
 */
class SynK : JavaPlugin() {
    companion object {
        lateinit var instance: SynK
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
        runBlocking {
            val eventController by ServiceLocator.eventController
            val bukkitPlayerManager by ServiceLocator.bukkitPlayerMapper
            val players = Bukkit.getOnlinePlayers().map(bukkitPlayerManager::toDTO)
            eventController.saveAllPlayers(players)
        }
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
