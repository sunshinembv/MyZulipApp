package com.example.myzulipapp.di

import com.example.myzulipapp.network.ZulipApi
import dagger.Module
import dagger.Provides
import okhttp3.Credentials
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideZulipApi(): ZulipApi {

        val okHttpClient = OkHttpClient.Builder()
            .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor { chain ->
                val modifiedRequest =
                    chain.request().newBuilder()
                        .addHeader(AUTHORIZATION_HEADER, Credentials.basic(EMAIL, API_KEY)).build()
                chain.proceed(modifiedRequest)
            }.connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder().client(okHttpClient).baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create()).build()

        return retrofit.create()
    }

    companion object {

        var BASE_URL = "https://tzeusltip1.zulipchat.com/"
        const val EMAIL = "zuliptest123@gmail.com"
        const val API_KEY = "JXD5a0hUNp4LLVWEMD6Fgjw49BcpvKKn"

        const val AUTHORIZATION_HEADER = "Authorization"

        private const val TIMEOUT = 90L
    }
}
