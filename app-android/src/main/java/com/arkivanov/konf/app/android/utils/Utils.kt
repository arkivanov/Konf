package com.arkivanov.konf.app.android.utils

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Outline
import android.os.Build
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.ImageView
import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arkivanov.konf.app.android.App
import com.arkivanov.konf.app.android.R
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

private var timeFormatCache = ProvidableCache<Locale, DateFormat> { SimpleDateFormat("h:mm", it) }
private var dateFormatCache = ProvidableCache<Locale, DateFormat> { SimpleDateFormat("E dd", it) }
private var timeZoneCache = ProvidableCache<String, TimeZone>(TimeZone::getTimeZone)

private fun getTimeFormat(context: Context, timeZone: String): DateFormat {
    val timeFormat = timeFormatCache[context.resources.configuration.getLocaleCompat()]
    timeFormat.timeZone = timeZoneCache[timeZone]

    return timeFormat
}

private fun getDateFormat(context: Context, timeZone: String): DateFormat {
    val dateFormat = dateFormatCache[context.resources.configuration.getLocaleCompat()]
    dateFormat.timeZone = timeZoneCache[timeZone]

    return dateFormat
}

fun formatTime(context: Context, timeZone: String, millis: Long): String =
    getTimeFormat(context = context, timeZone = timeZone).format(Date(millis))

fun formatDate(context: Context, timeZone: String, millis: Long): String =
    getDateFormat(context = context, timeZone = timeZone).format(Date(millis))

fun <T : Any> T?.requireNotNull(): T = requireNotNull(this)

fun <T : View> View.requireViewWithId(@IdRes id: Int): T = findViewById<T>(id).requireNotNull()

fun <T> RecyclerView.Adapter<*>.dispatchUpdates(oldItems: List<T>, newItems: List<T>, areItemsTheSame: (T, T) -> Boolean) {
    DiffUtil
        .calculateDiff(DiffUtilCallback(oldItems = oldItems, newItems = newItems, areItemsTheSame = areItemsTheSame))
        .dispatchUpdatesTo(this)
}

val Context.app: App get() = this.applicationContext as App

val Context.layoutInflater: LayoutInflater get() = LayoutInflater.from(this)

fun Resources.dpToPx(dp: Float): Float = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics)

@Suppress("DEPRECATION")
fun Configuration.getLocaleCompat(): Locale =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) locales[0] else locale

inline fun FragmentManager.transaction(block: FragmentTransaction.() -> Unit) {
    beginTransaction()
        .apply(block)
        .commit()
}

fun FragmentTransaction.setDefaultFragmentAnimations() {
    setCustomAnimations(R.anim.slide_fade_in_bottom, R.anim.scale_fade_out, R.anim.scale_fade_in, R.anim.slide_fade_out_bottom)
}

fun ImageView.clipToCircle() {
    outlineProvider =
        object : ViewOutlineProvider() {
            override fun getOutline(view: View, outline: Outline) {
                outline.setOval(0, 0, view.width, view.height)
            }
        }

    clipToOutline = true
}
