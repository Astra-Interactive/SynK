package com.astrainteractive.synk.api.mapping

import com.astrainteractive.synk.api.remote.exception.RemoteApiException
import com.astrainteractive.synk.models.dto.PlayerDTO
import com.astrainteractive.synk.utils.Serializer
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import ru.astrainteractive.astralibs.domain.mapping.Mapper
import ru.astrainteractive.astralibs.utils.uuid
import java.util.*

interface BukkitPlayerMapper : Mapper<Player, PlayerDTO>

object BukkitPlayerMapperImpl : BukkitPlayerMapper {
    override fun toDTO(it: Player): PlayerDTO = PlayerDTO(
        minecraftUUID = it.uuid,
        totalExperience = it.totalExperience,
        health = it.health,
        foodLevel = it.foodLevel,
        lastServerName = "",
        items = Serializer.encodeList(it.inventory.contents.filterNotNull()),
        enderChestItems = Serializer.encodeList(it.enderChest.contents.filterNotNull()),
        effects = Serializer.encodeList(it.activePotionEffects.filterNotNull()),
    )

    override fun fromDTO(it: PlayerDTO): Player {
        val player = Bukkit.getPlayer(UUID.fromString(it.minecraftUUID)) ?: throw RemoteApiException.PlayerNotFoundException
        player.totalExperience = it.totalExperience
        player.health = it.health
        player.foodLevel = it.foodLevel
        player.inventory.contents = Serializer.decodeList<ItemStack>(it.items).toTypedArray()
        player.enderChest.contents = Serializer.decodeList<ItemStack>(it.enderChestItems).toTypedArray()
        player.activePotionEffects.map { player.removePotionEffect(it.type) }
        Serializer.decodeList<PotionEffect>(it.effects).map(player::addPotionEffect)
        return player
    }
}