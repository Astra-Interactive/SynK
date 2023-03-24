package com.astrainteractive.synk.api.remote.mapping

import com.astrainteractive.synk.api.remote.entities.PlayerDAO
import com.astrainteractive.synk.models.dto.PlayerDTO
import ru.astrainteractive.astralibs.domain.mapping.Mapper

interface PlayerDTOMapper : Mapper<PlayerDAO, PlayerDTO>, ExposedMapper<PlayerDAO, PlayerDTO>

object PlayerDTOMapperImpl : PlayerDTOMapper {
    override fun toDTO(it: PlayerDAO): PlayerDTO {
        return PlayerDTO(
            minecraftUUID = it.minecraftUUID,
            totalExperience = it.experience,
            health = it.health,
            foodLevel = it.foodLevel,
            lastServerName = it.lastServerName,
            items = it.items,
            enderChestItems = it.enderChestItems,
            effects = it.effects
        )
    }

    override fun fromDTO(it: PlayerDTO): PlayerDAO {
        throw NotImplementedError()
    }

    override fun toExposed(it: PlayerDTO): PlayerDAO.() -> Unit = {
        this.minecraftUUID = it.minecraftUUID
        this.experience = it.totalExperience
        this.health = it.health
        this.foodLevel = it.foodLevel
        this.lastServerName = it.lastServerName
        this.items = it.items
        this.enderChestItems = it.enderChestItems
        this.effects = it.effects
    }
}