package com.astrainteractive.synk.api

import com.astrainteractive.synk.api.local.LocalInventoryApi
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import ru.astrainteractive.astralibs.AstraLibs
import ru.astrainteractive.astralibs.file_manager.FileManager
import java.io.File

class SpigotLocalPlayerDataSource : LocalInventoryApi<Player, ItemStack> {


    override fun savePlayer(player: Player, type: LocalInventoryApi.TYPE) {
        val name = "temp/${player.name}/${type.name}_${System.currentTimeMillis()}.yml"
        val fileManager = FileManager(name)
        val config = fileManager.fileConfiguration
        config.set("player.items", player.inventory.contents)
        config.set("player.enderchest", player.enderChest.contents)
        fileManager.save()
    }

    override fun loadPlayerSaves(player: Player): List<File> {
        val dataFolder = AstraLibs.instance.dataFolder
        val playerFolder = File(dataFolder, "temp/${File.separator}${player.name}${File.separator}")
        return playerFolder.listFiles()?.filter { it.extension == "yml" } ?: emptyList()
    }

    override fun readPlayerInventorySave(player: Player, file: File): List<ItemStack> {
        val name = "temp/${player.name}/${file.name}"
        val fileManager = FileManager(name)
        val config = fileManager.fileConfiguration
        return config.getList("player.items") as? List<ItemStack> ?: emptyList()
    }

    override fun readPlayerEnderChestSave(player: Player, file: File): List<ItemStack> {
        val name = "temp/${player.name}/${file.name}"
        val fileManager = FileManager(name)
        val config = fileManager.fileConfiguration
        return config.getList("player.enderchest") as? List<ItemStack> ?: emptyList()
    }

}