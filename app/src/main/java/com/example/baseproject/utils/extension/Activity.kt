@file:Suppress("DEPRECATION")

package com.example.baseproject.utils.extension

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.baseproject.R
import com.example.baseproject.utils.AppConstants
import com.github.drjacky.imagepicker.ImagePicker
import java.io.Serializable

inline fun <reified T : Serializable> Bundle.serializable(key: String, m_class: Class<T>): T? =
    when {
        Build.VERSION.SDK_INT >= 33 -> getSerializable(key, m_class)
        else -> @Suppress("DEPRECATION") getSerializable(key) as? T
    }

inline fun <reified T : Serializable> Intent.serializable(key: String, m_class: Class<T>): T? =
    when {
        Build.VERSION.SDK_INT >= 33 -> getSerializableExtra(key, m_class)
        else -> @Suppress("DEPRECATION") getSerializableExtra(key) as? T
    }

fun <T : Serializable?> getSerializable(intent: Intent, key: String, m_class: Class<T>): T? = try {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        intent.getSerializableExtra(key, m_class)
    } else {
        intent.getSerializableExtra(key) as? T
    }
} catch (e: Exception) {
    e.printStackTrace()
    null
}

fun Activity.gallery(): Intent {
    val size = AppConstants.MAX_IMAGE_SIZE
    return ImagePicker.with(this).crop().galleryMimeTypes(AppConstants.MIME_TYPE).galleryOnly()
        .maxResultSize(size, size)
        .setImageProviderInterceptor { imageProvider -> Log.d("imageProvider", imageProvider.name) }
        .createIntent()
}

fun Activity.camera(crop: Pair<Float, Float>? = null): Intent {
    val size = AppConstants.MAX_IMAGE_SIZE
    val picker = ImagePicker.with(this)
    if (crop != null) {
        picker.crop(crop.first, crop.second)
    } else {
        picker.crop()
    }
    return picker.galleryMimeTypes(AppConstants.MIME_TYPE)
        .cameraOnly()
        .maxResultSize(size, size)
        .setImageProviderInterceptor { imageProvider -> Log.d("imageProvider", imageProvider.name) }
        .createIntent()
}

/*fun Activity.showStatusBar() {
    WindowInsetsControllerCompat(window, window.decorView.findViewById(R.id.content)).show(
        WindowInsetsCompat.Type.statusBars()
    )
}

fun Activity.setStatusBarColor(
    @ColorRes colorRes: Int,
    lightBackground: Boolean = false,
) {
    window.statusBarColor = ContextCompat.getColor(this, colorRes)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        WindowInsetsControllerCompat(
            window, window.decorView.findViewById(R.id.content)
        ).isAppearanceLightStatusBars = lightBackground
    }
}*/

val Activity.screenSize: DisplayMetrics
    get() {
        val display = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            display
        } else {
            windowManager.defaultDisplay
        }
        val metrics = DisplayMetrics()
        display?.getRealMetrics(metrics)
        return metrics
    }

fun Activity.mapDirection(
    originLat: Double,
    originLng: Double,
    destinationLat: Double,
    destinationLng: Double
) {
    try {
        val uri =
            Uri.parse("http://maps.google.com/maps?saddr=${originLat},${originLng}&daddr=${destinationLat},${destinationLng}")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    } catch (e: Exception) {
        e.printStackTrace()
    }

}