package com.example.couponapp.common.extension

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer


var Fragment.actionBarTitle: CharSequence
  get() = (activity as? AppCompatActivity)?.supportActionBar?.title ?: ""
  set(value) {
    (activity as? AppCompatActivity)?.supportActionBar?.title = value
  }

inline fun <T> Fragment.observe(liveData: LiveData<T>, crossinline onChanged: (T) -> Unit) {
  liveData.observe(viewLifecycleOwner, Observer {
    onChanged(it)
  })
}