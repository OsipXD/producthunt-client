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
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.run
import ru.endlesscode.producthuntlite.api.ProductHunt
import ru.endlesscode.producthuntlite.api.TopicData
import ru.endlesscode.producthuntlite.mvp.view.TopicsView
import ru.endlesscode.producthuntlite.ui.adapter.TopicViewHolder
import ru.gildor.coroutines.retrofit.awaitResult
import ru.gildor.coroutines.retrofit.getOrThrow

@InjectViewState
class TopicsPresenter : MvpPresenter<TopicsView>() {

    private val topics = mutableListOf<TopicData>()
    private var isInLoading = false

    val topicsCount: Int
        get() = topics.size

    override fun onFirstViewAttach() {
        this.loadTopics()
    }

    private fun loadTopics() {
        if (isInLoading) return
        isInLoading = true

        viewState.onStartLoading()

        launch(CommonPool) {
            val result = ProductHunt.api.getTopics().awaitResult()
            val topics = result.getOrThrow().topics
            addTopics(topics)

            run(UI) {
                viewState.updateView()
            }

            onFinishLoading()
        }
    }

    private fun addTopics(topics: List<TopicData>) {
        this.topics.addAll(topics)
    }

    private fun onFinishLoading() {
        if (!isInLoading) return
        isInLoading = false

        viewState.onEndLoading()
    }

    fun onBindTopicAtPosition(position: Int, holder: TopicViewHolder) {
        holder.setData(topics[position])
    }
}