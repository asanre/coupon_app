package com.example.couponapp.common.data.adapters

import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class CallDelegate<T, R>(
    protected val proxy: Call<T>
) : Call<R> {
    override fun execute(): Response<R> = throw NotImplementedError()
    override fun enqueue(callback: Callback<R>) =


        proxy.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) =
                callback.onResponse(
                    this@CallDelegate,
                    Response.success(onResponse(response))
                )

            override fun onFailure(call: Call<T>, error: Throwable) =
                callback.onResponse(this@CallDelegate, Response.success(onError(error)))
        })


    override fun clone(): Call<R> = cloneCall()
    override fun cancel() = proxy.cancel()
    override fun request(): Request = proxy.request()
    override fun isExecuted() = proxy.isExecuted
    override fun isCanceled() = proxy.isCanceled

    abstract fun onResponse(response: Response<T>): R
    abstract fun onError(error: Throwable): R
    abstract fun cloneCall(): Call<R>
}
