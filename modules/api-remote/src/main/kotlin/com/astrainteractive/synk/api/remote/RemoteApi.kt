package com.astrainteractive.synk.api.remote

import ru.astrainteractive.synk.core.model.PlayerModel
import java.util.*

interface RemoteApi {
    fun insertOrUpdate(playerModel: PlayerModel): PlayerModel
    fun insert(playerModel: PlayerModel): PlayerModel
    fun select(uuid: UUID): PlayerModel?
    fun delete(uuid: UUID): Unit
}
