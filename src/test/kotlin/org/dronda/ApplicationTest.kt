package org.dronda

import io.ktor.client.call.body
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.*
import org.dronda.plugins.*

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

    @Test
    fun testMultipartUpload() = testApplication {
//        val byteCount = 10
        val byteCount = 1_000_000
        val testFile = ByteArray(byteCount) { 1.toByte() }

        application {
            configureRouting()
        }
        client.submitFormWithBinaryData(
            url = "/input",
            formData = formData {
                append("data", testFile, Headers.build {
                    append(HttpHeaders.ContentType, ContentType.Application.OctetStream.toString())
                    append(HttpHeaders.ContentDisposition, "form-data; name=\"data\"; filename=\"blob\"")
                })
            }
        ).apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals(byteCount, body<ByteArray>().size)
        }
    }
}
