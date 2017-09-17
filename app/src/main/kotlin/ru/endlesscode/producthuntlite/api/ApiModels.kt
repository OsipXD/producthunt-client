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
import ru.endlesscode.producthuntlite.mvp.model.Post
import ru.endlesscode.producthuntlite.mvp.model.Screenshot
import ru.endlesscode.producthuntlite.mvp.model.Thumbnail
import ru.endlesscode.producthuntlite.mvp.model.Topic

interface ListResponse<out T> {
    fun get(): List<T>
}

class TopicsResponse(val topics: List<TopicData>) : ListResponse<TopicData> {
    override fun get(): List<TopicData> = topics
    override fun toString(): String = topics.toString()
}

class PostsResponse(val posts: List<PostData>) : ListResponse<PostData> {
    override fun get(): List<PostData> = posts
    override fun toString(): String = posts.toString()
}

data class TopicData(
        override val id: Int,
        override val name: String,
        override val slug: String,
        override val description: String,
        override val image: String?
) : Topic

data class PostData(
        override val id: Int,
        override val name: String,
        override val thumbnail: ThumbnailData,
        @SerializedName("redirect_url")
        override val redirectUrl: String,
        @SerializedName("screenshot_url")
        override val screenshot: ScreenshotData,
        @SerializedName("tagline")
        override val desc: String,
        @SerializedName("votes_count")
        override val votesCount: Int
) : Post

data class ScreenshotData(
        @SerializedName("300px")
        override val px300: String,
        @SerializedName("850px")
        override val px850: String
) : Screenshot

data class ThumbnailData(
        @SerializedName("image_url")
        override val url: String
) : Thumbnail