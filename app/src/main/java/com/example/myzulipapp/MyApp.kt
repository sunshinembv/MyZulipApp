package com.example.myzulipapp

import android.app.Application
import com.example.myzulipapp.di.AppComponent
import com.example.myzulipapp.di.DaggerAppComponent

class MyApp : Application() {

    private var _appComponent: AppComponent? = null

    internal val appComponent: AppComponent
        get() = checkNotNull(_appComponent)

    override fun onCreate() {
        super.onCreate()
        _appComponent = DaggerAppComponent.factory().create(this)
    }
}
