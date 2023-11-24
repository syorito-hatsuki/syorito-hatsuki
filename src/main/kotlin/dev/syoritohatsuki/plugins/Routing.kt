package dev.syoritohatsuki.plugins

import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        staticResources("/", "assets", index = "index.html")
        get(".well-known/discord") {
            call.respond("dh=11763d348de6a805982e22214acbf6465ff22fb6")
        }
    }
}
