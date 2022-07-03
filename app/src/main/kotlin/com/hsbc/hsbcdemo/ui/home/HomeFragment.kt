package com.hsbc.hsbcdemo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.hsbc.hsbcdemo.R
import com.hsbc.hsbcdemo.databinding.FragmentHomeBinding
import com.hsbc.hsbcdemo.ui.widget.SpaceItemDecoration
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
        _binding!!.rv.layoutManager = GridLayoutManager(getActivity()!!,2)
        if(getActivity()!=null) {
            _binding!!.rv.addItemDecoration(SpaceItemDecoration(getActivity()!!, 15f, 2))
        }
        homeViewModel.fetchYtbVideoList()

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
            viewModel.fetchYtbVideoList()
        }
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
            ToastUtils.showLong("点击了第${position+1} 个")
    }

}