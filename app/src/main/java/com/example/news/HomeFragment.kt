package com.example.news

import android.os.Bundle
import android.util.Log
import android.util.Log.d
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.news.Adapter.NewsAdapter
import com.example.news.api.Article
import com.example.news.api.NewsApi
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Response
import java.lang.StringBuilder
import javax.security.auth.callback.Callback
import kotlin.math.log


class HomeFragment : Fragment() {

    private lateinit var newsAdapter: NewsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view: View= inflater.inflate(R.layout.fragment_home, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.rv_recyclerView)
        val request=NewsService.newsInstance.getNews("in") //it holds our retrofit object(service)
        newsAdapter = NewsAdapter(requireContext())
        recyclerView.layoutManager =  LinearLayoutManager(requireContext())
        recyclerView.adapter = newsAdapter


        request.enqueue(object : retrofit2.Callback<NewsApi?> {
            override fun onResponse(call: Call<NewsApi?>, response: Response<NewsApi?>) {
                val news = response.body()
             //   txt.text= news?.articles.toString()
                Log.d("data","success"+response.toString())
           //     if (news!=null){


                    progressbar.visibility = View.GONE
                news!!.articles?.let { newsAdapter.setStateWiseTracker(it) }

                    recyclerView.adapter = newsAdapter
            //      }

            }

            override fun onFailure(call: Call<NewsApi?>, t: Throwable) {
                Log.d("error","error data"+t.message)
            }
        })

        return view
    }


}
