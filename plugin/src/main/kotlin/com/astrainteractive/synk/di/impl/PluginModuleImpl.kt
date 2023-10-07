package com.astrainteractive.synk.di.impl

import com.astrainteractive.synk.SynK
import com.astrainteractive.synk.di.PluginModule
import com.astrainteractive.synk.models.config.PluginConfig
import com.astrainteractive.synk.plugin.PluginTranslation
import org.jetbrains.kotlin.tooling.core.UnsafeApi
import ru.astrainteractive.astralibs.async.BukkitDispatchers
import ru.astrainteractive.astralibs.async.DefaultBukkitDispatchers
import ru.astrainteractive.astralibs.configloader.ConfigLoader
import ru.astrainteractive.astralibs.encoding.BukkitIOStreamProvider
import ru.astrainteractive.astralibs.encoding.Serializer
import ru.astrainteractive.astralibs.event.EventListener
import ru.astrainteractive.astralibs.event.GlobalEventListener
import ru.astrainteractive.astralibs.filemanager.DefaultSpigotFileManager
import ru.astrainteractive.klibs.kdi.Lateinit
import ru.astrainteractive.klibs.kdi.Reloadable
import ru.astrainteractive.klibs.kdi.Single

class PluginModuleImpl : PluginModule {
    override val plugin: Lateinit<SynK> = Lateinit<SynK>()

    override val pluginConfig: Reloadable<PluginConfig> = Reloadable {
        val configFile = DefaultSpigotFileManager(plugin.value, "config.yml")
        ConfigLoader().unsafeParse<PluginConfig>(configFile.configFile)
    }
    override val bukkitDispatchers: Single<BukkitDispatchers> = Single {
        DefaultBukkitDispatchers(plugin.value)
    }

    @OptIn(UnsafeApi::class)
    override val eventListener: Single<EventListener> = Single<EventListener> {
        GlobalEventListener
    }
    override val translation: Reloadable<PluginTranslation> = Reloadable {
        PluginTranslation(plugin.value)
    }
    override val serializer: Single<Serializer> = Single {
        Serializer(BukkitIOStreamProvider)
    }
}
