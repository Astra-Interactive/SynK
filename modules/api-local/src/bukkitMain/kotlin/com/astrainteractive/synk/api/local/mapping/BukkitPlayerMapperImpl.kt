package com.astrainteractive.synk.api.local.mapping

import com.astrainteractive.synk.api.local.exception.ApiLocalException
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import ru.astrainteractive.astralibs.encoding.Encoder
import ru.astrainteractive.klibs.mikro.core.domain.Mapper
import ru.astrainteractive.synk.core.model.PlayerModel

interface BukkitPlayerMapper : Mapper<Player, PlayerModel> {
    companion object {
        operator fun invoke(serializer: Encoder): BukkitPlayerMapper {
            return BukkitPlayerMapperImpl(serializer)
        }
    }
}

internal class BukkitPlayerMapperImpl(
    private val encoder: Encoder
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

    override fun fromDTO(it: PlayerModel): Player {
        val player = Bukkit.getPlayer(it.minecraftUUID) ?: throw ApiLocalException.PlayerNotFoundException
        player.totalExperience = it.totalExperience
        player.health = it.health
        player.foodLevel = it.foodLevel
        player.inventory.contents = encoder.decodeList<ItemStack>(it.items).toTypedArray()
        player.enderChest.contents = encoder.decodeList<ItemStack>(it.enderChestItems).toTypedArray()
        player.activePotionEffects.map { player.removePotionEffect(it.type) }
        encoder.decodeList<PotionEffect>(it.effects).map(player::addPotionEffect)
        return player
    }
}
