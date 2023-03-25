package com.astrainteractive.synk.api.remote

import com.astrainteractive.synk.models.dto.PlayerDTO
import java.util.*

interface RemoteApi {
    fun update(playerDTO: PlayerDTO): PlayerDTO
    fun insert(playerDTO: PlayerDTO): PlayerDTO
    fun select(uuid: UUID): PlayerDTO?
    fun delete(uuid: UUID): Int
}