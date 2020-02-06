package com.example.couponapp.coupon.data.network

import com.example.couponapp.coupon.domain.Coupon
import com.example.couponapp.coupon.domain.Discount
import com.example.couponapp.coupon.domain.Product

data class CouponNetworkDto(
    val id: String,
    val image: String,
    val expiredAt: Int,
    val conditions: String,
    val quantityLimit: Int,

    val productId: String,
    val productName: String,
    val brand: String,
    val productDescription: String,

    val discountAmount: Int,
    val discountType: Int
) {
    fun toEntity(): Coupon = Coupon(
        id = id,
        image = image,
        product = Product(
            id = productId,
            name = productName,
            company = brand,
            description = productDescription
        ),
        discount = Discount(
            amount = discountAmount,
            type = discountType
        ),
        expiredAt = expiredAt,
        conditions = conditions,
        isActive = false,
        quantityLimit = quantityLimit
    )
}