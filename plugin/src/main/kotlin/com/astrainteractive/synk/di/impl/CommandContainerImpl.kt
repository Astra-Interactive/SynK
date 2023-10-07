package com.astrainteractive.synk.di.impl

import com.astrainteractive.synk.SynK
import com.astrainteractive.synk.api.mapping.BukkitPlayerMapper
import com.astrainteractive.synk.bungee.BungeeController
import com.astrainteractive.synk.commands.di.CommandContainer
import com.astrainteractive.synk.di.RootModule
import com.astrainteractive.synk.models.config.PluginConfig
import com.astrainteractive.synk.plugin.PluginTranslation
import com.astrainteractive.synk.shared.EventController
import ru.astrainteractive.klibs.kdi.getValue

class CommandContainerImpl(rootModule: RootModule) : CommandContainer {
    override val plugin: SynK by rootModule.pluginModule.plugin
    override val translation: PluginTranslation by rootModule.pluginModule.translation
    override val config: PluginConfig by rootModule.pluginModule.pluginConfig
    override val eventController: EventController = rootModule.sharedModule.eventController
    override val bukkitPlayerMapper: BukkitPlayerMapper = rootModule.apiLocalModule.bukkitPlayerMapper
    override val bungeeController: BungeeController by rootModule.spigotBungeeModule.bungeeController
}
