package com.example.couponapp.coupon.data.network.adapters

import kotlinx.io.IOException
import okhttp3.Request
import retrofit2.*
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

data class Failure(val statusCode: Int? = null, override val cause: Throwable? = null) : Throwable()
object NetworkError : Throwable(message = "NetworkError")

abstract class CallDelegate<TIn, TOut>(
    protected val proxy: Call<TIn>
) : Call<TOut> {
    override fun execute(): Response<TOut> = throw NotImplementedError()
    override fun enqueue(callback: Callback<TOut>) = enqueueImpl(callback)
    override fun clone(): Call<TOut> = cloneImpl()

    override fun cancel() = proxy.cancel()
    override fun request(): Request = proxy.request()
    override fun isExecuted() = proxy.isExecuted
    override fun isCanceled() = proxy.isCanceled

    abstract fun enqueueImpl(callback: Callback<TOut>)
    abstract fun cloneImpl(): Call<TOut>
}

open class CallWrapper<T, TOut>(proxy: Call<T>) : CallDelegate<T, TOut>(proxy) {
    override fun enqueueImpl(callback: Callback<TOut>) = proxy.enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) =
            callback.onResponse(this@CallWrapper, Response.success(onResponse(response)))

        override fun onFailure(call: Call<T>, t: Throwable) =
            callback.onResponse(this@CallWrapper, Response.success(onFailure(t)))
    })

    override fun cloneImpl() =
        CallWrapper<T, TOut>(proxy.clone())

    open fun onFailure(t: Throwable): TOut {
        throw NotImplementedError()
    }

    open fun onResponse(response: Response<T>): TOut {
        throw NotImplementedError()
    }
}

class ResultAdapterFactory private constructor() : CallAdapter.Factory() {
    companion object {
        fun create() =
            ResultAdapterFactory()
    }

    override fun get(
        returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit
    ) = when (getRawType(returnType)) {
        Call::class.java -> {
            val callType = getParameterUpperBound(0, returnType as ParameterizedType)
            when (getRawType(callType)) {
                Result::class.java -> {
                    val resultType = getParameterUpperBound(0, callType as ParameterizedType)
                    ResultAdapter(resultType)
                }
                else -> null
            }
        }
        else -> null
    }
}

class ResultAdapter(
    private val type: Type
) : CallAdapter<Type, Call<Result<Type?>>> {
    override fun responseType() = type
    override fun adapt(call: Call<Type>): Call<Result<Type?>> =
        object : CallWrapper<Type, Result<Type?>>(call) {
            override fun onFailure(t: Throwable): Result<Type?> =
                when (t) {
                    is IOException -> Result.failure(NetworkError)
                    else -> Result.failure(Failure(cause = t))
                }

            override fun onResponse(response: Response<Type>) = response.let {
                if (it.code() in 200 until 300) {
                    val body = response.body()
                    Result.success(body)
                } else {
                    Result.failure(Failure(it.code()))
                }
            }
        }
}