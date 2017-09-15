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

package ru.endlesscode.producthuntlite.model.topics

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import kotlinx.android.synthetic.main.topic_item.view.*
import ru.endlesscode.producthuntlite.R
import ru.endlesscode.producthuntlite.api.TopicData
import ru.endlesscode.producthuntlite.common.ViewTypeDelegateAdapter
import ru.endlesscode.producthuntlite.common.ViewTypeHolder
import ru.endlesscode.producthuntlite.load

class TopicDelegateAdapter : ViewTypeDelegateAdapter<TopicData> {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = TopicViewHolder(parent)

    inner class TopicViewHolder(parent: ViewGroup) : ViewTypeHolder<TopicData>(parent, R.layout.topic_item) {
        private val name = itemView.topic_name
        private val desc = itemView.topic_desc
        private val icon = itemView.topic_icon

        override fun bind(data: TopicData) {
            name.text = data.name
            desc.text = data.description
            icon.load(data.image)
        }
    }
}