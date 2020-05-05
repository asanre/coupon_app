package com.example.couponapp.coupon.data.network

import com.example.couponapp.common.data.adapters.Either
import retrofit2.http.GET

interface CouponApi {
    @GET("/coupons")
    suspend fun getCoupons(): List<CouponNetworkDto>

    @GET("/coupons")
    suspend fun getCouponsEither(): Either<Throwable, List<CouponNetworkDto>>
}