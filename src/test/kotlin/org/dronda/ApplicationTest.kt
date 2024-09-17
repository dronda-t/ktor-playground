package org.dronda

import kotlinx.coroutines.runBlocking
import java.net.URI
import java.net.http.HttpRequest
import java.net.http.HttpResponse.BodyHandlers
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.deleteIfExists
import kotlin.io.path.fileSize
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {
    @Test
    fun testDownload() = runBlocking {
        val file = Files.createTempFile(null, null).apply {
            deleteOnExit()
        }
        val byteCount = 1_000_000_000
        val client = java.net.http.HttpClient.newHttpClient()
        client.send(
            HttpRequest.newBuilder(URI("http://localhost:8080/test"))
                .GET()
                .build(),
            BodyHandlers.ofFile(file)
        )

        assertEquals(byteCount.toLong(), file.fileSize())
    }
}

fun Path.deleteOnExit() {
    Runtime.getRuntime().addShutdownHook(Thread {
        deleteIfExists()
    })
}
