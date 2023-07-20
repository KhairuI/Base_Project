package com.example.baseproject.utils.extension

import android.content.Context
import android.widget.Toast
import com.example.baseproject.ui.base.BaseDialogFragment
import com.example.baseproject.ui.base.BaseFragment
import com.example.baseproject.utils.arch.UiText
import es.dmoral.toasty.Toasty

fun Context.getResString(uiText: UiText): String = uiText.asString(this)

fun Context.normal(uiText: UiText) {
    Toasty.normal(this, getResString(uiText), Toast.LENGTH_LONG).show()
}

fun Context.success(uiText: UiText) {
    Toasty.success(this, getResString(uiText), Toast.LENGTH_SHORT, true).show()
}

fun Context.error(uiText: UiText) {
    Toasty.error(this, getResString(uiText), Toast.LENGTH_SHORT, true).show()
}

fun Context.warning(uiText: UiText) {
    Toasty.warning(this, getResString(uiText), Toast.LENGTH_SHORT, true).show()
}

fun BaseDialogFragment.getResString(uiText: UiText): String = requireContext().getResString(uiText)

fun BaseDialogFragment.normal(uiText: UiText) = requireContext().normal(uiText)

fun BaseDialogFragment.success(uiText: UiText) = requireContext().success(uiText)

fun BaseDialogFragment.error(uiText: UiText) = requireContext().error(uiText)

fun BaseDialogFragment.warning(uiText: UiText) = requireContext().warning(uiText)

fun BaseFragment.getResString(uiText: UiText): String = requireContext().getResString(uiText)

fun BaseFragment.normal(uiText: UiText) = requireContext().normal(uiText)

fun BaseFragment.success(uiText: UiText) = requireContext().success(uiText)

fun BaseFragment.error(uiText: UiText) = requireContext().error(uiText)

fun BaseFragment.warning(uiText: UiText) = requireContext().warning(uiText)