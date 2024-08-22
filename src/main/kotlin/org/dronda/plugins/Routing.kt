package org.dronda.plugins

import io.ktor.http.HttpStatusCode
import io.ktor.http.content.PartData
import io.ktor.http.content.forEachPart
import io.ktor.server.application.*
import io.ktor.server.request.receiveMultipart
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.asStream
import java.io.InputStream

fun Application.configureRouting() {
    routing {
        post("/input") {
            var stream: InputStream? = null
            call.receiveMultipart().forEachPart { part ->
                when (part) {
                    is PartData.FileItem -> {
                        stream = part.provider().asStream()
                    }
                    else -> {}
                }
            }

            if (stream != null) {
                call.respondText(stream!!.readAllBytes().decodeToString())
            } else {
                call.respondText("Failed", status = HttpStatusCode.InternalServerError)
            }
        }
    }
}
