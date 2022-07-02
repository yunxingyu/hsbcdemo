package com.hsbc.hsbcdemo.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.FileProvider
import java.lang.reflect.InvocationTargetException
import java.util.*

class Utils {
    companion object{

        private const val PERMISSION_ACTIVITY_CLASS_NAME =
            "com.blankj.utilcode.util.PermissionUtils\$PermissionActivity"
        @JvmStatic
        private val ACTIVITY_LIFECYCLE = ActivityLifecycleImpl()
        @JvmStatic
        @SuppressLint("StaticFieldLeak")
        private var sApplication: Application? = null
        /**
         * Init utils.
         *
         * Init it in the class of Application.
         *
         * @param context context
         */
        @JvmStatic
        fun init(context: Context?) {
            if (context == null) {
                init(getApplicationByReflect())
                return
            }
            init(context.applicationContext )
        }
        @JvmStatic
        private fun getApplicationByReflect(): Application? {
            try {
                @SuppressLint("PrivateApi") val activityThread =
                    Class.forName("android.app.ActivityThread")
                val thread = activityThread.getMethod("currentActivityThread").invoke(null)
                val app = activityThread.getMethod("getApplication").invoke(thread)
                    ?: throw NullPointerException("u should init first")
                return app as Application
            } catch (e: NoSuchMethodException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            } catch (e: InvocationTargetException) {
                e.printStackTrace()
            }
            throw NullPointerException("u should init first")
        }

        /**
         * Return the context of Application object.
         *
         * @return the context of Application object
         */
        fun getApp(): Application? {
            if (sApplication != null) {
                return sApplication!!
            }
            val app = getApplicationByReflect()
            init(app)
            return app
        }
    }

    private fun Utils() {
        throw UnsupportedOperationException("u can't instantiate me...")
    }


    interface OnAppStatusChangedListener {
        fun onForeground()
        fun onBackground()
    }

    interface OnActivityDestroyedListener {
        fun onActivityDestroyed(activity: Activity?)
    }

    ///////////////////////////////////////////////////////////////////////////
    // interface
    ///////////////////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////////////////////////
    // interface
    ///////////////////////////////////////////////////////////////////////////
    internal class ActivityLifecycleImpl : Application.ActivityLifecycleCallbacks {
        val mActivityList = LinkedList<Activity>()
        val mStatusListenerMap: MutableMap<Any, OnAppStatusChangedListener> = HashMap()
        val mDestroyedListenerMap: MutableMap<Activity, MutableSet<OnActivityDestroyedListener>> =
            HashMap()
        private var mForegroundCount = 0
        private var mConfigCount = 0
        private var mIsBackground = false
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            setTopActivity(activity)
        }

        override fun onActivityStarted(activity: Activity) {
            if (!mIsBackground) {
                setTopActivity(activity)
            }
            if (mConfigCount < 0) {
                ++mConfigCount
            } else {
                ++mForegroundCount
            }
        }

        override fun onActivityResumed(activity: Activity) {
            setTopActivity(activity)
            if (mIsBackground) {
                mIsBackground = false
                postStatus(true)
            }
        }

        override fun onActivityPaused(activity: Activity) { /**/
        }

        override fun onActivityStopped(activity: Activity) {
            if (activity.isChangingConfigurations) {
                --mConfigCount
            } else {
                --mForegroundCount
                if (mForegroundCount <= 0) {
                    mIsBackground = true
                    postStatus(false)
                }
            }
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) { /**/
        }

        override fun onActivityDestroyed(activity: Activity) {
            mActivityList.remove(activity)
            consumeOnActivityDestroyedListener(activity)
            fixSoftInputLeaks(activity)
        }

        val topActivity: Activity?
            get() {
                if (!mActivityList.isEmpty()) {
                    val topActivity = mActivityList.last
                    if (topActivity != null) {
                        return topActivity
                    }
                }
                val topActivityByReflect = topActivityByReflect
                topActivityByReflect?.let { setTopActivity(it) }
                return topActivityByReflect
            }

        private fun setTopActivity(activity: Activity) {
            if (PERMISSION_ACTIVITY_CLASS_NAME == activity.javaClass.name) {
                return
            }
            if (mActivityList.contains(activity)) {
                if (mActivityList.last != activity) {
                    mActivityList.remove(activity)
                    mActivityList.addLast(activity)
                }
            } else {
                mActivityList.addLast(activity)
            }
        }

        fun addOnAppStatusChangedListener(
            `object`: Any,
            listener: OnAppStatusChangedListener
        ) {
            mStatusListenerMap[`object`] = listener
        }

        fun removeOnAppStatusChangedListener(`object`: Any) {
            mStatusListenerMap.remove(`object`)
        }

        fun removeOnActivityDestroyedListener(activity: Activity?) {
            if (activity == null) {
                return
            }
            mDestroyedListenerMap.remove(activity)
        }

        fun addOnActivityDestroyedListener(
            activity: Activity?,
            listener: OnActivityDestroyedListener?
        ) {
            if (activity == null || listener == null) {
                return
            }
            val listeners: MutableSet<OnActivityDestroyedListener>
            if (!mDestroyedListenerMap.containsKey(activity)) {
                listeners = HashSet()
                mDestroyedListenerMap[activity] = listeners
            } else {
                listeners = mDestroyedListenerMap[activity]!!
                if (listeners.contains(listener)) {
                    return
                }
            }
            listeners.add(listener)
        }

        private fun postStatus(isForeground: Boolean) {
            if (mStatusListenerMap.isEmpty()) {
                return
            }
            for (onAppStatusChangedListener in mStatusListenerMap.values) {
                if (onAppStatusChangedListener == null) {
                    return
                }
                if (isForeground) {
                    onAppStatusChangedListener.onForeground()
                } else {
                    onAppStatusChangedListener.onBackground()
                }
            }
        }

        private fun consumeOnActivityDestroyedListener(activity: Activity) {
            val iterator: MutableIterator<Map.Entry<Activity, Set<OnActivityDestroyedListener>>> =
                mDestroyedListenerMap.entries.iterator()
            while (iterator.hasNext()) {
                val (key, value) = iterator.next()
                if (key === activity) {
                    for (listener in value) {
                        listener.onActivityDestroyed(activity)
                    }
                    iterator.remove()
                }
            }
        }

        private val topActivityByReflect: Activity?
            private get() {
                try {
                    @SuppressLint("PrivateApi") val activityThreadClass =
                        Class.forName("android.app.ActivityThread")
                    val currentActivityThreadMethod =
                        activityThreadClass.getMethod("currentActivityThread").invoke(null)
                    val mActivityListField = activityThreadClass.getDeclaredField("mActivityList")
                    mActivityListField.isAccessible = true
                    val activities = mActivityListField[currentActivityThreadMethod] as Map<*, *>
                        ?: return null
                    for (activityRecord in activities.values) {
                        val activityRecordClass: Class<*> = activityRecord!!.javaClass
                        val pausedField = activityRecordClass.getDeclaredField("paused")
                        pausedField.isAccessible = true
                        if (!pausedField.getBoolean(activityRecord)) {
                            val activityField = activityRecordClass.getDeclaredField("activity")
                            activityField.isAccessible = true
                            return activityField[activityRecord] as Activity
                        }
                    }
                } catch (e: ClassNotFoundException) {
                    e.printStackTrace()
                } catch (e: NoSuchFieldException) {
                    e.printStackTrace()
                } catch (e: NoSuchMethodException) {
                    e.printStackTrace()
                } catch (e: InvocationTargetException) {
                    e.printStackTrace()
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                }
                return null
            }

        companion object {
            private fun fixSoftInputLeaks(activity: Activity?) {
                if (activity == null) {
                    return
                }
                val imm = getApp()
                    ?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    ?: return
                val leakViews =
                    arrayOf("mLastSrvView", "mCurRootView", "mServedView", "mNextServedView")
                for (leakView in leakViews) {
                    try {
                        val leakViewField =
                            InputMethodManager::class.java.getDeclaredField(leakView)
                                ?: continue
                        if (!leakViewField.isAccessible) {
                            leakViewField.isAccessible = true
                        }
                        val obj = leakViewField[imm] as? View ?: continue
                        if (obj.rootView === activity.window.decorView.rootView) {
                            leakViewField[imm] = null
                        }
                    } catch (ignore: Throwable) { /**/
                    }
                }
            }
        }
    }

    class FileProvider4UtilCode : FileProvider() {
        override fun onCreate(): Boolean {
            init(context)
            return true
        }
    }
}