package org.dronda.plugins

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.InputStream

fun Application.configureRouting() {
    val byteCount = 1_000_000_000
    fun testStream(): InputStream {
        return object : InputStream() {
            val sequence = sequence {
                var count = byteCount
                while (true) {
                    if (--count >= 0) {
                        yield(1)
                    } else {
                        yield(-1)
                    }
                }
            }.iterator()

            override fun read(): Int {
                return sequence.next()
            }

        }
    }

    routing {
        get("/test") {
            call.respondOutputStream(
                status = HttpStatusCode.OK,
            ) {
                testStream().buffered().transferTo(this)
            }
        }
    }
}
