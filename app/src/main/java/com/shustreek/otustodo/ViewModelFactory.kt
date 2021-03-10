package com.shustreek.otustodo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shustreek.otustodo.storage.AppDatabase
import com.shustreek.otustodo.ui.addtask.AddTaskViewModel
import com.shustreek.otustodo.ui.tasklist.TaskListViewModel

class ViewModelFactory(private val appDatabase: AppDatabase) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when (modelClass) {
            TaskListViewModel::class.java -> TaskListViewModel(appDatabase.taskDao())
            AddTaskViewModel::class.java -> AddTaskViewModel(appDatabase.taskDao())
            else -> throw IllegalArgumentException("Cannot find $modelClass")
        } as T
    }
}