package com.shustreek.otustodo.ui.tasklist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import com.shustreek.otustodo.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_task_list.*

class TaskListAdapter(val listener: (Long, Boolean) -> Unit) :
    RecyclerView.Adapter<TaskListAdapter.ViewHolder>() {

    var items = listOf<TaskListItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_task_list, parent, false),
            listener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(override val containerView: View, val listener: (Long, Boolean) -> Unit) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(item: TaskListItem) {
            with(item) {
                checkbox.isChecked = isChecked
                checkbox.isEnabled = !isChecked
                checkbox.setOnCheckedChangeListener { buttonView, isChecked ->
                    listener(id, isChecked)
                }
                tvName.text = name
                tvTime.isGone = datetime.isNullOrEmpty()
                tvTime.text = datetime
            }
        }

    }

}
