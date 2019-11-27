package com.soower.networkpagination.model

import com.google.gson.annotations.SerializedName

data class News(
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("title") val title: String,
    @SerializedName("url") val url: String
)