package com.astrainteractive.synk.plugin

import ru.astrainteractive.astralibs.utils.Permission


sealed class SynkPermission(override val value: String) : Permission {
    object Reload : SynkPermission("synk.reload")
    class Server(server: String) : SynkPermission("synk.server.$server")
    object History : SynkPermission("synk.history")
}

