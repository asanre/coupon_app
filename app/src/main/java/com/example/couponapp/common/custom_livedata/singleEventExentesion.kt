package com.example.couponapp.common.custom_livedata

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer


fun <T> LiveData<out Event<T>>.observeEvent(owner: LifecycleOwner, onEventUnhandled: (T) -> Unit) {
  observe(owner, Observer {
    it.getValue()?.also<T>(onEventUnhandled)
  })
}

fun LiveData<out VoidEvent>.observeEvent(owner: LifecycleOwner, onEventUnhandled: () -> Unit) {
  observe(owner, Observer { if (!it.hasBeenHandled()) onEventUnhandled() })
}

fun <T> SingleLiveEvent<T>.updateEvent(transform: (T?) -> T?) {
  value = transform(value?.peekContent())?.toSingleEvent()
}