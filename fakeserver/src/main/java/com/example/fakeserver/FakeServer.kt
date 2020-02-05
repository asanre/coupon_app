package com.example.fakeserver

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.gson.gson
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.netty.util.internal.logging.InternalLoggerFactory
import io.netty.util.internal.logging.JdkLoggerFactory


internal fun main() {
    FakeServer.start()
}

object FakeServer {
    fun start() {
        InternalLoggerFactory.setDefaultFactory(JdkLoggerFactory.INSTANCE)
        embeddedServer(Netty, 8080, module = Application::module).start()
    }
}

var times = 1
private fun Application.module() {
    install(DefaultHeaders)
    install(ContentNegotiation) { gson() }
    install(Routing) {
        get("/") {
            times += 1
            call.respond(mapOf("message" to "Hello world $times"))
        }

        get("/status") {
            call.respond(HttpStatusCode.OK)
        }
    }
}