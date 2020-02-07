package com.example.couponapp.coupon.data

import com.example.couponapp.coupon.couponModule
import com.example.couponapp.coupon.data.network.CouponApi
import com.example.couponapp.coupon.domain.CouponError
import com.example.couponapp.di.koinNetworkModule
import com.example.fakeserver.FakeServer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
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
    fun `given a getCoupons request should return a list of coupons`() =
        runBlocking {
            FakeServer.start()

            assert(sut.getCoupons().isNotEmpty())

            FakeServer.stop()
        }

    @Test(expected = CouponError.GetCouponsError::class)
    fun `given a getCoupons request with exception should return a GetCouponsError`() =
        runBlocking {
            sut.getCoupons()
            Unit
        }
}