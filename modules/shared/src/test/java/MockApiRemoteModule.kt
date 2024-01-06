import com.astrainteractive.synk.api.remote.di.ApiRemoteModule
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
