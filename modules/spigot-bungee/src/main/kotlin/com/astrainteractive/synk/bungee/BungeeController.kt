package com.astrainteractive.synk.bungee

import com.astrainteractive.synk.bungee.models.BungeeMessage
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class BungeeController(
    private val plugin: JavaPlugin,
    private val bungeeDecoder: BungeeDecoder
) {
    var serversAndPlayers: HashMap<String, Set<String>> = HashMap()
        private set
    var servers: HashSet<String> = HashSet()
    var currentServer: String? = null
        private set

    fun registerChannel(channel: BungeeMessage) {
        Bukkit.getServer().messenger.registerOutgoingPluginChannel(plugin, channel.value)
    }

    fun connectPlayerToServer(server: String, player: Player) {
        bungeeDecoder.sendBungeeMessage(
            BungeeMessage.CONNECT,
            server,
            player
        )
    }
}
