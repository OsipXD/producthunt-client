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

package ru.endlesscode.producthuntlite.mvp.common

import retrofit2.Call
import ru.endlesscode.producthuntlite.api.ListWrapper

interface ItemList<T> {
    val items: MutableList<T>

    var isInLoading: Boolean

    val count
        get() = items.size

    fun refresh() {
        loadItems(append = false)
    }

    fun loadItems(append: Boolean = true)

    fun requestItems()

    fun addItems(topics: List<T>, append: Boolean = true) {
        if (!append) {
            this.items.clear()
        }

        this.items.addAll(topics)
        isInLoading = false
    }

    fun onBindItemAtPosition(position: Int, holder: DataHolder<T>) {
        holder.setData(items[position])
    }

    fun getApiCall(before: Int? = null): Call<out ListWrapper<T>>
}
