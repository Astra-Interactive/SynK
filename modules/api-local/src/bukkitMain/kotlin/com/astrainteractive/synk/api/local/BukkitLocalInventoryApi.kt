package com.astrainteractive.synk.api.local

import com.astrainteractive.synk.api.local.mapping.BukkitPlayerMapper
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import ru.astrainteractive.astralibs.filemanager.DefaultSpigotFileManager
import ru.astrainteractive.synk.core.model.PlayerDTO
import java.io.File

internal class BukkitLocalInventoryApi(
    private val plugin: JavaPlugin,
    private val bukkitPlayerMapper: BukkitPlayerMapper
) : LocalInventoryApi<ItemStack> {

    override fun savePlayer(player: PlayerDTO, type: LocalInventoryApi.TYPE) {
        val bukkitPlayer = bukkitPlayerMapper.fromDTO(player)

        val name = "temp/${player.minecraftUUID}/${type.name}_${System.currentTimeMillis()}.yml"
        val fileManager = DefaultSpigotFileManager(plugin, name)
        val config = fileManager.fileConfiguration
        config.set("player.items", bukkitPlayer.inventory.contents)
        config.set("player.enderchest", bukkitPlayer.enderChest.contents)
        fileManager.save()
    }

    override fun loadPlayerSaves(player: PlayerDTO): List<File> {
        val dataFolder = plugin.dataFolder
        val playerFolder = File(dataFolder, "temp/${File.separator}${player.minecraftUUID}${File.separator}")
        return playerFolder.listFiles()?.filter { it.extension == "yml" } ?: emptyList()
    }

    override fun readPlayerInventorySave(player: PlayerDTO, file: File): List<ItemStack> {
        val name = "temp/${player.minecraftUUID}/${file.name}"
        val fileManager = DefaultSpigotFileManager(plugin, name)
        val config = fileManager.fileConfiguration
        return config.getList("player.items") as? List<ItemStack> ?: emptyList()
    }

    override fun readPlayerEnderChestSave(player: PlayerDTO, file: File): List<ItemStack> {
        val name = "temp/${player.minecraftUUID}/${file.name}"
        val fileManager = DefaultSpigotFileManager(plugin, name)
        val config = fileManager.fileConfiguration
        return config.getList("player.enderchest") as? List<ItemStack> ?: emptyList()
    }
}
