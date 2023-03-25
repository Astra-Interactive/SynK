package com.astrainteractive.synk.api

import com.astrainteractive.synk.api.local.LocalInventoryApi
import com.astrainteractive.synk.api.mapping.BukkitPlayerMapper
import com.astrainteractive.synk.models.dto.PlayerDTO
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import ru.astrainteractive.astralibs.AstraLibs
import ru.astrainteractive.astralibs.file_manager.FileManager
import java.io.File

class SpigotLocalPlayerDataSource(
    val bukkitPlayerMapper: BukkitPlayerMapper
) : LocalInventoryApi<ItemStack> {


    override fun savePlayer(player: PlayerDTO, type: LocalInventoryApi.TYPE) {
        val bukkitPlayer = bukkitPlayerMapper.fromDTO(player)

        val name = "temp/${player.minecraftUUID}/${type.name}_${System.currentTimeMillis()}.yml"
        val fileManager = FileManager(name)
        val config = fileManager.fileConfiguration
        config.set("player.items", bukkitPlayer.inventory.contents)
        config.set("player.enderchest", bukkitPlayer.enderChest.contents)
        fileManager.save()
    }

    override fun loadPlayerSaves(player: PlayerDTO): List<File> {

        val dataFolder = AstraLibs.instance.dataFolder
        val playerFolder = File(dataFolder, "temp/${File.separator}${player.minecraftUUID}${File.separator}")
        return playerFolder.listFiles()?.filter { it.extension == "yml" } ?: emptyList()
    }

    override fun readPlayerInventorySave(player: PlayerDTO, file: File): List<ItemStack> {
        val name = "temp/${player.minecraftUUID}/${file.name}"
        val fileManager = FileManager(name)
        val config = fileManager.fileConfiguration
        return config.getList("player.items") as? List<ItemStack> ?: emptyList()
    }

    override fun readPlayerEnderChestSave(player: PlayerDTO, file: File): List<ItemStack> {
        val name = "temp/${player.minecraftUUID}/${file.name}"
        val fileManager = FileManager(name)
        val config = fileManager.fileConfiguration
        return config.getList("player.enderchest") as? List<ItemStack> ?: emptyList()
    }

}