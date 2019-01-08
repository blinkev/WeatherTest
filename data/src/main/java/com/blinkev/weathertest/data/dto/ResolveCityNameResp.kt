package com.blinkev.weathertest.data.dto

import com.google.gson.annotations.SerializedName

data class ResolveCityNameResp(@SerializedName("search_api") val searchApi: SearchApi) {

    data class SearchApi(val result: List<Result>) {
        companion object
    }

    data class Result(val region: List<StringValueDto>) {
        companion object
    }

    data class StringValueDto(val value: String) {
        companion object
    }

    companion object
}