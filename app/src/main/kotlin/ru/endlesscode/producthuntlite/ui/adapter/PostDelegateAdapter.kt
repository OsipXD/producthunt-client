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

package ru.endlesscode.producthuntlite.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.post_item.view.*
import ru.endlesscode.producthuntlite.R
import ru.endlesscode.producthuntlite.api.PostData
import ru.endlesscode.producthuntlite.load
import ru.endlesscode.producthuntlite.ui.common.ViewTypeDelegateAdapter
import ru.endlesscode.producthuntlite.ui.common.ViewTypeHolder

class PostDelegateAdapter : ViewTypeDelegateAdapter<PostData> {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = PostViewHolder(parent)

    inner class PostViewHolder(parent: ViewGroup) : ViewTypeHolder<PostData>(parent, R.layout.post_item) {
        private var title: TextView = itemView.post_title
        private var desc: TextView = itemView.post_desc
        private var votes: TextView = itemView.post_votes
        private var thumbnail: ImageView = itemView.post_thumbnail

        override fun bind(data: PostData) {
            title.text = data.name
            desc.text = data.desc
            votes.text = data.votesCount.toString()
            thumbnail.load(data.thumbnailUrl)
        }
    }
}
