/*
 * This file is part of ProductHuntLite, licensed under the MIT License (MIT).
 *
 * Copyright (c) Osip Fatkullin <osip.fatkullin@gmail.com>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package ru.endlesscode.producthuntlite.api

import com.google.gson.annotations.SerializedName
import ru.endlesscode.producthuntlite.mvp.common.Item
import java.io.Serializable

interface ListWrapper<out T> {
    fun get(): List<T>
}

class TopicsResponse(val topics: List<TopicData>) : ListWrapper<TopicData> {

    override fun get(): List<TopicData> = topics

    override fun toString(): String = topics.toString()
}

data class TopicData(
        override val id: Int,
        override val name: String,
        val slug: String,
        val description: String,
        val image: String?
) : Item, Serializable

class PostsResponse(val posts: List<PostData>) : ListWrapper<PostData> {

    override fun get(): List<PostData> = posts

    override fun toString(): String = posts.toString()
}

data class PostData(
        override val id: Int,
        override val name: String,
        val day: String,
        val thumbnail: ThumbnailData,
        @SerializedName("screenshot_url") val screenshotUrl: ScreenshotUrl,
        @SerializedName("tagline") val desc: String,
        @SerializedName("category_id") val categoryId: Int,
        @SerializedName("votes_count") val votesCount: Int
) : Item, Serializable {
    val thumbnailUrl: String
        get() = thumbnail.imageUrl
}

data class ScreenshotUrl(
        @SerializedName("300px") val px300: String,
        @SerializedName("850px") val px850: String
) : Serializable

data class ThumbnailData(
        val id: Int,
        @SerializedName("image_url") val imageUrl: String
) : Serializable