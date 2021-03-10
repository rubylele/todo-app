package com.shustreek.otustodo.ui.addtask

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.AlarmManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionManager
import com.shustreek.otustodo.R
import com.shustreek.otustodo.receive.NotifyBroadcast
import com.shustreek.otustodo.receive.NotifyBroadcast.Companion.EXT_ID
import com.shustreek.otustodo.receive.NotifyBroadcast.Companion.EXT_NAME
import com.shustreek.otustodo.utils.factory
import com.shustreek.otustodo.utils.observe
import kotlinx.android.synthetic.main.fragment_add_task.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class AddTaskFragment : Fragment() {

    val viewModel: AddTaskViewModel by viewModels { factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        etName.doOnTextChanged { text, start, before, count ->
            viewModel.onNameChanged(text)
        }
        switchTime.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onTimeCheckedChange(isChecked)
        }
        datePicker.addOnDateChangedListener { displayed, date ->
            viewModel.onDateChanged(date)
        }
        btnSave.setOnClickListener { viewModel.onSaveClick() }
        btnCancel.setOnClickListener { viewModel.onCancelClick() }

        observe(viewModel.saveEnabled) {
            btnSave.isEnabled = it
        }
        observe(viewModel.timeChecked) {
            TransitionManager.beginDelayedTransition(card)
            grTimeEdit.isVisible = it
        }
        observe(viewModel.dateTime) {
            tvDateTime.text = DateFormat.format("E dd MMM HH:mm", it)
        }
        observe(viewModel.result) { result ->
            if (result is Result.Save) {
                result.time?.let { time ->
                    ContextCompat.getSystemService(requireContext(), AlarmManager::class.java)
                        ?.let {
                            val pi = PendingIntent.getBroadcast(
                                requireContext(),
                                result.id,
                                Intent(requireContext(), NotifyBroadcast::class.java)
                                    .putExtra(EXT_NAME, result.name)
                                    .putExtra(EXT_ID, result.id),
                                PendingIntent.FLAG_ONE_SHOT
                            )

                            AlarmManagerCompat.setExactAndAllowWhileIdle(
                                it,
                                AlarmManager.RTC_WAKEUP,
                                time,
                                pi
                            )

                        }
                }
            }
            findNavController().popBackStack()
        }
    }
}