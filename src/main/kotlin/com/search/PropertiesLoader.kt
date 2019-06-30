package com.search

import java.io.FileInputStream
import java.util.Properties

object PropertiesLoader {
    fun load(path: String): Properties {
        val propertiesFile = FileReader.readFile(path)
        val properties = Properties()

        properties.load(propertiesFile)

        return properties
    }
}