package com.hsbc.hsbcdemo.ui.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hsbc.hsbcdemo.R
import com.hsbc.hsbcdemo.data.model.Statistics
import com.hsbc.hsbcdemo.data.model.VideoInfo

class HomeVideoAdapter(data: ArrayList<VideoInfo>) :
    BaseQuickAdapter<VideoInfo, BaseViewHolder>(R.layout.adapter_play_item, data) {


    override fun convert(holder: BaseViewHolder, item: VideoInfo) {

        Glide.with(context).load("https://share.suconghou.cn/video/${item.id}.jpg")
            .into( holder.getView(R.id.iv_cover))
        if (item.title.isNullOrEmpty())
            (holder.getView(R.id.tv_title) as TextView).text = "youtube 热门视频"
        else
            (holder.getView(R.id.tv_title) as TextView).text = item.title

        if(item.statistics!=null){
            (holder.getView(R.id.tv_static) as TextView).visibility = View.VISIBLE
            (holder.getView(R.id.tv_static) as TextView).text = viewCount(item.statistics)
        }else {
             (holder.getView(R.id.tv_static) as TextView).visibility = View.INVISIBLE
        }

    }


    fun viewCount( item: Statistics):String{
        var n = 0;
        if(item.viewCount.isNullOrEmpty()){
            return ""
        }
        var viewCount =item.viewCount?.toLongOrNull()
        if (viewCount!=null && viewCount > 10000) {
            return "${(viewCount / 1000).dec()}万观看 ${item.likeCount} 喜欢 ${item.likeCount} 收藏";
        }
        return "$viewCount 观看 ${item.likeCount} 喜欢 ${item.likeCount} 收藏";
    }
}

