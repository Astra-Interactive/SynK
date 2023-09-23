package com.astrainteractive.synk.di.impl

import com.astrainteractive.synk.SynK
import com.astrainteractive.synk.api.mapping.BukkitPlayerMapper
import com.astrainteractive.synk.di.RootModuleImpl
import com.astrainteractive.synk.events.di.EventModule
import com.astrainteractive.synk.shared.EventController
import ru.astrainteractive.astralibs.async.BukkitDispatchers
import ru.astrainteractive.astralibs.event.EventListener
import ru.astrainteractive.klibs.kdi.getValue

class EventModuleImpl(rootModuleImpl: RootModuleImpl) : EventModule {
    override val controller: EventController by rootModuleImpl.eventController
    override val playerMapper: BukkitPlayerMapper by rootModuleImpl.bukkitPlayerMapper
    override val eventListener: EventListener by rootModuleImpl.rootEventListener
    override val plugin: SynK by rootModuleImpl.plugin
    override val dispatch: BukkitDispatchers by rootModuleImpl.bukkitDispatchers
}
