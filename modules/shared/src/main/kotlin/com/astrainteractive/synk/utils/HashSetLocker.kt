package com.astrainteractive.synk.utils

class HashSetLocker<T> : Locker<T> {
    private val set: MutableSet<T> = HashSet<T>()
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
