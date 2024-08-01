package com.example.newsapp.services.room.converter

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class NewsTypeConverter {
    private val json = Json {
        isLenient = true
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    @TypeConverter
    fun fromString(value: String): List<String> {
        return json.decodeFromString<List<String>>(value)
    }

    @TypeConverter
    fun fromList(list: List<String>): String {
        return json.encodeToString<List<String>>(list)
    }

}