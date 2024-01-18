package ru.astrainteractive.synk.core.di

import kotlinx.coroutines.CoroutineScope
import ru.astrainteractive.astralibs.async.AsyncComponent
import ru.astrainteractive.astralibs.encoding.encoder.ObjectEncoder
import ru.astrainteractive.astralibs.lifecycle.Lifecycle
import ru.astrainteractive.astralibs.serialization.YamlSerializer
import ru.astrainteractive.klibs.kdi.Dependency
import ru.astrainteractive.klibs.kdi.Reloadable
import ru.astrainteractive.klibs.mikro.core.dispatchers.KotlinDispatchers
import ru.astrainteractive.synk.core.PluginConfig
import ru.astrainteractive.synk.core.PluginTranslation
import ru.astrainteractive.synk.core.di.factory.PluginConfigurationFactory
import ru.astrainteractive.synk.core.di.factory.TranslationFactory
import java.io.File

interface CoreModule {
    val lifecycle: Lifecycle
    val yamlSerializer: YamlSerializer
    val pluginScope: CoroutineScope
    val dispatchers: KotlinDispatchers
    val translation: Dependency<PluginTranslation>
    val configurationModule: Dependency<PluginConfig>
    val encoder: ObjectEncoder

    class Default(
        dataFolder: File,
        override val dispatchers: KotlinDispatchers,
        override val encoder: ObjectEncoder
    ) : CoreModule {
        override val yamlSerializer: YamlSerializer by lazy {
            YamlSerializer()
        }

        override val pluginScope by lazy {
            AsyncComponent.Default()
        }

        override val translation: Reloadable<PluginTranslation> = Reloadable {
            TranslationFactory(
                dataFolder = dataFolder,
                yamlSerializer = yamlSerializer
            ).create()
        }

        override val configurationModule: Reloadable<PluginConfig> = Reloadable {
            PluginConfigurationFactory(
                dataFolder = dataFolder,
                yamlSerializer = yamlSerializer
            ).create()
        }
        override val lifecycle: Lifecycle by lazy {
            Lifecycle.Lambda(
                onReload = {
                    configurationModule.reload()
                    translation.reload()
                },
                onDisable = {
                    pluginScope.close()
                }
            )
        }
    }
}
