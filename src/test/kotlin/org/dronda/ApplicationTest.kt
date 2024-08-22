package org.dronda

import io.ktor.client.request.*
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.*
import org.dronda.plugins.*

class ApplicationTest {
    @Test
    fun testRoot() = testApplication {
        val testFile = Thread.currentThread().contextClassLoader.getResourceAsStream("test.txt")!!
            .readAllBytes()

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
            assertEquals("test", bodyAsText())
        }
    }
    /*

        val response = client.submitFormWithBinaryData(
            url = "/file/upload/part/${jobId}",
            formData = io.ktor.client.request.forms.formData {
                append("data", data, Headers.build {
                    append(HttpHeaders.ContentType, ContentType.Application.OctetStream.toString())
                    append(HttpHeaders.ContentDisposition, "form-data; name=\"data\"; filename=\"blob\"")
                })
            }
        ) {
            onUpload { bytesSentTotal, contentLength -> onUploadProgress(bytesSentTotal, contentLength) }
        }
     */
}
