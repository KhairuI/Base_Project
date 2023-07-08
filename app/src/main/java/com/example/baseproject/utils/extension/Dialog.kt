package com.example.baseproject.utils.extension

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.WindowManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.baseproject.ui.base.BaseDialogFragment
import com.example.baseproject.ui.base.BaseFragment
import com.example.baseproject.R
import com.example.baseproject.ui.base.BaseActivity

fun Dialog?.fullscreen() = this?.window?.apply {
    setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    setLayout(
        /* width = */ WindowManager.LayoutParams.MATCH_PARENT,
        /* height = */ WindowManager.LayoutParams.MATCH_PARENT
    )
}

fun Dialog?.wrap() = this?.window?.apply {
    setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    setLayout(
        /* width = */ 9 * context.resources.displayMetrics.widthPixels / 10,
        /* height = */ WindowManager.LayoutParams.WRAP_CONTENT
    )
}

fun Dialog?.ration(width: Double, height: Double) = this?.window?.apply {
    setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    setLayout(
        context.resources.displayMetrics.widthPixels.times(width).toInt(),
        context.resources.displayMetrics.heightPixels.times(height).toInt()
    )
}

fun Dialog?.hideProgress() {
    this?.let { dialog -> if (dialog.isShowing) dialog.cancel() }
}

fun Activity.showProgress(): Dialog? {
    if (isFinishing) return null

    val dialog = Dialog(this)
    dialog.show()
    dialog.window?.setBackgroundDrawableResource(R.color.transparent)
    dialog.setContentView(R.layout.progress_dialog)
    dialog.setCancelable(false)
    dialog.setCanceledOnTouchOutside(false)
    return dialog
}

fun BaseActivity.loading(isLoading: Boolean = true) {
    when {
        isLoading -> {
            progress.hideProgress()
            progress = showProgress()
        }

        else -> progress.hideProgress()
    }
}

fun BaseFragment.loading(isLoading: Boolean) {
    (requireActivity() as? BaseActivity)?.loading(isLoading)
}

fun BaseDialogFragment.loading(isLoading: Boolean) {
    (requireActivity() as? BaseActivity)?.loading(isLoading)
}

fun SwipeRefreshLayout.loading(isLoading: Boolean) {
    isRefreshing = isLoading
}