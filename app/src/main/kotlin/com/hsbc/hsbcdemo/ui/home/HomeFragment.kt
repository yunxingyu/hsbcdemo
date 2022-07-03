package com.hsbc.hsbcdemo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.blankj.utilcode.util.SizeUtils
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.hsbc.hsbcdemo.R
import com.hsbc.hsbcdemo.databinding.FragmentHomeBinding
import com.hsbc.hsbcdemo.ui.widget.SpaceItemDecoration
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
import com.scwang.smart.refresh.layout.listener.OnRefreshListener
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : AppCompatDialogFragment() , OnItemClickListener{

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!
    @get:VisibleForTesting
    internal val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel = viewModel
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        homeViewModel.videoAdapter.setEmptyView(R.layout.item_empty_layout)
        homeViewModel.videoAdapter.setOnItemClickListener(this@HomeFragment)
       // var headerView = inflater.inflate(R.layout.home_footer_view, _binding!!.rv, false)
       // homeViewModel.videoAdapter.addHeaderView(headerView)
      //  var footerView = inflater.inflate(R.layout.home_head_view, _binding!!.rv, false)
      //  homeViewModel.videoAdapter.addFooterView(footerView, 0)

        _binding!!.rv.adapter = homeViewModel.videoAdapter
        _binding!!.rv.layoutManager=GridLayoutManager(getActivity()!!,2)
       var  decoration =  SpaceItemDecoration(SizeUtils.dp2px(12f), SpaceItemDecoration.GRIDLAYOUT)
        if (_binding!!.rv.getItemDecorationCount() > 0) {
            val itemDecorationAt: ItemDecoration = _binding!!.rv.getItemDecorationAt(0)
            if (itemDecorationAt == null) {
                _binding!!.rv.addItemDecoration(decoration)
            }
        } else {
            //需要在setLayoutManager()之后调用addItemDecoration()
            _binding!!.rv.addItemDecoration(decoration)
        }
        _binding!!.rv.setHasFixedSize(true)
        _binding!!.rv.isNestedScrollingEnabled = false
        homeViewModel.videoAdapter.notifyDataSetChanged()

        homeViewModel.fetchYtbVideoList(true)
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }




inner  class ClickProxy {


        fun login() {

        }

        fun search() {
            viewModel.fetchYtbVideoList(true)
        }
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
            ToastUtils.showLong("点击了第${position+1} 个")
    }

}