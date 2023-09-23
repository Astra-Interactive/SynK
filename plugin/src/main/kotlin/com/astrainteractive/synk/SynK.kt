package com.astrainteractive.synk

import com.astrainteractive.synk.bungee.models.BungeeMessage
import com.astrainteractive.synk.di.RootModuleImpl
import kotlinx.coroutines.runBlocking
import org.bukkit.Bukkit
import org.bukkit.event.HandlerList
import org.bukkit.plugin.java.JavaPlugin

/**
 * Initial class for your plugin
 */
class SynK : JavaPlugin() {
    private val rootModule = RootModuleImpl

    /**
     * This method called when server starts or PlugMan load plugin.
     */
    override fun onEnable() {
        reloadPlugin()
        rootModule.databaseModule.value
        rootModule.eventHandler.value
        rootModule.commandManager.value
        rootModule.bungeeController.value.registerChannel(BungeeMessage.BUNGEE_CHANNEL)
    }

    /**
     * This method called when server is shutting down or when PlugMan disable plugin.
     */
    override fun onDisable() {
        runBlocking {
            val players = Bukkit.getOnlinePlayers().map(rootModule.bukkitPlayerMapper.value::toDTO)
            rootModule.eventController.value.saveAllPlayers(players)
        }
        HandlerList.unregisterAll(this)
        rootModule.rootEventListener.value.onDisable()
    }

    /**
     * As it says, function for plugin reload
     */
    fun reloadPlugin() {
        rootModule.translationModule.reload()
        rootModule.configModule.reload()
    }
}
