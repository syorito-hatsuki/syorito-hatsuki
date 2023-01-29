package dev.syoritohatsuki.plugins

import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.http.content.*
import io.ktor.server.application.*
import java.net.URI

fun Application.configureRouting() {

    routing {
        get("{...}") {
            call.respondText(URI.create("http://localhost:1541/static/index.html").toURL().readText(), ContentType.Text.Html)
        }
        static("/static") {
            resources("static")
        }
    }
}
