package com.astrainteractive.synk.api.local.loader

import com.astrainteractive.synk.api.local.exception.ApiLocalException
import org.bukkit.Bukkit
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import ru.astrainteractive.astralibs.encoding.Encoder
import ru.astrainteractive.synk.core.model.PlayerModel

internal class PlayerLoaderImpl(private val encoder: Encoder) : PlayerLoader {
    override fun loadPlayer(it: PlayerModel) {
        val player = Bukkit.getPlayer(it.minecraftUUID) ?: throw ApiLocalException.PlayerNotFoundException
        player.totalExperience = it.totalExperience
        player.health = it.health
        player.foodLevel = it.foodLevel
        player.inventory.contents = encoder.decodeList<ItemStack>(it.items).toTypedArray()
        player.enderChest.contents = encoder.decodeList<ItemStack>(it.enderChestItems).toTypedArray()
        player.activePotionEffects.map { player.removePotionEffect(it.type) }
        encoder.decodeList<PotionEffect>(it.effects).map(player::addPotionEffect)
    }
}
