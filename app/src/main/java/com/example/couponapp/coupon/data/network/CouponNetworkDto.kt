package com.example.couponapp.coupon.data.network

data class CouponNetworkDto(
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