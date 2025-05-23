package com.example.contacts.di.modules

import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.contacts.MainApplication
import com.example.contacts.common.Const
import com.example.contacts.data.network.ApiInterface
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val apis = module {
    single { provideBaseRetrofit(get()) }
    single { provideOkHttpClient() }

    single { provideBaseRetrofit(get()).create(ApiInterface::class.java) }
}


fun provideBaseRetrofit(okHttpClient: OkHttpClient): Retrofit =
    Retrofit.Builder()
        .baseUrl(Const.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

fun provideOkHttpClient(): OkHttpClient {
    val chuckerInterceptor = ChuckerInterceptor.Builder(MainApplication.appContext)
        // The previously created Collector
        .collector(ChuckerCollector(MainApplication.appContext))

        // The max body content length in bytes, after this responses will be truncated.
        .maxContentLength(250_000L)

        // Read the whole response body even when the client does not consume the response completely.
        // This is useful in case of parsing errors or when the response body
        // is closed before being read like in Retrofit with Void and Unit types.
        .alwaysReadResponseBody(true)

        // Controls Android shortcut creation.
        .createShortcut(true)

        .build()

    return OkHttpClient.Builder()
        .addInterceptor(chuckerInterceptor)
        .build()
}