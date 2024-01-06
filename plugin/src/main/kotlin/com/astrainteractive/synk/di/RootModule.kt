package com.astrainteractive.synk.di

import com.astrainteractive.synk.api.local.di.BukkitApiLocalModule
import com.astrainteractive.synk.api.remote.di.ApiRemoteModule
import com.astrainteractive.synk.bungee.di.SpigotBungeeModule
import com.astrainteractive.synk.events.di.EventModule
import ru.astrainteractive.astralibs.async.DefaultBukkitDispatchers
import ru.astrainteractive.astralibs.encoding.BukkitIOStreamProvider
import ru.astrainteractive.astralibs.encoding.Encoder
import ru.astrainteractive.synk.core.di.CoreModule

interface RootModule {
    val pluginModule: PluginModule
    val apiLocalModule: BukkitApiLocalModule
    val apiRemoteModule: ApiRemoteModule
    val sharedModule: SharedModule
    val spigotBungeeModule: SpigotBungeeModule
    val coreModule: CoreModule
    val eventModule: EventModule
    class Default : RootModule {
        override val pluginModule: PluginModule by lazy {
            PluginModule.Default()
        }
        override val coreModule: CoreModule by lazy {
            CoreModule.Default(
                dataFolder = pluginModule.plugin.value.dataFolder,
                dispatchers = DefaultBukkitDispatchers(pluginModule.plugin.value),
                encoder = Encoder(BukkitIOStreamProvider)
            )
        }

        override val apiLocalModule: BukkitApiLocalModule by lazy {
            BukkitApiLocalModule.Default(
                encoder = coreModule.encoder,
                plugin = pluginModule.plugin.value
            )
        }
        override val apiRemoteModule: ApiRemoteModule by lazy {
            ApiRemoteModule.Default(configProvider = { coreModule.configurationModule.value.mysql })
        }
        override val sharedModule: SharedModule by lazy {
            SharedModule.Default(
                remoteApi = apiRemoteModule.remoteApi,
                localInventoryApi = apiLocalModule.localPlayerDataSource,
                dispatchers = DefaultBukkitDispatchers(pluginModule.plugin.value)
            )
        }

        override val spigotBungeeModule: SpigotBungeeModule by lazy {
            SpigotBungeeModule.Default(
                plugin = pluginModule.plugin.value
            )
        }
        override val eventModule: EventModule by lazy {
            EventModule.Default(this)
        }
    }
}
