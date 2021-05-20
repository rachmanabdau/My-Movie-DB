package com.example.mymoviddb.core.model.category.tv

import com.example.mymoviddb.core.model.ShowResult


abstract class TvField : ShowResult() {
    abstract val firstAirDate: String?
    abstract val originCountry: List<String>
}