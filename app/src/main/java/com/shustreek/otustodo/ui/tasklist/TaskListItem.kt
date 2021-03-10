package com.shustreek.otustodo.ui.tasklist

data class TaskListItem(
    val id: Long,
    val name: String,
    val isChecked: Boolean,
    val datetime: CharSequence?
)
