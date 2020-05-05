package com.example.couponapp.common.extension


//region Compose functions
infix fun <A, B, C> ((A) -> B).andThen(compose: (B) -> C): (A) -> C =
  { t -> compose(this(t)) }
//endregion

