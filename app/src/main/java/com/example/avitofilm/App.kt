package com.example.avitofilm

import android.app.Application
import android.content.Context
import android.os.Build
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.decode.ImageDecoderDecoder
import com.example.avitofilm.di.AppComponent
import com.example.avitofilm.di.DaggerAppComponent

class App : Application(), ImageLoaderFactory {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
    }
    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .build()
    }
}

val Context.appComponent: AppComponent
    get() = when(this){
        is App -> appComponent
        else -> applicationContext.appComponent
    }