import com.astrainteractive.synk.command.di.CommandContainer
import com.astrainteractive.synk.command.history
import com.astrainteractive.synk.command.reload
import com.astrainteractive.synk.command.syncServer
import com.astrainteractive.synk.command.tabCompleter
import ru.astrainteractive.astralibs.serialization.KyoriComponentSerializer
import ru.astrainteractive.astralibs.string.BukkitTranslationContext

/**
 * Command handler for your plugin
 * It's better to create different executors for different commands
 * @see Reload
 */
class CommandManager(container: CommandContainer) :
    CommandContainer by container,
    BukkitTranslationContext by BukkitTranslationContext.Default({ KyoriComponentSerializer.Legacy }) {
    /**
     * Here you should declare commands for your plugin
     *
     * Commands stored in plugin.yml
     *
     * etemp has TabCompleter
     */
    init {
        tabCompleter()
        reload()
        syncServer()
        history()
    }
}
