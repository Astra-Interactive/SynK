package com.astrainteractive.synk.api.remote.mapping

import com.astrainteractive.synk.api.remote.entities.PlayerDAO
import com.astrainteractive.synk.models.dto.PlayerDTO
import ru.astrainteractive.astralibs.domain.mapping.Mapper
import ru.astrainteractive.astralibs.utils.encoding.Serializer
import java.util.*

interface PlayerDTOMapper : Mapper<PlayerDAO, PlayerDTO>, ExposedMapper<PlayerDAO, PlayerDTO>

object PlayerDTOMapperImpl : PlayerDTOMapper {
    override fun toDTO(it: PlayerDAO): PlayerDTO {
        return PlayerDTO(
            minecraftUUID = UUID.fromString(it.minecraftUUID),
            totalExperience = it.experience,
            health = it.health,
            foodLevel = it.foodLevel,
            lastServerName = it.lastServerName,
            items = Serializer.Wrapper.Base64(it.items),
            enderChestItems = Serializer.Wrapper.Base64(it.enderChestItems),
            effects = Serializer.Wrapper.Base64(it.effects)
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
