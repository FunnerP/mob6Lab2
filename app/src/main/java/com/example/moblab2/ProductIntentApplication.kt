package com.example.moblab2

import android.app.Application

class ProductIntentApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        ProductRepository.initialize(this)
    }
}