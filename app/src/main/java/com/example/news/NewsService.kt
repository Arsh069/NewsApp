package com.example.news

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory
import com.example.news.Connection.InternetConnectivity
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
    private const val cacheSize : Long = 10 * 1024 * 1024

    fun getClient(context: Context): ApiInterface {
        var cache: Cache = Cache(context.cacheDir, cacheSize)
    /*    var okHttpClient = OkHttpClient.Builder()
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
            ).build()*/

      val REWRITE_RESPONSE_INTERCEPTOR = Interceptor { chain ->
          val originalResponse = chain.proceed(chain.request())
          val cacheControl = originalResponse.header("Cache-Control")
          var maxAge = 60
          originalResponse.newBuilder()
              .header("Cache-Control", "public, max-age=$maxAge")
              .removeHeader("Pragma")
              .build()
      }
        val REWRITE_RESPONSE_INTERCEPTOR_OFFLINE = Interceptor { chain ->
            var request: Request = chain.request()
            var maxStale = 60 * 60 * 24 * 30
            if (!InternetConnectivity.isNetworkAvailable(context)!!) {
                request = request.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                    .removeHeader("Pragma")
                    .build()
            }
            chain.proceed(request)
        }

        val okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .cache(cache)
            .addNetworkInterceptor(REWRITE_RESPONSE_INTERCEPTOR)
            .addInterceptor(REWRITE_RESPONSE_INTERCEPTOR_OFFLINE)
            .build()



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