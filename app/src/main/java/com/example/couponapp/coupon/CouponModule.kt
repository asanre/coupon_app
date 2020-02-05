package com.example.couponapp.coupon

import com.example.couponapp.coupon.data.CouponRepository
import com.example.couponapp.coupon.data.network.CouponApi
import org.koin.dsl.module
import retrofit2.Retrofit

val couponModule = module {
    single { get<Retrofit>().create(CouponApi::class.java) }

    single { CouponRepository(get()) }
}