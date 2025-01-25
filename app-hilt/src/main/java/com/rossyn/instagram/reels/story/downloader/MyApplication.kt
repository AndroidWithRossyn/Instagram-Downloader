/*
 * Copyright (c) 2024 RohitrajKhorwal
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.rossyn.instagram.reels.story.downloader

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import androidx.multidex.MultiDexApplication
import com.gu.toolargetool.TooLargeTool
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * The InSaver App.
 *
 * @author Rossyn
 * @author Rohitraj Khorwal
 * @since v1.0.0
 * @see <a href="mailto:reyuna.apps@gmail.com">banrossyn@gmail.com</a>
 */
@HiltAndroidApp
class MyApplication : MultiDexApplication(), Application.ActivityLifecycleCallbacks {

    companion object {
        val isDebug: Boolean by lazy { BuildConfig.DEBUG }
    }

    override fun onCreate() {
        super.onCreate()

        this.registerActivityLifecycleCallbacks(this)


        if (isDebug) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                StrictMode.setVmPolicy(
                    StrictMode.VmPolicy.Builder().detectUnsafeIntentLaunch().build()
                )
            }
            // Start logging for tooLarge Exception checks
            TooLargeTool.startLogging(this)

            Timber.plant(object : Timber.DebugTree() {
                override fun createStackElementTag(element: StackTraceElement): String {
                    return "(${element.fileName}:${element.lineNumber})"
                }

                override fun log(
                    priority: Int, tag: String?, message: String, t: Throwable?
                ) {
                    val enhancedMessage = "[InSaver]--->  $message"
                    super.log(priority, tag, enhancedMessage, t)
                }
            })

        }

        Timber.d("onCreate: ")
    }

    override fun onTerminate() {
        Timber.d("onTerminate: ")
        if (isDebug) TooLargeTool.stopLogging(this)
        this.unregisterActivityLifecycleCallbacks(this)
        super.onTerminate()
    }


    @SuppressLint("SourceLockedOrientationActivity")
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun onActivityStarted(activity: Activity) {}
    override fun onActivityResumed(activity: Activity) {}
    override fun onActivityPaused(activity: Activity) {}
    override fun onActivityStopped(activity: Activity) {}
    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
    override fun onActivityDestroyed(activity: Activity) {}
}