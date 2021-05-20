package com.example.mymoviddb.core.datasource.local.converter

import androidx.room.TypeConverter

class OriginCountryConverter {
    @TypeConverter
    fun restoreList(originLaguage: String): List<String?> {
        return originLaguage.replace("[", "")
            .replace("]", "")
            .split(", ")
    }

    @TypeConverter
    fun saveList(originLaguageList: List<String>): String {
        return originLaguageList.toString()
    }
}