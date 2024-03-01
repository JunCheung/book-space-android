package com.bookspace.app.data.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PageResult<T>(
    @Json(name = "list")
    val list: List<T>,
    @Json(name = "total")
    val total: Long
)