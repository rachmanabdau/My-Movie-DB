package com.example.mymoviddb.core.datasource.local.converter

import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test

class OriginCountryConverterTest {

    private lateinit var origrinCountryConverter: OriginCountryConverter
    private val originCountries = mutableListOf<String>()

    @Before
    fun setupConverter() {
        origrinCountryConverter = OriginCountryConverter()
        originCountries.add("Indonesia")
        originCountries.add("Singapore")
        originCountries.add("Germany")
    }

    @Test
    fun saveNonEmptyOriginCountriesToDatabae() {
        // GIVEN expected non empty origin country list
        val expectedResult = "[Indonesia, Singapore, Germany]"
        // WHEN actual non empty origin country list converted to string
        val actualResult = origrinCountryConverter.saveList(originCountries)
        // THEN actual result should be the same as expected result
        MatcherAssert.assertThat(
            actualResult, CoreMatchers.`is`(expectedResult)
        )
    }

    @Test
    fun saveEmptyOriginCountriesToDatabae() {
        // GIVEN empty expected origin country list
        val expectedResult = "[]"
        // WHEN empty origin country list converted to string
        originCountries.clear()
        val actualResult = origrinCountryConverter.saveList(originCountries)
        // THEN actual result should be the same as expected result
        MatcherAssert.assertThat(
            actualResult, CoreMatchers.`is`(expectedResult)
        )
    }

    @Test
    fun fetchOrigincountriesFromDatabaase() {
        // GIVEN origin countries fetched from database
        val originCountryFromDatabase = "[Indonesia, Singapore, Germany]"
        val expectedResult = originCountries
        // WHEN origin countries converted to list
        val actualResult = origrinCountryConverter.restoreList(originCountryFromDatabase)
        // THEN actual result should be the same as expected result
        MatcherAssert.assertThat(
            actualResult, CoreMatchers.`is`(expectedResult)
        )
    }

    @Test
    fun fetchEmptyOrigincountriesFromDatabaase() {
        // GIVEN emtpy origin countries fetched from database
        val emptyOriginCountryFromDatabase = "[]"
        originCountries.clear()
        val expectedResult = originCountries
        // WHEN origin countries converted to list
        val actualResult = origrinCountryConverter.restoreList(emptyOriginCountryFromDatabase)
        // THEN actual result should be the same as expected result
        MatcherAssert.assertThat(
            actualResult.toString(), CoreMatchers.`is`(expectedResult.toString())
        )
    }

}