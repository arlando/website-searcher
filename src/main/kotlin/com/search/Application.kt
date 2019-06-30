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
    var SEARCH_TERM = "foo"
    if (args.isNotEmpty()) {
        SEARCH_TERM = args[0]
    }

    log("Searching for term: ${SEARCH_TERM}")

    val set = CopyOnWriteArraySet<String>()

    runBlocking {

        val urls = URLFileReader.getURLs("/urls.txt")

        val searchChannel = produce(CoroutineName("request")) {
            for (url in urls) {
                send(url)
            }
        }

        // Launch the search coroutines
        withContext(Dispatchers.IO) {
            coroutineScope {
                repeat(NUMBER_OF_THREADS) {
                    launch {
                        searchPage(searchChannel, SEARCH_TERM, set)
                    }
                }
            }

            // Write results
            ResultWriter.writeFile("results.txt", set)
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

fun log(msg: String) { println("[${Thread.currentThread().name}] $msg")}
