package com.example.news.ViewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.example.news.api.Article
import com.example.news.api.NewsApi
import com.example.news.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

 class MainViewModel(application: Application):AndroidViewModel(application) {

     val repository=Repository(application)
     var showProgress: LiveData<Boolean>
     val news:LiveData<NewsApi>

     init {
         this.showProgress=repository.showProgress
         this.news=repository.news
     }


   // val myresponse:MutableLiveData<NewsApi> =MutableLiveData()

   fun getPost(country:String) {
        repository.getPost(country)
        }
    }

