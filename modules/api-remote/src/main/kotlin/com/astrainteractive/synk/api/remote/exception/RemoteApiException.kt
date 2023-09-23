package com.astrainteractive.synk.api.remote.exception

sealed class RemoteApiException : Throwable() {

    data object PlayerNotFoundException : RemoteApiException()
    data object PlayerLockedException : RemoteApiException()
    data object PlayerDataNotExists : RemoteApiException()
}
