package com.astrainteractive.synk.api.remote

import com.astrainteractive.synk.models.dto.PlayerDTO
import java.util.*

interface RemoteApi {
    fun update(playerDTO: PlayerDTO): Result<PlayerDTO>
    fun insert(playerDTO: PlayerDTO): Result<PlayerDTO>
    fun select(uuid: UUID): Result<PlayerDTO>
    fun delete(uuid: UUID): Result<*>
}
