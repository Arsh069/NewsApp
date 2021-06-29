package com.example.news

import com.example.news.api.NewsApi
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

const val api_key: String = "5f320a3ed71348e991917ff684a7bdd4"

interface ApiInterface {
    @GET("v2/top-headlines")
 suspend fun getNews(@Query("country") country:String,
             @Query("apiKey")apiKey:String): Response<NewsApi>

}