package com.soower.networkpagination.model.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.soower.networkpagination.model.News
import com.soower.networkpagination.model.repo.API

class NewsDataSourceFactory(val query: String) : DataSource.Factory<Int, News>() {
    val mutableNewsDataSource = MutableLiveData<NewsDataSource>()
    val api = API.getRetrofit()
    override fun create(): DataSource<Int, News> {
        val newsDataSource = NewsDataSource(api, query)
        mutableNewsDataSource.postValue(newsDataSource)
        return newsDataSource
    }

}