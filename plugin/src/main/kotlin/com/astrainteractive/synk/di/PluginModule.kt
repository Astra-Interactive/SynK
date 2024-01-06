package com.astrainteractive.synk.di

import com.astrainteractive.synk.SynK
import ru.astrainteractive.klibs.kdi.Lateinit

interface PluginModule {
    val plugin: Lateinit<SynK>
    class Default : PluginModule {
        override val plugin: Lateinit<SynK> = Lateinit<SynK>()
    }
}
