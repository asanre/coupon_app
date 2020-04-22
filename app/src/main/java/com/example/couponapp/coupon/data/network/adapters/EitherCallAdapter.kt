package com.example.couponapp.coupon.data.network.adapters

import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

sealed class Either<out L, out R> {
    data class Left<out L>(val l: L) : Either<L, Nothing>()
    data class Right<out R>(val r: R) : Either<Nothing, R>()
}

class EitherAdapter(
    private val type: Type
) : CallAdapter<Type, Call<Either<Throwable, Type>>> {
    override fun responseType() = type
    override fun adapt(call: Call<Type>): Call<Either<Throwable, Type>> = EitherCall(call)

    inner class EitherCall<T>(proxy: Call<T>) : CallDelegate<T, Either<Throwable, T>>(proxy) {
        override fun onResponse(response: Response<T>): Either<Throwable, T> =
            response.body().takeIf { it != null && response.code() in 200 until 300 }
                ?.let { Either.Right(it) } ?: Either.Left(
                Throwable(
                    message = response.code().toString()
                )
            )

        override fun onError(error: Throwable): Either<Throwable, T> = Either.Left(error)
        override fun cloneCall(): Call<Either<Throwable, T>> = EitherCall(proxy.clone())
    }
}

class CustomResponseAdapterFactory private constructor() : CallAdapter.Factory() {
    companion object {
        fun create() = CustomResponseAdapterFactory()
    }

    override fun get(
        returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit
    ) = when (getRawType(returnType)) {
        Call::class.java -> {
            val callType = getParameterUpperBound(0, returnType as ParameterizedType)
            when (getRawType(callType)) {
                Either::class.java -> {
                    val resultType = getParameterUpperBound(1, callType as ParameterizedType)
                    EitherAdapter(resultType)
                }
                else -> null
            }
        }
        else -> null
    }
}