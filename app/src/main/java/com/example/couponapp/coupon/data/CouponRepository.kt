package com.example.couponapp.coupon.data

import com.example.couponapp.coupon.data.network.CouponApi
import com.example.couponapp.coupon.domain.Coupon
import com.example.couponapp.coupon.domain.ICouponRepository

class CouponRepository(
    private val api: CouponApi
) : ICouponRepository {

    override suspend fun getCoupons(): List<Coupon> = api.getCoupons().map { it.toEntity() }
}