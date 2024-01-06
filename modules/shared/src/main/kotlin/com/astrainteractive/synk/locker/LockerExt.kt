package com.astrainteractive.synk.locker

import com.astrainteractive.synk.api.remote.exception.RemoteApiException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

object LockerExt {
    suspend inline fun <reified T> Locker<T>.withLock(
        obj: T,
        crossinline block: suspend CoroutineScope.() -> Unit
    ): Unit = coroutineScope {
        if (isLocked(obj)) throw RemoteApiException.PlayerLockedException
        lock(obj)
        block.invoke(this)
        unlock(obj)
    }

    inline fun <reified T> Locker<T>.launchWithLock(
        obj: T,
        coroutineScope: CoroutineScope,
        context: CoroutineContext = Dispatchers.IO,
        crossinline block: suspend CoroutineScope.() -> Unit
    ) {
        coroutineScope.launch(context) {
            withLock(
                obj = obj,
                block = block
            )
        }
    }
}
