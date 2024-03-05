package com.banrossyn.ininsta.story.downloader.api;

import android.app.Activity;
import android.app.Application;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;


public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();


        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity a, Bundle savedInstanceState) {
                if (Build.VERSION.SDK_INT != Build.VERSION_CODES.O)
                    a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {

            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {

            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {

            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {

            }
        });
    }

}
