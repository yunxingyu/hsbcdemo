package com.hsbc.hsbcdemo.ui.home

import android.util.Log
import androidx.lifecycle.*
import com.hsbc.hsbcdemo.data.home.HomeRepository
import com.hsbc.hsbcdemo.ui.login.LoginResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
) : ViewModel() {

    private val TAG = "HomeViewModel";


    fun fetchVideoList(pageToken: String) {
        viewModelScope.launch {
            homeRepository.fetchVideoList(
                playlistId = "20",
                pageToken = pageToken,
                maxResults = 30,
                onStart = {
                    Log.e(TAG, "==============000000==============")
                },
                onComplete = {
                    Log.e(TAG, "==============111==============")
                },
                onError = {
                    Log.e(TAG, "==============111==============")
                }
            )
        }
    }
}