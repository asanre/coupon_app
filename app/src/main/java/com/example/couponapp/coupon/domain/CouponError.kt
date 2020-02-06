package com.example.couponapp.coupon.domain


sealed class CouponError : Error() {
    object GetCouponsError : CouponError()
}