package com.example.couponapp

import android.app.Application
import com.example.couponapp.coupon.couponModule
import com.example.couponapp.di.koinNetworkModule
import com.example.fakeserver.FakeServer
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CouponApp : Application() {
    private val koinModules = listOf(
        koinNetworkModule,
        couponModule
    )

    override fun onCreate() {
        super.onCreate()
        initializeKoin()
        startFakeServer()
    }

    private fun initializeKoin() {
        startKoin {
            androidContext(this@CouponApp)
            modules(koinModules)
        }
    }

    private fun startFakeServer() {
        FakeServer.start()
    }
}