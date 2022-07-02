package com.hsbc.hsbcdemo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.hsbc.hsbcdemo.R
import com.hsbc.hsbcdemo.data.model.VideoInfo
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
        homeViewModel.videoAdapter.emptyLayout?.addView(inflater.inflate(R.layout.item_empty_layout,null))
        homeViewModel.videoAdapter.setOnItemClickListener(this@HomeFragment)
        _binding!!.rv.adapter = homeViewModel.videoAdapter
        _binding!!.rv.addItemDecoration(SpaceItemDecoration(requireActivity(),15f, 2))
        _binding!!.rv.layoutManager = GridLayoutManager(context,2)

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