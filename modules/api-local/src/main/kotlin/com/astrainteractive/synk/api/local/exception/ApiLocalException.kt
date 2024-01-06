package com.astrainteractive.synk.api.local.exception

sealed class ApiLocalException : Throwable() {
    data object PlayerNotFoundException : ApiLocalException()
}
