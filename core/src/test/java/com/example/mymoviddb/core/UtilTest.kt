package com.example.mymoviddb.core

import com.example.mymoviddb.core.utils.Util
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class UtilTest {

    @Test
    fun `convert time to milli second`() {
        // Time in milli second for 2020-11-22 01:00:00
        val expectedOutput = 1605981600000L
        val dateSample = "2020-11-22 01:00:00"
        val actualOutput = Util.convertStringTimeToMills(dateSample)

        assertThat(
            actualOutput, `is`(expectedOutput)
        )
    }
}