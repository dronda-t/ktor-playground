package org.dronda

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsChannel
import io.ktor.http.*
import io.ktor.server.application.call
import io.ktor.server.response.respondOutputStream
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import io.ktor.server.testing.*
import io.ktor.utils.io.jvm.javaio.toInputStream
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import kotlin.test.*
import org.dronda.plugins.*
import java.io.InputStream
import java.net.URI
import java.net.http.HttpClient.Version
import java.net.http.HttpRequest
import java.net.http.HttpResponse.BodyHandlers
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.deleteIfExists
import kotlin.io.path.fileSize
import kotlin.io.path.outputStream

class ApplicationTest {

//    @Test
//    fun testHttpRoute() {
//        runBlocking {
//            main()
//            val testFile = ByteArray(1_000_000) { 1.toByte() }
//
//            val client = HttpClient(OkHttp)
//
//            client.submitFormWithBinaryData(
//                url = "http://localhost:8080/input",
//                formData = formData {
//                    append("data", testFile, Headers.build {
//                        append(HttpHeaders.ContentType, ContentType.Application.OctetStream.toString())
//                        append(HttpHeaders.ContentDisposition, "form-data; name=\"data\"; filename=\"blob\"")
//                    })
//                }
//            ).apply {
//                assertEquals(HttpStatusCode.OK, status)
//                assertEquals(1_000_000, body<ByteArray>().size)
//            }
//        }
//    }
//

//    @Test
//    fun testDownload() = testApplication {
//        val byteCount = 300_000_000
//        application {
//            val testInputStream = object : InputStream() {
//                val sequence = sequence {
//                    var count = byteCount
//                    while (true) {
//                        if (count-- >= 0) {
//                            yield(1)
//                        } else {
//                            yield(-1)
//                        }
//                    }
//                }.iterator()
//
//                override fun read(): Int {
//                    return sequence.next()
//                }
//            }
//
//            routing {
//                get("/test") {
//                    call.respondOutputStream(
//                        status = HttpStatusCode.OK,
//                    ) {
//                        testInputStream.buffered().transferTo(this)
//                    }
//                }
//            }
//
//        }
//
//        val response = client.get("/test")
//        val inputStream = response.body<InputStream>()
//        val file = Files.createTempFile(null, null).apply {
//            deleteOnExit()
//        }
//        file.outputStream().use { os ->
//            inputStream.transferTo(os)
//        }
//
//        assertEquals(file.fileSize(), byteCount.toLong())
//    }

    @Test
    fun test2() = runBlocking {
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
//        val client = HttpClient(OkHttp)
//        val response = client("http://localhost:8080/test")
//        val inputStream = response.bodyAsChannel().toInputStream().buffered()
//        file.outputStream().use { os ->
//            inputStream.use { inst -> inst.transferTo(os) }
//        }

        assertEquals(byteCount.toLong(), file.fileSize())
    }
}

fun Path.deleteOnExit() {
    Runtime.getRuntime().addShutdownHook(Thread {
        deleteIfExists()
    })
}
