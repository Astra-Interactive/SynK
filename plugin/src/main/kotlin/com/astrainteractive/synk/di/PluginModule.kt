package com.astrainteractive.synk.di

import com.astrainteractive.synk.SynK
import ru.astrainteractive.astralibs.event.EventListener
import ru.astrainteractive.klibs.kdi.Lateinit
import ru.astrainteractive.klibs.kdi.Single

interface PluginModule {
    val plugin: Lateinit<SynK>
    val eventListener: Single<EventListener>
    class Default : PluginModule {
        override val plugin: Lateinit<SynK> = Lateinit<SynK>()

        override val eventListener: Single<EventListener> = Single<EventListener> {
            EventListener.Default()
        }
    }
}
