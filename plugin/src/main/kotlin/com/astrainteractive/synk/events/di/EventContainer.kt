package com.astrainteractive.synk.events.di

import com.astrainteractive.synk.SynK
import com.astrainteractive.synk.api.local.mapping.BukkitPlayerMapper
import com.astrainteractive.synk.di.RootModule
import com.astrainteractive.synk.shared.EventController
import ru.astrainteractive.astralibs.event.EventListener
import ru.astrainteractive.klibs.kdi.getValue
import ru.astrainteractive.klibs.mikro.core.dispatchers.KotlinDispatchers

interface EventContainer {
    val controller: EventController
    val playerMapper: BukkitPlayerMapper
    val eventListener: EventListener
    val plugin: SynK
    val dispatch: KotlinDispatchers
    class Default(rootModuleImpl: RootModule) : EventContainer {
        override val controller: EventController = rootModuleImpl.sharedModule.eventController
        override val playerMapper: BukkitPlayerMapper = rootModuleImpl.apiLocalModule.bukkitPlayerMapper
        override val eventListener: EventListener by rootModuleImpl.pluginModule.eventListener
        override val plugin: SynK by rootModuleImpl.pluginModule.plugin
        override val dispatch: KotlinDispatchers = rootModuleImpl.coreModule.dispatchers
    }
}
