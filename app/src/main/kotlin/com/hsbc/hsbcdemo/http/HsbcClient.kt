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
import javax.inject.Inject

class HsbcClient @Inject constructor(
     val hsbcService: HsbcService
) {
    suspend fun fetchVideoList(
        playlistId: String,
        pageToken: String,
        maxResults: Int
    ): ApiResponse<ListVideo> =
        hsbcService.fetchVideoList(
            playlistId = playlistId,
            pageToken = pageToken,
            maxResults = maxResults
        )

    companion object {
        private const val PAGING_SIZE = 20
    }
}
