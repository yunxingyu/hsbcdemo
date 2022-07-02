package com.hsbc.hsbcdemo.data.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Statistics(
    @field:Json(name = "viewCount") var viewCount :String?,
    @field:Json(name = "likeCount") var likeCount :String?,
    @field:Json(name = "favoriteCount") var favoriteCount: String?,
    @field:Json(name = "commentCount") var commentCount: String?

) : Parcelable