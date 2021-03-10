package com.shustreek.otustodo.storage.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskEntity(

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "notify_enabled")
    val notifyEnabled: Boolean,

    @ColumnInfo(name = "datetime")
    val datetime: Long?,

    @ColumnInfo(name = "is_checked")
    val isChecked: Boolean = false,

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
)