package com.search

import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import java.io.InputStream
import java.lang.RuntimeException
import java.util.*

object URLFileReader {
    /**
     * Gets the urls out of a CSV file
     * @param path - file path to csv txt file
     */
    fun getURLs(path: String): CSVParser {
        val stream: InputStream = this.javaClass.getResourceAsStream(path)
//        val stream: InputStream = this.javaClass.getResourceAsStream(path)
//        val stream = javaClass.getResourceAsStream(path)
        val reader = stream.bufferedReader()
        if (!Objects.isNull(reader)) {
            // TODO: not sure if CSVParser will automatically close the file descriptor
            return CSVParser(reader, CSVFormat.DEFAULT)
        }
        throw RuntimeException("The requested file $path not found.")
    }
}