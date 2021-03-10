package com.shustreek.otustodo.ui.tasklist

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.shustreek.otustodo.R
import com.shustreek.otustodo.receive.NotifyBroadcast
import com.shustreek.otustodo.utils.factory
import com.shustreek.otustodo.utils.observe
import kotlinx.android.synthetic.main.fragment_task_list.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class TaskListFragment : Fragment() {

    private val adapter = TaskListAdapter() { id, isChecked ->
        viewModel.onCheckedTask(id, isChecked)
    }
    private val viewModel: TaskListViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvList.adapter = adapter
        observe(viewModel.getTasks()) {
            adapter.items = it
        }
        observe(viewModel.cancelTask) { id ->
            ContextCompat.getSystemService(requireContext(), AlarmManager::class.java)?.cancel(
                PendingIntent.getBroadcast(
                    requireContext(),
                    id,
                    Intent(requireContext(), NotifyBroadcast::class.java),
                    PendingIntent.FLAG_ONE_SHOT
                )
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_add -> {
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}