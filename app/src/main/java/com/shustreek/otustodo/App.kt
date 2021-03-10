package com.shustreek.otustodo

import android.app.Application
import androidx.room.Room
import com.shustreek.otustodo.storage.AppDatabase

class App : Application() {

    lateinit var factory: ViewModelFactory

    override fun onCreate() {
        super.onCreate()
//        val room = Room.inMemoryDatabaseBuilder(this, AppDatabase::class.java).build()
        val room = Room
            .databaseBuilder(this, AppDatabase::class.java, "database")
            .build()
        factory = ViewModelFactory(room)
    }
}