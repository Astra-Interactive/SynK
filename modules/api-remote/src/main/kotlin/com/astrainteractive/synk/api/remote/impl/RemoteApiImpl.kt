package com.astrainteractive.synk.api.remote.impl

import com.astrainteractive.synk.api.remote.RemoteApi
import com.astrainteractive.synk.api.remote.entity.PlayerDAO
import com.astrainteractive.synk.api.remote.entity.PlayerTable
import com.astrainteractive.synk.api.remote.mapping.PlayerModelMapper
import com.astrainteractive.synk.api.remote.mapping.PlayerModelMapperImpl
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import ru.astrainteractive.synk.core.model.PlayerModel
import java.util.UUID

internal class RemoteApiImpl(
    private val database: Database,
    private val playerModelMapper: PlayerModelMapper = PlayerModelMapperImpl
) : RemoteApi {
    override fun insertOrUpdate(
        playerModel: PlayerModel
    ) = transaction(database) {
        val isExists = select(playerModel.minecraftUUID) != null
        if (isExists) delete(playerModel.minecraftUUID)
        insert(playerModel)
    }

    override fun insert(
        playerModel: PlayerModel
    ) = transaction(database) {
        PlayerDAO.new(playerModelMapper.toExposed(playerModel)).let(playerModelMapper::toDTO)
    }

    override fun select(
        uuid: UUID
    ) = transaction(database) {
        PlayerDAO.find(PlayerTable.minecraftUUID eq uuid.toString()).firstOrNull()?.let(playerModelMapper::toDTO)
    }

    override fun delete(uuid: UUID) = transaction(database) {
        PlayerDAO.find(PlayerTable.minecraftUUID eq uuid.toString()).first().delete()
    }
}
