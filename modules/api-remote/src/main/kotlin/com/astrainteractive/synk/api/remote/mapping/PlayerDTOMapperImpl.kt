package com.astrainteractive.synk.api.remote.mapping

import com.astrainteractive.synk.api.remote.entities.PlayerDAO
import com.astrainteractive.synk.models.dto.PlayerDTO
import ru.astrainteractive.astralibs.encoding.IO
import ru.astrainteractive.klibs.mikro.core.domain.Mapper
import java.util.UUID

interface PlayerDTOMapper : Mapper<PlayerDAO, PlayerDTO>, ExposedMapper<PlayerDAO, PlayerDTO>

object PlayerDTOMapperImpl : PlayerDTOMapper {
    override fun toDTO(it: PlayerDAO): PlayerDTO {
        return PlayerDTO(
            minecraftUUID = UUID.fromString(it.minecraftUUID),
            totalExperience = it.experience,
            health = it.health,
            foodLevel = it.foodLevel,
            lastServerName = it.lastServerName,
            items = IO.Base64(it.items),
            enderChestItems = IO.Base64(it.enderChestItems),
            effects = IO.Base64(it.effects)
        )
    }

    override fun fromDTO(it: PlayerDTO): PlayerDAO {
        throw NotImplementedError()
    }

    override fun toExposed(it: PlayerDTO): PlayerDAO.() -> Unit = {
        this.minecraftUUID = it.minecraftUUID.toString()
        this.experience = it.totalExperience
        this.health = it.health
        this.foodLevel = it.foodLevel
        this.lastServerName = it.lastServerName
        this.items = it.items.value
        this.enderChestItems = it.enderChestItems.value
        this.effects = it.effects.value
    }
}
