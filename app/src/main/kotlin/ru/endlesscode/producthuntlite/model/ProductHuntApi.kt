package ru.endlesscode.producthuntlite.model

import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.httpGet

object ProductHuntApi {
    private val API_URL = "https://api.producthunt.com/v1"
    private val ACCESS_TOKEN = "591f99547f569b05ba7d8777e2e0824eea16c440292cce1f8dfb3952cc9937ff"

    init {
        FuelManager.instance.basePath = API_URL
    }

    fun requestCategories() {
        route("/categories").responseJson { request, response, result ->
            val (data, error) = result
            if (data != null) {
                data.array().
            }
        }
    }

    private fun route(value: String): Request {
        return value.httpGet().header(
                "Accept" to "application/json",
                "Content-Type" to "application/json",
                "Authorization" to "Bearer $ACCESS_TOKEN",
                "Host" to "api.producthunt.com"
        )
    }
}