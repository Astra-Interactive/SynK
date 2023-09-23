package com.astrainteractive.synk.bungee

import com.astrainteractive.synk.bungee.models.BungeeMessage
import com.google.common.io.ByteArrayDataOutput
import com.google.common.io.ByteStreams
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.plugin.messaging.PluginMessageRecipient

class BungeeDecoder(private val plugin: JavaPlugin) {

    private val anyPlayerMessageRecipient: PluginMessageRecipient?
        get() = Bukkit.getOnlinePlayers().firstOrNull()

    private val serverMessageRecipient: PluginMessageRecipient
        get() = Bukkit.getServer()

    private val defaultMessageRecipient: PluginMessageRecipient
        get() = anyPlayerMessageRecipient ?: serverMessageRecipient

    private fun createByteOutputArray(action: String, message: String? = null): ByteArrayDataOutput {
        return ByteStreams.newDataOutput().apply {
            action.split(" ").forEach(::writeUTF)
            message?.let(::writeUTF)
        }
    }

    fun sendBungeeMessage(
        action: BungeeMessage,
        message: String? = null,
        sender: PluginMessageRecipient = defaultMessageRecipient,
    ) {
        val out = createByteOutputArray(action.value, message)
        sender.sendPluginMessage(plugin, BungeeMessage.BUNGEE_CHANNEL.value, out.toByteArray())
    }
}
