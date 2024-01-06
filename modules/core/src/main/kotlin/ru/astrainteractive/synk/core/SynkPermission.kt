package ru.astrainteractive.synk.core

import ru.astrainteractive.astralibs.permission.Permission

sealed class SynkPermission(override val value: String) : Permission {
    data object Reload : SynkPermission("synk.reload")
    class Server(server: String) : SynkPermission("synk.server.$server")
    data object History : SynkPermission("synk.history")
}
