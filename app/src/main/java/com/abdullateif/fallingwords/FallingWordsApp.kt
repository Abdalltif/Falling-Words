package com.abdullateif.fallingwords

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FallingWordsApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}