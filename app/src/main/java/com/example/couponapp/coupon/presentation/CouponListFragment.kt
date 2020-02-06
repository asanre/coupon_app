package com.example.couponapp.coupon.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.couponapp.R
import com.example.couponapp.coupon.presentation.adapter.CouponAdapter
import kotlinx.android.synthetic.main.fragment_coupon_list.*
import org.koin.android.ext.android.inject

class CouponListFragment : Fragment(R.layout.fragment_coupon_list) {
    private val couponViewModel: CouponViewModel by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val couponAdapter = CouponAdapter({
            print("on Item")
        }, {
            print("on activate")
        })
        couponRv.adapter = couponAdapter

        couponViewModel.state.observe(viewLifecycleOwner, Observer {
            couponAdapter.submitList(it)
        })

        couponViewModel.getCoupons()
    }
}