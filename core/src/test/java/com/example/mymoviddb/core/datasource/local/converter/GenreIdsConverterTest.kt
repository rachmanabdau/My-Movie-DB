package com.example.mymoviddb.core.datasource.local.converter

import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Test

class GenreIdsConverterTest {

    private lateinit var genreIdsConverter: GenreIdsConverter
    private val genres = mutableListOf<Int>()

    @Before
    fun setupConverter() {
        genreIdsConverter = GenreIdsConverter()
        for (i in 1..3) {
            genres.add(i)
        }
    }

    @After
    fun cleanUpList() {
        genres.clear()
    }

    @Test
    fun saveNonEmptyGenreIdList() {
        // GIVEN saved expected non empty genre list
        val expectedResult = "[1, 2, 3]"
        // WHEN actual non empty genre id list is saved to database
        val savedResult = genreIdsConverter.saveList(genres)
        // THEN should return string same as expectedResult
        MatcherAssert.assertThat(
            savedResult, CoreMatchers.`is`(expectedResult)
        )
    }

    @Test
    fun saveEmptyGenreIdList() {
        // GIVEN saved empty expected genre list
        val expectedResult = "[]"
        // WHEN empty genre list saved to database
        genres.clear()
        val savedEmptyGenreId = genreIdsConverter.saveList(genres)
        // THEN should return string same as expectedResult
        MatcherAssert.assertThat(
            savedEmptyGenreId, CoreMatchers.`is`(expectedResult)
        )
    }

    @Test
    fun getNonEmptyGenreListFromDatabase() {
        // GIVEN expected non empty genre list
        val expectedResult = genres
        // WHEN non empty genre list fetched from database
        val actualResult = genreIdsConverter.restoreList("[1, 2, 3]")
        // THEN string should be converted to list of genre (expectedResult)
        MatcherAssert.assertThat(
            actualResult, CoreMatchers.`is`(expectedResult)
        )
    }

    @Test(expected = NumberFormatException::class)
    fun getEmptyGenreListFromDatabase() {
        // GIVEN expected empty genre list
        genres.clear()
        val expectedResult = genres
        // WHEN  empty genre list fetched from database
        val actualResult = genreIdsConverter.restoreList("[]")
        // THEN string should be converted to list of genre (expectedResult)
        MatcherAssert.assertThat(
            actualResult, CoreMatchers.`is`(expectedResult)
        )
    }
}