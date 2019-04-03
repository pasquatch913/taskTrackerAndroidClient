package com.example.tasktrackerclient.rest

import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor(val credentials: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = request.newBuilder()
            .addHeader("Authorization", credentials)
            .build()
        return chain.proceed(newRequest)
    }
}