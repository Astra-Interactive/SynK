package com.astrainteractive.synk.events.di

import com.astrainteractive.synk.SynK
import com.astrainteractive.synk.api.mapping.BukkitPlayerMapper
import com.astrainteractive.synk.shared.EventController
import ru.astrainteractive.astralibs.async.BukkitDispatchers
import ru.astrainteractive.astralibs.event.EventListener

interface EventContainer {
    val controller: EventController
    val playerMapper: BukkitPlayerMapper
    val eventListener: EventListener
    val plugin: SynK
    val dispatch: BukkitDispatchers
}
