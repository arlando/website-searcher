package com.search

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.apache.commons.csv.CSVRecord
import java.util.concurrent.CopyOnWriteArraySet

val NUMBER_OF_THREADS = 20


@kotlinx.coroutines.ExperimentalCoroutinesApi
fun main(args: Array<String>) {

    // Load in the properties
    val properties = PropertiesLoader.load("/config.properties")
    val numberOfThreads = properties.getProperty("threads").toInt()
    val urlFilePath = properties.getProperty("urlFilePath")
    val resultsFilePath = properties.getProperty("resultsFilePath")

    val searchTerm = getSearchTerm(args)
    log("Searching for term: ${searchTerm}")

    val set = CopyOnWriteArraySet<String>()

    runBlocking {

        val urls = URLFileReader.getURLs(urlFilePath)

        val searchChannel = produce(CoroutineName("request")) {
            for (url in urls) {
                send(url)
            }
        }

        // Launch the search coroutines
        withContext(Dispatchers.IO) {
            coroutineScope {
                repeat(numberOfThreads) {
                    launch {
                        searchPage(searchChannel, searchTerm, set)
                    }
                }
            }

            // Write results
            ResultWriter.writeFile(resultsFilePath, set)
        }
    }
}

/**
 * Searches a page for a given query string
 */
private suspend fun searchPage(queriesChannel: ReceiveChannel<CSVRecord>, searchString: String,
                               set: CopyOnWriteArraySet<String>) {
    for (query in queriesChannel) {
        val url = "https://${query.get(1)}"

        val r = URLFetcher.getURLContent(url)

        if (Search.appears(r, searchString)) {
            set.add(url)
        }
    }
}

private fun getSearchTerm(args: Array<String>): String {
    var term = "foo"
    if (args.isNotEmpty()) {
        term = args[0]
    }

    return term
}

fun log(msg: String) { println("[${Thread.currentThread().name}] $msg")}
