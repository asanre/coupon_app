package com.example.couponapp.coupon

import com.example.couponapp.coupon.data.CouponRepository
import com.example.couponapp.coupon.data.network.CouponApi
import com.example.couponapp.coupon.domain.CouponService
import com.example.couponapp.coupon.domain.ICouponRepository
import com.example.couponapp.coupon.presentation.CouponViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val couponModule = module {
    single { get<Retrofit>().create(CouponApi::class.java) }
    single { CouponRepository(get()) as ICouponRepository }

    single { CouponService(get()) }
    viewModel { CouponViewModel(get()) }
}