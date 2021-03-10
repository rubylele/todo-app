package com.shustreek.otustodo.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shustreek.otustodo.storage.entity.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: TaskEntity): Long

    @Query("SELECT * FROM tasks ORDER BY id")
    fun getTasks(): Flow<List<TaskEntity>>

    @Query("UPDATE tasks SET is_checked=:checked WHERE id=:id")
    suspend fun setChecked(id: Long, checked: Boolean)

}
