package com.astrainteractive.synk.api.remote

import com.astrainteractive.synk.api.remote.entities.PlayerDAO
import com.astrainteractive.synk.api.remote.entities.PlayerTable
import com.astrainteractive.synk.api.remote.mapping.PlayerDTOMapper
import com.astrainteractive.synk.api.remote.mapping.PlayerDTOMapperImpl
import com.astrainteractive.synk.models.dto.PlayerDTO
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction

class RemoteApiImpl(
    private val playerDTOMapper: PlayerDTOMapper = PlayerDTOMapperImpl
) : RemoteApi {
    override fun update(playerDTO: PlayerDTO) = transaction {
        val isExists = select(playerDTO.minecraftUUID) != null
        if (isExists)
            delete(playerDTO.minecraftUUID)
        insert(playerDTO)
    }

    override fun insert(playerDTO: PlayerDTO): PlayerDTO {
        return PlayerDAO.new(playerDTOMapper.toExposed(playerDTO)).let(playerDTOMapper::toDTO)
    }

    override fun select(uuid: String): PlayerDTO? = transaction {
        PlayerDAO.find(PlayerTable.minecraftUUID eq uuid).firstOrNull()?.let(playerDTOMapper::toDTO)
    }


    override fun delete(uuid: String): Int {
        return PlayerTable.deleteWhere { PlayerTable.minecraftUUID eq uuid }
    }
}