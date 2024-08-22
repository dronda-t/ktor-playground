package org.dronda.plugins

import io.ktor.http.HttpStatusCode
import io.ktor.http.content.PartData
import io.ktor.http.content.forEachPart
import io.ktor.server.application.*
import io.ktor.server.request.receiveMultipart
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.utils.io.readRemaining
import kotlinx.io.readByteArray

fun Application.configureRouting() {
    routing {
        post("/input") {
            var stream: ByteArray? = null
            call.receiveMultipart().forEachPart { part ->
                when (part) {
                    is PartData.FileItem -> {
                        stream = part.provider().readRemaining().readByteArray()
                    }

                    else -> {}
                }
            }

            if (stream != null) {
                call.respondBytes(stream!!)
            } else {
                call.respondText("Failed", status = HttpStatusCode.InternalServerError)
            }
        }
    }
}
