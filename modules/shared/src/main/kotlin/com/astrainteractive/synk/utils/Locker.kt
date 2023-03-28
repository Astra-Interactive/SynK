package com.astrainteractive.synk.utils

interface Locker<T> {
    suspend fun lock(obj: T): Boolean
    suspend fun unlock(obj: T): Boolean
    suspend fun isLocked(obj: T?): Boolean
    fun clear()
}
