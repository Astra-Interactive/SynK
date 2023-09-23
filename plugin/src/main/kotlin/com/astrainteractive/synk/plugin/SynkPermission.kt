package com.astrainteractive.synk.plugin

import ru.astrainteractive.astralibs.util.Permission

sealed class SynkPermission(override val value: String) : Permission {
    data object Reload : SynkPermission("synk.reload")
    class Server(server: String) : SynkPermission("synk.server.$server")
    data object History : SynkPermission("synk.history")
}
