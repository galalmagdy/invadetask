package com.example.invadetask.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromString(value: String?): List<String> {
        return if (value != null) {
            val listType = object : TypeToken<List<String>>() {}.type
            Gson().fromJson(value, listType)
        } else {
            listOf()
        }
    }

    @TypeConverter
    fun fromList(list: List<String>?): String {
        return Gson().toJson(list ?: emptyList<String>())
    }
}