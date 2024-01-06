package com.astrainteractive.synk.command.di

import CommandManager
import com.astrainteractive.synk.di.RootModule
import ru.astrainteractive.astralibs.lifecycle.Lifecycle

interface CommandModule {
    val lifecycle: Lifecycle

    class Default(rootModule: RootModule) : CommandModule {
        override val lifecycle: Lifecycle by lazy {
            Lifecycle.Lambda(
                onEnable = {
                    val dependencies = CommandDependencies.Default(rootModule)
                    CommandManager(dependencies)
                }
            )
        }
    }
}
