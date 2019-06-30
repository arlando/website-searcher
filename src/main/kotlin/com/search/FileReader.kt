package com.search

import java.io.InputStream

object FileReader {
    fun readFile(path: String): InputStream {
        return this.javaClass.getResourceAsStream(path)
    }
}