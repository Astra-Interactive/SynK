
import com.astrainteractive.synk.commands.history
import com.astrainteractive.synk.commands.reload
import com.astrainteractive.synk.commands.syncServer
import com.astrainteractive.synk.commands.tabCompleter

/**
 * Command handler for your plugin
 * It's better to create different executors for different commands
 * @see Reload
 */
class CommandManager {
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
