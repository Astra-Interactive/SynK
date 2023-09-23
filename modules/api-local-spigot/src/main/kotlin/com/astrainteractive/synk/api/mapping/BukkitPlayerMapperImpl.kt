package com.astrainteractive.synk.api.mapping

import com.astrainteractive.synk.api.remote.exception.RemoteApiException
import com.astrainteractive.synk.models.dto.PlayerDTO
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import ru.astrainteractive.astralibs.encoding.Serializer
import ru.astrainteractive.klibs.mikro.core.domain.Mapper

interface BukkitPlayerMapper : Mapper<Player, PlayerDTO> {
    companion object {
        operator fun invoke(serializer: Serializer): BukkitPlayerMapper {
            return BukkitPlayerMapperImpl(serializer)
        }
    }
}

internal class BukkitPlayerMapperImpl(
    private val serializer: Serializer
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
        val player = Bukkit.getPlayer(it.minecraftUUID) ?: throw RemoteApiException.PlayerNotFoundException
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
