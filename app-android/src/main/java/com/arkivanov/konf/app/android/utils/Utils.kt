package com.arkivanov.konf.app.android.utils

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Outline
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.ImageView
import androidx.annotation.AttrRes
import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arkivanov.konf.app.android.App
import com.arkivanov.konf.app.android.R
import com.squareup.picasso.Picasso
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

private fun Resources.Theme.resolveAttribute(@AttrRes attrId: Int): TypedValue? =
    TypedValue().takeIf { resolveAttribute(attrId, it, true) }

fun ImageView.loadImage(url: String?) {
    loadImage(url = url, placeholder = null)
}

fun ImageView.loadImage(url: String?, @AttrRes placeholderAttrId: Int) {
    loadImage(
        url = url,
        placeholder = context.theme.resolveAttribute(attrId = placeholderAttrId)?.resourceId?.let(context::getDrawable)
    )
}

fun ImageView.loadImage(url: String?, placeholder: Drawable?) {
    Picasso
        .get()
        .load(url)
        .run { if (placeholder != null) placeholder(placeholder) else this }
        .into(this)
}
