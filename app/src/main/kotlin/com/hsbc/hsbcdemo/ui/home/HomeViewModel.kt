package com.hsbc.hsbcdemo.ui.home

import android.util.Log
import androidx.lifecycle.*
import com.hsbc.hsbcdemo.data.home.HomeRepository
import com.hsbc.hsbcdemo.data.model.ListVideo
import com.hsbc.hsbcdemo.data.model.VideoInfo
import com.hsbc.hsbcdemo.ui.adapter.HomeVideoAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
) : ViewModel() {

    private val TAG = "HomeViewModel";
    val videoList :ArrayList<VideoInfo> = ArrayList()
    val videoAdapter : HomeVideoAdapter = HomeVideoAdapter(videoList)

    fun fetchYtbVideoList() {
        homeRepository.hsbcClient.hsbcService.fetchYtbVideoList("mostPopular","HK","30","20").enqueue(object :
            retrofit2.Callback<ListVideo?> {
            override fun onResponse(call: Call<ListVideo?>, response: Response<ListVideo?>) {
                videoList.clear()
                if(response.body()?.items!=null){
                    Log.e(TAG, response.body()!!.items.get(0).toString());
                    videoList.addAll(response.body()!!.items)
                    videoAdapter.notifyDataSetChanged()
                }else {
                    videoAdapter.notifyDataSetChanged()
                }

            }

            override fun onFailure(call: Call<ListVideo?>, t: Throwable) {
                videoList.clear()
                videoAdapter.notifyDataSetChanged()
            }
        })
    }
}