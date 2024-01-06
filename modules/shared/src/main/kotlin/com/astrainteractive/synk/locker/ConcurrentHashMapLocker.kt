package com.astrainteractive.synk.locker

import java.util.concurrent.ConcurrentHashMap

class ConcurrentHashMapLocker<T> : Locker<T> {
    private val set: MutableSet<T> = ConcurrentHashMap.newKeySet<T>()
    override suspend fun lock(obj: T): Boolean {
        return set.add(obj)
    }

    override suspend fun unlock(obj: T): Boolean {
        return set.remove(obj)
    }

    override suspend fun isLocked(obj: T?): Boolean {
        return set.contains(obj)
    }
    override fun clear() {
        set.clear()
    }
}
