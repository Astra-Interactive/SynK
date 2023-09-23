package com.astrainteractive.synk.di

import CommandManager
import com.astrainteractive.synk.SynK
import com.astrainteractive.synk.api.SpigotLocalPlayerDataSource
import com.astrainteractive.synk.api.local.LocalInventoryApi
import com.astrainteractive.synk.api.mapping.BukkitPlayerMapper
import com.astrainteractive.synk.api.remote.RemoteApi
import com.astrainteractive.synk.api.remote.RemoteApiImpl
import com.astrainteractive.synk.bungee.BungeeController
import com.astrainteractive.synk.bungee.BungeeDecoder
import com.astrainteractive.synk.di.factories.DatabaseFactory
import com.astrainteractive.synk.di.impl.CommandModuleImpl
import com.astrainteractive.synk.di.impl.EventModuleImpl
import com.astrainteractive.synk.events.EventHandler
import com.astrainteractive.synk.models.config.PluginConfig
import com.astrainteractive.synk.plugin.PluginTranslation
import com.astrainteractive.synk.shared.EventController
import com.astrainteractive.synk.utils.ConcurrentHashMapLocker
import org.bukkit.inventory.ItemStack
import org.jetbrains.kotlin.tooling.core.UnsafeApi
import ru.astrainteractive.astralibs.async.DefaultBukkitDispatchers
import ru.astrainteractive.astralibs.configloader.ConfigLoader
import ru.astrainteractive.astralibs.encoding.BukkitIOStreamProvider
import ru.astrainteractive.astralibs.encoding.Serializer
import ru.astrainteractive.astralibs.event.EventListener
import ru.astrainteractive.astralibs.event.GlobalEventListener
import ru.astrainteractive.astralibs.filemanager.DefaultSpigotFileManager
import ru.astrainteractive.klibs.kdi.Lateinit
import ru.astrainteractive.klibs.kdi.Provider
import ru.astrainteractive.klibs.kdi.Reloadable
import ru.astrainteractive.klibs.kdi.Single
import ru.astrainteractive.klibs.kdi.getValue
import java.util.UUID

object RootModuleImpl {
    val plugin = Lateinit<SynK>()

    val configFile = Single {
        DefaultSpigotFileManager(plugin.value, "config.yml")
    }

    val configModule = Reloadable {
        ConfigLoader().unsafeParse<PluginConfig>(configFile.value.configFile)
    }

    val bukkitDispatchers = Single {
        DefaultBukkitDispatchers(plugin.value)
    }

    @OptIn(UnsafeApi::class)
    val rootEventListener = Single<EventListener> {
        GlobalEventListener
    }

    val translationModule = Reloadable {
        PluginTranslation(plugin.value)
    }

    val databaseModule = Single {
        val config by configModule
        DatabaseFactory(config).create()
    }

    val RemoteDataSourceModule = Single {
        val database by databaseModule
        RemoteApiImpl(database) as RemoteApi
    }

    val serializer = Single {
        Serializer(BukkitIOStreamProvider)
    }

    val bukkitPlayerMapper = Single {
        val serializer by serializer
        BukkitPlayerMapper(serializer)
    }

    val LocalDataSourceModule = Single {
        val bukkitPlayerMapper by bukkitPlayerMapper
        SpigotLocalPlayerDataSource(plugin.value, bukkitPlayerMapper) as LocalInventoryApi<ItemStack>
    }

    val uuidLockerModule = Single {
        ConcurrentHashMapLocker<UUID>()
    }

    val eventController = Single {
        val locker by uuidLockerModule
        val remoteDataSource by RemoteDataSourceModule
        val localDataSource by LocalDataSourceModule
        EventController(
            locker = locker,
            sqlDataSource = remoteDataSource,
            localDataSource = localDataSource
        )
    }

    val bungeeDecoder = Single {
        BungeeDecoder(plugin.value)
    }

    val bungeeController = Single {
        BungeeController(
            plugin = plugin.value,
            bungeeDecoder = bungeeDecoder.value
        )
    }

    private val eventModule by Provider {
        EventModuleImpl(this)
    }
    private val commandModule by Provider {
        CommandModuleImpl(this)
    }
    val eventHandler = Single {
        EventHandler(eventModule)
    }
    val commandManager = Single {
        CommandManager(commandModule)
    }
}
