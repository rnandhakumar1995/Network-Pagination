package com.soower.networkpagination.model.repo


import com.soower.networkpagination.model.pojo.SearchResult
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface API {
    @GET("/api/v1/search")
    fun getNews(@Query("query") query: String, @Query("page") page: Int): Single<SearchResult>

    companion object {
        val END_POINT = "https://hn.algolia.com/"
        fun getRetrofit(): API {
            return Retrofit.Builder().baseUrl(END_POINT)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).addConverterFactory(
                    GsonConverterFactory.create()
                ).build().create(API::class.java)
        }
    }
}