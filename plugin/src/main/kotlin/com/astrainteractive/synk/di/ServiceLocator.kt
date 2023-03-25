package com.astrainteractive.synk.di

import com.astrainteractive.synk.api.SpigotLocalPlayerDataSource
import com.astrainteractive.synk.api.local.LocalInventoryApi
import com.astrainteractive.synk.api.mapping.BukkitPlayerMapper
import com.astrainteractive.synk.api.remote.RemoteApi
import com.astrainteractive.synk.api.remote.RemoteApiImpl
import com.astrainteractive.synk.di.factories.DatabaseFactory
import com.astrainteractive.synk.models.config.PluginConfig
import com.astrainteractive.synk.plugin.PluginTranslation
import com.astrainteractive.synk.shared.EventController
import com.astrainteractive.synk.utils.ConcurrentHashMapLocker
import org.bukkit.inventory.ItemStack
import ru.astrainteractive.astralibs.EmpireSerializer
import ru.astrainteractive.astralibs.di.getValue
import ru.astrainteractive.astralibs.di.module
import ru.astrainteractive.astralibs.di.reloadable
import ru.astrainteractive.astralibs.utils.encoding.BukkitIOStreamProvider
import ru.astrainteractive.astralibs.utils.encoding.Serializer
import java.util.*

object ServiceLocator {
    val configModule = reloadable {
        EmpireSerializer.toClass<PluginConfig>(Files.configFile.configFile)!!
    }
    val translationModule = reloadable {
        PluginTranslation()
    }
    val databaseModule = module {
        val config by configModule
        DatabaseFactory(config).value
    }
    val RemoteDataSourceModule = module {
        val database by databaseModule
        RemoteApiImpl(database) as RemoteApi
    }
    val serializer = module {
        Serializer(BukkitIOStreamProvider)
    }
    val bukkitPlayerMapper = module {
        val serializer by serializer
        BukkitPlayerMapper(serializer)
    }
    val LocalDataSourceModule = module {
        val bukkitPlayerMapper by bukkitPlayerMapper
        SpigotLocalPlayerDataSource(bukkitPlayerMapper) as LocalInventoryApi<ItemStack>
    }
    val uuidLockerModule = module {
        ConcurrentHashMapLocker<UUID>()
    }

    val eventController = module {
        val locker by uuidLockerModule
        val remoteDataSource by RemoteDataSourceModule
        val localDataSource by LocalDataSourceModule
        EventController(
            locker = locker,
            sqlDataSource = remoteDataSource,
            localDataSource = localDataSource
        )
    }
}
