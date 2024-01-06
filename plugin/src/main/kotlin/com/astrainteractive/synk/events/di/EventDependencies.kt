package com.astrainteractive.synk.events.di

import com.astrainteractive.synk.SynK
import com.astrainteractive.synk.api.local.loader.PlayerLoader
import com.astrainteractive.synk.api.local.mapping.BukkitPlayerMapper
import com.astrainteractive.synk.di.RootModule
import com.astrainteractive.synk.shared.EventController
import ru.astrainteractive.astralibs.event.EventListener
import ru.astrainteractive.klibs.kdi.getValue
import ru.astrainteractive.klibs.mikro.core.dispatchers.KotlinDispatchers

interface EventDependencies {
    val controller: EventController
    val playerMapper: BukkitPlayerMapper
    val playerLoader: PlayerLoader
    val eventListener: EventListener
    val plugin: SynK
    val dispatch: KotlinDispatchers

    class Default(rootModule: RootModule, eventModule: EventModule) : EventDependencies {
        override val controller: EventController = rootModule.sharedModule.eventController
        override val playerMapper: BukkitPlayerMapper = rootModule.apiLocalModule.bukkitPlayerMapper
        override val eventListener: EventListener = eventModule.eventListener
        override val plugin: SynK by rootModule.pluginModule.plugin
        override val dispatch: KotlinDispatchers = rootModule.coreModule.dispatchers
        override val playerLoader: PlayerLoader = rootModule.apiLocalModule.playerLoader
    }
}
