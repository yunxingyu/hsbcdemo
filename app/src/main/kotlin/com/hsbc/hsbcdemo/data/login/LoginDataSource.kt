package com.hsbc.hsbcdemo.data.login

import com.hsbc.hsbcdemo.data.Result
import com.hsbc.hsbcdemo.data.model.LoggedInUser
import com.hsbc.hsbcdemo.utils.MMKVUtils
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    fun login(username: String, password: String): Result<LoggedInUser> {
        try {
            // TODO: handle loggedInUser authentication
            val fakeUser = LoggedInUser(java.util.UUID.randomUUID().toString(), username)
            MMKVUtils.put("isLogin",true)
            MMKVUtils.put("username",username)
            return Result.Success(fakeUser)
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        MMKVUtils.put("isLogin",false)
        MMKVUtils.put("username","")

    }
}