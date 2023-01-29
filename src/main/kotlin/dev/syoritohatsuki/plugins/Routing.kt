package dev.syoritohatsuki.plugins

import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        static("/") {
            staticBasePackage = "static"
            defaultResource("index.html")
        }
    }
}
