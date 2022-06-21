package com.hush.yacd.app

import android.app.Application
import com.blankj.utilcode.util.Utils
import com.google.android.material.color.DynamicColors

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this);
        Utils.init(this)
    }

}