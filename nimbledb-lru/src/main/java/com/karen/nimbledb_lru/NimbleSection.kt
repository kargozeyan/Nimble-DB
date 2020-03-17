package com.karen.nimbledb_lru

import android.content.Context
import android.util.LruCache

class NimbleSection internal constructor(
    name: String,
    context: Context,
    lruCache: LruCache<String, NimbleStorage<out Any>>
) {
    private var lru = lruCache
    private var dir = NimbleDir(name, context)

    fun <T : Any> put(key: String, value: T) {
        lru.put(key, NimbleStorage(value))
        dir.put(key, NimbleStorage(value))
    }

    fun <T : Any> get(key: String): T {
        val lruStorage = lru.get(key)
        if (lruStorage == null) {
            val storage: NimbleStorage<T> = dir.get<T>(key) as NimbleStorage<T>
            lru.put(key, storage)
            return storage.data
        }

        return (lruStorage as NimbleStorage<T>).data
    }

    fun <T : Any> get(key: String, default: T): T {
        val lruStorage = lru.get(key)
        if (lruStorage == null) {
            val storage = dir.get<T>(key)
            return if (storage != null) {
                lru.put(key, storage)
                storage.data
            } else {
                lru.put(key, NimbleStorage(default))
                default
            }
        }

        return (lruStorage as NimbleStorage<T>).data
    }

    fun remove(key: String) {
        lru.remove(key)
        dir.remove(key)
    }

    val allKeys: ArrayList<String>
        get() = dir.allKeys

    fun exists(key: String) = dir.exists(key)

    fun clearAll() {
        lru.evictAll()
        dir.destroy()
    }
}