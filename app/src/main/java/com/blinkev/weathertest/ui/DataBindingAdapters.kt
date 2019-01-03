package com.blinkev.weathertest.ui

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.Keep
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@Keep
@BindingAdapter(value = ["imageUrl", "placeholderRes", "errorRes"], requireAll = false)
fun ImageView.setImageUrl(imageUrl: String?, placeholderRes: Drawable?, errorRes: Drawable?) {
    val options = RequestOptions().apply {
        placeholder(placeholderRes)
        error(errorRes)
    }
    Glide.with(context).load(imageUrl).apply(options).into(this)
}