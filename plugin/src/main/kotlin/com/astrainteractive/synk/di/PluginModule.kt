package com.astrainteractive.synk.di

import com.astrainteractive.synk.SynK
import ru.astrainteractive.astralibs.serialization.KyoriComponentSerializer
import ru.astrainteractive.klibs.kdi.Lateinit
import ru.astrainteractive.klibs.kdi.Reloadable

interface PluginModule {
    val plugin: Lateinit<SynK>
    val kyoriComponentSerializer: Reloadable<KyoriComponentSerializer>

    class Default : PluginModule {
        override val plugin: Lateinit<SynK> = Lateinit<SynK>()
        override val kyoriComponentSerializer: Reloadable<KyoriComponentSerializer> = Reloadable {
            KyoriComponentSerializer.Legacy
        }
    }
}
