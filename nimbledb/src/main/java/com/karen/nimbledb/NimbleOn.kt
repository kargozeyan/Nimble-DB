package com.karen.nimbledb

import android.content.Context

class NimbleOn internal constructor(name: String, context: Context) {
    private var dir = NimpleDir(name, context)

    fun <T : Any> put(key: String, value: T) {

        dir.put(key, NimbleStorage(value))
    }

    fun <T : Any> get(key: String): T {
        val storage: NimbleStorage<T> = dir.get<T>(key) as NimbleStorage<T>
        return storage.data
    }

    fun remove(key: String) {
        dir.remove(key)
    }

    val allKeys: ArrayList<String>
        get() = dir.allKeys

    fun exists(key: String): Boolean = dir.exists(key)

    fun removeAll() {
        dir.removeAll()
    }
}