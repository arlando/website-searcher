package com.search

import org.junit.Test
import org.junit.Assert.assertEquals

class URLFileReaderTest {

    @Test
    fun testGetURLs() {
        val path = "/test-urls.txt"
        val results = URLFileReader.getURLs(path)
        assertEquals(results.first().get(1), "facebook.com/")
    }

}