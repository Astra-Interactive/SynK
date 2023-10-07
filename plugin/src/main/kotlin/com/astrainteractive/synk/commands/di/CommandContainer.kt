package com.astrainteractive.synk.commands.di

import com.astrainteractive.synk.SynK
import com.astrainteractive.synk.api.mapping.BukkitPlayerMapper
import com.astrainteractive.synk.bungee.BungeeController
import com.astrainteractive.synk.models.config.PluginConfig
import com.astrainteractive.synk.plugin.PluginTranslation
import com.astrainteractive.synk.shared.EventController

interface CommandContainer {
    val plugin: SynK
    val translation: PluginTranslation
    val config: PluginConfig
    val eventController: EventController
    val bukkitPlayerMapper: BukkitPlayerMapper
    val bungeeController: BungeeController
}
