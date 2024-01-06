package com.astrainteractive.synk.api.remote.impl

import com.astrainteractive.synk.api.remote.RemoteApi
import com.astrainteractive.synk.api.remote.entities.PlayerDAO
import com.astrainteractive.synk.api.remote.entities.PlayerTable
import com.astrainteractive.synk.api.remote.mapping.PlayerDTOMapper
import com.astrainteractive.synk.api.remote.mapping.PlayerDTOMapperImpl
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import ru.astrainteractive.synk.core.model.PlayerDTO
import java.util.UUID

internal class RemoteApiImpl(
    private val database: Database,
    private val playerDTOMapper: PlayerDTOMapper = PlayerDTOMapperImpl
) : RemoteApi {
    override fun insertOrUpdate(
        playerDTO: PlayerDTO
    ) = transaction(database) {
        val isExists = select(playerDTO.minecraftUUID) != null
        if (isExists) delete(playerDTO.minecraftUUID)
        insert(playerDTO)
    }

    override fun insert(
        playerDTO: PlayerDTO
    ) = transaction(database) {
        PlayerDAO.new(playerDTOMapper.toExposed(playerDTO)).let(playerDTOMapper::toDTO)
    }

    override fun select(
        uuid: UUID
    ) = transaction(database) {
        PlayerDAO.find(PlayerTable.minecraftUUID eq uuid.toString()).firstOrNull()?.let(playerDTOMapper::toDTO)
    }

    override fun delete(uuid: UUID) = transaction(database) {
        PlayerDAO.find(PlayerTable.minecraftUUID eq uuid.toString()).first().delete()
    }
}
