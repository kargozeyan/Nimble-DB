package com.karen.nimbledb

import android.content.Context
import com.esotericsoftware.kryo.Kryo
import com.esotericsoftware.kryo.io.Input
import com.esotericsoftware.kryo.io.Output
import com.esotericsoftware.kryo.util.DefaultInstantiatorStrategy
import org.objenesis.strategy.StdInstantiatorStrategy
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

internal class NimpleDir(name: String, context: Context) {
    private val directory =
        File(
            context.filesDir.path +
                    File.separator +
                    Constants.ROOT_DIRECTORY_NAME +
                    File.separator +
                    name
        )

    init {
        if (directory.exists().not())
            directory.mkdirs()
    }

    private val kryo: Kryo
        get() = kryos.get()!!

    private var kryos = object : ThreadLocal<Kryo>() {
        override fun initialValue(): Kryo? {
            val kryo = Kryo()
            kryo.register(NimbleStorage::class.java)
            kryo.references = false
            kryo.isRegistrationRequired = false
            kryo.instantiatorStrategy =
                DefaultInstantiatorStrategy(
                    StdInstantiatorStrategy()
                )

            return kryo
        }
    }

    fun <T : Any> put(key: String, value: NimbleStorage<T>) {
        val file = getFileByKey(key)
        val out = Output(
            FileOutputStream(file)
        )
        kryo.writeObject(out, value)
        out.close()
    }

    fun <T : Any> get(key: String): NimbleStorage<T>? {
        val file = getFileByKey(key)

        if (file.exists().not())
            return null

        val input = Input(
            FileInputStream(file)
        )
        val storage = kryo.readObject(input, NimbleStorage::class.java) as NimbleStorage<T>
        input.close()
        return storage
    }

    fun remove(key: String) {
        val file = getFileByKey(key)

        if (file.exists()) {
            file.delete()
        }
    }

    fun removeAll() {
        directory.listFiles()?.forEach {
            it.delete()
        }
        directory.delete()
    }

    fun exists(key: String) = getFileByKey(key).exists()

    val allKeys: ArrayList<String>
        get() {
            val list = ArrayList<String>()
            directory.listFiles { _, name -> name.endsWith(Constants.KS_EXTENSION) }?.forEach {
                list.add(it.name.replace(Constants.KS_EXTENSION, ""))
            }
            return list
        }

    private fun getFileByKey(key: String): File {
        return File(directory.path + File.separator + key + Constants.KS_EXTENSION)
    }
}