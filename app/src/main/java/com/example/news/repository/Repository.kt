package com.example.news.repository

import android.app.Application
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.news.ApiInterface
import com.example.news.BASE_URL
import com.example.news.NewsService
import com.example.news.api.NewsApi
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Repository(application: Application ) {

    val showProgress=MutableLiveData<Boolean>()
    val news=MutableLiveData<NewsApi>()

    fun getPost(country:String) {
        showProgress.value=true

       val retrofit=Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
          val service=retrofit.create(ApiInterface::class.java)
          service.getNews(country).enqueue(object :Callback<NewsApi>{
              override fun onResponse(call: Call<NewsApi>, response: Response<NewsApi>) {
                  val new=response.body()
                  showProgress.value=false
                  news.value=new
                  Log.d("data","success+${Gson().toJson(news.value.toString())}")

              }
              override fun onFailure(call: Call<NewsApi>, t: Throwable) {
                  //showProgress.value=false
                 showProgress.value=true
              }


          })
    }
}