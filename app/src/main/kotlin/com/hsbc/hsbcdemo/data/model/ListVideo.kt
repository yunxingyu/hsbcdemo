package com.hsbc.hsbcdemo.data.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize


@Parcelize
@JsonClass(generateAdapter = true)
data class ListVideo(
    @field:Json(name = "items") var items: List<VideoInfo>,
    @field:Json(name = "nextPageToken") var nextPageToken: String?,


): Parcelable