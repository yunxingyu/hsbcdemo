package com.hsbc.hsbcdemo.ui.mine

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hsbc.hsbcdemo.data.login.LoginRepository

class MineViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Mine Fragment"
    }
    val text: LiveData<String> = _text

    fun logout() {
        // can be launched in a separate asynchronous job
        loginRepository.logout()

    }
}