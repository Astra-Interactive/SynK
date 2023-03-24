package com.astrainteractive.synk.api.remote.exception

sealed class RemoteApiException : Exception() {

    object PlayerNotFoundException : RemoteApiException()
    object PlayerLockedException : RemoteApiException()
    object PlayerDataNotExists : RemoteApiException()
}