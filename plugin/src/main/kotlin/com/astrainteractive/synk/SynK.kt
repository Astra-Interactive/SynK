package com.astrainteractive.synk

import CommandManager
import com.astrainteractive.synk.bungee.models.BungeeMessage
import com.astrainteractive.synk.di.impl.CommandContainerImpl
import com.astrainteractive.synk.di.impl.EventContainerImpl
import com.astrainteractive.synk.di.impl.RootModuleImpl
import com.astrainteractive.synk.events.EventHandler
import kotlinx.coroutines.runBlocking
import org.bukkit.Bukkit
import org.bukkit.event.HandlerList
import org.bukkit.plugin.java.JavaPlugin

/**
 * Initial class for your plugin
 */
class SynK : JavaPlugin() {
    private val rootModule = RootModuleImpl()

    /**
     * This method called when server starts or PlugMan load plugin.
     */
    override fun onEnable() {
        reloadPlugin()
        EventHandler(EventContainerImpl(rootModule))
        CommandManager(CommandContainerImpl(rootModule))
        rootModule.spigotBungeeModule.bungeeController.value.registerChannel(BungeeMessage.BUNGEE_CHANNEL)
    }

    /**
     * This method called when server is shutting down or when PlugMan disable plugin.
     */
    override fun onDisable() {
        runBlocking {
            val players = Bukkit.getOnlinePlayers().map(rootModule.apiLocalModule.bukkitPlayerMapper::toDTO)

            rootModule.sharedModule.eventController.saveAllPlayers(players)
        }
        HandlerList.unregisterAll(this)
        rootModule.pluginModule.eventListener.value.onDisable()
    }

    /**
     * As it says, function for plugin reload
     */
    fun reloadPlugin() {
        with(rootModule.pluginModule) {
            translation.reload()
            pluginConfig.reload()
        }
    }
}
