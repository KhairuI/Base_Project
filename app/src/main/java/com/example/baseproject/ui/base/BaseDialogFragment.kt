package com.example.baseproject.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.AppCompatDialogFragment
import com.example.baseproject.utils.extension.development
import com.example.baseproject.utils.extension.wrap

abstract class BaseDialogFragment : AppCompatDialogFragment() {

    override fun onResume() {
        super.onResume()
        dialog.wrap()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup(view)
        observeViewModel()
        development { dev() }
    }

    abstract fun setup(view: View)

    protected open fun observeViewModel(): Unit = Unit

    protected open fun dev(): Unit = Unit
}