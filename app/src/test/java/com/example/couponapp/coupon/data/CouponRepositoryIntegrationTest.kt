package com.example.couponapp.coupon.data

import com.example.couponapp.coupon.couponModule
import com.example.couponapp.coupon.data.network.CouponApi
import com.example.couponapp.coupon.domain.CouponError
import com.example.couponapp.di.koinNetworkModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject


@ExperimentalCoroutinesApi
class CouponRepositoryIntegrationTest : KoinTest {


    private val api by inject<CouponApi>()
    private lateinit var sut: CouponRepository

    @Before
    fun before() {
        startKoin { modules(listOf(koinNetworkModule, couponModule)) }
        sut = CouponRepository(api)
    }

    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun `given getCoupons request should return a list of coupon with size equals 1`() =
        runBlocking {
            val result = sut.getCoupons()
            assertEquals(1, result.size)
        }

    @Test(expected = CouponError.GetCouponsError::class)
    fun `given a getCoupons request with exception should return a GetCouponsError`() =
        runBlocking {
            sut.getCoupons()
            Unit
        }
}