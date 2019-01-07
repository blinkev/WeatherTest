package com.blinkev.weathertest.ui.util

interface ResourceProvider {
    fun getString(resId: Int): String
}