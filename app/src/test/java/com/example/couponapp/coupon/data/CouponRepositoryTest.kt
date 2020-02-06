package com.example.couponapp.coupon.data

import com.example.couponapp.coupon.couponModule
import com.example.couponapp.coupon.domain.ICouponRepository
import com.example.couponapp.di.koinNetworkModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*
import org.junit.Assert.assertEquals
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject


@ExperimentalCoroutinesApi
class CouponRepositoryTest : KoinTest {

    val sut: ICouponRepository by inject()

    @Before
    fun before() {
        startKoin {
            modules(listOf(koinNetworkModule, couponModule))
        }
    }

    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun `should return a list of coupon with size equals 1`() = runBlockingTest {
        val result = sut.getCoupons()
        assertEquals(1, result.size)
    }
}