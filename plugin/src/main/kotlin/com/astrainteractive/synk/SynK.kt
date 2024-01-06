package com.astrainteractive.synk

import CommandManager
import com.astrainteractive.synk.bungee.model.BungeeMessage
import com.astrainteractive.synk.command.di.CommandDependencies
import com.astrainteractive.synk.di.RootModule
import kotlinx.coroutines.runBlocking
import org.bukkit.Bukkit
import org.bukkit.event.HandlerList
import org.bukkit.plugin.java.JavaPlugin
import ru.astrainteractive.astralibs.lifecycle.Lifecycle

/**
 * Initial class for your plugin
 */
class SynK : JavaPlugin(), Lifecycle {
    private val rootModule = RootModule.Default()
    private val lifecycles: List<Lifecycle>
        get() = listOf(
            rootModule.coreModule.lifecycle,
            rootModule.apiRemoteModule.lifecycle,
            rootModule.eventModule.lifecycle,
            rootModule.commandModule.lifecycle
        )

    /**
     * This method called when server starts or PlugMan load plugin.
     */
    override fun onEnable() {
        reloadPlugin()
        CommandManager(CommandDependencies.Default(rootModule))
        rootModule.spigotBungeeModule.bungeeController.value.registerChannel(BungeeMessage.BUNGEE_CHANNEL)
        lifecycles.forEach(Lifecycle::onEnable)
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
        lifecycles.forEach(Lifecycle::onDisable)
    }

    /**
     * As it says, function for plugin reload
     */
    fun reloadPlugin() {
        lifecycles.forEach(Lifecycle::onReload)
    }
}
