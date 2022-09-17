package com.textifly.divinekiddy.ApiManager

import com.textifly.divinekiddy.Utils.Urls
import com.textifly.divinekiddy.Utils.WebService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitHelper {


    fun getRetrofitInstance(): Retrofit {

        val mHttpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        val mOkHttpClient = OkHttpClient
            .Builder()
            .connectTimeout(60*1000, TimeUnit.MILLISECONDS)
            .readTimeout(60*1000, TimeUnit.MILLISECONDS)
            .writeTimeout(60*1000, TimeUnit.MILLISECONDS)
            .addInterceptor(mHttpLoggingInterceptor)
            .build()

        return Retrofit.Builder().baseUrl(Urls.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(mOkHttpClient)
            .build()
            //.create(WebService::class.java)
    }
}
