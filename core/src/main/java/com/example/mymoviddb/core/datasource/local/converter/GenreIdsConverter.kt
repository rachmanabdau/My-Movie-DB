package com.example.mymoviddb.core.datasource.local.converter

import androidx.room.TypeConverter

class GenreIdsConverter {
    @TypeConverter
    fun restoreList(genreList: String): List<Int> {
        return genreList.replace("[", "")
            .replace("]", "")
            .split(", ")
            .map { it.toInt() }
    }

    @TypeConverter
    fun saveList(genreList: List<Int>): String {
        return genreList.toString()
    }
}