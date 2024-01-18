package ru.astrainteractive.synk.core.di.factory

import kotlinx.serialization.encodeToString
import ru.astrainteractive.astralibs.filemanager.impl.JVMResourceFileManager
import ru.astrainteractive.astralibs.serialization.SerializerExt.parse
import ru.astrainteractive.astralibs.serialization.YamlSerializer
import ru.astrainteractive.klibs.kdi.Factory
import ru.astrainteractive.synk.core.PluginConfig
import java.io.File

internal class PluginConfigurationFactory(
    private val dataFolder: File,
    private val yamlSerializer: YamlSerializer
) : Factory<PluginConfig> {

    override fun create(): PluginConfig {
        val configFile = JVMResourceFileManager("config.yml", dataFolder, this::class.java)
        val configuration = yamlSerializer.parse<PluginConfig>(configFile.configFile).getOrThrow()
        if (!configFile.configFile.exists()) {
            configFile.configFile.createNewFile()
        }
        configFile.configFile.writeText(yamlSerializer.yaml.encodeToString(configuration))
        return configuration
    }
}
