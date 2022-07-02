package com.hsbc.hsbcdemo.utils

import android.app.Application
import com.tencent.mmkv.MMKV

/**
 * 腾讯mmap本地缓存，取代sp
 */
class MMKVUtils {
    companion object{
        /**
         * 保存在手机里面的文件名
         */
        private val FILE_NAME = "hsbc"
        private var mmkv: MMKV? = null
        /**
         * sp数据导入MMKV
         *
         * @param mApp
         */
        fun initMMKV(mApp: Application?) {
            MMKV.initialize(mApp)
            mmkv = MMKV.mmkvWithID(FILE_NAME)
        }

        /**
         * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
         *
         * @param key
         * @param object
         */
        fun put(key: String?, `object`: Any?) {
            if (`object` is String) {
                mmkv!!.encode(key, `object` as String?)
            } else if (`object` is Int) {
                mmkv!!.encode(key, (`object` as Int?)!!)
            } else if (`object` is Boolean) {
                mmkv!!.encode(key, (`object` as Boolean?)!!)
            } else if (`object` is Float) {
                mmkv!!.encode(key, (`object` as Float?)!!)
            } else if (`object` is Long) {
                mmkv!!.encode(key, (`object` as Long?)!!)
            } else {
                if (`object` == null) {
                    mmkv!!.encode(key, "")
                } else {
                    mmkv!!.encode(key, `object`.toString())
                }
            }
        }

        /**
         * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
         *
         * @param key
         * @param defaultObject
         * @return
         */
        operator fun get(key: String?, defaultObject: Any?): Any? {
            if (defaultObject is String) {
                return mmkv!!.decodeString(key, defaultObject as String?)
            } else if (defaultObject is Int) {
                return mmkv!!.decodeInt(key, (defaultObject as Int?)!!)
            } else if (defaultObject is Boolean) {
                return mmkv!!.decodeBool(key, (defaultObject as Boolean?)!!)
            } else if (defaultObject is Float) {
                return mmkv!!.decodeFloat(key, (defaultObject as Float?)!!)
            } else if (defaultObject is Long) {
                return mmkv!!.decodeLong(key, (defaultObject as Long?)!!)
            }
            return defaultObject
        }

        /**
         * 移除某个key值已经对应的值
         *
         * @param key
         */
        fun remove(key: String?) {
            mmkv!!.remove(key)
        }
    }







}
