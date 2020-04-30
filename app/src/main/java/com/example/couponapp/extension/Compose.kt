package com.example.couponapp.extension


//region Compose
infix fun <A, B, C> ((A) -> B).andThen(compose: (B) -> C): (A) -> C =
    { t -> compose(this(t)) }

infix fun <T, R> T.pipe(compose: (T) -> R): R =
    compose(this)
//endregion


infix fun <T, R> T?.ifNotNull(block: (T) -> R): R? = this?.let { block(it) }
