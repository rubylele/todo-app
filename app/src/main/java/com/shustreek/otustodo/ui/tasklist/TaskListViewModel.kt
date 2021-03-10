package com.shustreek.otustodo.ui.tasklist

import android.text.format.DateFormat
import androidx.lifecycle.*
import com.shustreek.otustodo.storage.dao.TaskDao
import com.shustreek.otustodo.utils.SingleLiveEvent
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.*

class TaskListViewModel(private val dao: TaskDao) : ViewModel() {

    private val mCancelTask: MutableLiveData<Int> = SingleLiveEvent()

    val cancelTask: LiveData<Int> = mCancelTask

    fun getTasks() = dao.getTasks()
        .map {
            it.map { list ->
                with(list) {
                    TaskListItem(
                        id,
                        name,
                        isChecked,
                        datetime?.let { time -> DateFormat.format("E dd MMM HH:mm", Date(time)) }
                    )
                }
            }
        }
        .asLiveData(viewModelScope.coroutineContext)

    fun onCheckedTask(id: Long, checked: Boolean) {
        if (checked) mCancelTask.value = id.toInt()
        viewModelScope.launch {
            dao.setChecked(id, checked)
        }
    }

}