package com.soower.networkpagination.model.pojo

import com.google.gson.annotations.SerializedName

data class SearchResult(@SerializedName("hits") val newses: List<News>)