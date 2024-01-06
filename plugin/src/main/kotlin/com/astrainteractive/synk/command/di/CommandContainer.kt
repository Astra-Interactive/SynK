package com.astrainteractive.synk.command.di

import com.astrainteractive.synk.SynK
import com.astrainteractive.synk.api.local.mapping.BukkitPlayerMapper
import com.astrainteractive.synk.bungee.BungeeController
import com.astrainteractive.synk.di.RootModule
import com.astrainteractive.synk.shared.EventController
import kotlinx.coroutines.CoroutineScope
import ru.astrainteractive.klibs.kdi.getValue
import ru.astrainteractive.synk.core.PluginConfig
import ru.astrainteractive.synk.core.PluginTranslation

interface CommandContainer {
    val plugin: SynK
    val translation: PluginTranslation
    val config: PluginConfig
    val eventController: EventController
    val bukkitPlayerMapper: BukkitPlayerMapper
    val bungeeController: BungeeController
    val scope: CoroutineScope

    class Default(rootModule: RootModule) : CommandContainer {
        override val plugin: SynK by rootModule.pluginModule.plugin
        override val translation: PluginTranslation by rootModule.coreModule.translation
        override val config: PluginConfig by rootModule.coreModule.configurationModule
        override val eventController: EventController = rootModule.sharedModule.eventController
        override val bukkitPlayerMapper: BukkitPlayerMapper = rootModule.apiLocalModule.bukkitPlayerMapper
        override val bungeeController: BungeeController by rootModule.spigotBungeeModule.bungeeController
        override val scope: CoroutineScope = rootModule.coreModule.pluginScope
    }
}
