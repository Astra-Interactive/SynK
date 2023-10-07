package com.astrainteractive.synk.api.exception

sealed class ApiLocalException : Throwable() {
    data object PlayerNotFoundException : ApiLocalException()
}
