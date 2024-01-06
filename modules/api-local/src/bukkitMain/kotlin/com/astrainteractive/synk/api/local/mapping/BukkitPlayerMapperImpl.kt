package com.astrainteractive.synk.api.local.mapping

import com.astrainteractive.synk.api.local.exception.ApiLocalException
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import ru.astrainteractive.astralibs.encoding.Encoder
import ru.astrainteractive.klibs.mikro.core.domain.Mapper
import ru.astrainteractive.synk.core.model.PlayerDTO

interface BukkitPlayerMapper : Mapper<Player, PlayerDTO> {
    companion object {
        operator fun invoke(serializer: Encoder): BukkitPlayerMapper {
            return BukkitPlayerMapperImpl(serializer)
        }
    }
}

internal class BukkitPlayerMapperImpl(
    private val serializer: Encoder
) : BukkitPlayerMapper {
    override fun toDTO(it: Player): PlayerDTO = PlayerDTO(
        minecraftUUID = it.uniqueId,
        totalExperience = it.totalExperience,
        health = it.health,
        foodLevel = it.foodLevel,
        lastServerName = "",
        items = serializer.encodeList(it.inventory.contents.filterNotNull()),
        enderChestItems = serializer.encodeList(it.enderChest.contents.filterNotNull()),
        effects = serializer.encodeList(it.activePotionEffects.filterNotNull()),
    )

    override fun fromDTO(it: PlayerDTO): Player {
        val player = Bukkit.getPlayer(it.minecraftUUID) ?: throw ApiLocalException.PlayerNotFoundException
        player.totalExperience = it.totalExperience
        player.health = it.health
        player.foodLevel = it.foodLevel
        player.inventory.contents = serializer.decodeList<ItemStack>(it.items).toTypedArray()
        player.enderChest.contents = serializer.decodeList<ItemStack>(it.enderChestItems).toTypedArray()
        player.activePotionEffects.map { player.removePotionEffect(it.type) }
        serializer.decodeList<PotionEffect>(it.effects).map(player::addPotionEffect)
        return player
    }
}
