package com.example.couponapp.coupon.domain

class CouponService(private val repository: ICouponRepository) {
    suspend fun getCoupons() = repository.getCoupons()
}