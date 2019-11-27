package com.soower.networkpagination.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.soower.networkpagination.model.NetworkState
import com.soower.networkpagination.model.pojo.News
import com.soower.networkpagination.model.datasource.NewsDataSource
import com.soower.networkpagination.model.datasource.NewsDataSourceFactory

class MainActivityViewModel : ViewModel() {
    var newsList: LiveData<PagedList<News>> = MutableLiveData()
    private lateinit var newsDataSourceFactory: NewsDataSourceFactory
    fun updaterQuery(query: String) {
        newsDataSourceFactory = NewsDataSourceFactory(query)
        val config = PagedList.Config.Builder().setPageSize(20).setEnablePlaceholders(false).build()
        newsList = LivePagedListBuilder<Int, News>(newsDataSourceFactory, config).build()
    }

    fun getNetworkState(): LiveData<NetworkState> = Transformations.switchMap<NewsDataSource, NetworkState>(
            newsDataSourceFactory.mutableNewsDataSource, { it.networkState })
}