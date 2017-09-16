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

package ru.endlesscode.producthuntlite.ui.fragment

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.topics_fragment.*
import ru.endlesscode.producthuntlite.R
import ru.endlesscode.producthuntlite.addOnScrollListener
import ru.endlesscode.producthuntlite.inflate
import ru.endlesscode.producthuntlite.mvp.common.Item
import ru.endlesscode.producthuntlite.mvp.presenter.TopicsPresenter
import ru.endlesscode.producthuntlite.mvp.view.ItemsView
import ru.endlesscode.producthuntlite.ui.adapter.TopicsAdapter
import ru.endlesscode.producthuntlite.ui.common.InfiniteScrollListener

class ItemsFragment : MvpAppCompatFragment(), ItemsView {

    @InjectPresenter
    lateinit var presenter: TopicsPresenter

    private lateinit var scrollListener: InfiniteScrollListener

    private val topicsRefresh by lazy { topics_refresh }
    private val topicList by lazy {
        topics_list.setHasFixedSize(true)
        topics_list
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            container?.inflate(R.layout.topics_fragment)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        topicList.init()
        topicsRefresh.setOnRefreshListener { presenter.refresh() }
    }

    private fun RecyclerView.init() {
        this.layoutManager = LinearLayoutManager(this@ItemsFragment.context)

        val dividerItemDecoration = DividerItemDecoration(topics_list.context,
                (layoutManager as LinearLayoutManager).orientation)
        this.addItemDecoration(dividerItemDecoration)

        if (adapter == null) {
            adapter = TopicsAdapter(presenter)
        }

        scrollListener = this.addOnScrollListener {
            presenter.requestItems()
        }
    }

    override fun updateView() {
        topicList.adapter.notifyDataSetChanged()
        scrollListener.onUpdate()
    }

    override fun onStartRefreshing() {
        topicsRefresh.isRefreshing = true
    }

    override fun onEndRefreshing() {
        topicsRefresh.isRefreshing = false
    }

    override fun openItem(item: Item) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}