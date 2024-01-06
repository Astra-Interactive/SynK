package com.astrainteractive.synk.event.di

import com.astrainteractive.synk.di.RootModule
import com.astrainteractive.synk.event.CommonEvents
import ru.astrainteractive.astralibs.event.EventListener
import ru.astrainteractive.astralibs.lifecycle.Lifecycle
import ru.astrainteractive.klibs.kdi.Factory

interface EventModule {
    val lifecycle: Lifecycle
    val eventListener: EventListener

    class Default(rootModule: RootModule) : EventModule {
        override val eventListener: EventListener by lazy {
            EventListener.Default()
        }

        private val dependencies by lazy {
            EventDependencies.Default(rootModule, this)
        }

        private val commonEventsFactory = Factory {
            CommonEvents(dependencies)
        }

        override val lifecycle: Lifecycle by lazy {
            Lifecycle.Lambda(
                onEnable = {
                    eventListener.onEnable(rootModule.pluginModule.plugin.value)
                    commonEventsFactory.create()
                },
                onDisable = {
                    eventListener.onDisable()
                }
            )
        }
    }
}
