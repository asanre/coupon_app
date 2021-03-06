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
import io.ktor.server.netty.NettyApplicationEngine
import io.netty.util.internal.logging.InternalLoggerFactory
import io.netty.util.internal.logging.JdkLoggerFactory
import java.util.*
import java.util.concurrent.TimeUnit


internal fun main() {
    FakeServer.start()
}

object FakeServer {
    private var fakeServer: NettyApplicationEngine? = null

    fun start() {
        InternalLoggerFactory.setDefaultFactory(JdkLoggerFactory.INSTANCE)
        fakeServer = embeddedServer(Netty, 8080, module = Application::module)
        fakeServer?.start()
    }

    fun stop() {
        fakeServer?.stop(1000, 1500, TimeUnit.MILLISECONDS)
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

        get("/coupons") {
            call.respond(HttpStatusCode.OK, coupons)
        }
    }
}

private val coupons
    get() = listOf(
        Coupon(
            id = UUID.randomUUID().toString(),
            image = "",
            expiredAt = 2,
            conditions = "",
            quantityLimit = 2,
            productId = 2,
            productName = "Plátano de Canarias",
            brand = "",
            productDescription = "",
            discountAmount = 23,
            discountType = 1
        ), Coupon(
            id = UUID.randomUUID().toString(),
            image = "",
            expiredAt = 5,
            conditions = "",
            quantityLimit = 2,
            productId = 2,
            productName = "Patatas sabor jamón",
            brand = "",
            productDescription = "",
            discountAmount = 23,
            discountType = 1
        ), Coupon(
            id = UUID.randomUUID().toString(),
            image = "",
            expiredAt = 1,
            conditions = "",
            quantityLimit = 2,
            productId = 2,
            productName = "Ensalada gurmet",
            brand = "",
            productDescription = "",
            discountAmount = 23,
            discountType = 1
        )
    )

private data class Coupon(
    val id: String,
    val image: String,
    val expiredAt: Int,
    val conditions: String,
    val quantityLimit: Int,

    val productId: Int,
    val productName: String,
    val brand: String,
    val productDescription: String,

    val discountAmount: Int,
    val discountType: Int
)