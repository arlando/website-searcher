package com.search

import org.junit.Test
import org.junit.Assert.assertEquals
import java.io.File

class ResultWriterTest {

    val TEST_PATH = "src/test/resources/test-results.txt"

    @Test
    fun testWriteFile() {
        val number = Math.random().toString()
        ResultWriter.writeFile(TEST_PATH, listOf(number))

        val file = File(TEST_PATH).readLines()[0]
        assertEquals(file, number)
    }

}