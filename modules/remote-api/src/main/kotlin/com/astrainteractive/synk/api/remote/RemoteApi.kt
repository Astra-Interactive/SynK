package com.astrainteractive.synk.api.remote

import com.astrainteractive.synk.models.dto.PlayerDTO

interface RemoteApi {
    fun update(playerDTO: PlayerDTO): PlayerDTO
    fun insert(playerDTO: PlayerDTO): PlayerDTO
    fun select(uuid: String): PlayerDTO?
    fun delete(uuid: String): Int
}