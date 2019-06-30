package com.search

import org.junit.Test
import org.junit.Assert.assertTrue

class SearchTest {
    @Test
    fun testCanFindQuery() {
        val appeared = Search.appears("foobazbin", "baz")
        assertTrue("Failure - expected to find baz.", appeared)
    }

    @Test
    fun testCanFindQueryCaseInsensitive() {
        val appeared = Search.appears("foobazbin", "BaZ")
        assertTrue("Failure - expected to find BAZ.", appeared)
    }
}