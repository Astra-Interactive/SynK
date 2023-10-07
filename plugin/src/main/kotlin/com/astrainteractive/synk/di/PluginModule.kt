package com.astrainteractive.synk.di

import com.astrainteractive.synk.SynK
import com.astrainteractive.synk.models.config.PluginConfig
import com.astrainteractive.synk.plugin.PluginTranslation
import ru.astrainteractive.astralibs.async.BukkitDispatchers
import ru.astrainteractive.astralibs.encoding.Serializer
import ru.astrainteractive.astralibs.event.EventListener
import ru.astrainteractive.klibs.kdi.Lateinit
import ru.astrainteractive.klibs.kdi.Reloadable
import ru.astrainteractive.klibs.kdi.Single

interface PluginModule {
    val plugin: Lateinit<SynK>
    val pluginConfig: Reloadable<PluginConfig>
    val bukkitDispatchers: Single<BukkitDispatchers>
    val eventListener: Single<EventListener>
    val translation: Reloadable<PluginTranslation>
    val serializer: Single<Serializer>
}
