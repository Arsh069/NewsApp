package com.example.news.Adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.news.R
import com.example.news.api.Article
import com.example.news.api.NewsApi
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_news.view.*

class NewsAdapter(private val context: Context):RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    var articles= listOf<Article>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {

    val v= LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
    // return NewsAdapter.NewsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_news,parent,false),context)
    return NewsViewHolder(v)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article=articles[position]
        holder.newsTittle.text=article.title.toString()
        holder.newsDescription.text=article.description.toString()

        Picasso.with(context)
            .load(article.urlToImage)
            .into(holder.newsImage)

        //on clicking

        holder.itemView.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(article.url.toString())
            ContextCompat.startActivity(holder.itemView.context, intent, null)
        }

        holder.itemView.setOnLongClickListener {
            Toast.makeText(context, "${article.publishedAt}", Toast.LENGTH_SHORT).show()
            return@setOnLongClickListener true
        }


    }

    override fun getItemCount(): Int {
        return articles.size

    }

     class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val newsImage=itemView.iv_image
        val newsTittle=itemView.tv_tittle
        val newsDescription=itemView.tv_description

     }

    fun setStateWiseTracker( list: List<Article>) {
        
        this.articles=list
        notifyDataSetChanged()
    }


}