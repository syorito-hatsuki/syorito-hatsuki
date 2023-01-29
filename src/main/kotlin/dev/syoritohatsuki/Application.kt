package dev.syoritohatsuki

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.cio.*
import dev.syoritohatsuki.plugins.*

fun main() {
    embeddedServer(CIO, port = 1541, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureRouting()
}
