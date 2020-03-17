package com.arkivanov.konf.app.android.utils

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arkivanov.konf.app.android.App
import java.util.Locale

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
