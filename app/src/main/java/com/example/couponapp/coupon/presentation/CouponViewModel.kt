package com.example.couponapp.coupon.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.couponapp.coupon.domain.Coupon
import com.example.couponapp.coupon.domain.CouponError
import com.example.couponapp.coupon.domain.CouponService
import kotlinx.coroutines.launch

class CouponViewModel(private val service: CouponService) : ViewModel() {

    private val _couponsState: MutableLiveData<List<Coupon>> = MutableLiveData()
    val state: LiveData<List<Coupon>> = _couponsState

    private val _error: MutableLiveData<CouponError> = MutableLiveData()
    val error: LiveData<CouponError> = _error

    fun getCoupons() = viewModelScope.launch {
        runCatching {
            _couponsState.value = service.getCoupons()
        }.getOrElse {
            _error.value = CouponError.GetCouponsError
        }
    }

}