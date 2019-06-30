package com.search

/**
 * Searches through text for a given query string
 */
object Search {
    /**
     *  Returns true if the query string appears in the content. False if the string does not.
     *  @param content - The raw content of the URL.
     *  @param query - The search query.
     *  @return true if the query string is inside of the content.
     */
    fun appears(content: String, query: String): Boolean {
        return content.contains(
            query,
            ignoreCase = true
        )
    }
}