package com.hsbc.hsbcdemo

import android.app.Application
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.hsbc.hsbcdemo.utils.MMKVUtils
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() , ViewModelStoreOwner {

    var mAppViewModelStore: ViewModelStore? = null

    override fun onCreate() {
        super.onCreate()
        mAppViewModelStore = ViewModelStore()
        MMKVUtils.initMMKV(this)//MMKV 初始化
    }

    override fun getViewModelStore(): ViewModelStore {
        return mAppViewModelStore!!
    }
}