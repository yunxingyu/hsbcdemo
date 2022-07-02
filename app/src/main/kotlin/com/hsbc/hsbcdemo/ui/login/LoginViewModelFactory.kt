package com.hsbc.hsbcdemo.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hsbc.hsbcdemo.data.login.LoginDataSource
import com.hsbc.hsbcdemo.data.login.LoginRepository
import com.hsbc.hsbcdemo.ui.mine.MineViewModel

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class LoginViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(
                loginRepository = LoginRepository(
                    dataSource = LoginDataSource()
                )
            ) as T
        }else    if (modelClass.isAssignableFrom(MineViewModel::class.java)) {
                return MineViewModel(
                    loginRepository = LoginRepository(
                        dataSource = LoginDataSource()
                    )
                ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}