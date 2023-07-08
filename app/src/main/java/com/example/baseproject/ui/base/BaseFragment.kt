package com.example.baseproject.ui.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.baseproject.utils.extension.development

abstract class BaseFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup(view)
        observeViewModel()
        development { dev() }
    }

    abstract fun setup(view: View)

    protected open fun dev(): Unit = Unit

    protected open fun observeViewModel(): Unit = Unit
}