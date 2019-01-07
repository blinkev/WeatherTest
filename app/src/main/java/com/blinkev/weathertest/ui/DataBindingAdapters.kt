package com.blinkev.weathertest.ui

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.Keep
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@Keep
@BindingAdapter(value = ["imageUrl"])
fun ImageView.setImageUrl(imageUrl: String) {
    Glide.with(context).load(imageUrl).into(this)
}