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
import ru.endlesscode.producthuntlite.mvp.doInBackground
import ru.endlesscode.producthuntlite.mvp.model.Item
import ru.endlesscode.producthuntlite.mvp.model.ItemHolder
import ru.endlesscode.producthuntlite.mvp.model.ItemList
import ru.endlesscode.producthuntlite.mvp.view.ItemsView

abstract class ItemsPresenter<TItem : Item> : MvpPresenter<ItemsView>(), ItemList<TItem> {

    override val items: MutableList<TItem> = mutableListOf()
    override var isInLoading = false

    fun onActivityCreated() {
        loadItems()
    }

    override fun loadItems(append: Boolean) {
        if (isInLoading) return
        isInLoading = true

        viewState.onStartLoading()
        getApiCall().doInBackground { itemsData ->
            val newItems = itemsData.map { createItem(it) }
            addItems(newItems, append)
            if (append) {
                viewState.onAddedItems(items.size - newItems.size, newItems.size)
            } else {
                viewState.onItemsClear()
            }
            viewState.onEndLoading()
        }
    }

    abstract fun createItem(item: TItem): TItem

    override fun requestItems(count: Int) {
        if (isInLoading) return
        isInLoading = true

        getApiCall(before = items.last().id, count = count).doInBackground { itemsData ->
            val newItems = itemsData.map { createItem(it) }
            val insertPosition = items.lastIndex + 1
            addItems(newItems)
            viewState.onAddedItems(insertPosition, newItems.size)
        }
    }

    fun itemClicked(holder: ItemHolder<TItem>) {
        viewState.openItem(holder.item)
    }
}