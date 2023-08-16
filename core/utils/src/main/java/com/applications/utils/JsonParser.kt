package com.applications.utils

import com.google.gson.Gson

object JsonParser {

    inline fun <reified T> parseJson(response: String) = Gson().fromJson(response, T::class.java)

    fun toJson(data: Any): String = Gson().toJson(data)
}