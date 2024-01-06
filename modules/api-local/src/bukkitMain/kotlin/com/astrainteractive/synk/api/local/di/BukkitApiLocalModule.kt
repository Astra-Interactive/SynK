package com.astrainteractive.synk.api.local.di

import com.astrainteractive.synk.api.local.BukkitLocalInventoryApi
import com.astrainteractive.synk.api.local.LocalInventoryApi
import com.astrainteractive.synk.api.local.mapping.BukkitPlayerMapper
import com.astrainteractive.synk.api.local.mapping.BukkitPlayerMapperImpl
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import ru.astrainteractive.astralibs.encoding.Encoder
import ru.astrainteractive.klibs.kdi.Provider
import ru.astrainteractive.klibs.kdi.getValue

interface BukkitApiLocalModule {
    val bukkitPlayerMapper: BukkitPlayerMapper
    val localPlayerDataSource: LocalInventoryApi<ItemStack>

    class Default(serializer: Encoder, plugin: JavaPlugin) : BukkitApiLocalModule {
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
