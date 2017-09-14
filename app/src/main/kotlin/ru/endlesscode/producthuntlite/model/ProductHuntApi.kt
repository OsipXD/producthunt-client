package ru.endlesscode.producthuntlite.model

import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.httpGet
import ru.endlesscode.producthuntlite.asJsonObject
import ru.endlesscode.producthuntlite.getAsList

object ProductHuntApi {
    private val API_URL = "https://api.producthunt.com/v1"
    private val ACCESS_TOKEN = "591f99547f569b05ba7d8777e2e0824eea16c440292cce1f8dfb3952cc9937ff"

    init {
        FuelManager.instance.basePath = API_URL
    }

    fun getCategories(): List<CategoryData> {
        val (_, _, result) = route("/categories").responseString()
        val (data, _) = result

        val categories = mutableListOf<CategoryData>()
        if (data != null) {
            categories.addAll(data.asJsonObject().getAsList("categories", CategoryData::class))
        }

        return categories
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