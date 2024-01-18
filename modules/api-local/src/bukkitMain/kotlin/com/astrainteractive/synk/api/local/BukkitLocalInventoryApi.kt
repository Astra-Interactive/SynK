package com.astrainteractive.synk.api.local

import com.astrainteractive.synk.api.local.mapping.BukkitPlayerMapper
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import ru.astrainteractive.astralibs.filemanager.DefaultFileConfigurationManager
import ru.astrainteractive.synk.core.model.PlayerModel
import java.io.File

internal class BukkitLocalInventoryApi(
    private val plugin: JavaPlugin,
    private val bukkitPlayerMapper: BukkitPlayerMapper
) : LocalInventoryApi<ItemStack> {

    override fun savePlayer(playerModel: PlayerModel, type: LocalInventoryApi.TYPE) {
        val bukkitPlayer = bukkitPlayerMapper.fromDTO(playerModel)

        val name = "temp/${playerModel.minecraftUUID}/${type.name}_${System.currentTimeMillis()}.yml"
        val fileManager = DefaultFileConfigurationManager(plugin, name)
        val config = fileManager.fileConfiguration
        config.set("player.items", bukkitPlayer.inventory.contents)
        config.set("player.enderchest", bukkitPlayer.enderChest.contents)
        fileManager.save()
    }

    override fun loadPlayerSaves(playerModel: PlayerModel): List<File> {
        val dataFolder = plugin.dataFolder
        val playerFolder = File(dataFolder, "temp/${File.separator}${playerModel.minecraftUUID}${File.separator}")
        return playerFolder.listFiles()?.filter { it.extension == "yml" } ?: emptyList()
    }

    override fun readPlayerInventorySave(playerModel: PlayerModel, file: File): List<ItemStack> {
        val name = "temp/${playerModel.minecraftUUID}/${file.name}"
        val fileManager = DefaultFileConfigurationManager(plugin, name)
        val config = fileManager.fileConfiguration
        return config.getList("player.items") as? List<ItemStack> ?: emptyList()
    }

    override fun readPlayerEnderChestSave(playerModel: PlayerModel, file: File): List<ItemStack> {
        val name = "temp/${playerModel.minecraftUUID}/${file.name}"
        val fileManager = DefaultFileConfigurationManager(plugin, name)
        val config = fileManager.fileConfiguration
        return config.getList("player.enderchest") as? List<ItemStack> ?: emptyList()
    }
}
