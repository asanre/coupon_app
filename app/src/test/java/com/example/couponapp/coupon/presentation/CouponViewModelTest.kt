package com.example.couponapp.coupon.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.couponapp.coupon.domain.*
import com.example.couponapp.util.CoroutineTestRule
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CouponViewModelTest {
    @get:Rule
    val instantExecutor = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private val service = mockk<CouponService>()

    private lateinit var viewModel: CouponViewModel

    private val activeCountObserver = mockk<Observer<Int>>(relaxed = true)


    @Before
    fun setUp() {
        viewModel = CouponViewModel(service)
        viewModel.activeCouponCount.observeForever(activeCountObserver)
    }


    @Test
    fun `given not active coupons when activate a coupon then the activeCouponCount should increase to 1`() {
        val coupons = getCoupons()
        coEvery {
            service.getCoupons()
        } returns coupons

        viewModel.getCoupons()
        verify { activeCountObserver.onChanged(0) }
        viewModel.couponStateChange(coupons.first())
        verify { activeCountObserver.onChanged(1) }
    }

    @Test
    fun `given all active coupons when activate a coupon then the activeCouponCount should decrement  1`() {
        val coupons = getCoupons().map { it.copy(isActive = true) }
        coEvery {
            service.getCoupons()
        } returns coupons

        viewModel.getCoupons()
        verify { activeCountObserver.onChanged(2) }
        viewModel.couponStateChange(coupons.first())
        verify { activeCountObserver.onChanged(1) }
    }


    @Test
    fun `given a success request to getCoupons then state should update with emptyList`() {
        val couponsObserver = mockk<Observer<List<Coupon>>>(relaxed = true)
        viewModel.state.observeForever(couponsObserver)

        coEvery {
            service.getCoupons()
        } returns emptyList()

        viewModel.state.observeForever(couponsObserver)
        viewModel.getCoupons()

        verify { couponsObserver.onChanged(emptyList()) }
    }

    @Test
    fun `given a getCoupons with error then error state should update with GetCouponsError`() {
        val errorObserver = mockk<Observer<CouponError>>(relaxed = true)
        viewModel.error.observeForever(errorObserver)

        coEvery {
            service.getCoupons()
        } throws CouponError.GetCouponsError

        viewModel.getCoupons()

        verify { errorObserver.onChanged(CouponError.GetCouponsError) }
    }

    private fun getCoupons(): List<Coupon> {
        return listOf(
            Coupon(
                id = "123",
                image = "",
                isActive = false,
                expiredAt = 1,
                conditions = "",
                quantityLimit = 1,
                product = Product("", "platano", "banana", ""),
                discount = Discount(12, 1)
            ), Coupon(
                id = "1234",
                image = "",
                isActive = false,
                expiredAt = 1,
                conditions = "",
                quantityLimit = 1,
                product = Product("", "patata", "gallega", ""),
                discount = Discount(12, 1)
            )
        )
    }

}

