package com.example.couponapp

import android.app.Application
import com.example.fakeserver.FakeServer

class CouponApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startFakeServer()
    }

    private fun startFakeServer() {
        FakeServer.start()
    }
}