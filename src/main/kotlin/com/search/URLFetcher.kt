package com.search

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.coroutines.awaitString

object URLFetcher {
    /**
     * Retrieves the raw URL content for a given string. Does not render the page.
     * If the request fails for any reason it will return a blank string.
     * TODO - Investigate using retry logic to reattempt request
     * @param url The URL string which to get the content from.
     * @return An async HTTP GET request to the given URL.
     */
    suspend fun getURLContent(url: String): String {
        try {
            return Fuel.get(url).awaitString()
        } catch (exception: Exception) {
            return ""
        }
    }
}