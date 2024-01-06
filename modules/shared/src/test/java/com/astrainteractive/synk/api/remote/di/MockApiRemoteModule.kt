package com.astrainteractive.synk.api.remote.di

import ru.astrainteractive.synk.core.PluginConfig
import java.io.File

class MockApiRemoteModule : ApiRemoteModule by ApiRemoteModule.Default(
    configProvider = {
        val file = File.createTempFile(
            "database",
            "db"
        )
        PluginConfig.SqlConfig.SQLite("$file")
    }
)
