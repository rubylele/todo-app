package com.shustreek.otustodo.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.shustreek.otustodo.App
import com.shustreek.otustodo.ViewModelFactory

inline fun <T> Fragment.observe(data: LiveData<T>, crossinline callback: (T) -> Unit) {
    data.observe(viewLifecycleOwner, Observer { event -> event?.let { callback(it) } })
}

inline val Fragment.factory: ViewModelFactory
    get() = (requireActivity().application as App).factory
