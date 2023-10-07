package com.astrainteractive.synk.di.impl

import com.astrainteractive.synk.api.di.BukkitApiLocalModule
import com.astrainteractive.synk.api.remote.di.ApiRemoteModule
import com.astrainteractive.synk.bungee.di.SpigotBungeeModule
import com.astrainteractive.synk.di.PluginModule
import com.astrainteractive.synk.di.RootModule
import com.astrainteractive.synk.di.SharedModule
import ru.astrainteractive.klibs.kdi.Provider
import ru.astrainteractive.klibs.kdi.Single
import ru.astrainteractive.klibs.kdi.getValue

class RootModuleImpl : RootModule {

    override val pluginModule: PluginModule by Single {
        PluginModuleImpl()
    }
    override val apiLocalModule: BukkitApiLocalModule by Single {
        BukkitApiLocalModule.Default(
            serializer = pluginModule.serializer.value,
            plugin = pluginModule.plugin.value
        )
    }
    override val apiRemoteModule: ApiRemoteModule by Single {
        ApiRemoteModule.Default(
            config = pluginModule.pluginConfig.value
        )
    }
    override val sharedModule: SharedModule by Single {
        SharedModule.Default(
            remoteApi = apiRemoteModule.remoteApi,
            localInventoryApi = apiLocalModule.localPlayerDataSource
        )
    }

    override val spigotBungeeModule: SpigotBungeeModule by Single {
        SpigotBungeeModule.Default(
            plugin = pluginModule.plugin.value
        )
    }

    override val eventContainer by Provider {
        EventContainerImpl(this)
    }
    override val commandContainer by Provider {
        CommandContainerImpl(this)
    }
}
