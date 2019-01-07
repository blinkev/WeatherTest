package com.blinkev.weathertest.ui.util

import android.content.res.Resources

class ResourceProviderImpl(private val res: Resources) : ResourceProvider {

    override fun getString(resId: Int): String = res.getString(resId)
}