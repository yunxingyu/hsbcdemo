package com.hsbc.hsbcdemo.data.model
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class VideoInfo(
    @field:Json(name = "videoId")  var videoId: String?,
    @field:Json(name = "videoCover")  var videoCover: String?,
    @field:Json(name = "title")   var title: String?,
    @field:Json(name = "viewCount")  var viewCount: Int
): Parcelable