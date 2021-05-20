package com.example.mymoviddb.core.model.category.movie

import com.example.mymoviddb.core.model.ShowResult

abstract class MovieField : ShowResult() {
    abstract val adult: Boolean
    abstract val releaseDate: String?
    abstract val video: Boolean
}