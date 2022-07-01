package com.hsbc.hsbcdemo.utils;

import android.app.Application;

import com.tencent.mmkv.MMKV;


public class MMKVUtils {
    /**
     * 保存在手机里面的文件名
     */
    private static final String FILE_NAME = "hsbc";

    private static MMKV mmkv;

    /**
     * sp数据导入MMKV
     *
     * @param mApp
     */
    public static void initMMKV(Application mApp) {
        MMKV.initialize(mApp);
        mmkv = MMKV.mmkvWithID(FILE_NAME);
    }
    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param key
     * @param object
     */
    public static void put(String key, Object object) {
        if (object instanceof String) {
            mmkv.encode(key, (String) object);
        } else if (object instanceof Integer) {
            mmkv.encode(key, (Integer) object);
        } else if (object instanceof Boolean) {
            mmkv.encode(key, (Boolean) object);
        } else if (object instanceof Float) {
            mmkv.encode(key, (Float) object);
        } else if (object instanceof Long) {
            mmkv.encode(key, (Long) object);
        } else {
            if (object == null) {
                mmkv.encode(key, "");
            } else {
                mmkv.encode(key, object.toString());
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
    public static Object get(String key, Object defaultObject) {
        if (defaultObject instanceof String) {
            return mmkv.decodeString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return mmkv.decodeInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return mmkv.decodeBool(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return mmkv.decodeFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return mmkv.decodeLong(key, (Long) defaultObject);
        }
        return defaultObject;
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param key
     */
    public static void remove(String key) {
        mmkv.remove(key);
    }
}
