package com.hsbc.hsbcdemo.data.home

import android.util.Log
import androidx.annotation.WorkerThread
import com.hsbc.hsbcdemo.data.ErrorResponseMapper
import com.hsbc.hsbcdemo.data.Repository
import com.hsbc.hsbcdemo.http.HsbcClient
import com.skydoves.sandwich.*
import com.skydoves.sandwich.map

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class HomeRepository @Inject constructor(
     val hsbcClient: HsbcClient,
     val ioDispatcher: CoroutineDispatcher
) : Repository {

    @WorkerThread
    fun fetchVideoList(
        playlistId: String,
        pageToken: String,
        maxResults: Int,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    )  =
        flow {
            Log.e("HomeRepository", "==============22222222==============")
            val response = hsbcClient.fetchVideoList(
                playlistId = playlistId,
                pageToken = pageToken,
                maxResults = maxResults
            )
            Log.e("HomeRepository", "===============333333333=============")
            response.suspendOnSuccess {
                emit(data)
                Log.e("HomeRepository", "===============$data=============")
            }
                // handles the case when the API request gets an error response.
                // e.g., internal server error.
                .onError {
                    /** maps the [ApiResponse.Failure.Error] to the [HsbcErrorResponse] using the mapper. */
                    map(ErrorResponseMapper) {
                        onError("[Code: $code]: $message")
                        Log.e("HomeRepository", "===============$message=============")
                    }

                }
                // handles the case when the API request gets an exception response.
                // e.g., network connection error.
                .onException {
                    onError(message)
                    Log.e("HomeRepository", "===============$message 222=============")
                }
            Log.e("HomeRepository", "===============999999=============")
        }.flowOn(ioDispatcher).onStart { onStart() }.onCompletion { onComplete() }

}

