package com.astrainteractive.synk.di

import com.astrainteractive.synk.api.di.BukkitApiLocalModule
import com.astrainteractive.synk.api.remote.di.ApiRemoteModule
import com.astrainteractive.synk.bungee.di.SpigotBungeeModule
import com.astrainteractive.synk.commands.di.CommandContainer
import com.astrainteractive.synk.events.di.EventContainer

interface RootModule {
    val pluginModule: PluginModule
    val apiLocalModule: BukkitApiLocalModule
    val apiRemoteModule: ApiRemoteModule
    val sharedModule: SharedModule
    val spigotBungeeModule: SpigotBungeeModule

    // Containers
    val eventContainer: EventContainer
    val commandContainer: CommandContainer
}
