package com.example.couponapp.coupon.data.network

import retrofit2.http.GET

interface CouponApi {
    @GET("/coupons")
    suspend fun getCoupons(): List<CouponNetworkDto>
}