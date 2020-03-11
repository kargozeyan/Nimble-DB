package com.karen.nimbledb_lru

import android.content.Context
import android.util.LruCache

class NimbleSection internal constructor(
    name: String,
    context: Context,
    lruCache: LruCache<String, NimbleStorage<out Any>>
) {
    private var lru = lruCache
    private var ksKryo = NimbleDir(name, context)

    fun <T : Any> put(key: String, value: T) {
        lru.put(key, NimbleStorage(value))
        ksKryo.put(key, NimbleStorage(value))
    }

    fun <T : Any> get(key: String): T {
        val lruStorage = lru.get(key)
        if (lruStorage == null) {
            val storage: NimbleStorage<T> = ksKryo.get<T>(key) as NimbleStorage<T>
            lru.put(key, storage)
            return storage.data
        }

        return (lruStorage as NimbleStorage<T>).data
    }

    fun remove(key: String) {
        lru.remove(key)
        ksKryo.remove(key)
    }

    fun exists(key: String) = ksKryo.exists(key)

    val allKeys: ArrayList<String>
        get() = ksKryo.allKeys

    fun destroy() {
        lru.evictAll()
        ksKryo.destroy()
    }
}