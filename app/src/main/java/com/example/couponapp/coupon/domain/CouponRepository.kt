package com.example.couponapp.coupon.domain

interface ICouponRepository {
    suspend fun getCoupons(): List<Coupon>
}