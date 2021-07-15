package com.tawfiq.meteo.database

import android.app.Application

class App : Application() {

    companion object{
        lateinit var instance: App

        val database : DatabaseHelper by lazy {
            DatabaseHelper(instance)
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}