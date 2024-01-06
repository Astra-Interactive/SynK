package com.astrainteractive.synk.api.remote.di

import com.astrainteractive.synk.api.remote.RemoteApi
import com.astrainteractive.synk.api.remote.di.factory.DatabaseFactory
import com.astrainteractive.synk.api.remote.impl.RemoteApiImpl
import com.astrainteractive.synk.api.remote.mapping.PlayerDTOMapper
import com.astrainteractive.synk.api.remote.mapping.PlayerDTOMapperImpl
import org.jetbrains.exposed.sql.Database
import ru.astrainteractive.astralibs.lifecycle.Lifecycle
import ru.astrainteractive.klibs.kdi.Provider
import ru.astrainteractive.klibs.kdi.Single
import ru.astrainteractive.klibs.kdi.getValue
import ru.astrainteractive.synk.core.PluginConfig

interface ApiRemoteModule {
    val lifecycle: Lifecycle
    val remoteApi: RemoteApi

    class Default(
        configProvider: Provider<PluginConfig.SqlConfig>
    ) : ApiRemoteModule {
        private val playerDTOMapper: PlayerDTOMapper by Provider {
            PlayerDTOMapperImpl
        }
        private val database: Database by Single {
            DatabaseFactory(sqlConfig = configProvider.provide()).create()
        }
        override val remoteApi: RemoteApi by Provider {
            RemoteApiImpl(
                database = database,
                playerDTOMapper = playerDTOMapper
            )
        }
        override val lifecycle: Lifecycle by lazy {
            Lifecycle.Lambda(
                onEnable = {
                    remoteApi
                },
                onDisable = {
                    database.connector.invoke().close()
                },
                onReload = {
                    onDisable()
                    onEnable()
                }
            )
        }
    }
}
