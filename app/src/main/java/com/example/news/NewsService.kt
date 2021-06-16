package com.example.news

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory
import com.example.news.Connection.checkConnection
import kotlinx.coroutines.withContext
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

const val BASE_URL="https://newsapi.org/"

object NewsService {

    fun getClient(context: Context): ApiInterface {
        var cache: Cache = Cache(context.cacheDir, 10 * 1024 * 1024)
        var okHttpClient = OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(Interceptor { chain ->
                var request: Request = chain.request()
                request = if (checkConnection.hasNetwork(context)!!)
                    request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
                else
                    request.newBuilder().header(
                        "Cache-Control",
                        "public, only-if-cached, max-stale=" + 60 * 60 * 5
                    ).build()
                chain.proceed(request)
            }
            ).build()


        val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
        }

        val newsInstance: ApiInterface by lazy {
           retrofit.create(ApiInterface::class.java) //attaching object to interface
        }
        return newsInstance
    }
}