package com.myetherwallet.mewconnect.content.api.mew

import com.myetherwallet.mewconnect.BuildConfig
import com.myetherwallet.mewconnect.core.utils.MewLog
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MewClient {

    val retrofit = createRetrofit()

    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BuildConfig.MEW_API_END_POINT)
                .client(createClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    private fun createClient(): OkHttpClient {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        if (MewLog.shouldDisplayLogs()) {
            val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            okHttpClientBuilder.addInterceptor(loggingInterceptor)
        }
        return okHttpClientBuilder.build()
    }
}