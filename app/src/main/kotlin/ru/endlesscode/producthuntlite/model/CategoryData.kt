package ru.endlesscode.producthuntlite.model

import com.google.gson.annotations.SerializedName

data class CategoryData(
        val id: Int,
        val slug: String,
        val name: String,
        val color: String,
        @SerializedName("item_name") val itemName: String
)