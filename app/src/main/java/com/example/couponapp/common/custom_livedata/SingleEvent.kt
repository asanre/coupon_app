package com.example.couponapp.common.custom_livedata

import androidx.lifecycle.MutableLiveData
import java.util.concurrent.atomic.AtomicBoolean

typealias SingleLiveEvent<T> = MutableLiveData<Event<T>>
typealias SingleVoidEvent = MutableLiveData<VoidEvent>


class Event<out T>(
  private val value: T
) {

  private val isConsumed = AtomicBoolean(false)

  internal fun getValue(): T? =
    if (isConsumed.compareAndSet(false, true)) value
    else null

  fun peekContent(): T = value
}

fun <T> T.toSingleEvent() =
  Event(this)

class VoidEvent {
  private var hasBeenHandled = false

  fun hasBeenHandled(): Boolean = if (hasBeenHandled) {
    true
  } else {
    hasBeenHandled = true
    false
  }
}
