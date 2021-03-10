package com.shustreek.otustodo.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shustreek.otustodo.storage.dao.TaskDao
import com.shustreek.otustodo.storage.entity.TaskEntity

@Database(version = 1, exportSchema = false, entities = [TaskEntity::class])
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}