package com.astrainteractive.synk.di

import com.astrainteractive.synk.api.local.LocalInventoryApi
import com.astrainteractive.synk.api.remote.RemoteApi
import com.astrainteractive.synk.locker.ConcurrentHashMapLocker
import com.astrainteractive.synk.locker.Locker
import com.astrainteractive.synk.shared.EventController
import ru.astrainteractive.klibs.kdi.Single
import ru.astrainteractive.klibs.kdi.getValue
import ru.astrainteractive.klibs.mikro.core.dispatchers.KotlinDispatchers
import java.util.UUID

interface SharedModule {
    val eventController: EventController
    val uuidLocker: Locker<UUID>

    class Default(
        remoteApi: RemoteApi,
        localInventoryApi: LocalInventoryApi<*>,
        dispatchers: KotlinDispatchers
    ) : SharedModule {
        override val uuidLocker: Locker<UUID> by Single {
            ConcurrentHashMapLocker<UUID>()
        }
        override val eventController: EventController by Single {
            EventController(
                locker = uuidLocker,
                sqlDataSource = remoteApi,
                localDataSource = localInventoryApi,
                dispatchers = dispatchers
            )
        }
    }
}
