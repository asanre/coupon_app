package com.example.couponapp.coupon.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.couponapp.R
import com.example.couponapp.coupon.presentation.adapter.CouponAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_coupon_list.*
import org.koin.android.ext.android.inject

class CouponListFragment : Fragment(R.layout.fragment_coupon_list) {
    private val couponViewModel: CouponViewModel by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val couponAdapter = CouponAdapter(couponViewModel::couponStateChange) {
            print("on Item")
        }

        couponRv.adapter = couponAdapter
        observeState(couponAdapter)
        couponViewModel.getCoupons()
    }

    private fun observeState(couponAdapter: CouponAdapter) {
        couponViewModel.state.observe(viewLifecycleOwner, Observer {
            couponAdapter.submitList(it)
        })

        couponViewModel.activeCouponCount.observe(viewLifecycleOwner, Observer {
            setTitle(getString(R.string.coupon_list_title, it))
        })

        couponViewModel.error.observe(viewLifecycleOwner, Observer {
            view?.let { Snackbar.make(it, getString(R.string.error_get_coupons), Snackbar.LENGTH_LONG).show() }
        })
    }


    private fun Fragment.setTitle(value: String) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = value
    }
}