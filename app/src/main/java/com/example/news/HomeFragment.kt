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
import com.example.news.ViewModel.MainViewModelFactory
import com.example.news.api.Article
import com.example.news.api.NewsApi
import com.example.news.repository.Repository
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    private val newsAdapter: NewsAdapter by lazy{NewsAdapter(requireContext())}

    private lateinit var viewModel: MainViewModel

    private lateinit var repository: Repository

    private var res= emptyList<Article>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        /*val recyclerView: RecyclerView = view.findViewById(R.id.rv_recyclerView)


        val repository=Repository(this.requireContext())
        val vmf=MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this,vmf).get(MainViewModel::class.java)

       // if (viewModel.news.value == null)
            viewModel.getPost("in")

      //  newsAdapter = NewsAdapter(requireContext())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = newsAdapter


        viewModel.showProgress.observe(viewLifecycleOwner, Observer {
            if (it)
                progressbar.visibility = VISIBLE
            else
                progressbar.visibility = GONE
        })


        viewModel.news.observe(viewLifecycleOwner, Observer {
            Log.d("BOLT","success"+it.toString())
            newsAdapter.setStateWiseTracker(it.body()?.articles!!)

        })*/


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView: RecyclerView = view.findViewById(R.id.rv_recyclerView)


        val repository=Repository(this.requireContext())
        val vmf=MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this,vmf).get(MainViewModel::class.java)

        // if (viewModel.news.value == null)
        viewModel.getPost("in","5f320a3ed71348e991917ff684a7bdd4")

        //  newsAdapter = NewsAdapter(requireContext())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = newsAdapter


        viewModel.showProgress.observe(viewLifecycleOwner, Observer {
            if (it)
                progressbar.visibility = VISIBLE
            else
                progressbar.visibility = GONE
        })


        viewModel.news.observe(viewLifecycleOwner, Observer {
            Log.d("BOLT","success"+it.toString())
            res= it.body()?.articles!!
            it.body()?.let {
                newsAdapter.setStateWiseTracker(res)
                progressbar.visibility= GONE
            }


        })
    }
}


