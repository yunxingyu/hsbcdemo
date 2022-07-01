package com.hsbc.hsbcdemo.ui.page.login

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    //TODO tip 7：此处我们使用 "去除防抖特性" 的 ObservableField 子类 State，用以代替 MutableLiveData，
    var name: String = ""

    var password: String = ""

    var loadingVisible: Int = View.GONE
}