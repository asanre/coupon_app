package com.example.couponapp.coupon.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.couponapp.common.extension.transform
import com.example.couponapp.coupon.domain.Coupon
import com.example.couponapp.coupon.domain.CouponError
import com.example.couponapp.coupon.domain.CouponService
import kotlinx.coroutines.launch

class CouponViewModel(private val service: CouponService) : ViewModel() {

  private val _error: MutableLiveData<CouponError> = MutableLiveData()
  val error: LiveData<CouponError> = _error

  private val _couponsState: MutableLiveData<List<Coupon>> = MutableLiveData()
  val state: LiveData<List<Coupon>> = _couponsState

  val activeCouponCount: LiveData<Int> = _couponsState.transform(this::getActiveCouponCount)

  fun getCoupons() = viewModelScope.launch {
    runCatching {
      _couponsState.value = service.getCoupons()
    }.getOrElse {
      _error.value = CouponError.GetCouponsError
    }
  }

  fun couponStateChange(coupon: Coupon) {
    _couponsState.value = _couponsState.value?.map {
      if (it == coupon) it.copy(isActive = !it.isActive)
      else it
    }
  }

  private fun getActiveCouponCount(data: List<Coupon>) = data.filter { it.isActive }.size


}