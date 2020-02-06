package com.example.couponapp.coupon.domain

data class Coupon(
    val id: String,
    val image: String,
    val product: Product,
    val discount: Discount,
    val isActive: Boolean,
    val expiredAt: Int,
    val conditions: String,
    val quantityLimit: Int
)

data class Product(
    val id: String,
    val name: String,
    val company: String,
    val description: String
)

data class Discount(
    val amount: Int,// -21 %
    val type: Int // descuento - segundaUnidad
)