package com.astrainteractive.synk.di.impl

import com.astrainteractive.synk.SynK
import com.astrainteractive.synk.api.mapping.BukkitPlayerMapper
import com.astrainteractive.synk.events.di.EventContainer
import com.astrainteractive.synk.shared.EventController
import ru.astrainteractive.astralibs.async.BukkitDispatchers
import ru.astrainteractive.astralibs.event.EventListener
import ru.astrainteractive.klibs.kdi.getValue

class EventContainerImpl(rootModuleImpl: RootModuleImpl) : EventContainer {
    override val controller: EventController = rootModuleImpl.sharedModule.eventController
    override val playerMapper: BukkitPlayerMapper = rootModuleImpl.apiLocalModule.bukkitPlayerMapper
    override val eventListener: EventListener by rootModuleImpl.pluginModule.eventListener
    override val plugin: SynK by rootModuleImpl.pluginModule.plugin
    override val dispatch: BukkitDispatchers by rootModuleImpl.pluginModule.bukkitDispatchers
}
