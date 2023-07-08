package com.example.baseproject.utils.extension

import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.baseproject.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView

internal fun View.gone() {
    visibility = View.GONE
}

internal fun View.invisible() {
    visibility = View.INVISIBLE
}

internal fun View.visible() {
    visibility = View.VISIBLE
}

internal fun View.visible(isShow: Boolean) {
    visibility = if (isShow) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

internal fun View.show(isShow: Boolean) = when {
    isShow -> visible()
    else -> gone()
}

internal fun MaterialTextView.showText(textToShow: String?) = when (textToShow) {
    null -> gone()
    else -> visible().also { text = textToShow }
}

internal fun MaterialTextView.textColor(@ColorRes color: Int) {
    setTextColor(ContextCompat.getColor(context, color))
}

internal fun MaterialButton.icon(@DrawableRes res: Int) {
    setCompoundDrawablesWithIntrinsicBounds(0, 0, res, 0)
}

internal fun MaterialButton.iconLeft(@DrawableRes res: Int) {
    setCompoundDrawablesWithIntrinsicBounds(res, 0, 0, 0)
}

internal fun SwipeRefreshLayout.clicked(
    block: () -> Unit
) {
    val p1 = ContextCompat.getColor(context, R.color.p1)
    val p2 = ContextCompat.getColor(context, R.color.p2)
    with(this) {
        setColorSchemeColors(p1, p2)
        setOnRefreshListener { block() }
    }
}