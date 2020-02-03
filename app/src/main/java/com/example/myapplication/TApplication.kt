package com.example.myapplication

import android.app.Application
import com.example.basearoute.Aroute
import http.HttpHandle
import http.RetrofitHttp

class TApplication: Application() {



    override fun onCreate() {
        super.onCreate()
        HttpHandle.init(RetrofitHttp())
        Aroute.getInstance().init(this)
    }
}