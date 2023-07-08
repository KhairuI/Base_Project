@file:Suppress("DEPRECATION")

package com.example.baseproject.utils.extension

import android.content.Context
import android.graphics.Typeface
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.util.Base64
import android.util.Base64OutputStream
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
import android.view.WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.google.gson.GsonBuilder
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.Serializable
import java.text.DecimalFormat

fun AppCompatActivity.onBackAction(block: () -> Unit) {
    onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() = block()
    })
}

fun String.parentheses(text: String): String = when (text.isEmpty()) {
    true -> this
    else -> "$this($text)"
}

fun Double?.tk(): String = ("${(this ?: 0.0).toNonDecString()} ৳")

fun Int.toDecString(): String {
    return DecimalFormat("##,##,###").format(this)
}

fun Double.toDecString(): String {
    return DecimalFormat("##,##,###.##").format(this)
}

fun Double.toNonDecString(): String {
    return DecimalFormat("##,##,###").format(this)
}

fun Double.toAmountDecString(): String {
    return DecimalFormat("৳##,##,###.##").format(this)
}

fun Double.toAmountNonDecString(): String {
    return DecimalFormat("৳##,##,###").format(this)
}

fun AppCompatActivity.bundleBoolean(key: String) = this.intent.extras?.getBoolean(key) ?: false
fun AppCompatActivity.bundleDouble(key: String) = this.intent.extras?.getDouble(key)
fun AppCompatActivity.bundleString(key: String) = this.intent.extras?.getString(key)
fun AppCompatActivity.bundleStringNullable(key: String): String? =
    when (this.intent.extras?.getString(key)) {
        null -> null
        else -> this.intent.extras?.getString(key)
    }

fun AppCompatActivity.bundleIntNullable(key: String) = when (this.intent.extras?.getInt(key)) {
    0 -> null
    else -> this.intent.extras?.getInt(key)
}

fun AppCompatActivity.bundleInt(key: String): Int = this.intent.extras?.getInt(key) ?: 0

@Suppress("UNCHECKED_CAST")
inline fun <reified T : Any> Serializable.checkSerializableIsListOf() =
    if (this is List<*> && this.all { it is T }) this as List<T>
    else null

@Suppress("UNCHECKED_CAST")
inline fun <reified T : Any> List<*>.checkItemsAre() = if (all { it is T }) this as List<T>
else null

fun Any.toJson(): String? {
    return try {
        val builder = GsonBuilder().excludeFieldsWithoutExposeAnnotation()
        val gson = builder.setLenient().serializeNulls().create()

        gson.toJson(this)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun File.toBase64(): String {
    return ByteArrayOutputStream().use { outputStream ->
        Base64OutputStream(outputStream, Base64.NO_WRAP).use { base64FilterStream ->
            this.inputStream().use { inputStream ->
                inputStream.copyTo(base64FilterStream)
            }
        }
        return@use outputStream.toString()
    }
}

fun View.fontSupport(context: Context) {
    try {
        if (this is ViewGroup) {
            for (i in 0 until this.childCount) {
                val child: View = this.getChildAt(i)
                child.fontSupport(context)
            }
        } else if (this is TextView) {
            val typeface = Typeface.createFromAsset(
                context.assets, "fonts/source-sans-pro/SourceSansPro-Regular.ttf"
            )
            this.typeface = typeface
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun String.toBnString(): String {
    val bngNumber = arrayOf('০', '১', '২', '৩', '৪', '৫', '৬', '৭', '৮', '৯')
    val engNumber = arrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')

    val values = StringBuilder()
    val character = this
    for (value in character) {
        var c = ' '
        for (j in engNumber.indices) {
            if (value == engNumber[j]) {
                c = bngNumber[j]
                break
            } else {
                c = value
            }
        }
        values.append(c)
    }
    return values.toString()
}

fun String.toEnString(): String {
    val eng = arrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')
    val bng = arrayOf('০', '১', '২', '৩', '৪', '৫', '৬', '৭', '৮', '৯')

    val values = StringBuilder()
    val character = this
    for (value in character) {
        var c = ' '
        for (j in bng.indices) {
            if (value == bng[j]) {
                c = eng[j]
                break
            } else {
                c = value
            }
        }
        values.append(c)
    }
    return values.toString()
}

fun String.toSpanned(): Spanned {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        return Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
        @Suppress("DEPRECATION") return Html.fromHtml(this)
    }
}

fun String.toSpannedLink(): Spanned {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        return Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
        @Suppress("DEPRECATION") return Html.fromHtml(this)
    }
}

fun Context.showToast(
    text: CharSequence, duration: Int = Toast.LENGTH_SHORT
) {
    Toast.makeText(this, text, duration).show()
}

inline fun <T> justTry(block: () -> T) = try {
    block()
} catch (e: Throwable) {
    e.printStackTrace()
}

fun AppCompatActivity.hideToolbar() {
    supportActionBar?.hide()
}

fun AppCompatActivity.showToolbar() {
    supportActionBar?.show()
}

fun AppCompatActivity.adjustPan() = window.setSoftInputMode(SOFT_INPUT_ADJUST_PAN)
fun AppCompatActivity.adjustResize() = window.setSoftInputMode(SOFT_INPUT_ADJUST_RESIZE)

inline fun AppCompatActivity.transact(action: FragmentTransaction.() -> Unit) {
    supportFragmentManager.beginTransaction().apply { action() }.commit()
}