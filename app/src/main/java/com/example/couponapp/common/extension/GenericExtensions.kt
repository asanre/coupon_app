package com.example.couponapp.common.extension

infix fun <T, R> T?.ifNotNull(block: (T) -> R): R? = this?.let { block(it) }

infix fun <T, R> T.pipe(compose: (T) -> R): R =
  compose(this)
