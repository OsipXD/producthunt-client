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

package ru.endlesscode.producthuntlite.mvp.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import ru.endlesscode.producthuntlite.api.ProductHunt
import ru.endlesscode.producthuntlite.api.TopicData
import ru.endlesscode.producthuntlite.async
import ru.endlesscode.producthuntlite.mvp.view.TopicsView
import ru.endlesscode.producthuntlite.ui.adapter.TopicViewHolder

@InjectViewState
class TopicsPresenter : MvpPresenter<TopicsView>() {

    private val topics = mutableListOf<TopicData>()
    private var isInLoading = false

    val topicsCount: Int
        get() = topics.size

    override fun onFirstViewAttach() {
        this.loadTopics()
    }

    fun refreshPosts() {
        loadTopics(append = false)
    }

    private fun loadTopics(append: Boolean = true) {
        if (isInLoading) return
        isInLoading = true

        viewState.onStartRefreshing()
        ProductHunt.api.getTopics().async { topics ->
            addTopics(topics, append)
            viewState.updateView()
            viewState.onEndRefreshing()
        }
    }

    fun requestTopics() {
        if (isInLoading) return
        isInLoading = true

        ProductHunt.api.getTopics(before = topics.last().id).async { topics ->
            addTopics(topics)
            viewState.updateView()
        }
    }

    private fun addTopics(topics: List<TopicData>, append: Boolean = true) {
        if (!append) {
            this.topics.clear()
        }

        this.topics.addAll(topics)
        isInLoading = false
    }

    fun onBindTopicAtPosition(position: Int, holder: TopicViewHolder) {
        holder.setData(topics[position])
    }
}