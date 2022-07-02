package com.hsbc.hsbcdemo.ui.mine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.hsbc.hsbcdemo.R
import com.hsbc.hsbcdemo.databinding.FragmentMineBinding
import com.hsbc.hsbcdemo.ui.login.LoginViewModelFactory
import com.hsbc.hsbcdemo.utils.MMKVUtils

class MineFragment : Fragment() {

    private var _binding: FragmentMineBinding? = null
    private lateinit var mineViewModel: MineViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
         mineViewModel =  ViewModelProvider(this, LoginViewModelFactory())
             .get(MineViewModel::class.java)

        _binding = FragmentMineBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textMine
        mineViewModel.text.observe(viewLifecycleOwner) {
            val welcome = getString(R.string.welcome)
            val displayName = MMKVUtils.get("username","hsbc").toString()
            if(MMKVUtils.get("isLogin",false) as Boolean)
            textView.text = "$welcome \n $displayName"
            else {
                textView.text = "立即登录"
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}