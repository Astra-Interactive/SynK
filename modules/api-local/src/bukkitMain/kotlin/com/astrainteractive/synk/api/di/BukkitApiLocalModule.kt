package com.astrainteractive.synk.api.di

import com.astrainteractive.synk.api.BukkitLocalInventoryApi
import com.astrainteractive.synk.api.local.LocalInventoryApi
import com.astrainteractive.synk.api.mapping.BukkitPlayerMapper
import com.astrainteractive.synk.api.mapping.BukkitPlayerMapperImpl
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import ru.astrainteractive.astralibs.encoding.Serializer
import ru.astrainteractive.klibs.kdi.Provider
import ru.astrainteractive.klibs.kdi.getValue

interface BukkitApiLocalModule {
    val bukkitPlayerMapper: BukkitPlayerMapper
    val localPlayerDataSource: LocalInventoryApi<ItemStack>

    class Default(serializer: Serializer, plugin: JavaPlugin) : BukkitApiLocalModule {
        override val bukkitPlayerMapper: BukkitPlayerMapper by Provider {
            BukkitPlayerMapperImpl(serializer = serializer)
        }
        override val localPlayerDataSource: LocalInventoryApi<ItemStack> by Provider {
            BukkitLocalInventoryApi(
                plugin = plugin,
                bukkitPlayerMapper = bukkitPlayerMapper
            )
        }
    }
}
