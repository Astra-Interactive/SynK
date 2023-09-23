package com.astrainteractive.synk.di.impl

import com.astrainteractive.synk.SynK
import com.astrainteractive.synk.api.mapping.BukkitPlayerMapper
import com.astrainteractive.synk.bungee.BungeeController
import com.astrainteractive.synk.commands.di.CommandModule
import com.astrainteractive.synk.di.RootModuleImpl
import com.astrainteractive.synk.models.config.PluginConfig
import com.astrainteractive.synk.plugin.PluginTranslation
import com.astrainteractive.synk.shared.EventController
import ru.astrainteractive.klibs.kdi.getValue

class CommandModuleImpl(rootModule: RootModuleImpl) : CommandModule {
    override val plugin: SynK by rootModule.plugin
    override val translation: PluginTranslation by rootModule.translationModule
    override val config: PluginConfig by rootModule.configModule
    override val eventController: EventController by rootModule.eventController
    override val bukkitPlayerMapper: BukkitPlayerMapper by rootModule.bukkitPlayerMapper
    override val bungeeController: BungeeController by rootModule.bungeeController
}
