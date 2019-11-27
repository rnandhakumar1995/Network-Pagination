package com.soower.networkpagination.model.datasource

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PositionalDataSource
import com.soower.networkpagination.model.NetworkState
import com.soower.networkpagination.model.pojo.News
import com.soower.networkpagination.model.repo.API
import java.lang.Exception

class NewsDataSource(val api: API, val query: String) : PositionalDataSource<News>() {
    val initialLoad = MutableLiveData<NetworkState>()
    val networkState = MutableLiveData<NetworkState>()
    val TAG = NewsDataSource::class.java.simpleName
    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<News>) {
        try {
            Log.i(TAG, params.loadSize.toString() + " " + params.startPosition.toString())
            networkState.postValue(NetworkState.LOADING)
            api.getNews(query, params.startPosition / params.loadSize).subscribe ({ searchResult ->
                run {
                    networkState.postValue(NetworkState.LOADED)
                    callback.onResult(searchResult.newses)
                }
            },Throwable::printStackTrace)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<News>) {
        try {
            initialLoad.postValue(NetworkState.LOADING)
            networkState.postValue(NetworkState.LOADING)
            Log.i(
                TAG,
                "loadInitial: " + params.pageSize.toString() + " " + params.requestedLoadSize.toString() + " " + params.requestedStartPosition.toString()
            )
            api.getNews(query, 0).subscribe ({ searchResult ->
                run {
                    initialLoad.postValue(NetworkState.LOADED)
                    networkState.postValue(NetworkState.LOADED)
                    callback.onResult(searchResult.newses, 0)
                }
            },Throwable::printStackTrace)

        } catch (ex: Exception) {
            ex.printStackTrace()
        } }

}