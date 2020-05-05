package com.example.couponapp.common.extension

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations

fun <X, Y> LiveData<X>.transform(transform: (X) -> Y) =
  Transformations.map(this) { transform(it) }


fun <T> MutableLiveData<T>.update(block: (T?) -> T) {
  value = block(value)
}