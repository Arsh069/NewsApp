package com.example.news

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.View.GONE
import androidx.appcompat.app.AppCompatActivity
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.news.Adapter.NewsAdapter
import com.example.news.ViewModel.MainViewModel
import com.example.news.api.Article
import com.example.news.api.NewsApi
import com.example.news.repository.Repository
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    private lateinit var newsAdapter: NewsAdapter

    private lateinit var viewModel: MainViewModel

    private lateinit var repository: Repository


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.rv_recyclerView)



        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        if (viewModel.news.value == null)
            viewModel.getPost("in")

        newsAdapter = NewsAdapter(requireContext())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = newsAdapter


        viewModel.showProgress.observe(viewLifecycleOwner, Observer {
            if (it)
                progressbar.visibility = VISIBLE
            else
                progressbar.visibility = GONE
        })


        viewModel.news.observe(viewLifecycleOwner, Observer {
            newsAdapter.setStateWiseTracker(it.articles)
        })

///////////////////////////////////////////////////////
        /*request.enqueue(object : retrofit2.Callback<NewsApi?> {
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
        })*/
        return view
    }
}


