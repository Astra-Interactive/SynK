package com.astrainteractive.synk.api.local.mapping

import org.bukkit.entity.Player
import ru.astrainteractive.astralibs.encoding.encoder.ObjectEncoder
import ru.astrainteractive.astralibs.encoding.encoder.encodeList
import ru.astrainteractive.klibs.mikro.core.domain.Mapper
import ru.astrainteractive.synk.core.model.PlayerModel

interface BukkitPlayerMapper : Mapper<Player, PlayerModel> {
    companion object {
        operator fun invoke(serializer: ObjectEncoder): BukkitPlayerMapper {
            return BukkitPlayerMapperImpl(serializer)
        }
    }
}

internal class BukkitPlayerMapperImpl(
    private val encoder: ObjectEncoder
) : BukkitPlayerMapper {
    override fun toDTO(it: Player): PlayerModel = PlayerModel(
        minecraftUUID = it.uniqueId,
        totalExperience = it.totalExperience,
        health = it.health,
        foodLevel = it.foodLevel,
        lastServerName = "",
        items = encoder.encodeList(it.inventory.contents.filterNotNull()),
        enderChestItems = encoder.encodeList(it.enderChest.contents.filterNotNull()),
        effects = encoder.encodeList(it.activePotionEffects.filterNotNull()),
    )

    override fun fromDTO(it: PlayerModel): Player = error("This operation is not supported")
}
