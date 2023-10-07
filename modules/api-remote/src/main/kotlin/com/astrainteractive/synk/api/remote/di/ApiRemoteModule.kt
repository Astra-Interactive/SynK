package com.astrainteractive.synk.api.remote.di

import com.astrainteractive.synk.api.remote.RemoteApi
import com.astrainteractive.synk.api.remote.di.factory.DatabaseFactory
import com.astrainteractive.synk.api.remote.impl.RemoteApiImpl
import com.astrainteractive.synk.api.remote.mapping.PlayerDTOMapper
import com.astrainteractive.synk.api.remote.mapping.PlayerDTOMapperImpl
import com.astrainteractive.synk.models.config.PluginConfig
import org.jetbrains.exposed.sql.Database
import ru.astrainteractive.klibs.kdi.Provider
import ru.astrainteractive.klibs.kdi.Single
import ru.astrainteractive.klibs.kdi.getValue

interface ApiRemoteModule {
    val database: Database
    val remoteApi: RemoteApi

    class Default(
        config: PluginConfig
    ) : ApiRemoteModule {
        private val playerDTOMapper: PlayerDTOMapper by Provider {
            PlayerDTOMapperImpl
        }
        override val database: Database by Single {
            DatabaseFactory(config = config).create()
        }
        override val remoteApi: RemoteApi by Provider {
            RemoteApiImpl(
                database = database,
                playerDTOMapper = playerDTOMapper
            )
        }
    }
}
