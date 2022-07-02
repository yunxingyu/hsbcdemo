package com.hsbc.hsbcdemo.data.model
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class VideoInfo(
    @field:Json(name = "id")  var id: String?,
    @field:Json(name = "title")   var title: String? ="YouTube 视频",
    @field:Json(name = "statistics") var statistics: Statistics,
): Parcelable