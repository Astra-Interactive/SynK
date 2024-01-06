package com.astrainteractive.synk.api.remote

import ru.astrainteractive.synk.core.model.PlayerDTO
import java.util.*

interface RemoteApi {
    fun insertOrUpdate(playerDTO: PlayerDTO): PlayerDTO
    fun insert(playerDTO: PlayerDTO): PlayerDTO
    fun select(uuid: UUID): PlayerDTO?
    fun delete(uuid: UUID): Unit
}
