package com.soower.networkpagination.model

import com.google.gson.annotations.SerializedName

data class SearchResult(@SerializedName("hits") val newses: List<News>)