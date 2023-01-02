package com.example.proj

import android.app.Application
import com.example.proj.database.AppDatabase

class MainApplication:Application() {

    val database: AppDatabase by lazy {
        AppDatabase.getDatabase(this)
    }
}