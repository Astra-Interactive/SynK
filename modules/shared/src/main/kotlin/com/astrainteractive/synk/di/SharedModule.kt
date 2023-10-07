package com.astrainteractive.synk.di

import com.astrainteractive.synk.api.local.LocalInventoryApi
import com.astrainteractive.synk.api.remote.RemoteApi
import com.astrainteractive.synk.shared.EventController
import com.astrainteractive.synk.utils.ConcurrentHashMapLocker
import com.astrainteractive.synk.utils.Locker
import ru.astrainteractive.klibs.kdi.Single
import ru.astrainteractive.klibs.kdi.getValue
import java.util.UUID

interface SharedModule {
    val eventController: EventController
    val uuidLocker: Locker<UUID>

    class Default(
        remoteApi: RemoteApi,
        localInventoryApi: LocalInventoryApi<*>
    ) : SharedModule {
        override val uuidLocker: Locker<UUID> by Single {
            ConcurrentHashMapLocker<UUID>()
        }
        override val eventController: EventController by Single {
            EventController(
                locker = uuidLocker,
                sqlDataSource = remoteApi,
                localDataSource = localInventoryApi
            )
        }
    }
}
