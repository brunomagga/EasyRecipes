package com.devspace.myapplication

import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


private const val BASE_URL: String = "https://api.spoonacular.com/"
object RetrofitClient {

    private val httpClient: OkHttpClient
        get() {
            val clientBuilder = OkHttpClient.Builder()
                .connectTimeout(60,TimeUnit.SECONDS)
                .readTimeout(60,TimeUnit.SECONDS)
                .writeTimeout(60,TimeUnit.SECONDS)
            val token = BuildConfig.API_KEY

            clientBuilder.addInterceptor { chain ->
                val original: Request = chain.request()
                val request = original.newBuilder()
                val originalHttpUrl = chain.request().url
                val newUrl = originalHttpUrl.newBuilder()
                    .addQueryParameter("apiKey", token).build()
                chain.proceed(request.url(newUrl).build())
            }
            return clientBuilder.build()
        }

    val retrofitInstance :Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}