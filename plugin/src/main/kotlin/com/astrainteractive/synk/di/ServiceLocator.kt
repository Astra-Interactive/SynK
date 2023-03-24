package com.astrainteractive.synk.di

import com.astrainteractive.synk.api.SpigotLocalPlayerDataSource
import com.astrainteractive.synk.api.remote.RemoteApiImpl
import com.astrainteractive.synk.di.factories.DatabaseFactory
import com.astrainteractive.synk.models.config.PluginConfig
import com.astrainteractive.synk.utils.Locker
import com.astrainteractive.synk.plugin.PluginTranslation
import ru.astrainteractive.astralibs.EmpireSerializer
import ru.astrainteractive.astralibs.di.getValue
import ru.astrainteractive.astralibs.di.module
import ru.astrainteractive.astralibs.di.reloadable
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
        DatabaseFactory(config)
    }
    val RemoteDataSourceModule = module {
        val database by databaseModule
        RemoteApiImpl()
    }
    val LocalDataSourceModule = module {
        SpigotLocalPlayerDataSource() as SpigotLocalPlayerDataSource
    }
    val uuidLockerModule = module {
        Locker<UUID>()
    }
}
