package com.astrainteractive.synk.utils

import org.bukkit.util.io.BukkitObjectInputStream
import org.bukkit.util.io.BukkitObjectOutputStream
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.*

object Serializer {
    private fun <T> toByteArray(obj: T): ByteArray {
        val io = ByteArrayOutputStream()
        val os = BukkitObjectOutputStream(io)
        os.writeObject(obj)
        os.flush()
        return io.toByteArray()
    }

    private fun <T> fromByteArray(byteArray: ByteArray): T? = kotlin.runCatching {
        val _in = ByteArrayInputStream(byteArray)
        val _is = BukkitObjectInputStream(_in)
        _is.readObject() as T
    }.getOrNull()

    fun <T> toBase64(obj: T): String {
        val encoder = Base64.getEncoder()
        return encoder.encodeToString(toByteArray(obj))
    }

    fun <T> fromBase64(string: String): T? = kotlin.runCatching {
        fromByteArray<T>(Base64.getDecoder().decode(string))
    }.getOrNull()

    inline fun <reified T> encodeList(objects: List<T>): String {
        return Serializer.toBase64(objects)
    }

    inline fun <reified T> decodeList(encoded: String): List<T> {
        return Serializer.fromBase64(encoded) ?: emptyList()
    }
}