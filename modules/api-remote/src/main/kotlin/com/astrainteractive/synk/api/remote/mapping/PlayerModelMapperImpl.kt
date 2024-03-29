package com.astrainteractive.synk.api.remote.mapping

import com.astrainteractive.synk.api.remote.entity.PlayerDAO
import ru.astrainteractive.astralibs.encoding.model.EncodedObject
import ru.astrainteractive.klibs.mikro.core.domain.Mapper
import ru.astrainteractive.synk.core.model.PlayerModel
import java.util.UUID

internal interface PlayerModelMapper : Mapper<PlayerDAO, PlayerModel>, ExposedMapper<PlayerDAO, PlayerModel>

internal object PlayerModelMapperImpl : PlayerModelMapper {
    override fun toDTO(it: PlayerDAO): PlayerModel {
        return PlayerModel(
            minecraftUUID = UUID.fromString(it.minecraftUUID),
            totalExperience = it.experience,
            health = it.health,
            foodLevel = it.foodLevel,
            lastServerName = it.lastServerName,
            items = EncodedObject.Base64(it.items),
            enderChestItems = EncodedObject.Base64(it.enderChestItems),
            effects = EncodedObject.Base64(it.effects)
        )
    }

    override fun fromDTO(it: PlayerModel): PlayerDAO {
        throw NotImplementedError()
    }

    override fun toExposed(it: PlayerModel): PlayerDAO.() -> Unit = {
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
