package com.search

import java.io.File

/**
 * Writes a file to the file system with the given file name.
 */
object ResultWriter {
    fun writeFile(fileName: String, collection: Collection<String>) {
        val file = File(fileName)

        // overwrite file if it exists
        file.writeText("")

        for (result in collection) {
            file.appendText(result + "\n")
        }
    }
}