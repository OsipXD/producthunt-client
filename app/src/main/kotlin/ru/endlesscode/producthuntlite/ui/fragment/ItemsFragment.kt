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
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import ru.endlesscode.producthuntlite.mvp.model.Item
import ru.endlesscode.producthuntlite.mvp.presenter.ItemsPresenter
import ru.endlesscode.producthuntlite.mvp.view.ItemsView
import ru.endlesscode.producthuntlite.ui.addThresholdListener
import ru.endlesscode.producthuntlite.ui.common.InfiniteScrollListener
import ru.endlesscode.producthuntlite.ui.inflate


abstract class ItemsFragment<TPresenter : ItemsPresenter<out Item>> : MvpAppCompatFragment(), ItemsView {

    abstract var presenter: TPresenter

    private lateinit var scrollListener: InfiniteScrollListener
    protected abstract val layoutId: Int
    protected abstract val itemsRefresh: SwipeRefreshLayout
    protected abstract val toolbar: Toolbar
    protected abstract val itemsListId: Int
    protected lateinit var itemsList: RecyclerView

    abstract val title: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = container?.inflate(layoutId) ?: return null
        itemsList = view.findViewById(itemsListId)
        itemsList.init()

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        itemsRefresh.setOnRefreshListener {
            Log.d("onRefreshListener", "refreshing items")
            presenter.refresh()
        }
        toolbar.title = title

        presenter.onActivityCreated()
    }

    private fun RecyclerView.init() {
        this.setHasFixedSize(true)
        this.layoutManager = LinearLayoutManager(this@ItemsFragment.context)

        if (adapter == null) {
            adapter = createAdapter()
        }

        scrollListener = this.addThresholdListener {
            Log.d("onScrollListener", "requesting items")
            val requestCount = (adapter.itemCount / 2).coerceAtMost(50)
            presenter.requestItems(requestCount)
        }
    }

    abstract fun createAdapter(): RecyclerView.Adapter<*>?

    override fun onAddedItems(position: Int, count: Int) {
        itemsList.adapter.notifyItemRangeInserted(position, count)
        scrollListener.onUpdate()
    }

    override fun onItemsClear() {
        itemsList.adapter.notifyDataSetChanged()
    }

    override fun onStartLoading() {
        itemsRefresh.isRefreshing = true
    }

    override fun onEndLoading() {
        itemsRefresh.isRefreshing = false
    }
}
