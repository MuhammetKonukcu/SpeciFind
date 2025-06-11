package com.muhammetkonukcu.specifind.room

import androidx.room.TypeConverter
import kotlin.jvm.JvmStatic

object Converter {
    private const val DELIMITER = "||"

    @TypeConverter
    @JvmStatic
    fun fromPair(pair: Pair<String, String>?): String? {
        return pair?.let { "${it.first}$DELIMITER${it.second}" }
    }

    @TypeConverter
    @JvmStatic
    fun toPair(value: String?): Pair<String, String>? {
        if (value.isNullOrEmpty()) return null
        val parts = value.split(DELIMITER)
        val first = parts.getOrNull(0) ?: ""
        val second = parts.getOrNull(1) ?: ""
        return first to second
    }
}