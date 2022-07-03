package com.hsbc.hsbcdemo.ui.widget

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView

class SpaceItemDecoration : RecyclerView.ItemDecoration {
    constructor(mContext: FragmentActivity, paddingSpace: Float, spanCount: Int) {
        this.mContext = mContext
        this.divider =
            ((mContext.resources.displayMetrics.widthPixels - paddingSpace) / (spanCount - 1)).toInt()
        this.spanCount = spanCount
    }

    var mContext: Context
    var divider = 10 // 空隙距离
    var spanCount = 3 // 列数

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        var childPosition = parent.getChildLayoutPosition(view)
        var columNum = childPosition % spanCount + 1
        outRect.left = (columNum - 1) * divider / spanCount
        outRect.right = (spanCount - columNum) * divider / spanCount
        super.getItemOffsets(outRect, view, parent, state)

    }
}
