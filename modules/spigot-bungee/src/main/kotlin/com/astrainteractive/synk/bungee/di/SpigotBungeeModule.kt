package com.astrainteractive.synk.bungee.di

import com.astrainteractive.synk.bungee.BungeeController
import com.astrainteractive.synk.bungee.BungeeDecoder
import org.bukkit.plugin.java.JavaPlugin
import ru.astrainteractive.klibs.kdi.Single

interface SpigotBungeeModule {
    val bungeeDecoder: Single<BungeeDecoder>
    val bungeeController: Single<BungeeController>

    class Default(plugin: JavaPlugin) : SpigotBungeeModule {
        override val bungeeDecoder: Single<BungeeDecoder> = Single {
            BungeeDecoder(plugin)
        }
        override val bungeeController: Single<BungeeController> = Single {
            BungeeController(
                plugin = plugin,
                bungeeDecoder = bungeeDecoder.value
            )
        }
    }
}
