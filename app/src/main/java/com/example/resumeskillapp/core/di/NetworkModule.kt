/*
 *  MIT License
 *
 *  Copyright (C) 2022 Serhii Chorpita
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */

package com.example.resumeskillapp.core.di

import com.example.resumeskillapp.data.NetworkApi.BASE_URL
import com.example.resumeskillapp.data.api.PokemonApi
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val CONTENT_TYPE = "Content-Type"
private const val APPLICATION_JSON = "application/json"
private const val TIMEOUT_DELAY = 30L

val networkModule = module {
    single { GsonBuilder().setLenient().create() }
    single { RxJava2CallAdapterFactory.create() }
    single { createOkHttpClient() }
    single { GsonConverterFactory.create(get()) }
    single { createService<PokemonApi>(get(), get(), get()) }
}

private fun createOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .addNetworkInterceptor(createHeaderInterceptor())
        .addInterceptor(createHttpLoggingInterceptor())
        .connectTimeout(TIMEOUT_DELAY, TimeUnit.SECONDS)
        .readTimeout(TIMEOUT_DELAY, TimeUnit.SECONDS)
        .writeTimeout(TIMEOUT_DELAY, TimeUnit.SECONDS)
        .build()
}

private fun createHeaderInterceptor(): Interceptor {
    return Interceptor { chain ->
        val newRequest = chain.request()
            .newBuilder()
            .addHeader(CONTENT_TYPE, APPLICATION_JSON)
            .build()
        chain.proceed(newRequest)
    }
}

private fun createHttpLoggingInterceptor(): Interceptor {
    return HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
}

private inline fun <reified T> createService(
    okHttpClient: OkHttpClient,
    converterFactory: GsonConverterFactory,
    adapterFactory: RxJava2CallAdapterFactory,
    baseUrl: String = BASE_URL
): T {
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(converterFactory)
        .addCallAdapterFactory(adapterFactory)
        .build()
        .create(T::class.java)
}
