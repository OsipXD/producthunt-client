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

import com.arellomobile.mvp.MvpPresenter
import ru.endlesscode.producthuntlite.doInBackground
import ru.endlesscode.producthuntlite.mvp.common.Item
import ru.endlesscode.producthuntlite.mvp.common.ItemList
import ru.endlesscode.producthuntlite.mvp.view.ItemsView

abstract class ItemsPresenter<TItem : Item> : MvpPresenter<ItemsView>(), ItemList<TItem> {

    override val items: MutableList<TItem> = mutableListOf()
    override var isInLoading = false

    override fun onFirstViewAttach() {
        loadItems()
    }

    override fun loadItems(append: Boolean) {
        if (isInLoading) return
        isInLoading = true

        viewState.onStartRefreshing()
        getApiCall().doInBackground { items ->
            addItems(items, append)
            viewState.updateView()
            viewState.onEndRefreshing()
        }
    }

    override fun requestItems() {
        if (isInLoading) return
        isInLoading = true

        getApiCall(before = items.last().id).doInBackground { items ->
            addItems(items)
            viewState.updateView()
        }
    }
}