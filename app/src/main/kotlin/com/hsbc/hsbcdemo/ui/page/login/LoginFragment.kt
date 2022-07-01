package com.hsbc.hsbcdemo.ui.page.login

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.blankj.utilcode.util.ToastUtils
import com.hsbc.hsbcdemo.R
import com.hsbc.hsbcdemo.utils.MMKVUtils
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

    }

    inner class ClickProxy {
        fun back() {
            view?.let { Navigation.findNavController(it).navigateUp() }
        }

        fun login() {
            if (TextUtils.isEmpty(viewModel.name) || TextUtils.isEmpty(viewModel.password)) {
                ToastUtils.getDefaultMaker().show("用户名或密码为空，请重新输入~")
                return
            }

            GlobalScope.launch{
                MMKVUtils.put("isLogin",true)
                MMKVUtils.put("userName",viewModel.name)
                delay(1000)
                view?.let { Navigation.findNavController(it).navigateUp() }
            }
            viewModel.loadingVisible=View.VISIBLE;

        }
    }
}