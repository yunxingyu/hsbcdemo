/*
 * Designed and developed by 2020 skydoves (Jaewoong Eum)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hsbc.hsbcdemo.http

import com.hsbc.hsbcdemo.data.model.ListVideo
import com.skydoves.sandwich.ApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface HsbcService {

    @GET("playlistItems")
    suspend fun fetchVideoList(
        @Query("playlistId") playlistId: String = "20",
        @Query("pageToken") pageToken: String = "",
        @Query("maxResults") maxResults: Int = 30
    ): ApiResponse<ListVideo>

    //获取youtube短视频
    @GET("videos")
    fun fetchYtbVideoList(
    @Query("chart") playlistId: String = "mostPopular",
    @Query("regionCode") pageToken: String = "HK",
    @Query("maxResults") maxResults: String= "30",
   @Query("videoCategoryId") videoCategoryId: String= "20"
    ): Call<ListVideo>



}
