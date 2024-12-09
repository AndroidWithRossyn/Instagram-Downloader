package com.rossyn.instagram.reels.story.downloader.presentation.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.DefaultLifecycleObserver

open class BaseActivity: AppCompatActivity(), DefaultLifecycleObserver  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super<AppCompatActivity>.onCreate(savedInstanceState)
        lifecycle.addObserver(this)
    }

    override fun onDestroy() {
        super<AppCompatActivity>.onDestroy()
        lifecycle.removeObserver(this)
    }
}