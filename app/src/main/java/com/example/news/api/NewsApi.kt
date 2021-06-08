package com.example.news.api

data class NewsApi(
    val articles: List<Article>?,
    val status: String?,
    val totalResults: Int?
)