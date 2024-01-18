package com.astrainteractive.synk.api.local.di

import com.astrainteractive.synk.api.local.BukkitLocalInventoryApi
import com.astrainteractive.synk.api.local.LocalInventoryApi
import com.astrainteractive.synk.api.local.loader.PlayerLoader
import com.astrainteractive.synk.api.local.loader.PlayerLoaderImpl
import com.astrainteractive.synk.api.local.mapping.BukkitPlayerMapper
import com.astrainteractive.synk.api.local.mapping.BukkitPlayerMapperImpl
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import ru.astrainteractive.astralibs.encoding.encoder.ObjectEncoder
import ru.astrainteractive.klibs.kdi.Provider
import ru.astrainteractive.klibs.kdi.getValue

interface BukkitApiLocalModule {
    val bukkitPlayerMapper: BukkitPlayerMapper
    val localPlayerDataSource: LocalInventoryApi<ItemStack>
    val playerLoader: PlayerLoader

    class Default(encoder: ObjectEncoder, plugin: JavaPlugin) : BukkitApiLocalModule {
        override val bukkitPlayerMapper: BukkitPlayerMapper by Provider {
            BukkitPlayerMapperImpl(encoder = encoder)
        }

        override val playerLoader: PlayerLoader by Provider {
            PlayerLoaderImpl(encoder)
        }

        override val localPlayerDataSource: LocalInventoryApi<ItemStack> by Provider {
            BukkitLocalInventoryApi(
                plugin = plugin,
                bukkitPlayerMapper = bukkitPlayerMapper
            )
        }
    }
}
